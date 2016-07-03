package com.example.queueclient.models;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * Created by shintabmt@gmai.com on 7/4/2016.
 */
@Data
public class ConnectionStatus {
    @SerializedName("status")
    private String status;
    @SerializedName("error")
    private String error;
}
