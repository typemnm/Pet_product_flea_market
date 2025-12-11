package com.example.pet_products_flea_market;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        String userId = getIntent().getStringExtra("USER_ID");
        if (userId == null) userId = "알 수 없음";

        TextView tvUserId = findViewById(R.id.tvUserId);
        tvUserId.setText("아이디 : " + userId);
    }
}