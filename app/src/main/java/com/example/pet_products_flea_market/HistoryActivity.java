package com.example.pet_products_flea_market;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private RecyclerView rvHistory;
    private TextView tvEmptyMessage;
    private ProductDBHelper dbHelper;
    private String userId;
    private String mode; // "SALES" or "PURCHASE"

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list); // 텍스트뷰 ID(tvEmptyMessage)가 추가된 xml 사용 필요

        userId = getIntent().getStringExtra("USER_ID");
        mode = getIntent().getStringExtra("MODE");
        dbHelper = new ProductDBHelper(this);

        rvHistory = findViewById(R.id.rvProductList);
        tvEmptyMessage = findViewById(R.id.tvEmptyMessage);

        rvHistory.setLayoutManager(new LinearLayoutManager(this));

        if (findViewById(R.id.bottomNavigationView) != null) {
            findViewById(R.id.bottomNavigationView).setVisibility(View.GONE);
        }
        if (findViewById(R.id.btnAddProduct) != null) {
            findViewById(R.id.btnAddProduct).setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadHistory();
    }

    private void loadHistory() {
        if (userId == null) userId = "";

        List<Product> list;

        if ("SALES".equals(mode)) {
            list = dbHelper.loadUserSales(userId);
            if(getSupportActionBar() != null) getSupportActionBar().setTitle("판매 내역");

            // 판매 내역: 삭제 가능
            setupSalesAdapter(list);

        } else {
            list = dbHelper.loadUserPurchases(userId);
            if(getSupportActionBar() != null) getSupportActionBar().setTitle("구매 내역");

            // 구매 내역: 클릭 시 주문 정보 확인
            setupPurchaseAdapter(list);
        }

        // 빈 화면 처리
        if (list.isEmpty()) {
            rvHistory.setVisibility(View.GONE);
            if (tvEmptyMessage != null) {
                tvEmptyMessage.setVisibility(View.VISIBLE);
                tvEmptyMessage.setText("내역이 없습니다.");
            }
        } else {
            rvHistory.setVisibility(View.VISIBLE);
            if (tvEmptyMessage != null) tvEmptyMessage.setVisibility(View.GONE);
        }
    }

    // 판매 내역 어댑터 (SalesHistoryAdapter 사용)
    private void setupSalesAdapter(List<Product> list) {
        SalesHistoryAdapter adapter = new SalesHistoryAdapter(list, new SalesHistoryAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(Product product, int position) {
                dbHelper.deleteProduct(product.getId());
                list.remove(position);
                rvHistory.getAdapter().notifyItemRemoved(position);
                rvHistory.getAdapter().notifyItemRangeChanged(position, list.size());
                Toast.makeText(HistoryActivity.this, "삭제되었습니다.", Toast.LENGTH_SHORT).show();

                if (list.isEmpty()) loadHistory(); // 다 지웠을 때 빈 화면 갱신
            }

            @Override
            public void onItemClick(Product product) {
                // 판매자가 자기 상품 볼 때는 일반 상세화면
                Intent intent = new Intent(HistoryActivity.this, ProductDetailActivity.class);
                intent.putExtra(ProductDetailActivity.KEY_PRODUCT_DATA, product);
                intent.putExtra("USER_ID", userId);
                startActivity(intent);
            }
        });
        rvHistory.setAdapter(adapter);
    }

    // 구매 내역 어댑터
    private void setupPurchaseAdapter(List<Product> list) {
        ProductAdapter adapter = new ProductAdapter(list, product -> {
            Intent intent = new Intent(HistoryActivity.this, OrderResultActivity.class);
            intent.putExtra(OrderResultActivity.KEY_PRODUCT_DATA, product);
            intent.putExtra("USER_ID", userId);

            startActivity(intent);
        });
        rvHistory.setAdapter(adapter);
    }
}