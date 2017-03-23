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

import com.cc.demo.DemoApplication;
import com.cc.demo.apis.Apis;
import com.cc.demo.model.Radio;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.Lazy;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RemoteService extends Service {

    @Inject
    Lazy<Apis> mApis;

    final Messenger mMessenger = new Messenger(new IncomingHandler());
    Messenger mGetDataMessenger = null;

    @Override
    public void onCreate() {
        super.onCreate();

        // inject Dagger components/modules
        DemoApplication.getComponent().inject(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }

    class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {

            // handle message from client
            Bundle data = msg.getData();
            if (data.containsKey("command")) {
                if (data.getString("command").equalsIgnoreCase("get_data")) {
                    mGetDataMessenger = msg.replyTo;
                    getData();
                }
            }
        }
    }

    private void getData() {
        Call<List<Radio>> call = mApis.get().getRadios();
        call.enqueue(new Callback<List<Radio>>() {
            @Override
            public void onResponse(Call<List<Radio>> call, Response<List<Radio>> response) {
                if (mGetDataMessenger != null && response.body() != null && response.body().size() > 0) {

                    Log.e("toto", "respond to client");

                    // respond to client
                    try {
                        Message responseMessage = Message.obtain();
                        Bundle responseBundle = new Bundle();
                        responseBundle.putParcelableArrayList("data", new ArrayList<>(response.body()));
                        responseMessage.setData(responseBundle);
                        mGetDataMessenger.send(responseMessage);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<List<Radio>> call, Throwable t) {
            }
        });
    }

}
