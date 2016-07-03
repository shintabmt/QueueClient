package com.example.queueclient.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.queueclient.interfaces.DeviceStatusListener;
import com.example.queueclient.network.CustomerManager;
import com.example.queueclient.persistences.Server;

/**
 * Created by shintabmt@gmai.com on 7/4/2016.
 */
public class DeviceStatusReportActivity extends ButterKnifeActivity implements DeviceStatusListener {
    protected CustomerManager  customerManager;

    @Override
    public void onDeviceConnected(Server server) {

    }

    @Override
    public void onDeviceDisconnected(Server server) {

    }

    @Override
    public void onDeviceRead() {

    }
}
