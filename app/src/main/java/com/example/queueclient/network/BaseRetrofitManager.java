package com.example.queueclient.network;

import lombok.Getter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by shintabmt on 7/1/2016.
 */
public abstract class BaseRetrofitManager<T> {

    private static Retrofit sRetrofit;
    @Getter
    final private T service;

    public BaseRetrofitManager(Class<T> endpointClazz, String endPoint) {
        if (null == sRetrofit) {
            initRetrofit(endPoint);
        }
        service = sRetrofit.create(endpointClazz);
    }

    public void changeEnpoint(String endPoint) {
        sRetrofit = new Retrofit.Builder().baseUrl(endPoint).addConverterFactory(GsonConverterFactory.create()).build();
    }

    public static void initRetrofit(String endPoint) {
        sRetrofit = new Retrofit.Builder()
                .baseUrl(endPoint)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
