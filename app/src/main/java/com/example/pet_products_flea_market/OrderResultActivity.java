package com.example.pet_products_flea_market;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class OrderResultActivity extends AppCompatActivity {

    private ImageView imgProduct;
    private TextView txtName;
    private TextView txtPrice;
    private TextView txtAddress;
    private TextView txtPayment;
    private Button btnHome;
    private Product selectedProduct;
    private String userId;
    private boolean fromPurchase = false;

    public static final String KEY_PRODUCT_DATA = "KEY_PRODUCT_DATA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_result);

        // 인텐트 데이터 수신
        userId = getIntent().getStringExtra("USER_ID");
        fromPurchase = getIntent().getBooleanExtra("FROM_PURCHASE", false);
        selectedProduct = (Product) getIntent().getSerializableExtra(KEY_PRODUCT_DATA);

        // 뷰 바인딩
        imgProduct = findViewById(R.id.prod_img);
        txtName = findViewById(R.id.prod_name);
        txtPrice = findViewById(R.id.prod_price);
        txtAddress = findViewById(R.id.address);
        txtPayment = findViewById(R.id.payment);
        btnHome = findViewById(R.id.homebtn);

        // 데이터 표시
        if (selectedProduct != null) {
            // 이미지 로드
            ArrayList<String> imageUris = selectedProduct.getImageUris();
            if (imageUris != null && !imageUris.isEmpty()) {
                try {
                    imgProduct.setImageURI(Uri.parse(imageUris.get(0)));
                } catch (Exception e) {
                    imgProduct.setImageResource(R.drawable.ic_launcher_background);
                }
            } else {
                imgProduct.setImageResource(R.drawable.ic_launcher_background);
            }

            // 텍스트 설정
            txtName.setText(selectedProduct.getName());
            txtPrice.setText("가격: " + selectedProduct.getPrice());
        }

        String userAddr = getIntent().getStringExtra("U_ADDR");
        String prodPay = getIntent().getStringExtra("P_PAY");

        if (userAddr != null && !userAddr.isEmpty()) {
            txtAddress.setText(userAddr);
        }
        else if (selectedProduct != null && selectedProduct.getTradingAddress() != null) {
            txtAddress.setText(selectedProduct.getTradingAddress());
        }
        else {
            txtAddress.setText("정보 없음");
        }

        if (prodPay != null && !prodPay.isEmpty()) {
            txtPayment.setText(prodPay);
        }
        else if (selectedProduct != null && selectedProduct.getPaymentMethod() != null) {
            txtPayment.setText(selectedProduct.getPaymentMethod());
        }
        else {
            txtPayment.setText("-");
        }

        // 홈 버튼 클릭 리스너 설정
        btnHome.setOnClickListener(v -> goHome());
    }

    private void goHome() {
        Intent intent = new Intent(OrderResultActivity.this, ProductListActivity.class);
        intent.putExtra("USER_ID", userId);
        // FLAG_ACTIVITY_CLEAR_TOP: 스택에 있는 기존 ProductListActivity 위쪽의 모든 액티비티(상세, 주문 등)를 제거
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish(); // 현재 액티비티 종료
    }
}