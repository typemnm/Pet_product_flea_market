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

/**
 * 공지 사항
 * 서버 DB 에 저장된 공지 사항 목록을 가져와 ListView에 표시
 * GET 방식 -> NoticeList.php의 JSON 데이터를 받아옴
 */

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

        // XML ListView 연결
        noticeListView = (ListView) findViewById(R.id.noticeListView);

        // 리스트 초기화
        noticeList = new ArrayList<Notice>();

        // 어댑터 생성 후 ListView에 저장
        adapter = new NoticeListAdapter(NoticeActivity.this, noticeList);
        noticeListView.setAdapter(adapter);

        // 서버에서 공지사항 불러오기
        loadNoticeData();

    }

    /**
     * 서버에서 공지사항 목록 가져오기
     * GET 방식
     */
    private void loadNoticeData() {
        //유동적 API
        String url = "http://10.0.2.2/android/NoticeList.php";

        JsonObjectRequest request =
                new JsonObjectRequest(Request.Method.GET, url, null,
                        response -> {
                            try {
                                org.json.JSONArray jsonArray = response.getJSONArray("response");
                                // 배열 데이터를 Notice 객체에 담아 리스트에 추가
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject obj = jsonArray.getJSONObject(i);

                                    String content = obj.getString("noticeContent");
                                    String name = obj.getString("noticeName");
                                    String date = obj.getString("noticeDate");
                                    String contentContent = obj.getString("noticeContentContent");

                                    Notice notice = new Notice(content, name, date, contentContent);
                                    noticeList.add(notice);
                                }
                                // 화면 갱신
                                adapter.notifyDataSetChanged();

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