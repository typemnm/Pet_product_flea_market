package com.example.pet_products_flea_market;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
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
    private ProductDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_information);

        dbHelper = new ProductDBHelper(this);

        initData();
        initViews();
        setupViewPager();
        setupListeners();
        loadOtherProducts();
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
        ProductImageAdapter adapter = new ProductImageAdapter(
                this,
                currentProduct.getImageUris(),
                this::moveToImageZoomActivity
        );
        vpProductimg.setAdapter(adapter);
    }

    private void setupListeners() {
        btnBuy.setOnClickListener(v -> moveToPurchaseActivity());
    }

    // 실제 DB에서 다른 상품들을 가져와서 하단 뷰에 세팅
    private void loadOtherProducts() {
        List<Product> allProducts = dbHelper.loadAllProducts();
        List<Product> otherProducts = new ArrayList<>();

        // 현재 보고 있는 상품을 제외하고 리스트에 추가
        for (Product p : allProducts) {
            if (p.getId() != currentProduct.getId()) {
                otherProducts.add(p);
            }
        }

        // 각 카드뷰에 상품 정보 바인딩
        bindProductToCard(cvOtherproduct1, otherProducts, 0);
        bindProductToCard(cvOtherproduct2, otherProducts, 1);
        bindProductToCard(cvOtherproduct3, otherProducts, 2);
        bindProductToCard(cvOtherproduct4, otherProducts, 3);
    }

    // 카드뷰와 상품 데이터를 연결하는 헬퍼 함수
    private void bindProductToCard(CardView cardView, List<Product> products, int index) {
        if (index < products.size()) {
            Product product = products.get(index);
            cardView.setVisibility(View.VISIBLE);

            if (cardView.getChildCount() > 0 && cardView.getChildAt(0) instanceof ImageView) {
                ImageView imageView = (ImageView) cardView.getChildAt(0);

                // 썸네일 이미지 설정
                ArrayList<String> uris = product.getImageUris();
                if (uris != null && !uris.isEmpty()) {
                    try {
                        imageView.setImageURI(Uri.parse(uris.get(0)));
                    } catch (Exception e) {
                        imageView.setImageResource(R.drawable.ic_launcher_background);
                    }
                } else {
                    imageView.setImageResource(R.drawable.ic_launcher_background);
                }
            }

            // 클릭 시 해당 상품 상세 페이지로 이동
            cardView.setOnClickListener(v -> {
                Intent intent = new Intent(ProductDetailActivity.this, ProductDetailActivity.class);
                intent.putExtra(KEY_PRODUCT_DATA, product);
                startActivity(intent);
                finish();
            });
        } else {
            // 상품이 부족하면 카드뷰 숨김
            cardView.setVisibility(View.INVISIBLE);
        }
    }

    private void moveToImageZoomActivity(String imageUri) {
        Intent intent = new Intent(this, ImageZoomActivity.class);
        // ImageZoomActivity에서 받는 키 이름 변경 확인 ("KEY_IMAGE_URI")
        intent.putExtra("KEY_IMAGE_URI", imageUri);
        startActivity(intent);
    }

    private void moveToPurchaseActivity() {
        Intent intent = new Intent(this, OrderActivity.class);
        intent.putExtra(KEY_PRODUCT_DATA, currentProduct);
        startActivity(intent);
    }
}