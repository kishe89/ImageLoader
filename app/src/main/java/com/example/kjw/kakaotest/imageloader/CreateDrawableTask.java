package com.example.kjw.kakaotest.imageloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.InputStream;

public class CreateDrawableTask extends AsyncTask<Void, Void, Void>{

    private InputStream readableStream;
    private MemoryCache memoryCache;
    private String url;
    private ImageView view;
    private Bitmap bitmap;
    private static final int BUFFER_SIZER = 1024*4;

    public CreateDrawableTask(MemoryCache memCache, String url) {
        this.readableStream = readableStream;
        this.memoryCache = memCache;
        this.url = url;
        this.view = view;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        memoryCache.put(url,bitmap);
        display(bitmap, view);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        BufferedInputStream bufferedInputStream = new BufferedInputStream(readableStream,BUFFER_SIZER);
        bitmap = BitmapFactory.decodeStream(bufferedInputStream);
        return null;
    }
    private void display(Bitmap bitmap, ImageView view) {
        view.setImageBitmap(bitmap);
    }
}
