package com.example.queueclient.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

/**
 * Created by shintabmt@gmai.com on 7/5/2016.
 */
@Data
public class QueueStatus extends ConnectionStatus {
    @SerializedName("queueInfoList")
    private List<QueueInfo> queueInfoList;
}
