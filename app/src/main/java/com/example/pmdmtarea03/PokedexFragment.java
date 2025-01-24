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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PokedexFragment extends Fragment implements PokemonAdapter.OnPokemonCapturedListener{

    private FragmentPokedexBinding binding;
    private PokemonAdapter adapter;
    private List<Pokemon> pokemonList = new ArrayList<>();

    private List<Pokemon> capturedPokemonList = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPokedexBinding.inflate(inflater, container, false);

        // Configurar RecyclerView
        binding.recyclerViewPokemon.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new PokemonAdapter(pokemonList, capturedPokemonList, (PokemonAdapter.OnPokemonCapturedListener) this);
        binding.recyclerViewPokemon.setAdapter(adapter);

        //recupera los pokemons capturados
        fetchCapturedPokemons();

        // Cargar datos
        fetchPokemonList();

        return binding.getRoot();
    }



    private void fetchPokemonList() {
        RetrofitClient.getApiService().getPokemonList(0, 150).enqueue(new Callback<PokemonApiService.PokemonListResponse>() {
            @Override
            public void onResponse(Call<PokemonApiService.PokemonListResponse> call, Response<PokemonApiService.PokemonListResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    for (PokemonApiService.PokemonListResponse.Result result : response.body().getResults()) {
                        fetchPokemonDetails(result.getName());
                    }
                }
            }

            @Override
            public void onFailure(Call<PokemonApiService.PokemonListResponse> call, Throwable t) {
                Log.e("PokedexFragment", "Error al cargar la lista", t);
            }
        });
    }

    private void fetchPokemonDetails(String name) {
        RetrofitClient.getApiService().getPokemonDetails(name).enqueue(new Callback<Pokemon>() {
            @Override
            public void onResponse(Call<Pokemon> call, Response<Pokemon> response) {
                if (response.isSuccessful() && response.body() != null) {
                    pokemonList.add(response.body());
                    pokemonList.sort(Comparator.comparingInt(Pokemon::getOrder));
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<Pokemon> call, Throwable t) {
                Log.e("PokedexFragment", "Error al cargar detalles", t);
            }
        });
    }

    private void fetchCapturedPokemons() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String uid = auth.getCurrentUser().getUid();

        db.collection("pokemons")
                .document(uid)
                .collection("userPokemons")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    capturedPokemonList.clear();
                    for (DocumentSnapshot doc : queryDocumentSnapshots.getDocuments()) {
                        Pokemon pokemon = doc.toObject(Pokemon.class);
                        if (pokemon != null) {
                            capturedPokemonList.add(pokemon);
                        }
                    }
                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> Log.e("Firestore", "Error al recuperar Pokémon", e));
    }

    @Override
    public void onPokemonCaptured() {
        // Recargar los datos de Pokémon capturados y notificar al adaptador
        fetchCapturedPokemons();
    }
}
