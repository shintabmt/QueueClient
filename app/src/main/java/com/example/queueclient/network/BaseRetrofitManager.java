package com.example.queueclient.network;

import com.example.queueclient.Constants;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.concurrent.TimeUnit;

import lombok.Getter;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by shintabmt on 7/1/2016.
 */
public abstract class BaseRetrofitManager<T> implements Constants {

    private static Retrofit sRetrofit;
    @Getter
    final private T service;

    public BaseRetrofitManager(Class<T> endpointClazz, String endPoint) {
        if (null == sRetrofit) {
            initRetrofit(endPoint);
        }
        service = sRetrofit.create(endpointClazz);
    }

    public void changeEndpoint(String endPoint) {
        sRetrofit = new Retrofit.Builder().baseUrl(endPoint).addConverterFactory(GsonConverterFactory.create()).build();
    }

    public static void initRetrofit(String endPoint) {
        endPoint = HTTP_PREFIX + endPoint + ":" + SERVER_PORT + "/";
        sRetrofit = new Retrofit.Builder()
                .baseUrl(endPoint)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }
}
