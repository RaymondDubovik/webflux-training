package com.example.networking;

import retrofit2.Call;
import retrofit2.http.GET;

public interface LongApi {

    @GET("long-api-call")
    Call<ApiResponse> longCall();
}