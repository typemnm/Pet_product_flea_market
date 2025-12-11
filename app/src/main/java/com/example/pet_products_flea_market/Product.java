package com.example.pet_products_flea_market;

import java.io.Serializable;
import java.util.ArrayList;

public class Product implements Serializable {
    private int id;
    private String name;
    private String price;
    private String description;
    private ArrayList<String> imageUris;

    private String sellerId;
    private String buyerId;
    private boolean isSold;
    private String tradingAddress;
    private String paymentMethod;

    public Product(int id, String name, String price, String description, ArrayList<String> imageUris,
                   String sellerId, String buyerId, boolean isSold,
                   String tradingAddress, String paymentMethod) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.imageUris = imageUris;
        this.sellerId = sellerId;
        this.buyerId = buyerId;
        this.isSold = isSold;
        this.tradingAddress = tradingAddress;
        this.paymentMethod = paymentMethod;
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

    // [추가] Getters
    public String getTradingAddress() { return tradingAddress; }
    public String getPaymentMethod() { return paymentMethod; }
}