package com.example.myshop2024.categoty;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.myshop2024.BaseActivity;
import com.example.myshop2024.MainActivity;
import com.example.myshop2024.R;
import com.example.myshop2024.constants.Urls;
import com.example.myshop2024.dto.CategoryItemDTO;
import com.example.myshop2024.services.ApplicationNetwork;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryEditActivity extends BaseActivity {


    private int id;
    private CategoryItemDTO item = new CategoryItemDTO();

    TextInputLayout editCategoryID;
    TextInputLayout editCategoryName;
    TextInputLayout editCategoryDescription;
    ImageView editSelectImage;

    private String imagePath;
    private static final int PICK_IMAGE = 1;


    @SuppressLint("MissingInflatedId")
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

        editCategoryID = findViewById(R.id.editCategoryID);
        editCategoryName = findViewById(R.id.editCategoryName);
        editCategoryDescription = findViewById(R.id.editCategoryDescription);
        editSelectImage = findViewById(R.id.editSelectImage);

        loadCategory();
    }

//    private CategoryItemDTO loadCategory(){
//        Call<CategoryItemDTO> call = ApplicationNetwork.getInstance().getCategoriesApi().getById(id);
//        try
//        {
//            Response<CategoryItemDTO> response = call.execute();
//            CategoryItemDTO apiResponse = response.body();
//            //item = response.body();
//            Log.v("execute:         " , String.valueOf(response.body()));
//            //return apiResponse;
//            return call.execute().body();
//        }
//        catch (Exception ex)
//        {
//            ex.printStackTrace();
//        }
//        return null;
//    }

    private void loadCategory(){
        ApplicationNetwork.getInstance().getCategoriesApi().getById(id).enqueue(new Callback<CategoryItemDTO>() {
            @Override
            public void onResponse(Call<CategoryItemDTO> call, Response<CategoryItemDTO> response) {
                if(response.isSuccessful())
                {
                    item = response.body();
                    Log.v("OK" , "category id: " + response.body().getId()+"/category name: " + response.body().getName());
                    Log.v("OK" , "category id: " + item.getId()+"/category name: " + item.getName());
                    Log.v("item" , String.valueOf(item));

                    editCategoryID.getEditText().setText(String.valueOf(item.getId()));
                    //editCategoryID.getEditText().setText(item.getId());
                    editCategoryName.getEditText().setText(item.getName());
                    editCategoryDescription.getEditText().setText(item.getDescription());

                    String ImageUrl = item.getImagePath();
                    ImageUrl = ImageUrl.replace("\\","/");
                    String url = Urls.BASE+"/"+ImageUrl;

                    Glide.with(CategoryEditActivity.this).load(url)
                            .apply(new RequestOptions()
                                    .override(300))
                            .into(editSelectImage);
                }


            }

            @Override
            public void onFailure(Call<CategoryItemDTO> call, Throwable throwable) {
                Log.v("BAD", "category get id:  ERROR");
            }
        });
    }



    public void onCategorySaveChanges(View view) {

        int id = Integer.parseInt(Objects.requireNonNull(editCategoryID.getEditText())
                .getText().toString().trim());
        String name = Objects.requireNonNull(editCategoryName.getEditText())
                .getText().toString().trim();
        String description = Objects.requireNonNull(editCategoryDescription.getEditText())
                .getText().toString().trim();
        Log.v("edit item", id + "###" + name + "###" + description);

        Map<String, RequestBody> params = new HashMap<>();
        params.put("id", RequestBody.create(MediaType.parse("text/plain"), String.valueOf((id))));
        params.put("name", RequestBody.create(MediaType.parse("text/plain"), name));
        params.put("description", RequestBody.create(MediaType.parse("text/plain"), description));

        MultipartBody.Part imagePart = null;
        if (imagePath != null) {
            File imageFile = new File(imagePath);
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), imageFile);//uploadingImages
            imagePart = MultipartBody.Part.createFormData("imageFile", imageFile.getName(), requestFile);
        }

        Log.v("item", params.toString());



        ApplicationNetwork.getInstance()
                .getCategoriesApi()
                //.edit(params, imagePart)
                .edit(params, null)
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {

                            Intent intent = new Intent(CategoryEditActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable throwable) {

                    }
                });

    }


    public void openEditGallery(View view) {
        //open gallery on smartphone
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE);
    }

}

