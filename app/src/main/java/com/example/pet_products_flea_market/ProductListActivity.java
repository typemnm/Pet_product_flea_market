package com.example.pet_products_flea_market;

import android.content.ContentResolver;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public class ProductListActivity extends AppCompatActivity {

    private RecyclerView rvProductList;
    private ProductAdapter productAdapter;
    private List<Product> productDataList;
    private BottomNavigationView bottomNavigationView;
    private FloatingActionButton btnAddProduct;
    private ProductDBHelper dbHelper;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        userId = getIntent().getStringExtra("USER_ID");
        // 로그인 없이 들어온 경우 테스트용 ID 할당
        if (userId == null) userId = "testUser";

        dbHelper = new ProductDBHelper(this);

        initViews();
        checkAndLoadSampleData();
        initRecyclerView();
        initBottomNavigation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadProductsFromDB();

        if (bottomNavigationView != null) {
            bottomNavigationView.setSelectedItemId(R.id.nav_home);
        }
    }

    private void initViews() {
        rvProductList = findViewById(R.id.rvProductList);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        btnAddProduct = findViewById(R.id.btnAddProduct);

        btnAddProduct.setOnClickListener(v -> {
            Intent intent = new Intent(ProductListActivity.this, ProductAddActivity.class);
            intent.putExtra("USER_ID", userId); // 판매자 ID 전달
            startActivity(intent);
        });
    }

    private void checkAndLoadSampleData() {
        // 데이터가 아예 없으면 샘플 추가
        if (dbHelper.loadAvailableProducts().isEmpty()) {
            insertSampleDataToDB();
        }
    }

    private String getResourceUriString(int resId) {
        return ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
                getResources().getResourcePackageName(resId) + '/' +
                getResources().getResourceTypeName(resId) + '/' +
                getResources().getResourceEntryName(resId);
    }

    private void insertSampleDataToDB() {
        // 샘플 데이터의 판매자는 "ADMIN_SAMPLE"로 설정하여 내 판매내역에 안 뜨게 함
        String sampleSeller = "ADMIN_SAMPLE";

        ArrayList<String> catTowerImages = new ArrayList<>();
        catTowerImages.add(getResourceUriString(R.drawable.cat_tower));
        dbHelper.insertProduct("캣타워", "30,000원", "사용감 없고 흠집 살짝 있어요.\n직거래 희망합니다.", catTowerImages, sampleSeller);

        ArrayList<String> catFoodImages = new ArrayList<>();
        catFoodImages.add(getResourceUriString(R.drawable.cat_food));
        dbHelper.insertProduct("고양이 사료", "10,000원", "우리 냥이가 안 먹어서 팔아요. 미개봉입니다.", catFoodImages, sampleSeller);

        ArrayList<String> hamHouseImages = new ArrayList<>();
        hamHouseImages.add(getResourceUriString(R.drawable.hamster_house));
        dbHelper.insertProduct("햄스터 케이지", "20,000원", "햄스터가 집에 안들어가요ㅠㅠ", hamHouseImages, sampleSeller);

        ArrayList<String> woodChipsImages = new ArrayList<>();
        woodChipsImages.add(getResourceUriString(R.drawable.wood_chips));
        dbHelper.insertProduct("톱밥", "2,000원", "우리 아이 영양 톱밥입니당", woodChipsImages, sampleSeller);
    }

    private void loadProductsFromDB() {
        // 판매 중인 상품만 가져오기
        productDataList = dbHelper.loadAvailableProducts();

        if (productAdapter == null) {
            productAdapter = new ProductAdapter(productDataList, product -> {
                Intent intent = new Intent(ProductListActivity.this, ProductDetailActivity.class);
                intent.putExtra(ProductDetailActivity.KEY_PRODUCT_DATA, product);
                intent.putExtra("USER_ID", userId); // 상세 화면에도 유저 ID 전달
                startActivity(intent);
            });
            rvProductList.setAdapter(productAdapter);
        } else {
            productAdapter = new ProductAdapter(productDataList, product -> {
                Intent intent = new Intent(ProductListActivity.this, ProductDetailActivity.class);
                intent.putExtra(ProductDetailActivity.KEY_PRODUCT_DATA, product);
                intent.putExtra("USER_ID", userId);
                startActivity(intent);
            });
            rvProductList.setAdapter(productAdapter);
        }
    }

    private void initRecyclerView() {
        rvProductList.setLayoutManager(new LinearLayoutManager(this));
        loadProductsFromDB();
    }

    private void initBottomNavigation() {
        bottomNavigationView.setSelectedItemId(R.id.nav_home);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                return true;
            } else if (itemId == R.id.nav_notice) {
                Intent intent = new Intent(ProductListActivity.this, MainActivity.class);
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