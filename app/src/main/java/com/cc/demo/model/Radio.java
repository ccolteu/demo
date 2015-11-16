package com.cc.demo.model;

import org.parceler.Parcel;

@Parcel
public class Radio {

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
}