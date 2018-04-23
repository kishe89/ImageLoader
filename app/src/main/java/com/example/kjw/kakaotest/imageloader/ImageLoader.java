package com.example.kjw.kakaotest.imageloader;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ImageView;

import com.example.kjw.kakaotest.R;
import com.example.kjw.kakaotest.http.ImageApi;
import com.example.kjw.kakaotest.http.ServiceGenerator;

import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImageLoader implements CompleteWork,LoadBitmapListener{

    private static final String TAG = "ImageLoader";
    @SuppressLint("StaticFieldLeak")
    private static volatile ImageLoader instance;
    private static final LinkedList<String> url = new LinkedList<>();
    private static final HashMap<ImageView,Call<ResponseBody>> runningMap = new HashMap<>();
    private static final ImageApi imageDownApi = ServiceGenerator.createImageDownService(ImageApi.class);
    private static final MemoryCache memCache = MemoryCache.getInstance();
    private Drawable loadingDrawable;
    @SuppressLint("StaticFieldLeak")
    private static DiskCache diskCache;

    private ImageLoader(Context context) {
        diskCache = DiskCache.getInstance(context);
        instance = this;
    }

    public static ImageLoader getInstance(Context context){
        if(instance != null){
            instance.loadingDrawable = context.getResources().getDrawable(R.drawable.default_background);
            return instance;
        }
        return new ImageLoader(context);
    }

    @Override
    public String toString() {
        return "ImageLoader{" +
                "TAG='" + TAG + '\'' +
                '}';
    }

    public ImageLoader setLoadingDrawableImage(Drawable drawable){
        this.loadingDrawable = drawable;
        return this;
    }
    public ImageLoader withURL(String url) {
        ImageLoader.url.push(url);
        return this;
    }

    public void into(final ImageView view) throws Exception {
        if(url.size()==0){
            throw new Exception("URL is Empty");
        }
        view.setImageDrawable(this.loadingDrawable);

        final String url = ImageLoader.url.pop();
        final Call<ResponseBody> imageDownRequest = imageDownApi.imageDown(url);
        if (isCached(view, url, imageDownRequest)) return;
        targetCheck(view, imageDownRequest);
        requestImage(view, url, imageDownRequest);
    }

    private boolean isCached(ImageView view, String url, Call<ResponseBody> imageDownRequest) {
        Bitmap bitmap = memCache.get(url);
        if(bitmap != null){
            display(bitmap, view);
            Log.e(TAG,"Cached Data Load On Memory. Key is : "+url+"\nsize is : "+memCache.usedEntries());
            return true;
        }
        String path = diskCache.get(url);
        if(path != null){
            Log.e(TAG,"Cached Data Load On Disk. Key is : "+url+"\nsize is : "+diskCache.usedEntries());
            new LoadBitmapTask(path,view,memCache,url,imageDownRequest).setLoadBitmapListener(instance).execute();
            return true;
        }
        return false;
    }

    private void targetCheck(ImageView view, Call<ResponseBody> imageDownRequest) {
        Call<ResponseBody> running = runningMap.get(view);
        if(running != null){
            Log.e(TAG,view+" has request");
            Log.e(TAG,"runningMap size : "+runningMap.size());
            view.setImageDrawable(this.loadingDrawable);
            running.cancel();
            runningMap.remove(view);
        }
        runningMap.put(view,imageDownRequest);
    }

    private void display(Bitmap bitmap, ImageView view) {
        view.setImageBitmap(bitmap);
    }

    private void requestImage(final ImageView view, final String url, Call<ResponseBody> imageDownRequest) {
        imageDownRequest.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                Log.e(TAG,response.toString());
                InputStream readableStream = response.body() != null ? response.body().byteStream() : null;
                if(readableStream == null){
                    Log.e(TAG,"response body is null");
                    return;
                }
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
                    new CreateBitmapTask(readableStream,diskCache,url,view)
                            .setOnCompletListener(instance)
                            .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                }else{
                    Log.e(TAG,"Not Support");
                }

            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                if(call.isCanceled()){
                    Log.e(TAG,"Request cancelled");
                }else{
                    Log.e(TAG,t.toString());
                }
            }
        });
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        runningMap.clear();
        url.clear();
        memCache.clearMemory();
    }

    @Override
    public void onComplete(Bitmap bitmap,String url) {
        Log.e(TAG,"decode from readableStream complete ");
        memCache.put(url,bitmap);
    }

    @Override
    public void onLoadSuccess(LoadEvent event) {
        if(!event.isSuccess()){
            requestImage(event.getImageView(),event.getUrl(),event.getImageDownRequest());
        }
    }
}
