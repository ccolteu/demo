package com.cc.demo;

import android.app.Application;
import android.util.Log;

import com.cc.demo.components.DaggerDemoApplicationComponent;
import com.cc.demo.components.DemoApplicationComponent;
import com.cc.demo.modules.ApiModule;
import com.cc.demo.modules.DemoApplicationModule;
import com.cc.demo.modules.EventBusModule;
import com.cc.demo.modules.LocalServiceApiModule;
import com.cc.demo.modules.RemoteServiceApiModule;
import com.facebook.stetho.Stetho;

public class DemoApplication extends Application {

    // could be read from Shared Prefs
    private static final String BASE_URL = "http://claudiucolteu.com";

    private static DemoApplicationComponent demoApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        Stetho.initializeWithDefaults(this);

        demoApplicationComponent = DaggerDemoApplicationComponent.builder()
                .demoApplicationModule(new DemoApplicationModule(this))
                .apiModule(new ApiModule(BASE_URL))
                .eventBusModule(new EventBusModule())
                .remoteServiceApiModule(new RemoteServiceApiModule(this))
                .localServiceApiModule(new LocalServiceApiModule(this))
                .build();

        // Initialize the singletons so their instances
        // are bound to the application process.
        initSingletons();
    }

    public static DemoApplicationComponent getComponent() {
        return demoApplicationComponent;
    }

    protected void initSingletons() {
    }

    public void customAppMethod(String text) {
        Log.e("toto", "" + text);
    }
}
