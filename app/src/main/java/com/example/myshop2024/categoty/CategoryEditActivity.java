package com.example.myshop2024.categoty;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myshop2024.BaseActivity;
import com.example.myshop2024.R;
import com.example.myshop2024.dto.CategoryItemDTO;
import com.example.myshop2024.services.ApplicationNetwork;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryEditActivity extends BaseActivity {


    private int id;
    private CategoryItemDTO item = new CategoryItemDTO();

    TextInputLayout editCategoryID;
    TextInputLayout editCategoryName;
    TextInputLayout editCategoryDescription;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_category_edit);

        //Log.v("savedInstanceState" , String.valueOf(savedInstanceState));

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Bundle bundle = getIntent().getExtras();
        id = bundle.getInt("id", 0);
        Log.v("id" , String.valueOf(id));

        item = loadCategory();

        CategoryItemDTO newItem = new CategoryItemDTO();
        newItem.setName("111111");

        editCategoryID = findViewById(R.id.editCategoryID);
        editCategoryName = findViewById(R.id.editCategoryName);
        editCategoryDescription = findViewById(R.id.editCategoryDescription);
        //Log.v("Description" , item.getDescription());
        //Log.v("Item" , item.getDescription());
        //editCategoryID.getEditText().setText(item.getId());
        editCategoryName.getEditText().setText(newItem.getName());
        editCategoryDescription.getEditText().setText(item.getDescription());
    }

    private CategoryItemDTO loadCategory(){
        Call<CategoryItemDTO> call = ApplicationNetwork.getInstance().getCategoriesApi().getById(id);
//        item = ApplicationNetwork.getInstance().getCategoriesApi().getById(id).execute();
        try
        {
            Response<CategoryItemDTO> response = call.execute();
            CategoryItemDTO apiResponse = response.body();
            //item = response.body();
            Log.v("execute:         " , String.valueOf(response.body()));
            //return apiResponse;
            return call.execute().body();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        /*
        ApplicationNetwork.getInstance().getCategoriesApi().getById(id).enqueue(new Callback<CategoryItemDTO>() {
            @Override
            public void onResponse(Call<CategoryItemDTO> call, Response<CategoryItemDTO> response) {
                if(response.isSuccessful())
                {
                    Log.v("OK" , "category id: " + response.body().getId()+"/category name: " + response.body().getName());

                    //item.setId(response.body().getId());
                    //item.setDescription(response.body().getDescription());

                    item.setDescription(response.body().getDescription());
                    item.setName(response.body().getName());
                    item.setId(response.body().getId());
                    Log.v("setDescription" , item.getDescription());

                }
            }

            @Override
            public void onFailure(Call<CategoryItemDTO> call, Throwable throwable) {
                Log.v("BAD", "category get id:  ERROR");
            }
        });
        */

        return null;
    }
}