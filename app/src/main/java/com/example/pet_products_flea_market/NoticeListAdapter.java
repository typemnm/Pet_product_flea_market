package com.example.pet_products_flea_market;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Notice 리스트 데이터를 ListView의 각 줄(item) 형태로 변환하는 어댑터
 * - 공지 번호, 작성자, 날짜, 내용을 item_notice.xml에 표시
 */

public class NoticeListAdapter extends BaseAdapter {
    private Context context;
    private List<Notice> noticeList;

    public NoticeListAdapter(Context context, List<Notice> notcieList) {
        this.context = context;
        this.noticeList = notcieList;
    }
    // 리스트 총 개수 반환 (ListView의 줄 수)
    @Override
    public int getCount() {
        return noticeList.size();
    }

    // 특정 위치(i)의 공지사항 데이터 반환
    @Override
    public Object getItem(int i) {
        return noticeList.get(i);
    }

    // 아이템의 고유 ID 반환 (여기서는 index 그대로 사용)
    @Override
    public long getItemId(int i) {
        return i;
    }

    // 리스트의 한 줄을 어떻게 표시할지 정의
    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View v = View.inflate(context,R.layout.item_notice, null);
        TextView noticeText = (TextView) v.findViewById(R.id.noticeText);
        TextView nameText = (TextView) v.findViewById(R.id.nameText);
        TextView dateText = (TextView) v.findViewById(R.id.dateText);
        TextView contentText = (TextView) v.findViewById(R.id.contentText);

        Notice item = noticeList.get(i);

        noticeText.setText(noticeList.get(i).getNotice());
        nameText.setText(noticeList.get(i).getName());
        dateText.setText(noticeList.get(i).getDate());
        contentText.setText(noticeList.get(i).getContent());



        return v;
    }
}