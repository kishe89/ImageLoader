package com.example.kjw.kakaotest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.kjw.kakaotest.adapter.ImageAdapter;
import com.example.kjw.kakaotest.http.Image;
import com.example.kjw.kakaotest.http.ImageList;
import com.example.kjw.kakaotest.http.PageApi;
import com.example.kjw.kakaotest.http.ServiceGenerator;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ImageAdapter adapter;
    private RecyclerView view;
    private ArrayList<Image> imageList;
    private TextView loading_textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState != null){
            ArrayList<Image> reloadedArray = savedInstanceState.getParcelableArrayList("imageList");
            if (reloadedArray.size() == 0){
                initFirst();
                return;
            }
            init();
            addImage(reloadedArray);
            return;
        }
        initFirst();
    }

    private void initFirst() {
        init();
        callPage();
    }

    private void init() {
        view = findViewById(R.id.imageView_Container);
        loading_textView = findViewById(R.id.loading_textView);
        view.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        imageList = new ArrayList<>();
        adapter = new ImageAdapter();
        adapter.setmList(imageList);
        view.setAdapter(adapter);
    }

    private void callPage() {
        PageApi api = ServiceGenerator.createPageDownService(PageApi.class);
        Call<ImageList> pageDownCall = api.pageDown();
        pageDownCall.enqueue(new Callback<ImageList>() {
            @Override
            public void onResponse(Call<ImageList> call, Response<ImageList> response) {
                ArrayList<Image> imageList = response.body().getmList();
                addImage(imageList);
            }

            @Override
            public void onFailure(Call<ImageList> call, Throwable t) {
                Log.e(TAG,t.toString());
            }
        });
    }

    private void addImage(ArrayList<Image> list) {
        imageList.addAll(list);
        adapter.notifyDataSetChanged();
        dismissLoadingView();
    }

    private void dismissLoadingView() {
        loading_textView.setVisibility(View.GONE);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("imageList", imageList);
        super.onSaveInstanceState(outState);
    }
}
