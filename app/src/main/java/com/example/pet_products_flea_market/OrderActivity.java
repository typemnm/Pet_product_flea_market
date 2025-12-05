package com.example.pet_products_flea_market;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class OrderActivity extends AppCompatActivity {

    ImageView imgProduct;
    TextView txtName;
    TextView txtPrice;
    EditText etAddress;
    Button btnPayment;
    Button btnOrder;

    String itemName;
    Bitmap bitMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        imgProduct = findViewById(R.id.prod_img);
        txtName = findViewById(R.id.prod_name);
        txtPrice = findViewById(R.id.prod_price);
        etAddress = findViewById(R.id.address);
        btnPayment = findViewById(R.id.payment);
        btnOrder = findViewById(R.id.orderbtn);


        //TODO:고른 상품의 이미지(src)와 가격정보를 전의 액티비티에서 불러와서 설정하는 것 필요


        //비트맵 형태로 인텐트에 전달
        bitMap = BitmapFactory.decodeResource(getResources(), R.id.prod_img);
        //결제수단 선택
        btnPayment.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(this,v);
            popupMenu.getMenuInflater().inflate(R.menu.menu, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(
                    new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            //결제수단 고르면 그 결제수단의 이름으로 버튼 텍스트 변경
                            itemName = (String) item.getTitle();
                            btnPayment.setText(itemName);
                            return true;
                        }
                    }
            );
        });

        //상품 주문, 주문결과 화면으로 넘어가기(OrderResultActivity)
        btnOrder.setOnClickListener(v -> {
            Intent intent = new Intent(OrderActivity.this, OrderResultActivity.class);
            intent.putExtra("IMG_BIT", bitMap);
            intent.putExtra("P_NAME", txtName.getText().toString());
            intent.putExtra("P_PRICE", txtPrice.getText().toString());
            intent.putExtra("U_ADDR", etAddress.getText().toString());
            intent.putExtra("P_PAY", itemName);
            startActivity(intent);
            finish();
        });



    }
}
