package com.example.pet_products_flea_market;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView; // 추가
import com.google.android.material.floatingactionbutton.FloatingActionButton; // 추가
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private RecyclerView rvHistory;
    private TextView tvEmptyMessage;
    private TextView tvPageTitle;
    private ProductDBHelper dbHelper;
    private String userId;
    private String mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        userId = getIntent().getStringExtra("USER_ID");
        mode = getIntent().getStringExtra("MODE");
        dbHelper = new ProductDBHelper(this);

        rvHistory = findViewById(R.id.rvProductList);
        tvEmptyMessage = findViewById(R.id.tvEmptyMessage);
        tvPageTitle = findViewById(R.id.tvPageTitle);

        rvHistory.setLayoutManager(new LinearLayoutManager(this));

        BottomNavigationView nav = findViewById(R.id.bottomNavigationView);
        if (nav != null) nav.setVisibility(View.GONE);

        FloatingActionButton fab = findViewById(R.id.btnAddProduct);
        if (fab != null) fab.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        View nav = findViewById(R.id.bottomNavigationView);
        if (nav != null) nav.setVisibility(View.GONE);

        loadHistory();
    }

    private void loadHistory() {
        if (userId == null) userId = "";

        List<Product> list;

        if ("SALES".equals(mode)) {
            list = dbHelper.loadUserSales(userId);
            if (tvPageTitle != null) tvPageTitle.setText("판매 내역");
            setupSalesAdapter(list);

        } else {
            list = dbHelper.loadUserPurchases(userId);
            if (tvPageTitle != null) tvPageTitle.setText("구매 내역");
            setupPurchaseAdapter(list);
        }

        if (list.isEmpty()) {
            rvHistory.setVisibility(View.GONE);
            if (tvEmptyMessage != null) {
                tvEmptyMessage.setVisibility(View.VISIBLE);
                if("SALES".equals(mode)) tvEmptyMessage.setText("판매한 내역이 없습니다.");
                else tvEmptyMessage.setText("구매한 내역이 없습니다.");
            }
        } else {
            rvHistory.setVisibility(View.VISIBLE);
            if (tvEmptyMessage != null) tvEmptyMessage.setVisibility(View.GONE);
        }
    }

    private void setupSalesAdapter(List<Product> list) {
        SalesHistoryAdapter adapter = new SalesHistoryAdapter(list, new SalesHistoryAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(Product product, int position) {
                dbHelper.deleteProduct(product.getId());
                list.remove(position);
                rvHistory.getAdapter().notifyItemRemoved(position);
                rvHistory.getAdapter().notifyItemRangeChanged(position, list.size());
                Toast.makeText(HistoryActivity.this, "삭제되었습니다.", Toast.LENGTH_SHORT).show();
                if (list.isEmpty()) loadHistory();
            }

            @Override
            public void onItemClick(Product product) {
                Intent intent = new Intent(HistoryActivity.this, ProductDetailActivity.class);
                intent.putExtra(ProductDetailActivity.KEY_PRODUCT_DATA, product);
                intent.putExtra("USER_ID", userId);
                startActivity(intent);
            }
        });
        rvHistory.setAdapter(adapter);
    }

    private void setupPurchaseAdapter(List<Product> list) {
        PurchaseHistoryAdapter adapter = new PurchaseHistoryAdapter(list, product -> {
            Intent intent = new Intent(HistoryActivity.this, OrderResultActivity.class);
            intent.putExtra(OrderResultActivity.KEY_PRODUCT_DATA, product);
            intent.putExtra("USER_ID", userId);
            startActivity(intent);
        });
        rvHistory.setAdapter(adapter);
    }
}