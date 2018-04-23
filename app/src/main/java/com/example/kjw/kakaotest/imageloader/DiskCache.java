package com.example.kjw.kakaotest.imageloader;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class DiskCache {
    private static final String TAG = "DiskCache";
    private static final float DEFAULT_CACHE = 0.75f;
    private static final int FILE_NUMBER_LIMIT = 65535;
    @SuppressLint("StaticFieldLeak")
    private static volatile DiskCache instance;
    private Map<String, String> map;
    @SuppressLint("StaticFieldLeak")
    private static Context context;

    private DiskCache(Context context) {
        final int Capacity = FILE_NUMBER_LIMIT;
        DiskCache.context = context;
        Log.e(TAG,"Usage Capacity FILE_NUMBER_LIMIT : "+Capacity);
        map = Collections.synchronizedMap(new LinkedHashMap<String,String>(Capacity,DEFAULT_CACHE,true){
            @Override
            protected boolean removeEldestEntry(Entry eldest) {
                Log.e(TAG,"REMOVE OLD File : "+(size() > Capacity)+"| key : "+eldest.getKey());
                return size() > Capacity;
            }
        });
        Log.e(TAG,"Size : "+map.size());
    }

    public static synchronized DiskCache getInstance(Context context) {
        if(instance != null) return instance;
        instance = new DiskCache(context);
        return instance;
    }
    public synchronized String get(String key){
        return map.get(key);
    }
    public synchronized int usedEntries(){return map.size();}
    public synchronized void put(String key, Bitmap bitmap) {
        new PutCacheDisk(context,map,key,bitmap).execute();
    }
}
