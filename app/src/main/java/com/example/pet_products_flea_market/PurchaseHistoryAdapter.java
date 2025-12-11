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

public class PurchaseHistoryAdapter extends RecyclerView.Adapter<PurchaseHistoryAdapter.ViewHolder> {

    private List<Product> productList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Product product);
    }

    public PurchaseHistoryAdapter(List<Product> productList, OnItemClickListener listener) {
        this.productList = productList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 새로 만든 item_purchase_history.xml 사용
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_purchase_history, parent, false);
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
        TextView txtName, txtPrice, txtAddress, txtPayment;

        ViewHolder(View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            txtName = itemView.findViewById(R.id.txtName);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            txtAddress = itemView.findViewById(R.id.txtAddress);
            txtPayment = itemView.findViewById(R.id.txtPayment);
        }

        void bind(final Product product, final OnItemClickListener listener) {
            txtName.setText(product.getName());
            txtPrice.setText("가격: " + product.getPrice());

            String addr = product.getTradingAddress() != null ? product.getTradingAddress() : "정보 없음";
            String pay = product.getPaymentMethod() != null ? product.getPaymentMethod() : "정보 없음";

            txtAddress.setText("주소: " + addr);
            txtPayment.setText("결제수단: " + pay);

            // 이미지 로드
            ArrayList<String> imageUris = product.getImageUris();
            if (imageUris != null && !imageUris.isEmpty()) {
                try {
                    imgProduct.setImageURI(Uri.parse(imageUris.get(0)));
                } catch (Exception e) {
                    imgProduct.setImageResource(R.drawable.ic_launcher_background);
                }
            } else {
                imgProduct.setImageResource(R.drawable.ic_launcher_background);
            }

            itemView.setOnClickListener(v -> listener.onItemClick(product));
        }
    }
}