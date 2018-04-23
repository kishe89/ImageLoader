package com.example.kjw.kakaotest.http;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface PageApi {
    @GET("collections/archive/slim-aarons.aspx")
    Call<ImageList> pageDown();
}
