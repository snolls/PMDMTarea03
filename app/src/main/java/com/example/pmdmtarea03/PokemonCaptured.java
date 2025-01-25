package com.example.pmdmtarea03;

/**
 * Clase que representa un Pokémon capturado por el usuario.
 * Contiene información básica del Pokémon, como nombre, imagen, tipo, orden, altura y peso.
 */
public class PokemonCaptured {

    private String name;  // Nombre del Pokémon
    private String image; // URL de la imagen del Pokémon
    private String type;  // Tipo principal del Pokémon
    private int order;    // Número de orden del Pokémon
    private int height;   // Altura del Pokémon
    private int weight;   // Peso del Pokémon

    /**
     * Constructor vacío requerido para ciertas operaciones, como la deserialización.
     */
    public PokemonCaptured() {
    }

    /**
     * Constructor para inicializar todos los atributos del Pokémon capturado.
     *
     * @param name   Nombre del Pokémon.
     * @param image  URL de la imagen del Pokémon.
     * @param type   Tipo principal del Pokémon.
     * @param order  Número de orden del Pokémon.
     * @param height Altura del Pokémon.
     * @param weight Peso del Pokémon.
     */
    public PokemonCaptured(String name, String image, String type, int order, int height, int weight) {
        this.name = name;
        this.image = image;
        this.type = type;
        this.order = order;
        this.height = height;
        this.weight = weight;
    }

    // Métodos getter y setter para acceder y modificar los atributos

    /**
     * Devuelve el nombre del Pokémon.
     *
     * @return Nombre del Pokémon.
     */
    public String getName() {
        return name;
    }

    /**
     * Establece el nombre del Pokémon.
     *
     * @param name Nombre del Pokémon.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Devuelve la URL de la imagen del Pokémon.
     *
     * @return URL de la imagen del Pokémon.
     */
    public String getImage() {
        return image;
    }

    /**
     * Establece la URL de la imagen del Pokémon.
     *
     * @param image URL de la imagen del Pokémon.
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * Devuelve el tipo principal del Pokémon.
     *
     * @return Tipo principal del Pokémon.
     */
    public String getType() {
        return type;
    }

    /**
     * Establece el tipo principal del Pokémon.
     *
     * @param type Tipo principal del Pokémon.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Devuelve el número de orden del Pokémon.
     *
     * @return Número de orden del Pokémon.
     */
    public int getOrder() {
        return order;
    }

    /**
     * Establece el número de orden del Pokémon.
     *
     * @param order Número de orden del Pokémon.
     */
    public void setOrder(int order) {
        this.order = order;
    }

    /**
     * Devuelve la altura del Pokémon.
     *
     * @return Altura del Pokémon.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Establece la altura del Pokémon.
     *
     * @param height Altura del Pokémon.
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Devuelve el peso del Pokémon.
     *
     * @return Peso del Pokémon.
     */
    public int getWeight() {
        return weight;
    }

    /**
     * Establece el peso del Pokémon.
     *
     * @param weight Peso del Pokémon.
     */
    public void setWeight(int weight) {
        this.weight = weight;
    }
}
