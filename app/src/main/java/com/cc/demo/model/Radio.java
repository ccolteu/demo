package com.cc.demo.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Radio implements Parcelable, Serializable {

    private static final long serialVersionUID = 1L;

    @SerializedName("title")
    private String mTitle;
    @SerializedName("logo")
    private String mLogo;

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getLogo() {
        return mLogo;
    }

    public void setLogo(String logo) {
        this.mLogo = logo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mTitle);
        dest.writeString(this.mLogo);
    }

    public Radio() {
    }

    protected Radio(Parcel in) {
        this.mTitle = in.readString();
        this.mLogo = in.readString();
    }

    public static final Creator<Radio> CREATOR = new Creator<Radio>() {
        @Override
        public Radio createFromParcel(Parcel source) {
            return new Radio(source);
        }

        @Override
        public Radio[] newArray(int size) {
            return new Radio[size];
        }
    };
}