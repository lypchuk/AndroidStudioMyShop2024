package com.example.myshop2024.categoty;


import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myshop2024.R;
import com.example.myshop2024.dto.CategoryItemDTO;
import com.example.myshop2024.services.ApplicationNetwork;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import androidx.appcompat.app.AppCompatActivity;

//class for create one items list categories
public class CategoryCardViewHolder extends RecyclerView.ViewHolder {
    private TextView categoryName;
    private ImageView ivCategoryImage;
    private int id;
    private Context context;

    Dialog dialog;

    private Button btnDialogDeleteCancel;
    private Button btnDialogDeleteAgree;

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

        context = itemView.getContext();
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.category_delete_dialog);

        btnDialogDeleteCancel = dialog.findViewById(R.id.btnDialogCancel);
        btnDialogDeleteAgree = dialog.findViewById(R.id.btnDialogAgree);

        btnDialogDeleteCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                Log.v("OK", "it work dialog cancel");
            }
        });

        btnDialogDeleteAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApplicationNetwork.getInstance().getCategoriesApi().deleteCategoryById(id)
                        .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Log.v("category id: " + id , " delete");
                        }
                        dialog.cancel();

                        Activity activity = (Activity) context;
                        activity.recreate();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable throwable) {
                        Log.v("BAD" , "category id: " + id + " NOT delete");
                        dialog.cancel();
                    }
                });
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ApplicationNetwork.getInstance().getCategoriesApi().deleteCategoryById(id);
                //Call<Void> call = MainActivity.api.dele
                //int count = 1;
                //AlertDialog dialog = builder.create();

                dialog.show();
                Log.v("it work", String.valueOf(id));

                //finish();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Activity activity = (Activity) context;
                Intent intent = new Intent(activity,CategoryEditActivity.class);
                Bundle b = new Bundle();
                b.putInt("id", (id));
                intent.putExtras(b);


                Log.v("OK id: " , String.valueOf(id));

                startActivity(context,intent, b);
                //activity.finish();

//                ApplicationNetwork.getInstance().getCategoriesApi().getById(id).enqueue(new Callback<CategoryItemDTO>() {
//                    @Override
//                    public void onResponse(Call<CategoryItemDTO> call, Response<CategoryItemDTO> response) {
//                        if(response.isSuccessful())
//                        {
//                            Log.v("OK" , "category id: " + response.body().getId()+"/category name: " + response.body().getName());
//
//
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<CategoryItemDTO> call, Throwable throwable) {
//                        Log.v("BAD", "category get id:  ERROR");
//                    }
//                });
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
