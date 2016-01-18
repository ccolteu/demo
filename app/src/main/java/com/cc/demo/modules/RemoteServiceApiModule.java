package com.cc.demo.modules;

import android.content.Context;

import com.cc.demo.apis.RemoteServiceApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RemoteServiceApiModule {

    private final Context context;

    public RemoteServiceApiModule(Context ctx) {
        this.context = ctx;
    }

    @Provides
    @Singleton
    RemoteServiceApi providesRemoteServiceApi() {
        return RemoteServiceApi.getInstance(context);
    }
}
