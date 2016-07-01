package com.example.queueclient;

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
    @BindView(R.id.addButton)
    Button mAddButton;
    @BindView(R.id.contentPanel)
    View mContainer;

    private ServerAdapter mAdapter;
    private List<Server> mServerList;

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
//        String log = executeCmd("ping -c 10 -w 10 google.com", false);
//        Log.d(TAG, "log: " + log);
//        Log.d(TAG, "isOnline: " + isOnline());
    }

    @OnClick(R.id.addButton)
    void onAddCLick() {
        if (validateAdd()) {
            addServerToDb();
            if (mServerList == null) {
                mServerList = new ArrayList<>();
            }
            mServerList.add(createServerInstance());
            mAdapter.setItems(mServerList);
        }
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

    public static String executeCmd(String cmd, boolean sudo) {
        try {

            Process p;
            if (!sudo)
                p = Runtime.getRuntime().exec(cmd);
            else {
                p = Runtime.getRuntime().exec(new String[]{"su", "-c", cmd});
            }
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));

            String s;
            String res = "";
            while ((s = stdInput.readLine()) != null) {
                res += s + "\n";
            }
            p.destroy();
            return res;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";

    }

    public boolean isOnline() {

        Runtime runtime = Runtime.getRuntime();
        try {

            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int exitValue = ipProcess.waitFor();
            return (exitValue == 0);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return false;
    }
}
