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
    Product selectedProduct;
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

        //홈 버튼 기능구현
        btnHome.setOnClickListener(v -> {
            finish(); //연이은 finish()로 최종적으로 ProductListActivity로 이동
        });

        //TODO: 구매완료된 상품을 마이페이지-구매이력에서 볼 수 있도록 해야됨
    }



}
