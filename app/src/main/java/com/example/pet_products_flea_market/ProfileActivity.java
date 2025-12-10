package com.example.pet_products_flea_market;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Intent로 전달된 사용자 ID 받기
        String userId = getIntent().getStringExtra("USER_ID");
        if (userId == null) userId = "알 수 없음";

        // UI 요소 연결
        TextView tvUserId = findViewById(R.id.tvUserId);

        // 아이디 설정
        tvUserId.setText("아이디 : " + userId);

        // 나머지 데이터(가입일, 판매수, 구매수 등)는
        // XML에 하드코딩된 더미 데이터를 그대로 보여줍니다.
    }
}