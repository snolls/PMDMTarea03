package com.example.pmdmtarea03;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;

public interface PokemonApiService {

    @GET("pokemon")
    Call<PokemonListResponse> getPokemonList(
            @Query("offset") int offset,
            @Query("limit") int limit
    );

    @GET("pokemon/{name}")
    Call<Pokemon> getPokemonDetails(@Path("name") String name);

    class PokemonListResponse {
        private List<Result> results;

        public List<Result> getResults() {
            return results;
        }

        public static class Result {
            private String name;
            private String url;

            public String getName() {
                return name;
            }

            public String getUrl() {
                return url;
            }
        }
    }
}
