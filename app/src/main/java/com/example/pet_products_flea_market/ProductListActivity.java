package com.example.pet_products_flea_market;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

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
    private FloatingActionButton btnAddProduct; // 상품 추가 버튼 (FAB)

    private ProductDBHelper dbHelper; // DB Helper
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        userId = getIntent().getStringExtra("USER_ID");
        dbHelper = new ProductDBHelper(this); // DB 초기화

        initViews();
        checkAndLoadSampleData(); // 샘플 데이터 확인 및 적재
        initRecyclerView();
        initBottomNavigation(); // 하단 네비게이션 초기화
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 화면이 다시 보일 때(예: 상품 추가 후 돌아왔을 때) 목록 갱신
        loadProductsFromDB();
    }

    private void initViews() {
        rvProductList = findViewById(R.id.rvProductList);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        btnAddProduct = findViewById(R.id.btnAddProduct); // XML에 추가한 FAB 연결

        // FAB 클릭 리스너: 상품 추가(ProductAddActivity)로 이동
        btnAddProduct.setOnClickListener(v -> {
            Intent intent = new Intent(ProductListActivity.this, ProductAddActivity.class);
            startActivity(intent);
        });
    }

    // DB에 데이터가 없으면 샘플 데이터를 추가하는 함수
    private void checkAndLoadSampleData() {
        ArrayList<Product> currentList = dbHelper.loadAllProducts();
        if (currentList.isEmpty()) {
            insertSampleDataToDB();
        }
    }

    // 리소스 ID를 URI 문자열로 변환하는 헬퍼 함수
    private String getResourceUriString(int resId) {
        return ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
                getResources().getResourcePackageName(resId) + '/' +
                getResources().getResourceTypeName(resId) + '/' +
                getResources().getResourceEntryName(resId);
    }

    // 샘플 데이터 DB 삽입 (이미지 리소스를 URI 문자열로 변환하여 저장)
    private void insertSampleDataToDB() {
        ArrayList<String> catTowerImages = new ArrayList<>();
        catTowerImages.add(getResourceUriString(R.drawable.cat_tower));
        dbHelper.insertProduct("캣타워", "30,000원", "사용감 없고 흠집 살짝 있어요.\n직거래 희망합니다.", catTowerImages);

        ArrayList<String> catFoodImages = new ArrayList<>();
        catFoodImages.add(getResourceUriString(R.drawable.cat_food));
        dbHelper.insertProduct("고양이 사료", "10,000원", "우리 냥이가 안 먹어서 팔아요. 미개봉입니다.", catFoodImages);

        ArrayList<String> hamHouseImages = new ArrayList<>();
        hamHouseImages.add(getResourceUriString(R.drawable.hamster_house));
        dbHelper.insertProduct("햄스터 케이지", "20,000원", "햄스터가 집에 안들어가요ㅠㅠ", hamHouseImages);

        ArrayList<String> woodChipsImages = new ArrayList<>();
        woodChipsImages.add(getResourceUriString(R.drawable.wood_chips));
        dbHelper.insertProduct("톱밥", "2,000원", "우리 아이 영양 톱밥입니당", woodChipsImages);
    }

    // DB에서 상품 목록을 불러와 어댑터에 갱신
    private void loadProductsFromDB() {
        productDataList = dbHelper.loadAllProducts();

        if (productAdapter == null) {
            productAdapter = new ProductAdapter(productDataList, product -> {
                Intent intent = new Intent(ProductListActivity.this, ProductDetailActivity.class);
                intent.putExtra(ProductDetailActivity.KEY_PRODUCT_DATA, product);
                startActivity(intent);
            });
            rvProductList.setAdapter(productAdapter);
        } else {
            // 어댑터 데이터 갱신 (간단히 새 인스턴스로 교체하거나, Adapter 내부에 update 메서드를 구현하여 사용 가능)
            // 여기서는 리스트를 새로 생성하여 어댑터를 다시 설정하는 방식으로 처리
            productAdapter = new ProductAdapter(productDataList, product -> {
                Intent intent = new Intent(ProductListActivity.this, ProductDetailActivity.class);
                intent.putExtra(ProductDetailActivity.KEY_PRODUCT_DATA, product);
                startActivity(intent);
            });
            rvProductList.setAdapter(productAdapter);
        }
    }

    private void initRecyclerView() {
        rvProductList.setLayoutManager(new LinearLayoutManager(this));
        loadProductsFromDB(); // 초기 로드
    }

    private void initBottomNavigation() {
        // 현재 화면이 '홈'이므로 홈 아이템을 선택된 상태로 설정
        bottomNavigationView.setSelectedItemId(R.id.nav_home);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.nav_home) {
                // 이미 홈 화면이므로 아무 동작 안 함 (필요 시 최상단 스크롤 등 추가)
                return true;
            } else if (itemId == R.id.nav_notice) {
                // 공지사항(MainActivity)으로 이동
                Intent intent = new Intent(ProductListActivity.this, MainActivity.class);
                intent.putExtra("USER_ID", userId);
                startActivity(intent);
                return true;
            } else if (itemId == R.id.nav_mypage) {
                // 마이페이지로 이동
                Intent intent = new Intent(ProductListActivity.this, MyPageActivity.class);
                intent.putExtra("USER_ID", userId);
                startActivity(intent);
                return true;
            }
            return false;
        });
    }
}