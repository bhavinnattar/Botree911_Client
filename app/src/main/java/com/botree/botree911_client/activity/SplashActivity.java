package com.botree.botree911_client.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.botree.botree911_client.R;
import com.botree.botree911_client.model.Project;
import com.botree.botree911_client.utility.Constant;
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

        getData();
        mHandler.postDelayed(mRunnable, timeInterval);

    }// End of initElement()

    void getData(){

        Constant.allProjects.clear();

        Project project1 = new Project();
        project1.setId("1");
        project1.setName("BoTree911");
        project1.setDescription("BoTree 911 is a Mobile Application for clients opting for BoTree’s 24 X 7 support");
        project1.setProject_manager("[\"Arpan Christain\", \"Nipun BrahmBhatt\"]");
        project1.setSpocPerson("Amit Patel");
        project1.setNoOfTeam("10");
        project1.setClient("[\"Olivia Wilde\", \"Scarlett Johansson\",\"Emma Stone\"]");
        project1.setTicketStatus("[{\"Name\":\"To Do\", \"Value\":\"5\"},{\"Name\":\"InProgress\", \"Value\":\"3\"},{\"Name\":\"Resolved\", \"Value\":\"1\"}, {\"Name\":\"Closed\", \"Value\":\"0\"}]");
        project1.setTeam_leader("[\"Nipun BrahmBhatt\", \"Bhavin Nattar\"]");
        project1.setTeam_member("[\"Rahul Sadhu\", \"Sailesh Prajapati\",\"Prina Patel\"]");
        project1.setStartDate("Feb 01, 2017");

        Project project2=new Project();
        project2.setId("2");
        project2.setName("Order Managment System");
        project2.setDescription("OMS is a Mobile Application for clients opting for BoTree’s 24 X 7 support");
        project2.setProject_manager("[\"Arpan Christain\", \"Nipun BrahmBhatt\"]");
        project2.setNoOfTeam("5");
        project2.setSpocPerson("Parth Barot");
        project2.setClient("[\"Scarlett Johansson\", \"Olivia Wilde\",\"Emma Stone\"]");
        project2.setTicketStatus("[{\"Name\":\"Pending\", \"Value\":\"4\"},{\"Name\":\"InProgress\", \"Value\":\"5\"},{\"Name\":\"Resolved\", \"Value\":\"3\"}, {\"Name\":\"Closed\", \"Value\":\"1\"}]");
        project2.setTeam_leader("[\"Bhavin Nattar\", \"Nipun BrahmBhatt\"]");
        project2.setTeam_member("[\"Sailesh Prajapati\", \"Rahul Sadhu\",\"Prina Patel\"]");
        project2.setStartDate("Feb 10, 2017");


        Project project3=new Project();
        project3.setId("3");
        project3.setName("Adgrid");
        project3.setDescription("Adgrid is a Mobile Application for clients opting for BoTree’s 24 X 7 support");
        project3.setProject_manager("[\"Nipun BrahmBhatt\", \"Arpan Christain\"]");
        project3.setNoOfTeam("6");
        project3.setSpocPerson("Arpan christian");
        project3.setClient("[\"Emma Stone\", \"Olivia Wilde\",\"Scarlett Johansson\"]");
        project3.setTicketStatus("[{\"Name\":\"Pending\", \"Value\":\"10\"},{\"Name\":\"InProgress\", \"Value\":\"0\"},{\"Name\":\"Resolved\", \"Value\":\"3\"}, {\"Name\":\"Closed\", \"Value\":\"1\"}]");
        project3.setTeam_leader("[\"Nipun BrahmBhatt\", \"Bhavin Nattar\"]");
        project3.setTeam_member("[\"Prina Patel\", \"Rahul Sadhu\",\"Sailesh Prajapati\"]");
        project3.setStartDate("Feb 8, 2017");

        Constant.allProjects.add(project1);
        Constant.allProjects.add(project2);
        Constant.allProjects.add(project3);

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
