package com.example.pet_products_flea_market;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class OrderResultActivity extends AppCompatActivity {

    ImageView imgProduct;
    TextView txtName;
    TextView txtPrice;
    TextView txtAddress;
    TextView txtPayment;
    Button btnHome;
    Product selectedProduct;
    String userId;
    public static final String KEY_PRODUCT_DATA = "KEY_PRODUCT_DATA";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_result);
        //xml id 바인딩
        imgProduct = findViewById(R.id.prod_img);
        txtName = findViewById(R.id.prod_name);
        txtPrice = findViewById(R.id.prod_price);
        txtAddress = findViewById(R.id.address);
        txtPayment = findViewById(R.id.payment);
        btnHome = findViewById(R.id.homebtn);
        String userId = getIntent().getStringExtra("USER_ID");

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent intent = new Intent(OrderResultActivity.this, MyPageActivity.class);
            intent.putExtra("USER_ID", userId);  // ★ 필수
            startActivity(intent);
            finish();
        }, 1500);   // 1.5초 뒤 자동 이동 (원하면 수정 가능)
        //intent 가져오기
        selectedProduct  = (Product) getIntent().getSerializableExtra(KEY_PRODUCT_DATA);
        int[]  imgResource = selectedProduct.getImageResIds().stream().mapToInt(Integer::intValue).toArray();
        String prodName = selectedProduct.getName();
        String prodPrice = selectedProduct.getPrice();
        String userAddr = getIntent().getStringExtra("U_ADDR");
        String prodPay = getIntent().getStringExtra("P_PAY");

        //값 설정
        imgProduct.setImageResource(imgResource[0]);
        txtName.setText(prodName);
        txtPrice.setText("가격: "+prodPrice);
        txtAddress.setText(userAddr);
        txtPayment.setText(prodPay);

        // 구매내역 저장
        SharedPreferences prefs =
                getSharedPreferences("PURCHASE_HISTORY_" + userId, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        // 지금까지 저장된 개수 가져오기
        int count = prefs.getInt("COUNT", 0);
        int newIndex = count + 1;

        // 구매 항목 저장
        editor.putString("NAME_" + newIndex, prodName);
        editor.putString("PRICE_" + newIndex, prodPrice);
        editor.putString("ADDRESS_" + newIndex, userAddr);
        editor.putString("PAY_" + newIndex, prodPay);

        // 개수 업데이트
        editor.putInt("COUNT", newIndex);
        editor.apply();

        //홈 버튼 기능구현
        btnHome.setOnClickListener(v -> {
            Intent intent = new Intent(OrderResultActivity.this, ProductListActivity.class);
            intent.putExtra("USER_ID", userId);   // 필수
            startActivity(intent);
            finish();
        });




    }



}
