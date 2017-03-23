package com.cc.demo.modules;

import com.cc.demo.utils.ApiHeaders;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.cc.demo.apis.Apis;

import javax.inject.Singleton;
import javax.net.SocketFactory;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Module
public class ApiModule {

    private final String mBaseUrl;

    public ApiModule(String baseUrl) {
        mBaseUrl = baseUrl;
    }

    @Provides
    @Singleton
    Apis provideApis(final ApiHeaders apiHeaders) {

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'")
                .create();

        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.socketFactory(SocketFactory.getDefault())
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS);
        client.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                Request originalRequest = chain.request();
                Request.Builder request = chain.request().newBuilder()
                        .method(originalRequest.method(), originalRequest.body());

                Iterator it = apiHeaders.getHeaders().entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry header = (Map.Entry) it.next();
                    request.addHeader((String) header.getKey(), (String) header.getValue());
                }

                return chain.proceed(request.build());
            }
        });
        client.addNetworkInterceptor(new StethoInterceptor());

        // By default, Retrofit 2 can only deserialize HTTP bodies into OkHttp’s
        // ResponseBody type and it can only accept its RequestBodytype for @Body
        // So in order to enable Java serialization to convert Java Objects into
        // JSON and back, a GSON converter must be set.
        // Retrofit 2 interfaces does not support Observable out of the box anymore.
        // So an adapter is needed to convert Call to Observable for RxJava.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mBaseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client.build())
                .build();
        return retrofit.create(Apis.class);
    }

    @Provides
    @Singleton
    ApiHeaders provideApiHeaders() {
        return new ApiHeaders();
    }
}
