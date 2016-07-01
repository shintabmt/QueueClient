package com.example.queueclient.network;

import com.example.queueclient.models.QueueInfo;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.POST;

/**
 * Created by shintabmt on 6/30/2016.
 */
public interface CustomerEndPoint {
    @POST("customer/subscribe")
    List<QueueInfo> subscribe(@Field("name") String name, @Field("uid") String uid, @Field("type") String type);
}
