package com.example.pet_products_flea_market;

import android.content.Intent;
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

        ArrayList<Integer> catTowerImages = new ArrayList<>();
        catTowerImages.add(R.drawable.cat_tower);
        productDataList.add(new Product(1,"캣타워", "30,000원","사용감 없고 흠집 살짝 있어요.\n직거래 희망합니다.", catTowerImages));

        ArrayList<Integer> catFoodImages = new ArrayList<>();
        catFoodImages.add(R.drawable.cat_food);
        productDataList.add(new Product(2,"고양이 사료", "10,000원","우리 냥이가 안 먹어서 팔아요. 미개봉입니다.", catFoodImages));

        ArrayList<Integer> hamHouseImages = new ArrayList<>();
        hamHouseImages.add(R.drawable.hamster_house);
        productDataList.add(new Product(3,"햄스터 케이지", "20,000원","햄스터가 집에 안들어가요ㅠㅠ", hamHouseImages));

        ArrayList<Integer> woodChipsImages = new ArrayList<>();
        woodChipsImages.add(R.drawable.wood_chips);
        productDataList.add(new Product(4,"톱밥", "2,000원","우리 아이 영양 톱밥입니당", woodChipsImages));
    }

    private void initRecyclerView() {
        productAdapter = new ProductAdapter(productDataList, product ->{ // 클릭 리스너로 전달 -> 상세 페이지로 이동
            Intent intent = new Intent(ProductListActivity.this, ProductDetailActivity.class);
            intent.putExtra(ProductDetailActivity.KEY_PRODUCT_DATA, product);
            startActivity(intent);
        });
        rvProductList.setLayoutManager(new LinearLayoutManager(this));
        rvProductList.setAdapter(productAdapter);
    }
}
