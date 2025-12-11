package com.example.pet_products_flea_market;

/**
 * 공지사항 데이터를 저장하는 모델 클래스
 * - 공지 번호, 작성자 이름, 날짜, 내용 정보
 */

public class Notice {
    String notice;
    String name;
    String date;
    String content;

    public Notice(String notice, String name, String date, String content) {
        this.notice = notice;
        this.name = name;
        this.date = date;
        this.content = content;
    }

    // getter은 Adapter에서 사용
    public String getNotice() {
        return notice;
    }


    public String getName() {
        return name;
    }


    public String getDate() {
        return date;
    }



    public String getContent() {
        return content;
    }


}