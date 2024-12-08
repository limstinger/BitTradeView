package com.example.project;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class RetrofitClient {
    private static Retrofit retrofit = null;
    private static final String BASE_URL = "http://12.345.678.16:5000/"; // EC2 서버 주소와 포트(임의의 포트 번호)

    public static ApiService getApiService() {
        if (retrofit == null) {
            // Gson 설정을 느슨하게 적용
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            // 로깅 인터셉터 설정
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            // OkHttpClient에 로깅 인터셉터 추가
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(logging);

            // Retrofit 인스턴스 생성
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson)) // Gson 컨버터 설정
                    .client(httpClient.build())
                    .build();
        }
        return retrofit.create(ApiService.class);
    }
}
