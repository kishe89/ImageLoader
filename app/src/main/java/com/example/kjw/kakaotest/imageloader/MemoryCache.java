package com.example.kjw.kakaotest.imageloader;

import android.graphics.Bitmap;
import android.util.Log;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class MemoryCache {
    private static final String TAG = "MemoryCache";
    private static final float DEFAULT_CACHE = 0.75f;
    private static final int DEFAULT_IMAGE_SIZE = 4*1024*1024;
    private static final MemoryCache instance = new MemoryCache();
    private Map map;

    private MemoryCache() {
        final int Capacity = (int) (Runtime.getRuntime().maxMemory()*DEFAULT_CACHE/DEFAULT_IMAGE_SIZE);
        Log.e(TAG,"Usage memory size per 4mb from Runtime.getRuntime().maxMemory() : "+Capacity);
        map = Collections.synchronizedMap(new LinkedHashMap<String,Bitmap>(Capacity,DEFAULT_CACHE,true){
            @Override
            protected boolean removeEldestEntry(Entry eldest) {
                Log.e(TAG,"REMOVE OLD OBJECT : "+(size() > Capacity)+"| key : "+eldest.getKey());
                return size() > Capacity;
            }
        });
        Log.e(TAG,"Size : "+map.size());
    }

    public static MemoryCache getInstance() {
        return instance;
    }

    public synchronized Bitmap get (String key){
        return (Bitmap) (map.get(key));
    }
    public synchronized void put (String key, Bitmap bitmap){
        map.put(key,bitmap);
    }
    public synchronized int usedEntries(){
        return map.size();
    }
    public synchronized void clearMemory(){
        map.clear();
    }
}
