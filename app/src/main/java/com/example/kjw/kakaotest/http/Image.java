package com.example.kjw.kakaotest.http;

import android.os.Parcel;
import android.os.Parcelable;

public class Image implements Parcelable {
    private String filename;
    private String image;
    private String thumbnail;

    public Image(String filename,String image, String thumbnail) {
        this.filename = filename;
        this.image = image;
        this.thumbnail = thumbnail;
    }

    public Image(Parcel source) {
        this.filename = source.readString();
        this.image = source.readString();
        this.thumbnail = source.readString();
    }

    public String getImage() {
        return image;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    @Override
    public String toString() {
        return "Image{" +
                "filename='" + filename + '\'' +
                ", image='" + image + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(filename);
        dest.writeString(image);
        dest.writeString(thumbnail);
    }
    public static final Parcelable.Creator<Image> CREATOR = new Parcelable.Creator<Image>(){
        @Override
        public Image createFromParcel(Parcel source) {
            return new Image(source);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };
}
