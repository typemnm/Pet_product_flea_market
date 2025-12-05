package com.example.pet_products_flea_market;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
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

        //intent 가져오기
        Bitmap bitMap = getIntent().getParcelableExtra("IMG_BIT");
        String prodName = getIntent().getStringExtra("P_NAME");
        String prodPrice = getIntent().getStringExtra("P_PRICE");
        String userAddr = getIntent().getStringExtra("U_ADDR");
        String prodPay = getIntent().getStringExtra("P_PAY");

        //값 설정
        imgProduct.setImageBitmap(bitMap);
        txtName.setText(prodName);
        txtPrice.setText("가격: "+prodPrice);
        txtAddress.setText(userAddr);
        txtPayment.setText(prodPay);

        //홈 버튼 기능구현
        btnHome.setOnClickListener(v -> {

            //TODO: 인텐트 사용해서 홈으로 돌아갈지 finish()로 돌아갈지 정해야됨
            finish();
        });


    }



}
