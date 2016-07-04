package com.example.queueclient.network;

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

    public void changeEndpoint(String endPoint) {
        sRetrofit = new Retrofit.Builder().baseUrl(endPoint).addConverterFactory(GsonConverterFactory.create()).build();
    }

    public static void initRetrofit(final String endPoint) {
        String  fakeendPoint = "http://" +endPoint + "/";

        sRetrofit = new Retrofit.Builder()
                .baseUrl(fakeendPoint)
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient.Builder().retryOnConnectionFailure(true).readTimeout(60*1000, TimeUnit.MILLISECONDS).build())
                .build();
//        Thread t = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                String  fakeendPoint = "http://" +endPoint + "/";
//
//                sRetrofit = new Retrofit.Builder()
//                        .baseUrl(fakeendPoint)
//                        .addConverterFactory(GsonConverterFactory.create())
//                        .client(new OkHttpClient.Builder().retryOnConnectionFailure(false).proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(endPoint, 8888))).build())
//                        .build();
//            }
//        });

    }
}
