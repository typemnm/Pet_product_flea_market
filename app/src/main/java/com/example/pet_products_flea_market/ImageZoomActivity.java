package com.example.pet_products_flea_market;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class ImageZoomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_zoom);

        ImageView imgZoom = findViewById(R.id.imgZoom);

        String imageUriString = getIntent().getStringExtra("KEY_IMAGE_URI");

        if (imageUriString != null) {
            try {
                imgZoom.setImageURI(Uri.parse(imageUriString));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // 닫기 버튼 설정
        ImageButton btnClose = findViewById(R.id.btnClose);
        btnClose.setOnClickListener(v -> {
            finish(); // 액티비티 종료
        });
    }
}