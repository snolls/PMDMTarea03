package com.example.pmdmtarea03;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Pokemon {
    private String name;
    private int order;
    private int height;
    private int weight;

    private List<TypeWrapper> types;

    @SerializedName("sprites")
    private Sprites sprites;

    // Getters y clases anidadas

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

    public static class TypeWrapper {
        private Type type;

        public Type getType() {
            return type;
        }

        public static class Type {
            private String name;

            public String getName() {
                return name;
            }
        }
    }

    public static class Sprites {
        @SerializedName("other")
        private OtherSprites other;

        public OtherSprites getOther() {
            return other;
        }

        public static class OtherSprites {
            @SerializedName("home")
            private Home home;

            public Home getHome() {
                return home;
            }

            public static class Home {
                @SerializedName("front_default")
                private String frontDefault;

                public String getFrontDefault() {
                    return frontDefault;
                }
            }
        }
    }
}

