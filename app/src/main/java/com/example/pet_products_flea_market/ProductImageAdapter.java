package com.example.pet_products_flea_market;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProductImageAdapter extends RecyclerView.Adapter<ProductImageAdapter.ViewHolder> {

    private Context context;
    private ArrayList<String> imageList; // Integer -> String 변경
    private OnImageClickListener listener;

    public interface OnImageClickListener {
        void onImageClick(String imageUri);
    }

    public ProductImageAdapter(Context context, ArrayList<String> imageList, OnImageClickListener listener) {
        this.context = context;
        this.imageList = imageList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product_image, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String uriString = imageList.get(position);

        try {
            // 리소스 URI인 경우 (샘플 데이터)
            if (uriString.startsWith("android.resource")) {
                Uri uri = Uri.parse(uriString);
                holder.imgProductSlide.setImageURI(uri);
            }
            // 갤러리 이미지 URI인 경우
            else {
                holder.imgProductSlide.setImageURI(Uri.parse(uriString));
            }
        } catch (Exception e) {
            e.printStackTrace();
            holder.imgProductSlide.setImageResource(R.drawable.ic_launcher_background); // 에러 시 기본 이미지
        }

        holder.itemView.setOnClickListener(v -> listener.onImageClick(uriString));
    }

    @Override
    public int getItemCount() {
        return imageList != null ? imageList.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProductSlide;

        ViewHolder(View itemView) {
            super(itemView);
            imgProductSlide = itemView.findViewById(R.id.imgProductSlide);
        }
    }
}