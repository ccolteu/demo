package com.cc.demo;

import android.content.Context;

public class Sample {
    private Context mContext;
    public Sample(Context ctx) {
        mContext = ctx;
    }
    public String getHelloWorldString() {
        return mContext.getString(R.string.hello_world);
    }
}
