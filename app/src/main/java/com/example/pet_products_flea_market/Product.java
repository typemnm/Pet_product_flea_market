package com.example.pet_products_flea_market;

import java.io.Serializable;
import java.util.ArrayList;

public class Product implements Serializable {
    private int id;
    private String name;
    private String price;
    private String description;
    private ArrayList<String> imageUris;

    // 추가된 필드
    private String sellerId; // 판매자 ID
    private String buyerId;  // 구매자 ID
    private boolean isSold;  // 판매 완료 여부

    public Product(int id, String name, String price, String description, ArrayList<String> imageUris, String sellerId, String buyerId, boolean isSold) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.imageUris = imageUris;
        this.sellerId = sellerId;
        this.buyerId = buyerId;
        this.isSold = isSold;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getPrice() { return price; }
    public String getDescription() { return description; }
    public ArrayList<String> getImageUris() { return imageUris; }

    public String getSellerId() { return sellerId; }
    public String getBuyerId() { return buyerId; }
    public boolean isSold() { return isSold; }
}