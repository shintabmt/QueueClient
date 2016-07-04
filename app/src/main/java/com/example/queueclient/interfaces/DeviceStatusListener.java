package com.example.queueclient.interfaces;

import com.example.queueclient.models.QueueInfo;
import com.example.queueclient.models.QueueStatus;
import com.example.queueclient.persistences.Server;

import java.util.List;

/**
 * Created by shintabmt@gmai.com on 7/4/2016.
 */
public interface DeviceStatusListener {
    void onDeviceConnected();
    void onDeviceDisconnected(String error);
    void onDeviceRead();
    void onQueueSuccessfully(QueueStatus queueStatus);
    void onQueueFailure(String  error);

}
