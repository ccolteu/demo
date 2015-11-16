package com.cc.demo;

import android.app.Application;
import android.util.Log;

import com.cc.demo.components.DaggerMyAppComponent;
import com.cc.demo.components.MyAppComponent;
import com.cc.demo.modules.ApiModule;
import com.cc.demo.modules.EventBusModule;
import com.cc.demo.modules.MyAppModule;

public class MyApp extends Application {

    private static final String BASE_URL = "http://claudiucolteu.com";

    private static MyAppComponent myAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        myAppComponent = DaggerMyAppComponent.builder()
                .myAppModule(new MyAppModule(this))
                .apiModule(new ApiModule(BASE_URL))
                .eventBusModule(new EventBusModule())
                .build();

        // Initialize the singletons so their instances
        // are bound to the application process.
        initSingletons();
    }

    public static MyAppComponent getComponent() {
        return myAppComponent;
    }

    protected void initSingletons() {
    }

    public void customAppMethod(String text) {
        Log.e("toto", "" + text);
    }
}