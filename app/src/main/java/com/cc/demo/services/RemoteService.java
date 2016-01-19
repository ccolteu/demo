package com.cc.demo.services;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import com.cc.demo.MyApp;
import com.cc.demo.apis.Apis;
import com.cc.demo.model.Radio;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.Lazy;
import retrofit.RetrofitError;

public class RemoteService extends Service {

    @Inject
    Lazy<Apis> mApis;

    final Messenger myMessenger = new Messenger(new IncomingHandler());

    Messenger getDataMessenger = null;

    @Override
    public void onCreate() {
        super.onCreate();

        // inject Dagger components/modules
        MyApp.getComponent().inject(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return myMessenger.getBinder();
    }

    class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {

            // handle message from client
            Bundle data = msg.getData();
            if (data.containsKey("command")) {
                if (data.getString("command").equalsIgnoreCase("get_data")) {
                    getDataMessenger = msg.replyTo;
                    getData();
                }
            }
        }
    }

    private void getData() {
        mApis.get().getRadios(new retrofit.Callback<List<Radio>>() {
            @Override
            public void success(List<Radio> radios, retrofit.client.Response response) {
                if (getDataMessenger != null && radios != null && radios.size() > 0) {

                    Log.e("toto", "respond to client");

                    // respond to client
                    try {
                        Message responseMessage = Message.obtain();
                        Bundle responseBundle = new Bundle();
                        responseBundle.putParcelableArrayList("data", new ArrayList<>(radios));
                        responseMessage.setData(responseBundle);
                        getDataMessenger.send(responseMessage);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

}

