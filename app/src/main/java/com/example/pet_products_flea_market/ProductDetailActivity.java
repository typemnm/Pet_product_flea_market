package com.example.pet_products_flea_market;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;

public class ProductDetailActivity extends AppCompatActivity {

    public static final String KEY_PRODUCT_DATA = "KEY_PRODUCT_DATA";

    private ViewPager2 vpProductimg;
    private TextView tvTitle, tvPrice, tvDescription;
    private AppCompatButton btnBuy;
    private CardView cvOtherproduct1, cvOtherproduct2, cvOtherproduct3, cvOtherproduct4;

    private Product currentProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_information);

        initData();
        initViews();
        setupViewPager();
        setupListeners();
    }

    private void initData() {
        currentProduct = (Product) getIntent().getSerializableExtra(KEY_PRODUCT_DATA);
        if (currentProduct == null) {
            Toast.makeText(this, "상품 정보를 불러올 수 없습니다.", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void initViews() {
        vpProductimg = findViewById(R.id.vpProductimg);
        tvTitle = findViewById(R.id.tvTitle);
        tvPrice = findViewById(R.id.tvPrice);
        tvDescription = findViewById(R.id.tvDescription);
        btnBuy = findViewById(R.id.btnBuy);

        cvOtherproduct1 = findViewById(R.id.cvOtherproduct1);
        cvOtherproduct2 = findViewById(R.id.cvOtherproduct_2);
        cvOtherproduct3 = findViewById(R.id.cvOtherproduct_3);
        cvOtherproduct4 = findViewById(R.id.cvOtherproduct4);

        tvTitle.setText(currentProduct.getName());
        tvPrice.setText(currentProduct.getPrice());
        tvDescription.setText(currentProduct.getDescription());
    }

    private void setupViewPager() {
        // 이미지 어댑터 연결
        ProductImageAdapter adapter = new ProductImageAdapter(
                this,
                currentProduct.getImageResIds(),
                this::moveToImageZoomActivity
        );
        vpProductimg.setAdapter(adapter);
    }

    private void setupListeners() {
         btnBuy.setOnClickListener(v -> moveToPurchaseActivity());

        // 하단 상품 클릭 (예시)
        cvOtherproduct1.setOnClickListener(v -> loadOtherProduct(101));
        cvOtherproduct2.setOnClickListener(v -> loadOtherProduct(102));
        cvOtherproduct3.setOnClickListener(v -> loadOtherProduct(103));
        cvOtherproduct4.setOnClickListener(v -> loadOtherProduct(104));
    }

    private void moveToImageZoomActivity(int imageResId) {
        Intent intent = new Intent(this, ImageZoomActivity.class);
        intent.putExtra("KEY_IMAGE_RES_ID", imageResId);
        startActivity(intent);
    }

    private void moveToPurchaseActivity() {
        Intent intent = new Intent(this, OrderActivity.class);
        intent.putExtra(KEY_PRODUCT_DATA, currentProduct);
        startActivity(intent);
        finish();
    }

    private void loadOtherProduct(int newId) {
        // 더미 데이터 생성 후 이동
        ArrayList<Integer> dummyImages = new ArrayList<>();
        dummyImages.add(R.drawable.ic_doglogo); // 임시 이미지

        Product nextProduct = new Product(
                newId, "다른 상품 " + newId, "50,000원", "다른 상품 상세 설명입니다.", dummyImages
        );

        Intent intent = new Intent(this, ProductDetailActivity.class);
        intent.putExtra(KEY_PRODUCT_DATA, nextProduct);
        startActivity(intent);
    }
}