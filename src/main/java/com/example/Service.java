package com.example;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import com.example.networking.ApiResponse;
import com.example.networking.LongApi;
import com.fasterxml.jackson.databind.ObjectMapper;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@org.springframework.stereotype.Service
public class Service {

    private LongApi api;

    @PostConstruct
    private void setup() {
        api = new Retrofit.Builder()
                .baseUrl("http://localhost:3000/")
                .addConverterFactory(JacksonConverterFactory.create(new ObjectMapper())).build()
                .create(LongApi.class);
    }

    public ApiResponse longApiRequest() throws IOException {
        return api.longCall().execute().body(); // simplified, non-production ready networking code
    }
}
