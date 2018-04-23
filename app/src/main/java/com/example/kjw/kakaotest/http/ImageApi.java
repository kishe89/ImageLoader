package com.example.kjw.kakaotest.http;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface ImageApi {
    @GET
    Call<ResponseBody> imageDown(@Url String path);
}
