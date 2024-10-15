package com.example.myshop2024;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.Converter;

public class CategoryActivity extends BaseActivity {

    TextView textViewCategoryName;

//    interface RequestCategory{
//        @GET("api/category/get/{cid}")
//        Call<Category> getCategory(@Path("cid") String cid);
//    }
    //http://newmyshop2024.somee.com/api/category/all

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_category);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        String url = "https://img.freepik.com/free-photo/wide-angle-shot-single-tree-growing-clouded-sky-during-sunset-surrounded-by-grass_181624-22807.jpg?size=626&ext=jpg";
        //String url = "http://10.0.2.2:5041/uploadingImages//fdaf8a8a-5b38-42f5-9a94-3eb402a78b7f.webp";
        //String url = "http://127.0.0.1:5041/uploadingImages//fdaf8a8a-5b38-42f5-9a94-3eb402a78b7f.webp";

        /////////////////

        //ImageView imgTest = findViewById(R.id.imgTest);
        //Glide.with(this).load(url).into(imgTest);

        //textViewCategoryName = findViewById(R.id.textViewCategoryName);

        //Retrofit retrofit  = new Retrofit.Builder().baseUrl("http://newmyshop2024.somee.com").addConverterFactory(GsonConverterFactory.create()).build();

    }
}