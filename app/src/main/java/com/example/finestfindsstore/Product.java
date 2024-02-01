package com.example.finestfindsstore;
public class Product {
    private String imageResource;
    private String name;
    private String price;

    public Product(String imageResource, String name, String price) {
        this.imageResource = imageResource;
        this.name = name;
        this.price = price;
    }

    public String getImageResource() {
        return imageResource;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }
}