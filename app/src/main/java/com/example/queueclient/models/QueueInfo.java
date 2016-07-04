package com.example.queueclient.models;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * Created by shintabmt on 6/30/2016.
 */
@Data
public class QueueInfo {
    @SerializedName("queuePosition")
    private int queuePosition;
    @SerializedName("customer")
    private Customer customer;
}
