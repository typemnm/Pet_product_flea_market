package com.example.pet_products_flea_market;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

/**
 * 의견 남기기
 * 사용자가 입력한 별점과 내용을 서버 DB에 전송
 */

public class FeedbackActivity extends AppCompatActivity {
    RatingBar ratingBar;
    EditText feedback;
    Button btn_submit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        ratingBar = findViewById(R.id.ratingBar);
        feedback = findViewById(R.id.feedback);
        btn_submit = findViewById(R.id.btn_submit);

        // 제출 하기 버튼 클릭 시 서버로 내용 전송
        btn_submit.setOnClickListener(v -> sendFeedback());
    }


    /**
     * 서버로 의견 전송
     * POST 방식 (rating, content, userId 전달)
     * userId 전달 받은 방식 :
     * LoginActivity 에서 회원 정보 전달 -> MyPageActivity -> 현재 에서 get
     */
    private void sendFeedback() {
        //안드로이드 에뮬레이터가 XAMPP의 localhost에 접근할때 쓰는 주소
        String url = "http://10.0.2.2/android/Feedback.php";

        //사용자 입력값 가져오기
        float rating = ratingBar.getRating();
        String content = feedback.getText().toString();

        /**
         * Volley 방식 -> 서버로 POST 요청
         * Volley 방식 : (Java) StringRequest 생성 -> POST/Get 방식 선택 -> 서버 응답 시 -> 화면에 결과 전달
         * Post 방식을 쓴 이유 : GET 방식은 URL에 노출 되어서 보안상 POST가 정석
         */

        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                response -> {
                    Toast.makeText(this, "의견이 저장되었습니다", Toast.LENGTH_SHORT).show();
                },
                error -> {
                    Toast.makeText(this, "서버 오류: " + error, Toast.LENGTH_SHORT).show();
                }
        ) {
            // POST로 전달한 파라미터 구성
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                String userId = getIntent().getStringExtra("USER_ID");

                params.put("user_id", userId);
                params.put("rating", String.valueOf(rating));
                params.put("content", content);
                return params;
            }
        };
        //실행
        Volley.newRequestQueue(this).add(request);
    }
}