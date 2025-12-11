package com.example.pet_products_flea_market;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class PurchaseListActivity extends AppCompatActivity {

    ListView listView;
    PurchaseListAdapter adapter;
    ArrayList<Purchase> purchaseList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_list);

        listView = findViewById(R.id.purchaseListView);

        loadPurchaseHistory();

        adapter = new PurchaseListAdapter(this, purchaseList);
        listView.setAdapter(adapter);
    }

    private void loadPurchaseHistory() {

        SharedPreferences prefs = getSharedPreferences("PURCHASE_HISTORY", MODE_PRIVATE);

        int count = prefs.getInt("COUNT", 0);  // 저장된 개수

        for (int i = 1; i <= count; i++) {

            String name = prefs.getString("NAME_" + i, null);
            String price = prefs.getString("PRICE_" + i, null);
            String address = prefs.getString("ADDRESS_" + i, null);
            String pay = prefs.getString("PAY_" + i, null);

            // 데이터가 존재하는 경우만 추가
            if (name != null && price != null) {
                purchaseList.add(new Purchase(name, price, address, pay));
            }
        }
    }
}
