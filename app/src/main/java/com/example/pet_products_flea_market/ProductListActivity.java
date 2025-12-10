package com.example.pet_products_flea_market;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast; // Toast 메시지 사용을 위해 추가

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView; // BottomNavigationView 임포트 추가

import java.util.ArrayList;
import java.util.List;

public class ProductListActivity extends AppCompatActivity {

    private RecyclerView rvProductList;
    private ProductAdapter productAdapter;
    private List<Product> productDataList;
    private BottomNavigationView bottomNavigationView; // 하단 네비게이션 변수 선언

    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        userId = getIntent().getStringExtra("USER_ID");

        initViews();
        loadSampleData();
        initRecyclerView();
        initBottomNavigation(); // 하단 네비게이션 초기화 함수 호출
    }

    private void initViews() {
        rvProductList = findViewById(R.id.rvProductList);
        bottomNavigationView = findViewById(R.id.bottomNavigationView); // XML의 id와 연결
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
        productAdapter = new ProductAdapter(productDataList, product ->{
            Intent intent = new Intent(ProductListActivity.this, ProductDetailActivity.class);
            intent.putExtra(ProductDetailActivity.KEY_PRODUCT_DATA, product);
            startActivity(intent);
        });
        rvProductList.setLayoutManager(new LinearLayoutManager(this));
        rvProductList.setAdapter(productAdapter);
    }

    // 하단 네비게이션 기능 구현
    private void initBottomNavigation() {
        // 현재 화면이 '홈'이므로 홈 아이템을 선택된 상태로 설정
        bottomNavigationView.setSelectedItemId(R.id.nav_home);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.nav_home) {
                // 이미 홈 화면이므로 아무 동작 안 함 (또는 최상단 스크롤 등의 기능 추가 가능)
                return true;
            } else if (itemId == R.id.nav_notice) {
                // 공지사항(MainActivity)으로 이동
                Intent intent = new Intent(ProductListActivity.this, NoticeActivity.class);
                // 필요하다면 userId 정보도 같이 넘겨줄 수 있음
                intent.putExtra("USER_ID", userId);
                startActivity(intent);
                return true;
            } else if (itemId == R.id.nav_mypage) {
                Intent intent = new Intent(ProductListActivity.this, MyPageActivity.class);
                intent.putExtra("USER_ID", userId);
                startActivity(intent);
                return true;
            }
            return false;
        });
    }
}