package com.example.pet_products_flea_market;

import java.io.Serializable;
import java.util.ArrayList;

public class Product implements Serializable { // Serializable 구현
    private int id;
    private String name;
    private String price;
    private String description; // 설명 추가
    private ArrayList<Integer> imageResIds;

    public Product(int id, String name, String price, String description, ArrayList<Integer> imageResIds) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.imageResIds = imageResIds;
    }
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }
    public String getDescription() {
        return description;
    }

    public ArrayList<Integer> getImageResIds() {
        return imageResIds;
    }
}
