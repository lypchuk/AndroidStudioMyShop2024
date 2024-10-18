package com.example.myshop2024.network;

import com.example.myshop2024.dto.CategoryItemDTO;

import java.util.List;
import java.util.Map;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;

public interface CategoriesApi {
    //interface to work with categories api
    @Multipart
    @POST("/api/Category/create")
    //@POST("/api/category")
    public Call<Void> create(@PartMap Map<String, RequestBody> params,
                             @Part MultipartBody.Part imageFile);

    @GET("/api/Category/all")
    //@GET("/api/Category")
    public Call<List<CategoryItemDTO>> list();

    @GET("/api/Category/get/{id}")
    Call<CategoryItemDTO> getById(@Path("id") int id);

//    @PUT("/api/Category/edit")
//    Call<Void> setCategoryById(@Path("id") int id, @Body User user);

    @DELETE("/api/Category/delete/{id}")
    Call<Void> deleteCategoryById(@Path("id") int id);
}