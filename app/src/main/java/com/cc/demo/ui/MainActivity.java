package com.cc.demo.ui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.cc.demo.DemoApplication;
import com.cc.demo.R;
import com.cc.demo.apis.Apis;
import com.cc.demo.apis.LocalServiceApi;
import com.cc.demo.apis.RemoteServiceApi;
import com.cc.demo.jni.NativeWrapper;
import com.cc.demo.model.Radio;
import com.cc.demo.otto.Message;
import com.cc.demo.services.LocalService;
import com.cc.demo.utils.ApiHeaders;
import com.cc.demo.utils.JobExecutor;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.Lazy;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class MainActivity extends Activity {

    // load native library
    static {
        System.loadLibrary("add");
    }

    // Dagger injection
    @Inject
    DemoApplication mMyApp;
    @Inject Bus mBus;
    // lazy injection since mApis is only needed when the user clicks the button
    // mApis will not actually get injected until the first call to get(), from
    // then on it will remain injected
    @Inject Lazy<Apis> mApis;
    @Inject Lazy<RemoteServiceApi> mRemoteServiceApi;
    @Inject Lazy<LocalServiceApi> mLocalServiceApi;
    @Inject Lazy<ApiHeaders> mApiHeaders;


    // ButterKnife binding
    @Bind(R.id.list) ListView mListView;
    @OnClick(R.id.get_data)
    public void getDataClickHandler() {
        //getDataSync();
        getData();
        //getDataRx();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // inject Dagger components/modules
        DemoApplication.getComponent().inject(this);

        // inject ButterKnife dependencies
        ButterKnife.bind(this);

        mMyApp.customAppMethod("some text");

        // register Otto event bus
        mBus.register(this);

        performNativeCall();
    }

    private void performNativeCall() {
        int a = 4;
        int b = 5;
        int c = NativeWrapper.add(4, 5);
        Log.e("toto", "Native add: " + a + " + " + b + " = " + c);
    }

    /*
    Remote service demo
     */

    @OnClick(R.id.remote_service)
    public void remoteServiceTestClickHandler() {
        getDataViaRemoteService();
    }

    private void getDataViaRemoteService() {
        mRemoteServiceApi.get().setListener(mRemoteServiceListener);
    }

    @Override
    protected void onDestroy() {
        if (mRemoteServiceApi.get() != null) {
            mRemoteServiceApi.get().terminate();
        }

        super.onDestroy();
    }

    private RemoteServiceApi.RemoteServiceListener mRemoteServiceListener =
            new RemoteServiceApi.RemoteServiceListener() {
                @Override
                public void onConnected() {
                    Bundle bundle = new Bundle();
                    bundle.putString("command", "get_data");
                    mRemoteServiceApi.get().send(bundle);
                }

                @Override
                public void onFailedToConnect() {

                }

                @Override
                public void onReceive(Bundle data) {

                    // set the current class loader to be used by the un-marshalling process
                    // since the the parcelling is done in a separate process
                    data.setClassLoader(getClassLoader());

                    if (data.containsKey("data")) {
                        ArrayList<Radio> radios = data.getParcelableArrayList("data");
                        updateUI(radios);
                    }
                }
            };

    /*
    Local service demo
     */

    @OnClick(R.id.local_service)
    public void localServiceTestClickHandler() {
        getDataViaLocalService();
    }

    private void getDataViaLocalService() {
        mLocalServiceApi.get().setListener(mLocalServiceListener);
    }

    private LocalServiceApi.LocalServiceListener mLocalServiceListener = new LocalServiceApi.LocalServiceListener() {
        @Override
        public void onConnected() {
            mLocalServiceApi.get().getService().getData(new LocalService.OnGetData() {
                @Override
                public void onGotData(List<Radio> radios) {
                    updateUI(radios);
                }

                @Override
                public void onError(String error) {

                }
            });
        }

        @Override
        public void onFailedToConnect() {
        }
    };

    /*
     Otto
     */
    @Subscribe
    public void getMessage(Message msg) {
        Log.e("toto", "got message \"" + msg.getText() + "\" from " + msg.getSender());
        Toast.makeText(this, msg.getText(), Toast.LENGTH_LONG).show();
    }

    /*
    get data using Retrofit
     */
    private void getData() {

        // add run-time headers - anywhere, not necessarily here
        // can be OAuth token, session cookie, etc
        // they will be automatically injected into every API call
        mApiHeaders.get().addHeader("time", Long.toString(new Date().getTime()));

        Call<List<Radio>> call = mApis.get().getRadios();
        call.enqueue(new Callback<List<Radio>>() {
            @Override
            public void onResponse(Call<List<Radio>> call, Response<List<Radio>> response) {
                updateUI(response.body());
            }

            @Override
            public void onFailure(Call<List<Radio>> call, Throwable t) {
            }
        });
    }

    private void updateUI(List<Radio> radios) {
        ArrayList<String> titles = new ArrayList<>();
        for (Radio r : radios) {
            titles.add(r.getTitle());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this.getBaseContext(), android.R.layout.simple_list_item_1, android.R.id.text1, titles);
        mListView.setAdapter(adapter);

        Log.e("toto", "post message");
        mBus.post(new Message.MessageBuilder("got the data").sender("updateUI").build());
    }

    /*
    get data using Retrofit and RxJava
     */
    private void getDataRx() {

        Log.e("toto", getReducedStackTrace().substring(0, 256));

        Executor jobExecutor = JobExecutor.getInstance();
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, new ArrayList<String>());
        mListView.setAdapter(adapter);

        // add run-time headers - anywhere, not necessarily here
        // can be OAuth token, session cookie, etc
        // they will be automatically injected into every API call
        mApiHeaders.get().addHeader("time", Long.toString(new Date().getTime()));

        mCompositeSubscription.add(mApis.get().getRadiosRx()
                //.subscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.from(jobExecutor))
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<List<Radio>, Observable<Radio>>() {
                    @Override
                    public Observable<Radio> call(List<Radio> radios) {
                        return Observable.from(radios);
                    }
                })
                .filter(new Func1<Radio, Boolean>() {
                    @Override
                    public Boolean call(Radio r) {
                        return r.getTitle().contains("BBC");
                    }
                })
                .map(new Func1<Radio, String>() {
                    @Override
                    public String call(Radio radio) {
                        return radio.getTitle();
                    }
                })
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(String title) {
                        adapter.add(title);
                    }
                }));
    }

    /*
    RxJava boilerplate code ...
    Memory leaks caused by Observables which retain a copy of the Context
    can be solved by properly un-subscribing from your subscriptions in
    accordance with the activity lifecycle. It's a common pattern to
    use a CompositeSubscription to hold all of your Subscriptions
    and then un-subscribe in onPause.
     */
    private CompositeSubscription mCompositeSubscription = new CompositeSubscription();

    @Override
    public void onPause() {
        super.onPause();
        unsubscribe(mCompositeSubscription);
    }

    @Override
    public void onResume() {
        super.onResume();
        mCompositeSubscription = renewIfNeeded(mCompositeSubscription);
    }

    public static void unsubscribe(Subscription subscription) {
        if (subscription != null) {
            subscription.unsubscribe();
        }
    }

    public static CompositeSubscription renewIfNeeded(CompositeSubscription subscription) {
        if (subscription == null || subscription.isUnsubscribed()) {
            return new CompositeSubscription();
        }
        return subscription;
    }

    /*
    Utils
     */
    private String getStackTrace() {
        String stackTraceString = "";
        try {
            throw new Exception();
        } catch (Exception e) {
            StackTraceElement[] stack = e.getStackTrace();
            if (stack != null) {
                for (int i = 0; i < stack.length; i++) {
                    stackTraceString += stack[i].toString();
                    stackTraceString += "\n";
                }
            }
        }
        return stackTraceString;
    }

    private String getReducedStackTrace() {
        String stackTraceString = "";
        try {
            throw new Exception();
        } catch (Exception e) {
            StackTraceElement[] stack = e.getStackTrace();
            if (stack != null) {
                // starting index is 1 because the first stack
                // line is this function call, irrelevant data
                for (int i = 1; i < stack.length; i++) {
                    String stackLine = stack[i].toString();
                    String logLine = "";
                    int openBracketIndex = stackLine.indexOf("(");
                    int closeBracketIndex = stackLine.indexOf(")");
                    logLine += stackLine.substring(stackLine.substring(0, openBracketIndex).lastIndexOf(".") + 1, openBracketIndex);
                    logLine += ":";
                    logLine += stackLine.substring(openBracketIndex + 1, closeBracketIndex);
                    stackTraceString += logLine;
                    stackTraceString += "\n";
                }
            }
        }
        return stackTraceString;
    }
}
