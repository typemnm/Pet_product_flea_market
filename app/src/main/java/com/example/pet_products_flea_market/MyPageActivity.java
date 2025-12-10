package com.example.pet_products_flea_market;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MyPageActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        userId = getIntent().getStringExtra("USER_ID");

        initViews();
        setupListeners();
        initBottomNavigation();
    }

    private void initViews() {
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
    }

    private void setupListeners() {
        // 기능이 구현되지 않은 버튼들은 Toast 메시지로 대체
        findViewById(R.id.btnSalesHistory).setOnClickListener(v ->
                Toast.makeText(this, "판매 내역 기능 준비 중입니다.", Toast.LENGTH_SHORT).show());

        findViewById(R.id.btnPurchaseHistory).setOnClickListener(v ->
                Toast.makeText(this, "구매 내역 기능 준비 중입니다.", Toast.LENGTH_SHORT).show());

        findViewById(R.id.btnProfile).setOnClickListener(v -> {
            Intent intent = new Intent(MyPageActivity.this, ProfileActivity.class);
            intent.putExtra("USER_ID", userId); // 사용자 ID 전달
            startActivity(intent);
        });

        findViewById(R.id.btnFeedback).setOnClickListener(v ->
                Toast.makeText(this, "의견 남기기 기능 준비 중입니다.", Toast.LENGTH_SHORT).show());

        findViewById(R.id.tvDeleteAccount).setOnClickListener(v ->
                Toast.makeText(this, "회원탈퇴 처리가 완료되었습니다.", Toast.LENGTH_SHORT).show());

        // 공지사항 버튼 -> MainActivity (기존 공지사항 화면) 이동
        findViewById(R.id.btnNotice).setOnClickListener(v -> {
            Intent intent = new Intent(MyPageActivity.this, MainActivity.class);
            startActivity(intent);
        });

        // 로그아웃 버튼 -> 로그인 화면으로 이동
        findViewById(R.id.tvLogout).setOnClickListener(v -> {
            Toast.makeText(this, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MyPageActivity.this, LoginActivity.class);
            // 기존 액티비티 스택 모두 제거 (뒤로가기 방지)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
    }

    private void initBottomNavigation() {
        // 마이페이지 탭 선택 상태로 설정
        bottomNavigationView.setSelectedItemId(R.id.nav_mypage);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.nav_home) {
                // 홈(상품목록)으로 이동
                Intent intent = new Intent(MyPageActivity.this, ProductListActivity.class);
                intent.putExtra("USER_ID", userId);
                startActivity(intent);
                finish(); // 현재 화면 종료
                return true;
            } else if (itemId == R.id.nav_notice) {
                // 공지사항으로 이동
                Intent intent = new Intent(MyPageActivity.this, MainActivity.class);
                startActivity(intent);
                return true;
            } else if (itemId == R.id.nav_mypage) {
                return true;
            }
            return false;
        });
    }
}