package com.example.pet_products_flea_market;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

/**
 * 앱 실행 시 가장 먼저 보여지는 Splash 화면
 * 일정 시간 로고를 표시한 뒤 LoginActivity로 이동함
 */
public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DURATION = 1500; // 1.5초

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        moveToLoginWithDelay();
    }

    /**
     * 일정 시간 후 로그인 액티비티로 이동
     */
    private void moveToLoginWithDelay() {
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
            finish(); // 뒤로 가기 시 스플래시로 돌아오지 않도록
        }, SPLASH_DURATION);
    }
}

