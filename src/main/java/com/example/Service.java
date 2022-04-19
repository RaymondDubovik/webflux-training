package com.example;

import java.io.IOException;

import javax.annotation.PostConstruct;

import com.example.networking.ApiResponse;
import com.example.networking.LongApi;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.context.request.async.DeferredResult;
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

    public void longApiRequest(DeferredResult<String> deferredResult) {
        try {
            ApiResponse response = api.longCall().execute().body();
            deferredResult.setResult(response.getMessage());
        } catch (IOException e) {
            deferredResult.setErrorResult("Some error object");
        }
    }
}
