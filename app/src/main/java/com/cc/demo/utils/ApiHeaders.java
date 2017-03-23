package com.cc.demo.utils;

import java.util.HashMap;

public class ApiHeaders {

    private HashMap<String, String> mHeaders = new HashMap<>();

    public void setHeaders(HashMap<String, String> data) {
        mHeaders = (HashMap<String, String>) data.clone();
    }

    public void addHeader(String key, String value) {
        mHeaders.put(key, value);
    }

    public void removeHeader(String key) {
        mHeaders.remove(key);
    }

    public void clearHeaders() {
        mHeaders.clear();
    }

    public HashMap<String, String> getHeaders() {
        return mHeaders;
    }
}
