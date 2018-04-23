package com.example.kjw.kakaotest.imageloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

public class CreateBitmapTask extends AsyncTask<Void, Bitmap, Bitmap>{
    private static final String TAG = "CreateBitmapTask";
    private InputStream readableStream;
    private DiskCache diskCache;
    private String url;
    private ImageView view;
    private CompleteWork listener;

    public CreateBitmapTask(InputStream readableStream, DiskCache diskCache, String url, ImageView view) {
        this.url=url;
        this.readableStream = readableStream;
        this.diskCache = diskCache;
        this.view = view;
    }
    public CreateBitmapTask setOnCompletListener(CompleteWork listener){
        this.listener = listener;
        return this;
    }
    @Override
    protected Bitmap doInBackground(Void... voids) {
        return BitmapFactory.decodeStream(readableStream);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        if(diskCache == null) Log.e(TAG,"diskcache not open");
        diskCache.put(url,bitmap);
        view.setImageBitmap(bitmap);
        listener.onComplete(bitmap,url);
    }
}
