package com.example.pet_products_flea_market;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity {

    ImageView imgProduct;
    TextView txtName;
    TextView txtPrice;
    EditText etAddress;
    Button btnPayment;
    Button btnOrder;
    public static final String KEY_PRODUCT_DATA = "KEY_PRODUCT_DATA";
    String itemName;
    Product selectedProduct;
    String userId;
    ProductDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        dbHelper = new ProductDBHelper(this);
        selectedProduct = (Product) getIntent().getSerializableExtra(KEY_PRODUCT_DATA);
        userId = getIntent().getStringExtra("USER_ID");

        imgProduct = findViewById(R.id.prod_img);
        txtName = findViewById(R.id.prod_name);
        txtPrice = findViewById(R.id.prod_price);
        etAddress = findViewById(R.id.address);
        btnPayment = findViewById(R.id.payment);
        btnOrder = findViewById(R.id.orderbtn);

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

        txtName.setText(selectedProduct.getName());
        txtPrice.setText("가격: "+selectedProduct.getPrice());

        btnPayment.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(this,v);
            popupMenu.getMenuInflater().inflate(R.menu.menu, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(item -> {
                itemName = (String) item.getTitle();
                btnPayment.setText(itemName);
                return true;
            });
            popupMenu.show();
        });

        btnOrder.setOnClickListener(v -> {
            if(etAddress.getText().toString().isEmpty() || btnPayment.getText().equals("선택")){
                Toast.makeText(this, "올바른 주소나 결제수단을 입력해주세요!", Toast.LENGTH_SHORT).show();
            } else {
                // DB 업데이트: 판매 완료 처리 및 구매자 등록
                dbHelper.updateProductSold(selectedProduct.getId(), userId);

                Intent intent = new Intent(OrderActivity.this, OrderResultActivity.class);
                intent.putExtra(KEY_PRODUCT_DATA, selectedProduct);
                intent.putExtra("U_ADDR", etAddress.getText().toString());
                intent.putExtra("P_PAY", itemName);
                startActivity(intent);
                finish();
            }
        });
    }
}