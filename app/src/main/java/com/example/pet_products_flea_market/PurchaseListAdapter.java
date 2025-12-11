package com.example.pet_products_flea_market;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * 구매내역 리스트 데이터를 ListView의 각 줄(item) 화면으로 변환하는 어댑터
 * - 이름, 가격, 주소, 결제수단을 item_purchase.xml에 표시
 */
public class PurchaseListAdapter extends BaseAdapter {

    private Context context;
    private List<Purchase> list;

    public PurchaseListAdapter(Context context, List<Purchase> list) {
        this.context = context;
        this.list = list;
    }
    // 리스트 총 개수 반환
    @Override
    public int getCount() { return list.size(); }

    // 특정 위치(i)의 데이터 객체 반환
    @Override
    public Object getItem(int i) { return list.get(i); }

    // 아이템 고유 ID
    @Override
    public long getItemId(int i) { return i; }

    // 리스트의 한 줄을 어떻게 표시할지 정의
    @Override
    public View getView(int i, View convertView, ViewGroup parent) {

        View v = View.inflate(context, R.layout.item_purchase, null);

        TextView name = v.findViewById(R.id.productName);
        TextView price = v.findViewById(R.id.productPrice);
        TextView address = v.findViewById(R.id.productAddress);
        TextView pay = v.findViewById(R.id.productPay);

        Purchase item = list.get(i);

        name.setText(item.getName());
        price.setText("가격: " + item.getPrice());
        address.setText("주소: " + item.getAddress());
        pay.setText("결제수단: " + item.getPay());

        return v;
    }
}


