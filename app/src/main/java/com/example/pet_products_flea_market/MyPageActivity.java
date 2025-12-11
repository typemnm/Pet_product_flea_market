package com.example.pet_products_flea_market;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
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

    @Override
    protected void onResume() {
        super.onResume();

        if (bottomNavigationView != null) {
            bottomNavigationView.setSelectedItemId(R.id.nav_mypage);
        }
    }

    private void initViews() {
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
    }

    private void setupListeners() {
        // 판매 내역
        Button btnSalesHistory = findViewById(R.id.btnSalesHistory);
        if (btnSalesHistory != null) {
            btnSalesHistory.setOnClickListener(v -> {
                Intent intent = new Intent(MyPageActivity.this, HistoryActivity.class);
                intent.putExtra("USER_ID", userId);
                intent.putExtra("MODE", "SALES");
                startActivity(intent);
            });
        }

        // 구매 내역
        findViewById(R.id.btnPurchaseHistory).setOnClickListener(v -> {
            Intent intent = new Intent(MyPageActivity.this, HistoryActivity.class);
            intent.putExtra("USER_ID", userId);
            intent.putExtra("MODE", "PURCHASE");
            startActivity(intent);
        });

        // 프로필
        findViewById(R.id.btnProfile).setOnClickListener(v -> {
            Intent intent = new Intent(MyPageActivity.this, ProfileActivity.class);
            intent.putExtra("USER_ID", userId);
            startActivity(intent);
        });

        // 의견 남기기
        findViewById(R.id.btnFeedback).setOnClickListener(v -> {
            Intent intent = new Intent(MyPageActivity.this, FeedbackActivity.class);
            intent.putExtra("USER_ID", userId); // 작성자 ID 전달
            startActivity(intent);
        });

        // 의견 모아보기
        Button btnReadFeedback = findViewById(R.id.btnReadFeedback);
        if (btnReadFeedback != null) {
            btnReadFeedback.setOnClickListener(v -> {
                Intent intent = new Intent(MyPageActivity.this, FeedbackListActivity.class);
                startActivity(intent);
            });
        }

        // 공지사항
        findViewById(R.id.btnNotice).setOnClickListener(v -> {
            Intent intent = new Intent(MyPageActivity.this, NoticeActivity.class);
            startActivity(intent);
        });

        // 회원탈퇴
        findViewById(R.id.tvDeleteAccount).setOnClickListener(v ->
                Toast.makeText(this, "회원탈퇴 처리가 완료되었습니다.", Toast.LENGTH_SHORT).show());

        // 로그아웃
        findViewById(R.id.tvLogout).setOnClickListener(v -> {
            Toast.makeText(this, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MyPageActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
    }

    private void initBottomNavigation() {
        bottomNavigationView.setSelectedItemId(R.id.nav_mypage);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                Intent intent = new Intent(MyPageActivity.this, ProductListActivity.class);
                intent.putExtra("USER_ID", userId);
                startActivity(intent);
                finish();
                return true;
            } else if (itemId == R.id.nav_notice) {
                Intent intent = new Intent(MyPageActivity.this, NoticeActivity.class);
                startActivity(intent);
                return true;
            } else if (itemId == R.id.nav_mypage) {
                return true;
            }
            return false;
        });
    }
}