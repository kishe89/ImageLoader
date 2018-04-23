package com.example.kjw.kakaotest.viewholder;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kjw.kakaotest.R;
import com.example.kjw.kakaotest.http.Image;
import com.example.kjw.kakaotest.imageloader.ImageLoader;

public class ImageViewHolder extends BaseViewHolder<Image>{

    private final String TAG = "ImageViewHolder";
    private ImageView imageView;
    private TextView urlView;
    private Context context;
    private ImageViewHolder(View itemView) {
        super(itemView);
        context = itemView.getContext();
        imageView = itemView.findViewById(R.id.imageView);
        urlView = itemView.findViewById(R.id.urlView);
    }

    @Override
    public void onBindView(Image image, int position) {
        /**
         * @TODO Drawable parameter -> String 변경 하고 이곳에서 이미지 로더로 이미지 로딩
         */
        Log.e(TAG,"position : "+position+"|Image : "+image.toString());
        try {
            urlView.setText(image.getThumbnail());
            ImageLoader.getInstance(imageView.getContext())
                    .setLoadingDrawableImage(context.getResources().getDrawable(R.drawable.loding_test))
                    .withURL(image.getThumbnail())
                    .into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @NonNull
    public static ImageViewHolder newInstance(ViewGroup parent){
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);
        return new ImageViewHolder(itemView);
    }
}
