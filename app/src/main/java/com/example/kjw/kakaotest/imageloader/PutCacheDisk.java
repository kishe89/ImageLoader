package com.example.kjw.kakaotest.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

public class PutCacheDisk extends AsyncTask<Void, String, String>{

    private static final String TAG = "PutCacheDisk";
    private Context context;
    private Map map;
    private String key;
    private Bitmap bitmap;
    public PutCacheDisk(Context context, Map<String, String> map, String key, Bitmap bitmap) {
        this.context = context;
        this.map = map;
        this.key = key;
        this.bitmap = bitmap;
    }

    @Override
    protected String doInBackground(Void... voids) {
        File cacheDir= context.getCacheDir();
        File cacheFile = null;
        Log.e(TAG,cacheDir.getPath());
        if(cacheDir != null){
            if(cacheDir.exists()){
                /**
                 * default cache directory exist
                 */
                Log.e(TAG,"CacheDirectory Exist");
                cacheFile = new File(cacheDir,Uri.parse(key).getLastPathSegment());
                try {
                    cacheFile.createNewFile();
                    FileOutputStream fos = new FileOutputStream(cacheFile);
                    bitmap.compress(Bitmap.CompressFormat.JPEG,100,fos);
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return "";
                }
                Log.e(TAG,cacheFile.getPath());
            }
        }
        return cacheFile.getPath();
    }

    @Override
    protected void onPostExecute(String path) {
        super.onPostExecute(path);
        map.put(key,path);

    }
}
