package com.example.kjw.kakaotest.http;

import java.util.concurrent.TimeUnit;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

public class ServiceGenerator {

    public static final String BASE_URL = "http://127.0.0.1/";


    private static ConnectionPool pool = new ConnectionPool(10,5, TimeUnit.MINUTES);

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder().connectionPool(pool);
    private static Retrofit.Builder pageBuilder = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(Parser.FACTORY);
    private static Retrofit.Builder imageBuilder = new Retrofit.Builder()
            .baseUrl(BASE_URL);
    public static <S> S createPageDownService(Class<S> serviceClass){
        Retrofit retrofit;
        pageBuilder.client(httpClient.build());
        retrofit = pageBuilder.build();

        return retrofit.create(serviceClass);
    }
    public static <S> S createImageDownService(Class<S> serviceClass){
        Retrofit retrofit;
        imageBuilder.client(httpClient.build());
        retrofit = imageBuilder.build();

        return retrofit.create(serviceClass);
    }
}
