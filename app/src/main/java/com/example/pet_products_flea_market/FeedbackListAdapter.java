package com.example.pet_products_flea_market;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class FeedbackListAdapter extends BaseAdapter {

    private Context context;
    private List<Feedback> feedbackList;

    public FeedbackListAdapter(Context context, List<Feedback> feedbackList) {
        this.context = context;
        this.feedbackList = feedbackList;
    }

    @Override
    public int getCount() {
        return feedbackList.size();
    }

    @Override
    public Object getItem(int i) {
        return feedbackList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View v = View.inflate(context, R.layout.item_feedback, null);

        TextView noText = v.findViewById(R.id.noticeText2);
        TextView nameText = v.findViewById(R.id.nameText2);
        TextView dateText = v.findViewById(R.id.dateText2);
        TextView contentText = v.findViewById(R.id.contentText2);

        Feedback item = feedbackList.get(i);

        noText.setText(String.valueOf(item.getNo()));      // No.
        nameText.setText(item.getName());                  // 작성자
        dateText.setText(item.getDate());                  // 날짜
        contentText.setText(item.getContent());            // 내용

        return v;
    }
}
