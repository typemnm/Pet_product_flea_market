package com.example.pet_products_flea_market;

public class Feedback {

    private String id;        // 번호
    private String userId;    // 작성자
    private float rating;     // 평점
    private String content;   // 내용
    private String regDate;   // 날짜

    public Feedback(String id, String userId, float rating, String content, String regDate) {
        this.id = id;
        this.userId = userId;
        this.rating = rating;
        this.content = content;
        this.regDate = regDate;
    }

    // Adapter에서 사용될 getter들

    public String getNo() {    // item_feedback.xml에서 noticeText2 (No.) 영역
        return id;
    }

    public String getName() {  // 작성자
        return userId;
    }

    public String getDate() {  // 날짜
        return regDate;
    }

    public String getContent() { // 내용
        return content;
    }

    public float getRating() {
        return rating;
    }
}

