package com.example.pet_products_flea_market;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 의견 모아 보기
 * 서버 DB 에 저장된 사용자 의견 목록을 가져와 ListView에 표시
 */

public class FeedbackListActivity extends AppCompatActivity {

    private ListView feedbackListView;
    private FeedbackListAdapter adapter;
    private List<Feedback> feedbackList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_list);

        // XML의 ListView 연결
        feedbackListView = findViewById(R.id.feedbackListView);

        // 리스트 데이터 초기화
        feedbackList = new ArrayList<>();

        // 어댑터 연결
        // feedbackList의 데이터를 ListView에 넣음.
        adapter = new FeedbackListAdapter(FeedbackListActivity.this, feedbackList);
        feedbackListView.setAdapter(adapter);

        // 서버에서 데이터 불러오기
        loadFeedbackData();
    }

    private void loadFeedbackData() {
        String url = "http://10.0.2.2/android/FeedbackList.php";
        /*
         * 정보 불러 오기
         * JsonObject 요청 (GET 방식)
         * 서버에서 데이터를 가져올때는 GET 방식이 정석
         */
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    try {
                        org.json.JSONArray jsonArray = response.getJSONArray("response");
                        // 배열 안의 각각의 의견 내용을 Feedback 객체로 변환
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);

                            String id = obj.getString("id");
                            String userId = obj.getString("user_id");
                            float rating = Float.parseFloat(obj.getString("rating"));
                            String content = obj.getString("content");
                            String regDate = obj.getString("reg_date");

                            Feedback feedback = new Feedback(id, userId, rating, content, regDate);
                            // 리스트에 추가
                            feedbackList.add(feedback);
                        }
                        // 데이터가 바뀌었음을 알려 화면 갱신
                        adapter.notifyDataSetChanged();

                    } catch (Exception e) {
                        Log.e("JSON", "파싱 오류: " + e.toString());
                    }
                },
                error -> Log.e("SERVER", "서버 오류: " + error.toString())
        );
        //실행
        RequestQueue queue = Volley.newRequestQueue(FeedbackListActivity.this);
        queue.add(request);
    }
}