package com.example.queueclient.network;

import com.example.queueclient.models.ConnectionStatus;
import com.example.queueclient.models.Customer;
import com.example.queueclient.models.QueueInfo;

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
    @Headers("POST  HTTP/1.1, Host: http://10.0.100.36:8888, Cache-Control: no-cache, Content-Type: application/x-www-form-urlencoded, Postman-Token: 67b97745-8e68-e86f-d470-2b5c10246d87, Connection: close")
    Call<ConnectionStatus> subscribe(@Field("uid") String uid);

    @POST("customer/queue")
    Call<List<QueueInfo>> registerQueue(@Body Customer customer);
}
