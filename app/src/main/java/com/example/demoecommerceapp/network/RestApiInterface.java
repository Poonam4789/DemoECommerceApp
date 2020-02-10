package com.example.demoecommerceapp.network;

import com.example.demoecommerceapp.CommercialProductsResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RestApiInterface
{
    @GET("/json")
    Call<CommercialProductsResponse> getCommercialProducts();
}