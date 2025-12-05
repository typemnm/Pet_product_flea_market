package com.example.pet_products_flea_market;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProductImageAdapter extends RecyclerView.Adapter<ProductImageAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Integer> imageList;
    private OnImageClickListener listener;

    public interface OnImageClickListener {
        void onImageClick(int imageResId);
    }

    public ProductImageAdapter(Context context, ArrayList<Integer> imageList, OnImageClickListener listener) {
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
        int resId = imageList.get(position);
        holder.imgProductSlide.setImageResource(resId);
        holder.itemView.setOnClickListener(v -> listener.onImageClick(resId));
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProductSlide;

        ViewHolder(View itemView) {
            super(itemView);
            imgProductSlide = itemView.findViewById(R.id.imgProductSlide);
        }
    }
}