package com.example.myshop2024;

import static com.example.myshop2024.R.id.rcProducts;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myshop2024.categoty.CategoriesAdapter;
import com.example.myshop2024.dto.CategoryItemDTO;
import com.example.myshop2024.dto.ProductItemDTO;
import com.example.myshop2024.product.ProductsAdapter;
import com.example.myshop2024.services.ApplicationNetwork;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductActivity extends BaseActivity {

    TextView textViewProductName;
    RecyclerView rcProducts;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        rcProducts = findViewById(R.id.rcProducts);
        rcProducts.setHasFixedSize(true);
        rcProducts.setLayoutManager(new GridLayoutManager(this, 1, RecyclerView.VERTICAL, false));
        loadList();

        String url = "https://img.freepik.com/free-photo/wide-angle-shot-single-tree-growing-clouded-sky-during-sunset-surrounded-by-grass_181624-22807.jpg?size=626&ext=jpg";
    }

    void loadList() {
        //implement service categories api method list(get all)
        ApplicationNetwork
                .getInstance()
                .getProductsApi()
                .list()
                .enqueue(new Callback<List<ProductItemDTO>>() {
                    @Override
                    public void onResponse(Call<List<ProductItemDTO>> call, Response<List<ProductItemDTO>> response) {
                        //method if ok
                        List<ProductItemDTO> items = response.body();
                        ProductsAdapter ca = new ProductsAdapter(items);
                        //create list CategoryItemDTOs
                        rcProducts.setAdapter(ca);
                    }
                    @Override
                    public void onFailure(Call<List<ProductItemDTO>> call, Throwable throwable) {
                        //method if bad

                    }
                });
    }
}