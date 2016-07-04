package com.example.queueclient.network;

import android.widget.Toast;

import com.example.queueclient.interfaces.DeviceStatusListener;
import com.example.queueclient.models.ConnectionStatus;
import com.example.queueclient.models.Customer;
import com.example.queueclient.models.QueueInfo;
import com.example.queueclient.models.QueueStatus;
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
        getService().registerQueue(customer).enqueue(new Callback<QueueStatus>() {
            @Override
            public void onResponse(Call<QueueStatus> call, Response<QueueStatus> response) {
                QueueStatus queueStatus = response.body();
                if (queueStatus.getStatus().equalsIgnoreCase("ok")){
                    mDeviceStatusListener.onQueueSuccessfully(queueStatus);
                } else{
                    mDeviceStatusListener.onQueueFailure(queueStatus.getError());
                }
            }

            @Override
            public void onFailure(Call<QueueStatus> call, Throwable t) {
                mDeviceStatusListener.onDeviceDisconnected(t.getMessage());
            }
        });
    }
}
