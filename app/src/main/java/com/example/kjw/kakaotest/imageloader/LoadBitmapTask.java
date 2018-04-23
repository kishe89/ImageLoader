package com.example.kjw.kakaotest.imageloader;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class LoadBitmapTask extends AsyncTask<Void, Bitmap, Bitmap>{
    private static final String TAG = "LoadBitmapTask";
    private String path;
    @SuppressLint("StaticFieldLeak")
    private ImageView view;
    private MemoryCache memoryCache;
    private String url;
    private LoadBitmapListener listener;
    private Call<ResponseBody> imageDownRequest;

    LoadBitmapTask(String path, ImageView view, MemoryCache memCache, String url, Call<ResponseBody> imageDownRequest) {
        this.path = path;
        this.view = view;
        this.memoryCache = memCache;
        this.url = url;
        this.imageDownRequest = imageDownRequest;
    }
    public LoadBitmapTask setLoadBitmapListener(LoadBitmapListener listener){
        this.listener = listener;
        return this;
    }
    @Override
    protected Bitmap doInBackground(Void... voids) {
        File cacheFile = new File(path);
        if(cacheFile.exists()){
            FileInputStream fis;
            try {
                fis = new FileInputStream(cacheFile);
                return BitmapFactory.decodeStream(fis);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        if(bitmap == null){
            listener.onLoadSuccess(new LoadEvent(false,imageDownRequest,view,url));
        }
        view.setImageBitmap(bitmap);
        Log.e(TAG,"MemoryCache miss. reload to MemoryCache from diskCache : "+url);
        memoryCache.put(url,bitmap);
        listener.onLoadSuccess(new LoadEvent(true,imageDownRequest,view,url));
    }
}
