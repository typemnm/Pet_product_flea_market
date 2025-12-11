package com.example.pet_products_flea_market;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

/**
 * 구매 내역
 * SharedPreferences에 저장된 구매 데이터를 불러와 ListView에 표시하는 Activity
 * - DB를 사용하지 않음
 */

public class PurchaseListActivity extends AppCompatActivity {
    private String userId;
    ListView listView;
    PurchaseListAdapter adapter;
    ArrayList<Purchase> purchaseList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_list);
        //사용자 ID 받기
        userId = getIntent().getStringExtra("USER_ID");

        listView = findViewById(R.id.purchaseListView);

        // SharedPreferences에서 저장된 데이터 불러오기
        loadPurchaseHistory();

        adapter = new PurchaseListAdapter(this, purchaseList);
        listView.setAdapter(adapter);
    }

    /**
     * SharedPreferences에 저장된 구매 내역 불러오기
     * - PURCHASE_HISTORY 라는 이름의 Prefs에서 값 읽어옴
     * SharedPreferences : 서버 DB 와는 다름, 안드로이드 폰안의 'SharedPreferences'라는 작은 파일에 저장
     * 
     */
    private void loadPurchaseHistory() {

        SharedPreferences prefs = getSharedPreferences("PURCHASE_HISTORY_" + userId, MODE_PRIVATE);
        // 저장된 구매 내역 개수
        int count = prefs.getInt("COUNT", 0);

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
