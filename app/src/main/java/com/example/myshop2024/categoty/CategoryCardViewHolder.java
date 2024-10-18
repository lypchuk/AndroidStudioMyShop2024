package com.example.myshop2024.categoty;


import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myshop2024.MainActivity;
import com.example.myshop2024.R;
import com.example.myshop2024.dto.CategoryItemDTO;
import com.example.myshop2024.services.ApplicationNetwork;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

//class for create one items list categories
public class CategoryCardViewHolder extends RecyclerView.ViewHolder {
    private TextView categoryName;
    private ImageView ivCategoryImage;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private Button btnEdit;
    private Button btnDelete;

    //for create one items list categories
    public CategoryCardViewHolder(@NonNull View itemView) {
        super(itemView);
        categoryName = itemView.findViewById(R.id.categoryName);
        ivCategoryImage = itemView.findViewById(R.id.ivCategoryImage);

        btnEdit = itemView.findViewById(R.id.btnEdit);
        btnDelete = itemView.findViewById(R.id.btnDelete);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ApplicationNetwork.getInstance().getCategoriesApi().deleteCategoryById(id);
                //Call<Void> call = MainActivity.api.dele
                //int count = 1;
                Log.v("it work", String.valueOf(id));

                //finish();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.v("it work", String.valueOf(id));
            }
        });
    }

    public TextView getCategoryName() {
        return categoryName;
    }

    public ImageView getIvCategoryImage() {
        return ivCategoryImage;
    }

}

//ApplicationNetwork
//        .getInstance()
//                .getCategoriesApi()
//                .list()
//                .enqueue(new Callback<List<CategoryItemDTO>>() {
//    @Override
//    public void onResponse(Call<List<CategoryItemDTO>> call, Response<List<CategoryItemDTO>> response) {
//        List<CategoryItemDTO> items = response.body();
//        CategoriesAdapter ca = new CategoriesAdapter(items);
//        rcCategories.setAdapter(ca);
//    }
//    @Override
//    public void onFailure(Call<List<CategoryItemDTO>> call, Throwable throwable) {
//
//    }
//});