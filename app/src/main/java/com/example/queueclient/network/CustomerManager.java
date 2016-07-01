package com.example.queueclient.network;

import com.example.queueclient.models.QueueInfo;
import com.example.queueclient.persistences.Server;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by shintabmt on 7/1/2016.
 */
public class CustomerManager extends BaseRetrofitManager<CustomerEndPoint> {
    public CustomerManager(String endPoint) {
        super(CustomerEndPoint.class, endPoint);
    }

    public void subcrible(String name, String uid, String type) {
        getService().subscribe(name, uid, type).enqueue(new Callback<QueueInfo>() {
            @Override
            public void onResponse(Call<QueueInfo> call, Response<QueueInfo> response) {
                QueueInfo queueInfo =response.body();
            }

            @Override
            public void onFailure(Call<QueueInfo> call, Throwable t) {

            }
        });
    }
}
