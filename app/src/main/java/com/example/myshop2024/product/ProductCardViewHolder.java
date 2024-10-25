package com.example.myshop2024.product;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myshop2024.R;
import com.example.myshop2024.categoty.CategoryEditActivity;
import com.example.myshop2024.services.ApplicationNetwork;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProductCardViewHolder extends RecyclerView.ViewHolder {
    private TextView productName;
    private ImageView ivProductImage;
    private String id;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public ProductCardViewHolder(@NonNull View itemView) {
        super(itemView);
        productName = itemView.findViewById(R.id.productName);
        ivProductImage = itemView.findViewById(R.id.ivProductImage);


    }

    public TextView getProductName() {
        return productName;
    }

    public ImageView getIvProductImage() {
        return ivProductImage;
    }

}
