package com.botree.botree911_client.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.botree.botree911_client.R;
import com.botree.botree911_client.activity.LoginActivity;
import com.botree.botree911_client.utility.PreferenceUtility;

public class SplashActivity extends Activity {

    Context mContext;
    Handler mHandler;
    Runnable mRunnable;

    long timeInterval = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getElement();
        initElement();

    }// End of onCreate()

    void getElement(){

        mContext = this;

        mHandler = new Handler();

        mRunnable = new Runnable() {
            @Override
            public void run() {
                nextActivity();
            }
        };

    }// End of getElements()

    void initElement(){

        mHandler.postDelayed(mRunnable, timeInterval);

    }// End of initElement()

    void nextActivity(){

        if(PreferenceUtility.getIsLogin(mContext)){
            Intent intent = new Intent(mContext, ProjectListActivity.class);
            startActivity(intent);
            finish();
        }else {
            Intent intent = new Intent(mContext, LoginActivity.class);
            startActivity(intent);
            finish();
        }

    }// End of nextActivity()

}
