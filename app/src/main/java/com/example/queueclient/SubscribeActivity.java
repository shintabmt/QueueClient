package com.example.queueclient;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.queueclient.base.ButterKnifeActivity;
import com.example.queueclient.interfaces.DeviceStatusListener;
import com.example.queueclient.models.Customer;
import com.example.queueclient.models.QueueType;
import com.example.queueclient.network.CustomerManager;
import com.example.queueclient.persistences.Server;
import com.example.queueclient.utils.DeviceUtils;
import com.example.queueclient.views.adapters.QueueAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shintabmt@gmai.com on 7/4/2016.
 */
public class SubscribeActivity extends ButterKnifeActivity implements DeviceStatusListener {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private Server mServer;
    private QueueAdapter mAdapter;
    CustomerManager mCustomerManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribe);
        mUnbinder = ButterKnife.bind(this);
        mServer = (Server) getIntent().getSerializableExtra("server");
        mCustomerManager = new CustomerManager(mServer.getIpAddress(), this);
        initUi();
    }

    private void initUi() {
        mAdapter = new QueueAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        subscribe();
    }

    private void subscribe() {
        mCustomerManager.subscribe(DeviceUtils.getAndroidDeviceUid(this));
    }

    @Override
    public void onDeviceConnected() {
        showToast("connected to system");
        showDialog();
    }

    @Override
    public void onDeviceDisconnected(String error) {
        showToast(error);
    }

    @Override
    public void onDeviceRead() {

    }

    private void showDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).setTitle("Queue Type").setItems(new CharSequence[]{"Standard", "Priority"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                registerQueue(i);
            }
        }).create();
        alertDialog.show();
    }

    private void registerQueue(int type) {
        Customer customer = new Customer(DeviceUtils.getDeviceName(), DeviceUtils.getAndroidDeviceUid(this), QueueType.STANDARD);
        mCustomerManager.registerQueue(customer);
    }

    private void showToast(String string) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();

    }
}
