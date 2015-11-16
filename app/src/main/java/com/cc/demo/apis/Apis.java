package com.cc.demo.apis;

import com.cc.demo.model.Radio;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import rx.Observable;

public interface Apis {

    /*
     traditional APIs
      */
    @GET("/radio/radios.json")
    List<Radio> getRadiosSync();

    @GET("/radio/radios.json")
    void getRadios(Callback<List<Radio>> callback);

    /*
     RxJava APIs
      */
    @GET("/radio/radios.json")
    Observable<List<Radio>> getRadiosRx();
}
