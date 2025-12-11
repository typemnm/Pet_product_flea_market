package com.example.pet_products_flea_market;

/**
 * 구매 내역
 * - 상품명, 가격, 배송지 주소, 결제 방식 정보를 담고 있음
 * - PurchaseListAdapter에서 ListView에 표시하기 위해 사용되는 객체
 */

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

