package com.example.a1;

public class Notice {
    String notice;
    String name;
    String date;

    public Notice(String notice, String name, String date) {
        this.notice = notice;
        this.name = name;
        this.date = date;
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

}
