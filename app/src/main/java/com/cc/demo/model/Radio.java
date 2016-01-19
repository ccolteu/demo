package com.cc.demo.model;

import android.os.Parcelable;

//@Parcel
public class Radio implements android.os.Parcelable {

    private String title;
    private String logo;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
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
        dest.writeString(title);
        dest.writeString(logo);
    }

    private void readFromParcel(android.os.Parcel in) {
        title = in.readString();
        logo = in.readString();
    }
}