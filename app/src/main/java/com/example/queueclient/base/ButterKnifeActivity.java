package com.example.queueclient.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.Unbinder;

/**
 * Created by shintabmt@gmai.com on 6/29/2016.
 */
public abstract class ButterKnifeActivity extends AppCompatActivity {

    protected Unbinder mUnbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder == null){
            return;
        }
        mUnbinder.unbind();
    }
}
