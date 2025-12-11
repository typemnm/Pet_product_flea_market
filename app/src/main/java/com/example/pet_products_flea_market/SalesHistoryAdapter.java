package com.example.pet_products_flea_market;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SalesHistoryAdapter extends RecyclerView.Adapter<SalesHistoryAdapter.ViewHolder> {

    private List<Product> productList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onDeleteClick(Product product, int position); // 삭제 클릭 인터페이스
        void onItemClick(Product product); // 아이템 클릭(상세이동) 인터페이스
    }

    public SalesHistoryAdapter(List<Product> productList, OnItemClickListener listener) {
        this.productList = productList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_sales_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.bind(product, listener, position);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct;
        TextView txtName;
        TextView txtPrice;
        Button btnDelete;

        ViewHolder(View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            txtName = itemView.findViewById(R.id.txtName);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }

        void bind(final Product product, final OnItemClickListener listener, int position) {
            txtName.setText(product.getName());
            txtPrice.setText(product.getPrice());

            // 이미지 로드 (URI 방식)
            ArrayList<String> imageUris = product.getImageUris();
            if (imageUris != null && !imageUris.isEmpty()) {
                String uriString = imageUris.get(0);
                try {
                    if (uriString.startsWith("android.resource")) {
                        String[] parts = uriString.split("/");
                        String resourceName = parts[parts.length - 1];
                        int resId = itemView.getContext().getResources().getIdentifier(resourceName, "drawable", itemView.getContext().getPackageName());
                        imgProduct.setImageResource(resId);
                    } else {
                        imgProduct.setImageURI(Uri.parse(uriString));
                    }
                } catch (Exception e) {
                    imgProduct.setImageResource(R.drawable.ic_launcher_background);
                }
            } else {
                imgProduct.setImageResource(R.drawable.ic_launcher_background);
            }

            // 아이템 클릭 시 상세 화면 이동
            itemView.setOnClickListener(v -> listener.onItemClick(product));

            // 삭제 버튼 클릭 시
            btnDelete.setOnClickListener(v -> listener.onDeleteClick(product, position));
        }
    }
}