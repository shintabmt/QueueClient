package com.example.queueclient.interfaces;

import com.example.queueclient.persistences.Server;

/**
 * Created by shintabmt@gmai.com on 7/4/2016.
 */
public interface DeviceStatusListener {
    void onDeviceConnected();
    void onDeviceDisconnected(String error);
    void onDeviceRead();
}
