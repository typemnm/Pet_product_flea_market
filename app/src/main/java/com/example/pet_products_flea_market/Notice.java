package com.example.pet_products_flea_market;

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
