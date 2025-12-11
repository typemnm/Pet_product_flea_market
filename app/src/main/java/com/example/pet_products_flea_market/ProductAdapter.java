package com.example.pet_products_flea_market;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private List<Product> productList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Product product);
    }

    public ProductAdapter(List<Product> productList, OnItemClickListener listener) {
        this.productList = productList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.bind(product, listener);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct;
        TextView txtName;
        TextView txtPrice;

        ViewHolder(View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            txtName = itemView.findViewById(R.id.txtName);
            txtPrice = itemView.findViewById(R.id.txtPrice);
        }

        void bind(final Product product, final OnItemClickListener listener) {
            txtName.setText(product.getName());
            txtPrice.setText(product.getPrice());

            ArrayList<String> imageUris = product.getImageUris();
            if (imageUris != null && !imageUris.isEmpty()) {
                // 첫 번째 이미지를 대표 이미지로 설정
                String uriString = imageUris.get(0);
                try {
                    // 리소스 경로인지 확인 (샘플 데이터용)
                    if (uriString.startsWith("android.resource")) {
                        // 패키지명과 리소스 타입, 이름을 분리하여 ID 추출
                        String[] parts = uriString.split("/");
                        String resourceName = parts[parts.length - 1];
                        int resId = itemView.getContext().getResources().getIdentifier(resourceName, "drawable", itemView.getContext().getPackageName());
                        imgProduct.setImageResource(resId);
                    } else {
                        // 갤러리 이미지 URI인 경우
                        imgProduct.setImageURI(Uri.parse(uriString));
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    // 로드 실패 시 기본 이미지 설정 등 처리 필요
                    imgProduct.setImageResource(R.drawable.ic_launcher_background); // 임시 기본 이미지
                }
            } else {
                imgProduct.setImageResource(R.drawable.ic_launcher_background); // 이미지가 없을 경우 기본 이미지
            }

            itemView.setOnClickListener(v -> listener.onItemClick(product));
        }
    }
}