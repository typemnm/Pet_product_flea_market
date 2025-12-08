package com.example.pet_products_flea_market;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class ImageZoomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_zoom);

        // 이미지 뷰 설정
        ImageView imgZoom = findViewById(R.id.imgZoom);
        int imageResId = getIntent().getIntExtra("KEY_IMAGE_RES_ID", 0);

        if (imageResId != 0) {
            imgZoom.setImageResource(imageResId);
        }

        //  버튼 설정
        ImageButton btnClose = findViewById(R.id.btnClose);
        btnClose.setOnClickListener(v -> {
            finish(); // 액티비티 종료
        });
    }
}