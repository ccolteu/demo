package com.cc.demo.modules;

import com.cc.demo.DemoApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DemoApplicationModule {
    private final DemoApplication mApp;

    public DemoApplicationModule(DemoApplication app) {
        mApp = app;
    }

    @Provides
    @Singleton
    DemoApplication provideDemoApplication() {
        return mApp;
    }
}
