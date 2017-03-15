package com.cc.demo.apis;

import com.cc.demo.model.Radio;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import rx.Observable;

public interface Apis {

    /*
     traditional APIs
      */
    @GET("/radio/radios.json")
    Call<List<Radio>> getRadios();

    /*
     RxJava APIs
      */
    @GET("/radio/radios.json")
    Observable<List<Radio>> getRadiosRx();
}
