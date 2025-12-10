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

        btn_submit.setOnClickListener(v -> sendFeedback());
    }
    private void sendFeedback() {
        String url = "http://10.0.2.2/android/Feedback.php";

        float rating = ratingBar.getRating();
        String content = feedback.getText().toString();

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

        Volley.newRequestQueue(this).add(request);
    }
}
