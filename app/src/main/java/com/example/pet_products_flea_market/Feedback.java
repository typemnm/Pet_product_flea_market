package com.example.pet_products_flea_market;

/**
 * 의견 모아 보기
 * DB에 저장된 사용자 의견을 보이기 위해 객체화
 * - 의견 번호, 작성자 ID, 작성 날짜, 의견 내용, 별점
 */
public class Feedback {
    private String id;
    private String userId;
    private float rating;
    private String content;
    private String regDate;

    public Feedback(String id, String userId, float rating, String content, String regDate) {
        this.id = id;
        this.userId = userId;
        this.rating = rating;
        this.content = content;
        this.regDate = regDate;
    }

    // getter은 Adapter에서 사용
    public String getNo() {    // item_feedback.xml에서 noticeText2 (No.) 영역
        return id;
    }

    public String getName() { return userId; }

    public String getDate() {  // 날짜
        return regDate;
    }

    public String getContent() { // 내용
        return content;
    }
    public float getRating() {return rating; }
}