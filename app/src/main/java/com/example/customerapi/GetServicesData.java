package com.example.customerapi;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetServicesData {
    @GET("11")
    Call<JsonObject> getCustomerData();
}
