package com.example.queueclient;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;

import com.example.queueclient.base.ButterKnifeActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MainActivity extends ButterKnifeActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.hostTextView)
    TextInputEditText mHostText;
    @BindView(R.id.pingButton)
    Button mPingBtn;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUnbinder = ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick(R.id.pingButton) void onPingClick() {
        if (!validate()) {
            return;
        }
        executeCommand(mHostText.getText().toString());

        String log = executeCmd("ping -c 10 -w 10 google.com", false);
        Log.d(TAG, "log: " +log );
        Log.d(TAG, "isOnline: " +isOnline() );
    }

    private boolean validate() {
        if (TextUtils.isEmpty(mHostText.getText().toString())) {
            mHostText.setError("input valid IP address");
            return false;
        }

        return true;
    }

    private boolean executeCommand(String ipAddress) {
        Log.d(TAG, "executeCommand");
        Runtime runtime = Runtime.getRuntime();
        try {
            String pingExec = "/system/bin/ping -c 1 " + ipAddress;
            Process mIpAddrProcess = runtime.exec(pingExec);
            int mExitValue = mIpAddrProcess.waitFor();
            Log.d(TAG, " mExitValue " + mExitValue );
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

    public static String executeCmd(String cmd, boolean sudo){
        try {

            Process p;
            if(!sudo)
                p= Runtime.getRuntime().exec(cmd);
            else{
                p= Runtime.getRuntime().exec(new String[]{"su", "-c", cmd});
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
            int     exitValue = ipProcess.waitFor();
            return (exitValue == 0);

        } catch (IOException e)          { e.printStackTrace(); }
        catch (InterruptedException e) { e.printStackTrace(); }

        return false;
    }
}
