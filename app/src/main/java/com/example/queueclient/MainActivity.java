package com.example.queueclient;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.queueclient.base.ButterKnifeActivity;
import com.example.queueclient.interfaces.DeviceStatusListener;
import com.example.queueclient.models.Customer;
import com.example.queueclient.network.CustomerManager;
import com.example.queueclient.persistences.Server;
import com.example.queueclient.persistences.ServersRepository;
import com.example.queueclient.views.adapters.ServerAdapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends ButterKnifeActivity implements ServerAdapter.OnSettingClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.hostTextView)
    TextInputEditText mHostText;
    @BindView(R.id.hostNameTextView)
    TextInputEditText mNameText;
    @BindView(R.id.pingButton)
    Button mPingBtn;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.subscribeButton)
    Button mSubscribeButton;
    @BindView(R.id.contentPanel)
    View mContainer;

    private ServerAdapter mAdapter;
    private List<Server> mServerList;
    private Server mCurrentServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUnbinder = ButterKnife.bind(this);
        initUI();
    }

    private void initUI() {
        mAdapter = new ServerAdapter();
        mAdapter.setOnSettingClickListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        mServerList = loadServerList();
        if (mServerList == null) {
            return;
        }
        mAdapter.setItems(mServerList);
    }

    @Override
    public void onSettingClick(Server server) {
        mHostText.setText(server.getIpAddress());
        mNameText.setText(server.getName());
        mCurrentServer = server;
    }

    @OnClick(R.id.pingButton)
    void onPingClick() {
        if (!validatePing()) {
            return;
        }
        if (executeCommand(mHostText.getText().toString())) {
            Snackbar.make(mContainer, "Ping Ok", Snackbar.LENGTH_SHORT).show();
        } else {
            Snackbar.make(mContainer, "Ping Fail", Snackbar.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.subscribeButton)
    void onSubscribeCLick() {
        if (validateAdd()) {
            addServerToDb();
            moveToSubscribeActivity(createServerInstance());
            if (mServerList == null) {
                mServerList = new ArrayList<>();
            }
            mServerList.add(createServerInstance());
            mAdapter.setItems(mServerList);
        }
    }

    private void moveToSubscribeActivity(Server server){
        Intent intent = new Intent(this, SubscribeActivity.class);
        intent.putExtra("server", server);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


    private boolean validatePing() {
        if (TextUtils.isEmpty(mHostText.getText().toString())) {
            mHostText.setError("input valid IP address");
            return false;
        }
        return true;
    }


    private boolean validateAdd() {
        if (validatePing()) {
            if (TextUtils.isEmpty(mNameText.getText().toString())) {
                mNameText.setError("input valid host name");
                return false;
            }
        }
        return true;
    }

    private Server createServerInstance() {
        return new Server(mNameText.getText().toString(), mHostText.getText().toString());
    }

    private void addServerToDb() {
        ServersRepository.createServer(this, createServerInstance());
    }

    private List<Server> loadServerList() {
        return ServersRepository.getServerList(this);
    }

    private boolean executeCommand(String ipAddress) {
        Log.d(TAG, "executeCommand");
        Runtime runtime = Runtime.getRuntime();
        try {
            String pingExec = "/system/bin/ping -c 1 " + ipAddress;
            Process mIpAddrProcess = runtime.exec(pingExec);
            int mExitValue = mIpAddrProcess.waitFor();
            Log.d(TAG, " mExitValue " + mExitValue);
            if (mExitValue == 0) {
                return true;
            } else {
                return false;
            }
        } catch (InterruptedException ignore) {
            ignore.printStackTrace();
            Log.d(TAG, " Exception:" + ignore);
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, " Exception:" + e);
        }
        return false;
    }
}
