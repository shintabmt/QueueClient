package com.example.queueclient.network;

import com.example.queueclient.interfaces.DeviceStatusListener;
import com.example.queueclient.models.ConnectionStatus;
import com.example.queueclient.models.Customer;
import com.example.queueclient.models.QueueInfo;
import com.example.queueclient.models.QueueType;
import com.example.queueclient.persistences.Server;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by shintabmt on 7/1/2016.
 */
public class CustomerManager extends BaseRetrofitManager<CustomerEndPoint> {
    DeviceStatusListener mDeviceStatusListener;

    public CustomerManager(String endPoint, DeviceStatusListener deviceStatusListener) {
        super(CustomerEndPoint.class, endPoint);
        this.mDeviceStatusListener = deviceStatusListener;
    }

    public void subscribe(String uid) {
        getService().subscribe(uid).enqueue(new Callback<ConnectionStatus>() {
            @Override
            public void onResponse(Call<ConnectionStatus> call, Response<ConnectionStatus> response) {
                ConnectionStatus status = response.body();
                if (status.getStatus().equalsIgnoreCase("ok")){
                    mDeviceStatusListener.onDeviceConnected();
                } else {
                    mDeviceStatusListener.onDeviceDisconnected(status.getError());
                }
            }

            @Override
            public void onFailure(Call<ConnectionStatus> call, Throwable t) {
                mDeviceStatusListener.onDeviceDisconnected(t.getMessage());
            }
        });
    }

    public void registerQueue(Customer customer) {
        getService().registerQueue(customer).enqueue(new Callback<List<QueueInfo>>() {
            @Override
            public void onResponse(Call<List<QueueInfo>> call, Response<List<QueueInfo>> response) {
            }

            @Override
            public void onFailure(Call<List<QueueInfo>> call, Throwable t) {

            }
        });
    }
}
