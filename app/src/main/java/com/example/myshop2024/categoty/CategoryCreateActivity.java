package com.example.myshop2024.categoty;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.myshop2024.BaseActivity;
import com.example.myshop2024.MainActivity;
import com.example.myshop2024.R;
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

public class CategoryCreateActivity extends BaseActivity {

    private ImageView ivSelectImage;
    private static final int PICK_IMAGE = 1;

    private String imagePath;
    TextInputLayout tlCategoryName;
    TextInputLayout tlCategoryDescription;


    private final String TAG="CategoryCreateActivity";
    public  boolean isStoragePermissionGranted() {
        //check access to storage image/photo on smartphone
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted");
                return true;
            } else {

                Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else {
            //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted");
            return true;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //get data from input on form
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_category_create);

        ivSelectImage = findViewById(R.id.ivSelectImage);
        tlCategoryName = findViewById(R.id.tlCategoryName);
        tlCategoryDescription = findViewById(R.id.tlCategoryDescription);

        //String url = "http://malyska123.somee.com/images/noimage.jpg";
        String url = "http://newmyshop2024.somee.com/uploadingImages//noimage.jpg";
        Glide
                .with(this)
                .load(url)
                .apply(new RequestOptions().override(300))
                .into(ivSelectImage);


//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
        isStoragePermissionGranted();
    }

    public void openGallery(View view) {
        //open gallery on smartphone
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //used constructor base class
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            // Get the URI of the selected image
            Uri uri = data.getData();

            Glide
                    .with(this)
                    .load(uri)
                    .apply(new RequestOptions().override(300))
                    .into(ivSelectImage);

            // If you want to get the file path from the URI, you can use the following code:
            imagePath = getPathFromURI(uri);
        }
    }

    // This method converts the image URI to the direct file system path of the image file
    private String getPathFromURI(Uri contentUri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String filePath = cursor.getString(column_index);
            cursor.close();
            return filePath;
        }
        return null;
    }

    //create request on server for create category
    public void onCreateCategory(View view) {
        //input in request data from inputs and file
        String name = Objects.requireNonNull(tlCategoryName.getEditText()).getText().toString().trim();
        String description = Objects.requireNonNull(tlCategoryDescription.getEditText()).getText().toString().trim();
        Log.v(TAG, name + "###" + imagePath + "###" + description);

        Map<String, RequestBody> params = new HashMap<>();
        params.put("name", RequestBody.create(MediaType.parse("text/plain"), name));
        params.put("description", RequestBody.create(MediaType.parse("text/plain"), description));

        //use image file
        MultipartBody.Part imagePart = null;
        if (imagePath != null) {
            File imageFile = new File(imagePath);
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), imageFile);//uploadingImages
            imagePart = MultipartBody.Part.createFormData("imageFile", imageFile.getName(), requestFile);
        }

        //request on server about retrofit
        ApplicationNetwork.getInstance()
                .getCategoriesApi()
                .create(params, imagePart)
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {

                            Intent intent = new Intent(CategoryCreateActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable throwable) {

                    }
                });

    }
}