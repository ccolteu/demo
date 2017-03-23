package com.cc.demo.model;

import android.os.Parcelable;

//@Parcel
public class Radio implements android.os.Parcelable {

    private String mTitle;
    private String mLogo;

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getLogo() {
        return mLogo;
    }

    public void setLogo(String logo) {
        mLogo = logo;
    }

    /*
    Parcelable implementation
     */
    public int describeContents() {
        return 0;
    }

    public Radio() {
    }

    public Radio(android.os.Parcel in) {
        readFromParcel(in);
    }

    public static final Parcelable.Creator<Radio> CREATOR = new Parcelable.Creator<Radio>() {
        public Radio createFromParcel(android.os.Parcel in) {
            return new Radio(in);
        }

        public Radio[] newArray(int size) {
            return new Radio[size];
        }
    };

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeString(mTitle);
        dest.writeString(mLogo);
    }

    private void readFromParcel(android.os.Parcel in) {
        mTitle = in.readString();
        mLogo = in.readString();
    }
}