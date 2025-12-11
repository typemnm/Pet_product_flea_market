package com.example.pet_products_flea_market;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class PurchaseListAdapter extends BaseAdapter {

    private Context context;
    private List<Purchase> list;

    public PurchaseListAdapter(Context context, List<Purchase> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() { return list.size(); }

    @Override
    public Object getItem(int i) { return list.get(i); }

    @Override
    public long getItemId(int i) { return i; }

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


