package com.example.queueclient.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by shintabmt on 6/30/2016.
 */
public enum QueueType {
    @SerializedName("0")
    STANDARD,
    @SerializedName("1")
    PRIORITY;
}
