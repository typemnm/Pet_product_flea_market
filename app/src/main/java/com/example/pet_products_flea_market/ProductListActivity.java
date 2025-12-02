package com.example.pet_products_flea_market;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ProductListActivity extends AppCompatActivity {

    private RecyclerView rvProductList;
    private ProductAdapter productAdapter;
    private List<Product> productDataList;
    /**
     * 저장해둔 상품들을 목록 형태로 보여주는 Activity
     * RecyclerView + Adapter로 스크롤 가능 목록 구성
     */
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        initViews();
        userId = getIntent().getStringExtra("USER_ID");

        loadSampleData();
        initRecyclerView();
    }

    private void initViews() {
        rvProductList = findViewById(R.id.rvProductList);
    }

    private void loadSampleData() {
        productDataList = new ArrayList<>();
        productDataList.add(new Product("캣타워", "30,000원", R.drawable.cat_tower));
        productDataList.add(new Product("고양이 사료", "10,000원", R.drawable.cat_food));
        productDataList.add(new Product("햄스터 케이지", "20,000원", R.drawable.hamster_house));
        productDataList.add(new Product("톱밥", "2,000원", R.drawable.wood_chips));
    }

    private void initRecyclerView() {
        productAdapter = new ProductAdapter(productDataList);
        rvProductList.setLayoutManager(new LinearLayoutManager(this));
        rvProductList.setAdapter(productAdapter);
    }
}
