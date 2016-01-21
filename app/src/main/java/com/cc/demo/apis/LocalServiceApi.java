package com.cc.demo.apis;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import com.cc.demo.services.LocalService;

public class LocalServiceApi {

    private static LocalServiceApi instance;
    private static Context mContext;
    private static boolean isBound;
    private static LocalServiceListener mLocalServiceListener;
    private static LocalService mLocalService;

    public interface LocalServiceListener {
        void onConnected();
        void onFailedToConnect();
    }

    /*
    Public methods
     */

    public static LocalServiceApi getInstance(Context ctx) {
        if (instance == null) {
            instance = new LocalServiceApi();
            instance.mContext = ctx;
        }
        init();
        return instance;
    }

    public void setListener(LocalServiceListener listener) {
        mLocalServiceListener = listener;
    }

    public void terminate() {
        if (mContext != null && serviceConnection != null && isBound) {
            mContext.unbindService(serviceConnection);
        }
        isBound = false;
    }

    public LocalService getService() {
        return mLocalService;
    }

    /*
    Private methods
     */

    private LocalServiceApi() {

    }

    private static boolean init() {
        return bindToLocalService();
    }

    private static boolean bindToLocalService() {
        Intent intent = new Intent(mContext, LocalService.class);
        return mContext.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    private static ServiceConnection serviceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            isBound = true;

            // We've bound to LocalService, cast the IBinder and get LocalService instance
            LocalService.LocalServiceBinder binder = (LocalService.LocalServiceBinder) service;
            mLocalService = binder.getService();

            if (mLocalServiceListener != null) {
                mLocalServiceListener.onConnected();
            }
        }

        public void onServiceDisconnected(ComponentName className) {
            isBound = false;
            if (mLocalServiceListener != null) {
                mLocalServiceListener.onFailedToConnect();
            }
            Log.e("toto", "local service failed to bind");
        }
    };

}
