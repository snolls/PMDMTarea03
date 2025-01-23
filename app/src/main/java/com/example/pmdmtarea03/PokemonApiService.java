package com.example.pmdmtarea03;

import com.example.pmdmtarea03.models.PokemonListResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PokemonApiService {

    // Endpoint para obtener la lista de Pokémon
    @GET("pokemon?offset=0&limit=150")
    Call<PokemonListResponse> getPokemonList();

    // Endpoint para obtener los detalles de un Pokémon individual
    @GET("pokemon/{id}")
    Call<com.example.pmdmtarea03.Pokemon> getPokemonDetails(@Path("id") int id);
}
