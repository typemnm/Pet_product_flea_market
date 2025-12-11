package com.example.pet_products_flea_market;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private RecyclerView rvHistory;
    private ProductAdapter adapter;
    private ProductDBHelper dbHelper;
    private String userId;
    private String mode; // "SALES" or "PURCHASE"

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        userId = getIntent().getStringExtra("USER_ID");
        mode = getIntent().getStringExtra("MODE");
        dbHelper = new ProductDBHelper(this);

        rvHistory = findViewById(R.id.rvProductList);
        rvHistory.setLayoutManager(new LinearLayoutManager(this));

        // 하단바나 FAB 등 불필요한 뷰 숨김 처리
        if (findViewById(R.id.bottomNavigationView) != null) {
            findViewById(R.id.bottomNavigationView).setVisibility(android.view.View.GONE);
        }
        if (findViewById(R.id.btnAddProduct) != null) {
            findViewById(R.id.btnAddProduct).setVisibility(android.view.View.GONE);
        }

        loadHistory();
    }

    private void loadHistory() {
        List<Product> list;
        if ("SALES".equals(mode)) {
            // 내 판매 내역 (샘플 제외)
            list = dbHelper.loadUserSales(userId);
            setTitle("판매 내역");
        } else {
            // 내 구매 내역
            list = dbHelper.loadUserPurchases(userId);
            setTitle("구매 내역");
        }

        adapter = new ProductAdapter(list, product -> {
            // 상세 화면으로 이동
            Intent intent = new Intent(HistoryActivity.this, ProductDetailActivity.class);
            intent.putExtra(ProductDetailActivity.KEY_PRODUCT_DATA, product);
            intent.putExtra("USER_ID", userId);
            startActivity(intent);
        });
        rvHistory.setAdapter(adapter);
    }
}