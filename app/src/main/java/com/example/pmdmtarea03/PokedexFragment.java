package com.example.pmdmtarea03;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pmdmtarea03.databinding.FragmentPokedexBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PokedexFragment extends Fragment {

    private FragmentPokedexBinding binding;
    private PokemonAdapter adapter;
    private List<Pokemon> pokemonList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPokedexBinding.inflate(inflater, container, false);

        // Configurar RecyclerView
        binding.recyclerViewPokemon.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new PokemonAdapter(pokemonList);
        binding.recyclerViewPokemon.setAdapter(adapter);

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

                    // Ordenar la lista por el campo "order"
                    Collections.sort(pokemonList, new Comparator<Pokemon>() {
                        @Override
                        public int compare(Pokemon p1, Pokemon p2) {
                            return Integer.compare(p1.getOrder(), p2.getOrder());
                        }
                    });

                    adapter.notifyDataSetChanged(); // Actualizar el RecyclerView
                }
            }

            @Override
            public void onFailure(Call<Pokemon> call, Throwable t) {
                Log.e("PokedexFragment", "Error al cargar detalles", t);
            }
        });
    }
}
