package com.cc.demo.apis;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import com.cc.demo.utils.Utils;

public class RemoteServiceApi {
    private static RemoteServiceApi instance;
    private static Context mContext;
    private static Messenger messenger = null;
    private static boolean isBound;
    private ResponseHandler responseHandler = new ResponseHandler();
    private static RemoteServiceListener mRemoteServiceListener;

    public interface RemoteServiceListener {
        void onConnected();
        void onFailedToConnect();
        void onReceive(Bundle data);
    }

    /*
    Public methods
     */

    public static RemoteServiceApi getInstance(Context ctx) {
        if (instance == null) {
            instance = new RemoteServiceApi();
            instance.mContext = ctx;
        }
        init();
        return instance;
    }

    public void setListener(RemoteServiceListener listener) {
        mRemoteServiceListener = listener;
    }

    public void terminate() {
        if (mContext != null) {
            if (serviceConnection != null) {
                mContext.unbindService(serviceConnection);
            }
            isBound = false;
        }
    }

    public void send(Bundle data) {
        sendMessage(data);
    }

    /*
    Private methods
     */

    private RemoteServiceApi() {

    }

    private static boolean init() {
        return bindToRemoteService();
    }

    private static boolean bindToRemoteService() {
        Intent implicitIntent = new Intent("com.cc.RemoteService");
        Intent explicitIntent = Utils.createExplicitFromImplicitIntent(mContext, implicitIntent);
        try {
            boolean ret = mContext.bindService(explicitIntent, serviceConnection, Context.BIND_AUTO_CREATE);
            Log.d("toto", "bindService succeeded: " + ret);
            return ret;
        } catch (Exception e) {
            Log.e("toto", "bindService EXCEPTION: " + e.toString());
            e.printStackTrace();
            return false;
        }
    }

    private static ServiceConnection serviceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            messenger = new Messenger(service);
            isBound = true;

            if (mRemoteServiceListener != null) {
                mRemoteServiceListener.onConnected();
            }
        }

        public void onServiceDisconnected(ComponentName className) {
            messenger = null;
            isBound = false;
            if (mRemoteServiceListener != null) {
                mRemoteServiceListener.onFailedToConnect();
            }
            Log.e("toto", "remote service failed to bind");
        }
    };

    /*
    sends data via a Message to the remote service
     */
    private void sendMessage(Bundle data) {
        if (!isBound) {
            Log.e("toto", "send message to remote service failed, service not bound");
            return;
        }

        Message msg = Message.obtain();

        // set the response handler
        msg.replyTo = new Messenger(responseHandler);

        // set data
        if (data != null) {
            msg.setData(data);
        }

        // send message to remote service
        try {
            messenger.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /*
    handles all responses from the remote service
     */
    private class ResponseHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            Bundle data = msg.getData();
            if (mRemoteServiceListener != null) {
                mRemoteServiceListener.onReceive(data);
            }
        }
    }
}
