package com.example.kjw.kakaotest.imageloader;

import android.widget.ImageView;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class LoadEvent {
    private boolean success;
    private ImageView imageView;
    private String url;
    private Call<ResponseBody> imageDownRequest;

    public LoadEvent(boolean success, Call<ResponseBody> imageDownRequest, ImageView imageView, String url) {
        this.success = success;
        this.imageDownRequest = imageDownRequest;
        this.imageView = imageView;
        this.url = url;
    }

    public boolean isSuccess(){
        return success;
    }
    public Call<ResponseBody> getImageDownRequest(){
        return imageDownRequest;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public String getUrl() {
        return url;
    }
}
