package com.example.pet_products_flea_market;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class SalesHistoryActivity extends AppCompatActivity {

    private RecyclerView rvProductList;
    private ProductAdapter productAdapter;
    private ProductDBHelper dbHelper;
    private String userId;
    private TextView tvEmptyMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        userId = getIntent().getStringExtra("USER_ID");
        dbHelper = new ProductDBHelper(this);

        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadSalesHistory();
    }

    private void initViews() {
        rvProductList = findViewById(R.id.rvProductList);
        rvProductList.setLayoutManager(new LinearLayoutManager(this));

        View bottomNav = findViewById(R.id.bottomNavigationView);
        if (bottomNav != null) bottomNav.setVisibility(View.GONE);

        View fab = findViewById(R.id.btnAddProduct);
        if (fab != null) fab.setVisibility(View.GONE);
    }

    private void loadSalesHistory() {
        ArrayList<Product> mySalesList = dbHelper.loadUserSales(userId);

        if (productAdapter == null) {
            productAdapter = new ProductAdapter(mySalesList, product -> {
                Intent intent = new Intent(SalesHistoryActivity.this, ProductDetailActivity.class);
                intent.putExtra(ProductDetailActivity.KEY_PRODUCT_DATA, product);
                intent.putExtra("USER_ID", userId);
                startActivity(intent);
            });
            rvProductList.setAdapter(productAdapter);
        } else {
            productAdapter = new ProductAdapter(mySalesList, product -> {
                Intent intent = new Intent(SalesHistoryActivity.this, ProductDetailActivity.class);
                intent.putExtra(ProductDetailActivity.KEY_PRODUCT_DATA, product);
                intent.putExtra("USER_ID", userId);
                startActivity(intent);
            });
            rvProductList.setAdapter(productAdapter);
        }

        // 제목 설정
        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle("나의 판매 내역");
        }
    }
}