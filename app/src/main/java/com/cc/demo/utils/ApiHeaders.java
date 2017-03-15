package com.cc.demo.utils;

import java.util.HashMap;

public class ApiHeaders {

    private HashMap<String, String> headers = new HashMap<>();

    public void setHeaders(HashMap<String, String> data) {
        headers = (HashMap<String, String>) data.clone();
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public void removeHeader(String key) {
        headers.remove(key);
    }

    public void clearHeaders() {
        headers.clear();
    }

    public HashMap<String, String> getHeaders() {
        return headers;
    }
}
