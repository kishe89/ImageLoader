package com.example.kjw.kakaotest.http;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

public class Parser implements Converter<ResponseBody,ImageList>{
    private static final String TAG = "Parser";
    private static final String ELEMENT_CLASS = "gallery-item-group";
    private static final String FIRST_CHILD_ELEMENT_TAG = "a";
    private static final String FIRST_CHILD_ELEMENT_ATTRIBUTE = "href";
    private static final String SECOND_CHILD_ELEMENT_TAG = "img";
    private static final String SECOND_CHILD_ELEMENT_ATTRIBUTE = "src";


    public static final Converter.Factory FACTORY = new Converter.Factory(){
        @Nullable
        @Override
        public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
            Log.e(TAG,type.toString());
            if(type == ImageList.class) return new Parser();
            return null;
        }
    };
    @Override
    public ImageList convert(@NonNull ResponseBody body) throws IOException {
        Document document = Jsoup.parse(body.string());
        Elements value = document.body().getElementsByClass(ELEMENT_CLASS);
        return createImageList(value);
    }

    private ImageList createImageList(Elements value) {
        ImageList imageList = new ImageList();
        for(Element element:value){
            String imagePath= element.child(0).select(FIRST_CHILD_ELEMENT_TAG).first().attr(FIRST_CHILD_ELEMENT_ATTRIBUTE);
            String thumbnailPath = element.child(0).select(SECOND_CHILD_ELEMENT_TAG).first().attr(SECOND_CHILD_ELEMENT_ATTRIBUTE);
            imagePath = imagePath.substring(1);
            thumbnailPath = thumbnailPath.substring(1);
            String[] splittedPath = thumbnailPath.split("/");
            String filename = splittedPath[splittedPath.length-1];
            imageList.getmList().add(new Image(filename,imagePath,thumbnailPath));
        }
        return imageList;
    }
}
