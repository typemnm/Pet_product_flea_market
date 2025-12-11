package com.example.pet_products_flea_market;

import java.io.Serializable;
import java.util.ArrayList;

public class Product implements Serializable {
    private int id;
    private String name;
    private String price;
    private String description;
    // 이미지 정보를 URI 문자열로 저장
    private ArrayList<String> imageUris;

    public Product(int id, String name, String price, String description, ArrayList<String> imageUris) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.imageUris = imageUris;
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
    public ArrayList<String> getImageUris() {
        return imageUris;
    }
}