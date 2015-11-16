package com.cc.demo.modules;

import com.cc.demo.MyApp;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MyAppModule {
    private final MyApp app;

    public MyAppModule(MyApp app) {
        this.app = app;
    }

    @Provides
    @Singleton
    MyApp provideMyApp() {
        return app;
    }
}
