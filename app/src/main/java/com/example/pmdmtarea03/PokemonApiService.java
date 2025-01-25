package com.example.pmdmtarea03;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;

/**
 * Interfaz para definir los endpoints de la API de Pokémon.
 * Se utiliza Retrofit para realizar las solicitudes HTTP a la API.
 */
public interface PokemonApiService {

    /**
     * Solicita una lista de Pokémon desde la API.
     *
     * @param offset El desplazamiento para la paginación (número de Pokémon a omitir).
     * @param limit  El número máximo de Pokémon que se deben devolver.
     * @return Un objeto Call que encapsula la respuesta de la API, específicamente una lista de Pokémon.
     */
    @GET("pokemon")
    Call<PokemonListResponse> getPokemonList(
            @Query("offset") int offset, // Parámetro "offset" para omitir los primeros resultados
            @Query("limit") int limit    // Parámetro "limit" para limitar el número de resultados
    );

    /**
     * Solicita los detalles de un Pokémon específico desde la API.
     *
     * @param name El nombre del Pokémon del cual se quieren obtener los detalles.
     * @return Un objeto Call que encapsula la respuesta de la API con los detalles del Pokémon.
     */
    @GET("pokemon/{name}")
    Call<Pokemon> getPokemonDetails(@Path("name") String name); // Sustituye {name} en la URL con el nombre proporcionado

    /**
     * Clase interna para modelar la respuesta de la API que devuelve una lista de Pokémon.
     */
    class PokemonListResponse {
        private List<Result> results; // Lista de resultados, cada uno representando un Pokémon básico

        /**
         * Devuelve la lista de resultados de la API.
         *
         * @return Lista de objetos Result.
         */
        public List<Result> getResults() {
            return results;
        }

        /**
         * Clase interna que modela cada elemento de la lista de resultados.
         */
        public static class Result {
            private String name; // Nombre del Pokémon
            private String url;  // URL con más detalles del Pokémon

            /**
             * Devuelve el nombre del Pokémon.
             *
             * @return Nombre del Pokémon.
             */
            public String getName() {
                return name;
            }

            /**
             * Devuelve la URL asociada al Pokémon.
             *
             * @return URL con detalles del Pokémon.
             */
            public String getUrl() {
                return url;
            }
        }
    }
}
