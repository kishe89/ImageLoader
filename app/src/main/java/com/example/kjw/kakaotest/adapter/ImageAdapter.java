package com.example.kjw.kakaotest.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

import com.example.kjw.kakaotest.http.Image;
import com.example.kjw.kakaotest.viewholder.BaseViewHolder;
import com.example.kjw.kakaotest.viewholder.ImageViewHolder;

import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final int IMAGE_VIEW = 1;
    private final static String TAG = "ImageAdapter";
    private ArrayList<Image> mList;
    public ImageAdapter() {
        mList = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Log.e(TAG,viewType+"");
        if(viewType == IMAGE_VIEW){
            return ImageViewHolder.newInstance(parent);
        }else{
            return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((BaseViewHolder)holder).onBindView(getItem(position), position);
    }

    @Override
    public int getItemViewType(int position) {
        return IMAGE_VIEW;
    }

    public Image getItem(int position){
        return mList.get(position);
    }
    @Override
    public int getItemCount() {
        return mList.size();
    }

    public ArrayList<Image> getmList() {
        return mList;
    }

    public void setmList(ArrayList<Image> mList) {
        this.mList = mList;
    }
    public void addItem(Image image){
        mList.add(image);
    }
    public void removeItem(int position){
        mList.remove(position);
    }
}
