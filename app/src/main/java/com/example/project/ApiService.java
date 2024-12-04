package com.example.project;

import retrofit2.Call;
import retrofit2.http.GET;
import java.util.List;

public interface ApiService {
    @GET("/trades")
    Call<List<Trade>> getTrades();
}
