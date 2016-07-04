package com.example.queueclient.network;

import com.example.queueclient.models.ConnectionStatus;
import com.example.queueclient.models.Customer;
import com.example.queueclient.models.QueueInfo;
import com.example.queueclient.models.QueueStatus;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by shintabmt on 6/30/2016.
 */
public interface CustomerEndPoint {

    @FormUrlEncoded
    @POST("customer/subscribe")
    Call<ConnectionStatus> subscribe(@Field("uid") String uid);

    @POST("customer/queue")
    Call<QueueStatus> registerQueue(@Body Customer customer);
}
