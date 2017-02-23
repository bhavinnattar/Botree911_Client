package com.botree.botree911_client.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.botree.botree911_client.R;
import com.botree.botree911_client.model.ProjectOld;
import com.botree.botree911_client.utility.Constant;
import com.botree.botree911_client.utility.PreferenceUtility;

import net.danlew.android.joda.JodaTimeAndroid;



import java.sql.Time;

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

        getData();
        mHandler.postDelayed(mRunnable, timeInterval);

    }// End of initElement()

    void getData(){

        Constant.allProjectOlds.clear();

        ProjectOld projectOld1 = new ProjectOld();
        projectOld1.setId("1");
        projectOld1.setName("BoTree911");
        projectOld1.setDescription("BoTree 911 is a Mobile Application for clients opting for BoTree’s 24 X 7 support");
        projectOld1.setProject_manager("[\"Arpan Christain\", \"Nipun BrahmBhatt\"]");
        projectOld1.setSpocPerson("Amit Patel");
        projectOld1.setNoOfTeam("10");
        projectOld1.setClient("[\"Olivia Wilde\", \"Scarlett Johansson\",\"Emma Stone\"]");
        projectOld1.setTicketStatus("[{\"Name\":\"To Do\", \"Value\":\"5\"},{\"Name\":\"InProgress\", \"Value\":\"3\"},{\"Name\":\"Resolved\", \"Value\":\"1\"}, {\"Name\":\"Closed\", \"Value\":\"0\"}]");
        projectOld1.setTeam_leader("[\"Nipun BrahmBhatt\", \"Bhavin Nattar\"]");
        projectOld1.setTeam_member("[\"Rahul Sadhu\", \"Sailesh Prajapati\",\"Prina Patel\"]");
        projectOld1.setStartDate("Feb 01, 2017");

        ProjectOld projectOld2 =new ProjectOld();
        projectOld2.setId("2");
        projectOld2.setName("Order Managment System");
        projectOld2.setDescription("OMS is a Mobile Application for clients opting for BoTree’s 24 X 7 support");
        projectOld2.setProject_manager("[\"Arpan Christain\", \"Nipun BrahmBhatt\"]");
        projectOld2.setNoOfTeam("5");
        projectOld2.setSpocPerson("Parth Barot");
        projectOld2.setClient("[\"Scarlett Johansson\", \"Olivia Wilde\",\"Emma Stone\"]");
        projectOld2.setTicketStatus("[{\"Name\":\"Pending\", \"Value\":\"4\"},{\"Name\":\"InProgress\", \"Value\":\"5\"},{\"Name\":\"Resolved\", \"Value\":\"3\"}, {\"Name\":\"Closed\", \"Value\":\"1\"}]");
        projectOld2.setTeam_leader("[\"Bhavin Nattar\", \"Nipun BrahmBhatt\"]");
        projectOld2.setTeam_member("[\"Sailesh Prajapati\", \"Rahul Sadhu\",\"Prina Patel\"]");
        projectOld2.setStartDate("Feb 10, 2017");


        ProjectOld projectOld3 =new ProjectOld();
        projectOld3.setId("3");
        projectOld3.setName("Adgrid");
        projectOld3.setDescription("Adgrid is a Mobile Application for clients opting for BoTree’s 24 X 7 support");
        projectOld3.setProject_manager("[\"Nipun BrahmBhatt\", \"Arpan Christain\"]");
        projectOld3.setNoOfTeam("6");
        projectOld3.setSpocPerson("Arpan christian");
        projectOld3.setClient("[\"Emma Stone\", \"Olivia Wilde\",\"Scarlett Johansson\"]");
        projectOld3.setTicketStatus("[{\"Name\":\"Pending\", \"Value\":\"10\"},{\"Name\":\"InProgress\", \"Value\":\"0\"},{\"Name\":\"Resolved\", \"Value\":\"3\"}, {\"Name\":\"Closed\", \"Value\":\"1\"}]");
        projectOld3.setTeam_leader("[\"Nipun BrahmBhatt\", \"Bhavin Nattar\"]");
        projectOld3.setTeam_member("[\"Prina Patel\", \"Rahul Sadhu\",\"Sailesh Prajapati\"]");
        projectOld3.setStartDate("Feb 8, 2017");

        Constant.allProjectOlds.add(projectOld1);
        Constant.allProjectOlds.add(projectOld2);
        Constant.allProjectOlds.add(projectOld3);

    }

    void nextActivity(){

        if(PreferenceUtility.getIsLogin(mContext)){
            Intent intent = new Intent(mContext, TicketCreateActivity.class);
            startActivity(intent);
            finish();
        }else {
            Intent intent = new Intent(mContext, LoginActivity.class);
            startActivity(intent);
            finish();
        }

    }// End of nextActivity()

}
