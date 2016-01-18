package com.cc.demo.services;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.widget.Toast;

public class RemoteService extends Service {

    final Messenger myMessenger = new Messenger(new IncomingHandler());

    @Override
    public IBinder onBind(Intent intent) {
        return myMessenger.getBinder();
    }

    class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {

            // handle message from client
            Bundle data = msg.getData();
            String dataString = data.getString("incomingData");
            Toast.makeText(getApplicationContext(), dataString, Toast.LENGTH_LONG).show();

            // respond to client
            try {
                Message responseMessage = Message.obtain();
                Bundle responseBundle = new Bundle();
                responseBundle.putString("responseData", "roger that");
                responseMessage.setData(responseBundle);
                msg.replyTo.send(responseMessage);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

            // respond to client
            try {
                Message responseMessage = Message.obtain();
                Bundle responseBundle = new Bundle();
                responseBundle.putString("responseData", "roger that again");
                responseMessage.setData(responseBundle);
                msg.replyTo.send(responseMessage);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

}

