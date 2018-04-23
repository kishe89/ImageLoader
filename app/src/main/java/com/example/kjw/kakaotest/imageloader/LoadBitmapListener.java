package com.example.kjw.kakaotest.imageloader;

public interface LoadBitmapListener {
    /**
     * Send event to listener when bitmap Loading finished
     * @param event
     * @return
     */
    public void onLoadSuccess(LoadEvent event);
}
