package com.example.queueclient.models;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * Created by shintabmt on 6/30/2016.
 */
@Data
public class QueueInfo {
    @SerializedName("position")
    private String queuePosition;
}
