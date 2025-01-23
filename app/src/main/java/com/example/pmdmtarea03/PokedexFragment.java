package com.example.pmdmtarea03;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pmdmtarea03.databinding.FragmentCapturedBinding;
import com.example.pmdmtarea03.databinding.FragmentPokedexBinding;
import com.example.pmdmtarea03.databinding.FragmentToolsBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PokedexFragment extends Fragment {

    private FragmentPokedexBinding binding;
    private FirebaseAuth mAuth;

    public PokedexFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance(); // Inicializar Firebase Auth
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inicializar View Binding
        binding = FragmentPokedexBinding.inflate(inflater, container, false);

        // Configuración del RecyclerView
        binding.recyclerViewPokemon.setLayoutManager(new LinearLayoutManager(getContext()));

        // Llamada a la API
        PokemonApiService apiService = RetrofitInstance.getRetrofitInstance().create(PokemonApiService.class);
        apiService.getPokemonList().enqueue(new Callback<PokemonListResponse>() {
            @Override
            public void onResponse(Call<PokemonListResponse> call, Response<PokemonListResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<PokemonResult> results = response.body().getResults();
                    List<Pokemon> pokemonList = new ArrayList<>();

                    for (PokemonResult result : results) {
                        String url = result.getUrl();
                        int id = extractIdFromUrl(url);

                        if (id == -1) {
                            continue; // Si no se puede extraer el ID, salta al siguiente Pokémon
                        }

                        // Crear un objeto Pokemon con los datos necesarios
                        Pokemon pokemon = new Pokemon();
                        pokemon.setId(id);
                        pokemon.setName(result.getName());
                        pokemon.setSprites(new Pokemon.Sprites());
                        pokemon.getSprites().setOther(new Pokemon.Sprites.Other());
                        pokemon.getSprites().getOther().setOfficialArtwork(new Pokemon.Sprites.Other.OfficialArtwork());

                        // Generar la URL de la imagen
                        String imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/" + id + ".png";
                        pokemon.getSprites().getOther().getOfficialArtwork().setFrontDefault(imageUrl);

                        // Añadir a la lista
                        pokemonList.add(pokemon);
                    }

                    // Configura el adaptador y lo asocia al RecyclerView
                    PokemonAdapter pokemonAdapter = new PokemonAdapter(pokemonList);
                    binding.recyclerViewPokemon.setAdapter(pokemonAdapter);
                }
            }

            @Override
            public void onFailure(Call<PokemonListResponse> call, Throwable t) {
                Log.e("Pokemon API", "Error al cargar datos", t);
            }
        });

        // Retornar la vista raíz del binding
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Liberar el binding para evitar memory leaks
    }

    private int extractIdFromUrl(String url) {
        try {
            // Divide la URL por "/"
            String[] parts = url.split("/");

            // Verifica si la URL tiene suficientes partes y devuelve el penúltimo elemento
            return Integer.parseInt(parts[parts.length - 1]); // El ID es el penúltimo segmento
        } catch (Exception e) {
            // Manejo de errores: imprime un mensaje y devuelve un ID por defecto
            Log.e("extractIdFromUrl", "Error al extraer el ID de la URL: " + url, e);
            return -1; // Devuelve -1 si ocurre un error
        }
    }
}
