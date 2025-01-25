package com.example.pmdmtarea03;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Clase para configurar e inicializar un cliente Retrofit.
 *
 * Se utiliza para realizar solicitudes HTTP a la API de Pokémon (PokeAPI).
 */
public class RetrofitClient {
    private static Retrofit retrofit = null; // Instancia única de Retrofit

    /**
     * Devuelve una instancia de la interfaz `PokemonApiService` para interactuar con la API.
     *
     * @return Una implementación de `PokemonApiService` generada por Retrofit.
     */
    public static PokemonApiService getApiService() {
        // Inicializar Retrofit si aún no se ha hecho
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://pokeapi.co/api/v2/") // URL base de la API de Pokémon
                    .addConverterFactory(GsonConverterFactory.create()) // Convertidor para deserializar JSON a objetos
                    .build();
        }
        // Crear e implementar la interfaz PokemonApiService
        return retrofit.create(PokemonApiService.class);
    }
}
