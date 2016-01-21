package com.cc.demo.modules;

import android.content.Context;

import com.cc.demo.apis.LocalServiceApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class LocalServiceApiModule {

    private final Context context;

    public LocalServiceApiModule(Context ctx) {
        this.context = ctx;
    }

    @Provides
    @Singleton
    LocalServiceApi providesLocalServiceApi() {
        return LocalServiceApi.getInstance(context);
    }
}
