package com.cc.demo.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.cc.demo.DemoApplication;
import com.cc.demo.apis.Apis;
import com.cc.demo.model.Radio;

import java.util.List;

import javax.inject.Inject;

import dagger.Lazy;
import retrofit.RetrofitError;

public class LocalService extends Service {

    @Inject
    Lazy<Apis> mApis;

    public class LocalServiceBinder extends Binder {

        public LocalService getService() {
            // Return this instance of RemoteService
            // so clients can call public methods
            return LocalService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new LocalServiceBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // inject Dagger components/modules
        DemoApplication.getComponent().inject(this);
    }

    public interface OnGetData {
        void onGotData(List<Radio> data);
        void onError(String error);
    }

    public void getData(final OnGetData listener) {
        mApis.get().getRadios(new retrofit.Callback<List<Radio>>() {
            @Override
            public void success(List<Radio> radios, retrofit.client.Response response) {
                if (listener != null) {
                    listener.onGotData(radios);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (listener != null) {
                    listener.onError(error.getMessage());
                }
            }
        });
    }
}
