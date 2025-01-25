package com.example.pmdmtarea03;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Clase que representa un Pokémon con sus atributos y detalles,
 * incluyendo los tipos y las imágenes.
 *
 * Esta clase está diseñada para mapear la respuesta de la API de Pokémon.
 */
public class Pokemon {
    private String name; // Nombre del Pokémon
    private int order; // Número de orden del Pokémon
    private int height; // Altura del Pokémon
    private int weight; // Peso del Pokémon

    private List<TypeWrapper> types; // Lista de tipos del Pokémon (puede tener más de uno)

    @SerializedName("sprites")
    private Sprites sprites; // Información sobre las imágenes del Pokémon

    // Métodos Setter para asignar valores a las propiedades
    public void setName(String name) {
        this.name = name;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setTypes(List<TypeWrapper> types) {
        this.types = types;
    }

    public void setSprites(Sprites sprites) {
        this.sprites = sprites;
    }

    // Métodos Getter para obtener los valores de las propiedades
    public String getName() {
        return name;
    }

    public int getOrder() {
        return order;
    }

    public int getHeight() {
        return height;
    }

    public int getWeight() {
        return weight;
    }

    public List<TypeWrapper> getTypes() {
        return types;
    }

    public Sprites getSprites() {
        return sprites;
    }

    /**
     * Clase anidada que representa el tipo de un Pokémon.
     * Cada Pokémon puede tener uno o más tipos.
     */
    public static class TypeWrapper {
        private Type type; // Detalles del tipo

        // Métodos Getter y Setter
        public Type getType() {
            return type;
        }

        public void setType(Type typeObj) {
            this.type = typeObj;
        }

        /**
         * Clase anidada dentro de TypeWrapper para representar
         * los detalles de un tipo específico (por ejemplo, "fire", "water").
         */
        public static class Type {
            private String name; // Nombre del tipo (por ejemplo, "fire", "water")

            // Métodos Getter y Setter
            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }

    /**
     * Clase anidada que representa las imágenes (sprites) de un Pokémon.
     */
    public static class Sprites {
        @SerializedName("other")
        private OtherSprites other; // Sprites adicionales (por ejemplo, imágenes de alta calidad)

        // Métodos Getter y Setter
        public OtherSprites getOther() {
            return other;
        }

        public void setOther(OtherSprites otherSprites) {
            this.other = otherSprites;
        }

        /**
         * Clase anidada dentro de Sprites que representa otros sprites,
         * como imágenes para diferentes contextos (por ejemplo, "home").
         */
        public static class OtherSprites {
            @SerializedName("home")
            private Home home; // Imagen principal del Pokémon (versión "home")

            // Métodos Getter y Setter
            public Home getHome() {
                return home;
            }

            public void setHome(Home home) {
                this.home = home;
            }

            /**
             * Clase anidada dentro de OtherSprites que representa
             * la imagen principal de un Pokémon.
             */
            public static class Home {
                @SerializedName("front_default")
                private String frontDefault; // URL de la imagen frontal predeterminada

                // Métodos Getter y Setter
                public String getFrontDefault() {
                    return frontDefault;
                }

                public void setFrontDefault(String frontDefault) {
                    this.frontDefault = frontDefault;
                }
            }
        }
    }
}
