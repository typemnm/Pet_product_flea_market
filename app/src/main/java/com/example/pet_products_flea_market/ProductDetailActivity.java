package com.example.pet_products_flea_market;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

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

        loadOtherProduct();
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
        cvOtherproduct1.setOnClickListener(v -> loadOtherProduct());
        cvOtherproduct2.setOnClickListener(v -> loadOtherProduct());
        cvOtherproduct3.setOnClickListener(v -> loadOtherProduct());
        cvOtherproduct4.setOnClickListener(v -> loadOtherProduct());
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

    private void loadOtherProduct() {
        // 전체 리스트를 가져옴
        List<Product> allProducts = ProductListActivity.getSampleData();
        List<Product> otherProducts = new ArrayList<>();

        // 현재 상품을 제외한 상품만 골라내기
        for (Product p : allProducts) {
            if (p.getId() != currentProduct.getId()) {
                otherProducts.add(p);
            }
        }

        // CardView에 데이터 연결
        CardView[] cardViews = {cvOtherproduct1, cvOtherproduct2, cvOtherproduct3, cvOtherproduct4};

        for (int i = 0; i < cardViews.length; i++) {
            if (i < otherProducts.size()) {
                Product otherItem = otherProducts.get(i);

                // CardView 보이게 설정
                cardViews[i].setVisibility(android.view.View.VISIBLE);

                // 이미지 설정
                if (cardViews[i].getChildCount() > 0 && cardViews[i].getChildAt(0) instanceof ImageView) {
                    ImageView imgView = (ImageView) cardViews[i].getChildAt(0);
                    if (otherItem.getImageResIds() != null && !otherItem.getImageResIds().isEmpty()) {
                        imgView.setImageResource(otherItem.getImageResIds().get(0));
                    }
                }

                // 클릭 리스너 연결
                cardViews[i].setOnClickListener(v -> {
                    Intent intent = new Intent(ProductDetailActivity.this, ProductDetailActivity.class);
                    intent.putExtra(KEY_PRODUCT_DATA, otherItem);
                    startActivity(intent);
                    finish(); // 현재 상세 페이지 종료 후 이동
                });

            } else {
                // 데이터가 모자르면 빈 카드뷰 숨기기
                cardViews[i].setVisibility(android.view.View.INVISIBLE);
            }
        }
    }
}