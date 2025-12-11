package com.example.pet_products_flea_market;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class OrderActivity extends AppCompatActivity {

    ImageView imgProduct;
    TextView txtName;
    TextView txtPrice;
    EditText etAddress;
    Button btnPayment;
    Button btnOrder;
    public static final String KEY_PRODUCT_DATA = "KEY_PRODUCT_DATA";
    String itemName;
    int[] imgResource;
    Product selectedProduct;
    String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        //로그인한 사용자 ID 받기
        userId = getIntent().getStringExtra("USER_ID");

        //ProductDetailActivity에서 인텐트 가져오기(id, 이름, 가격, 설명, 이미지src)
        selectedProduct  = (Product) getIntent().getSerializableExtra(KEY_PRODUCT_DATA);

        //xml id 바인딩
        imgProduct = findViewById(R.id.prod_img);
        txtName = findViewById(R.id.prod_name);
        txtPrice = findViewById(R.id.prod_price);
        etAddress = findViewById(R.id.address);
        btnPayment = findViewById(R.id.payment);
        btnOrder = findViewById(R.id.orderbtn);

        //인텐트에서 값 가져와 적용
        //ArrayList<Integer> -> int로 변환, 이미지 리소스 적용
        imgResource = selectedProduct.getImageResIds().stream().mapToInt(Integer::intValue).toArray();
        imgProduct.setImageResource(imgResource[0]);
        txtName.setText(selectedProduct.getName());
        txtPrice.setText("가격: "+selectedProduct.getPrice());


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
            popupMenu.show();
        });

        //상품 주문, 주문결과 화면으로 넘어가기(OrderResultActivity)
        btnOrder.setOnClickListener(v -> {
            //주소나 결제수단이 입력 안되었으면 Toast메세지 띄움
            if(etAddress.getText().toString().isEmpty() || btnPayment.getText().equals("선택")){
                Toast.makeText(this, "올바른  주소나 결제수단을 입력해주세요!", Toast.LENGTH_SHORT).show();
            }else {
                Intent intent = new Intent(OrderActivity.this, OrderResultActivity.class);
                intent.putExtra(KEY_PRODUCT_DATA, selectedProduct);
                intent.putExtra("U_ADDR", etAddress.getText().toString());
                intent.putExtra("P_PAY", itemName);
                intent.putExtra("USER_ID", userId);
                startActivity(intent);
                finish();
            }
        });




    }
}
