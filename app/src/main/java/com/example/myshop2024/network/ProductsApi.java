package com.example.myshop2024.network;


import com.example.myshop2024.dto.ProductItemDTO;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ProductsApi {


    @GET("/api/Product/all")
    //@GET("/api/Category")
    public Call<List<ProductItemDTO>> list();

}