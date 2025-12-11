package com.example.pet_products_flea_market;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Feedback 리스트 데이터를 ListView의 각 줄(item) 화면으로 변환하는 어댑터
 */
public class FeedbackListAdapter extends BaseAdapter {

    private Context context;
    private List<Feedback> feedbackList;

    public FeedbackListAdapter(Context context, List<Feedback> feedbackList) {
        this.context = context;
        this.feedbackList = feedbackList;
    }

    // 리스트 총 개수 반환 (ListView의 줄 수)
    @Override
    public int getCount() {
        return feedbackList.size();
    }

    // 특정 위치(i)의 데이터 객체 반환
    @Override
    public Object getItem(int i) {
        return feedbackList.get(i);
    }

    // 아이템 고유 ID 반환
    @Override
    public long getItemId(int i) {
        return i;
    }

    // 리스트의 한 줄을 어떻게 표시할지 정의
    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View v = View.inflate(context, R.layout.item_feedback, null);

        TextView noText = v.findViewById(R.id.noticeText2);
        TextView nameText = v.findViewById(R.id.nameText2);
        TextView dateText = v.findViewById(R.id.dateText2);
        TextView contentText = v.findViewById(R.id.contentText2);

        Feedback item = feedbackList.get(i);

        noText.setText(String.valueOf(item.getNo()));
        nameText.setText(item.getName());
        dateText.setText(item.getDate());
        contentText.setText(item.getContent());

        return v;
    }
}
