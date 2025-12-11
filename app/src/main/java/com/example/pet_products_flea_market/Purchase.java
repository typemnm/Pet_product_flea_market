package com.example.pet_products_flea_market;

public class Purchase {
    private String name;
    private String price;
    private String address;
    private String pay;

    public Purchase(String name, String price, String address, String pay) {
        this.name = name;
        this.price = price;
        this.address = address;
        this.pay = pay;
    }

    public String getName() { return name; }
    public String getPrice() { return price; }
    public String getAddress() { return address; }
    public String getPay() { return pay; }
}

