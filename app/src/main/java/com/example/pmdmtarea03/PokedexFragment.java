package com.example.pmdmtarea03;

import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pmdmtarea03.databinding.FragmentPokedexBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Fragmento para mostrar la Pokédex con todos los Pokémon disponibles,
 * integrando datos desde una API externa y Firebase para gestionar los Pokémon capturados.
 */
public class PokedexFragment extends Fragment implements PokemonAdapter.OnPokemonCapturedListener {

    private FragmentPokedexBinding binding; // View Binding para acceder a las vistas del layout
    private PokemonAdapter adapter; // Adaptador para mostrar la lista de Pokémon en un RecyclerView
    private List<Pokemon> pokemonList = new ArrayList<>(); // Lista de todos los Pokémon obtenidos de la API
    private List<Pokemon> capturedPokemonList = new ArrayList<>(); // Lista de Pokémon capturados del usuario

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPokedexBinding.inflate(inflater, container, false);

        // Configurar el RecyclerView con un LinearLayoutManager y el adaptador
        binding.recyclerViewPokemon.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new PokemonAdapter(pokemonList, capturedPokemonList, this);
        binding.recyclerViewPokemon.setAdapter(adapter);

        // Recuperar los Pokémon capturados desde Firebase
        fetchCapturedPokemons();

        // Cargar la lista de Pokémon desde la API
        fetchPokemonList();

        return binding.getRoot(); // Retornar la vista raíz del fragmento
    }

    /**
     * Recupera la lista de Pokémon desde una API externa.
     */
    private void fetchPokemonList() {
        // Llamada a la API para obtener una lista de Pokémon
        RetrofitClient.getApiService().getPokemonList(0, 150).enqueue(new Callback<PokemonApiService.PokemonListResponse>() {
            @Override
            public void onResponse(Call<PokemonApiService.PokemonListResponse> call, Response<PokemonApiService.PokemonListResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Por cada resultado en la lista, recuperar los detalles del Pokémon
                    for (PokemonApiService.PokemonListResponse.Result result : response.body().getResults()) {
                        fetchPokemonDetails(result.getName());
                    }
                }
            }

            @Override
            public void onFailure(Call<PokemonApiService.PokemonListResponse> call, Throwable t) {
                Log.e("PokedexFragment", "Error al cargar la lista", t); // Manejo de errores en la llamada
            }
        });
    }

    /**
     * Recupera los detalles de un Pokémon específico desde la API.
     *
     * @param name Nombre del Pokémon del cual se quieren obtener los detalles.
     */
    private void fetchPokemonDetails(String name) {
        // Llamada a la API para obtener los detalles de un Pokémon
        RetrofitClient.getApiService().getPokemonDetails(name).enqueue(new Callback<Pokemon>() {
            @Override
            public void onResponse(Call<Pokemon> call, Response<Pokemon> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Agregar el Pokémon a la lista
                    pokemonList.add(response.body());

                    // Ordenar la lista por el atributo "order"
                    pokemonList.sort(Comparator.comparingInt(Pokemon::getOrder));

                    // Notificar al adaptador que los datos han cambiado
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<Pokemon> call, Throwable t) {
                Log.e("PokedexFragment", "Error al cargar detalles", t); // Manejo de errores en la llamada
            }
        });
    }

    /**
     * Recupera la lista de Pokémon capturados del usuario desde Firebase.
     */
    private void fetchCapturedPokemons() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String uid = auth.getCurrentUser().getUid(); // Obtener el ID del usuario actual

        // Recuperar la colección de Pokémon capturados del usuario
        db.collection("pokemons")
                .document(uid)
                .collection("userPokemons")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    capturedPokemonList.clear(); // Limpiar la lista actual de Pokémon capturados
                    for (DocumentSnapshot doc : queryDocumentSnapshots.getDocuments()) {
                        // Convertir cada documento a un objeto de tipo Pokémon
                        Pokemon pokemon = doc.toObject(Pokemon.class);
                        if (pokemon != null) {
                            capturedPokemonList.add(pokemon);
                        }
                    }
                    // Notificar al adaptador que los datos han cambiado
                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> Log.e("Firestore", "Error al recuperar Pokémon", e)); // Manejo de errores en Firebase
    }

    /**
     * Callback que se ejecuta cuando un Pokémon es capturado.
     * Recarga la lista de Pokémon capturados desde Firebase.
     */
    @Override
    public void onPokemonCaptured() {
        fetchCapturedPokemons(); // Volver a cargar los Pokémon capturados
    }
}
