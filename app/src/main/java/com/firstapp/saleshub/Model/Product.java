package com.firstapp.saleshub.Model;

import java.io.Serializable;

public class Product implements Serializable {

    private String name;
    private int image;
    private double price;
    private String description;

    public Product(String name, int image, double price, String description) {
        this.name = name;
        this.image = image;
        this.price = price;
        this.description = description;

    }

    public String getName() {
        return name;
    }

    public int getImage() {
        return image;
    }

    public double getPrice() {
        return price;
    }


    public String getDescription() {
        return description;
    }
}
