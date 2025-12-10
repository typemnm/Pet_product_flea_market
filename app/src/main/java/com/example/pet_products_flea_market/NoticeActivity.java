package com.example.pet_products_flea_market;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NoticeActivity extends AppCompatActivity {
    private ListView noticeListView;
    private NoticeListAdapter adapter;
    private List<Notice> noticeList;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.notice);


        noticeListView = (ListView) findViewById(R.id.noticeListView);
        noticeList = new ArrayList<Notice>();
        adapter = new NoticeListAdapter(NoticeActivity.this, noticeList);
        noticeListView.setAdapter(adapter);

        loadNoticeData();

    }
    private void loadNoticeData() {
        //유동적 API
        String url = "http://10.0.2.2/android/NoticeList.php";

        JsonObjectRequest request =
                new JsonObjectRequest(Request.Method.GET, url, null,
                        response -> {
                            try {
                                //유동적
                                org.json.JSONArray jsonArray = response.getJSONArray("response");

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject obj = jsonArray.getJSONObject(i);
                                    //JSON 키값은 유동적
                                    String content = obj.getString("noticeContent");
                                    String name = obj.getString("noticeName");
                                    String date = obj.getString("noticeDate");
                                    String contentContent = obj.getString("noticeContentContent");
                                    //객체 타입 유동적
                                    Notice notice = new Notice(content, name, date, contentContent);
                                    noticeList.add(notice);
                                }

                                adapter.notifyDataSetChanged(); // 화면 갱신

                            } catch (Exception e) {
                                e.printStackTrace();
                                Log.e("JSON", "파싱 오류: " + e.toString());
                            }
                        },
                        error -> Log.e("SERVER", "서버 오류: " + error.toString()));

        RequestQueue queue = Volley.newRequestQueue(NoticeActivity.this);
        queue.add(request);
    }

}