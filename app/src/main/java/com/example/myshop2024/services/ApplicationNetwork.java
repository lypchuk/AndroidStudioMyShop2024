package com.example.myshop2024.services;


import com.example.myshop2024.constants.Urls;
import com.example.myshop2024.network.CategoriesApi;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApplicationNetwork {
    private static ApplicationNetwork instance;

    private Retrofit retrofit;

    public ApplicationNetwork() {
        //create interceptor
        HttpLoggingInterceptor interceptor =  new  HttpLoggingInterceptor ();
        interceptor.setLevel( HttpLoggingInterceptor.Level.BODY );

        OkHttpClient.Builder client =  new  OkHttpClient.Builder ()
                .addInterceptor(interceptor);
        //build retrofit request
        retrofit = new Retrofit.Builder()
                .baseUrl(Urls.BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build();
    }

    //instance check
    public static ApplicationNetwork getInstance() {
        if(instance==null)
            instance = new ApplicationNetwork();
        return instance;
    }

    //
    public CategoriesApi getCategoriesApi() {
        return retrofit.create(CategoriesApi.class);
    }
}