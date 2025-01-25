package com.example.pmdmtarea03;

public class PokemonCaptured {
    private String name,image,type;
    private int order;
    private int height;
    private int weight;

    public PokemonCaptured() {
    }

    public PokemonCaptured(String name, String image, String type, int order, int height, int weight) {
        this.name = name;
        this.image = image;
        this.type = type;
        this.order = order;
        this.height = height;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
