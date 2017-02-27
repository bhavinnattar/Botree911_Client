package com.botree.botree911_client.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.botree.botree911_client.R;
import com.botree.botree911_client.adapter.NotificationAdapter;
import com.botree.botree911_client.model.LocalNotification;
import com.botree.botree911_client.utility.Constant;
import com.botree.botree911_client.utility.JSONParser;
import com.botree.botree911_client.utility.Utility;
import com.srx.widget.PullToLoadView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by bhavin on 21/2/17.
 */

public class NotificationListActivity extends AppCompatActivity implements View.OnClickListener {

    Context mContext;
    ProgressDialog mProgressDialog;

    private DrawerLayout mDrawerLayout;
    private LinearLayout lnrAllTickets, lnrUnassigned, lnrNotification, lnrLogout;
    ImageView ivMenu;
    TextView tvTitle;

    ArrayList<LocalNotification> allNotification;
    RecyclerView mRecyclerView;
    NotificationAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_notification);

        setCustomActionBar();

        getElements();
        initElements();

    }// End of onCreate()

    void setCustomActionBar(){

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        ActionBar.LayoutParams p = new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        p.gravity = Gravity.CENTER;

        View actionbar = getLayoutInflater().inflate(R.layout.custom_actionbar, null);
        ivMenu = (ImageView) actionbar.findViewById(R.id.iv_menu);
        tvTitle = (TextView) actionbar.findViewById(R.id.actionbar_title);

        ivMenu.setOnClickListener(this);
        tvTitle.setText(getString(R.string.notification));

        ivMenu.setVisibility(View.INVISIBLE);

        getSupportActionBar().setCustomView(actionbar);

    } // End of setCustomActionBar()

    void getElements(){

        mContext = this;

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        LinearLayoutManager manager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setMessage(getString(R.string.please_wait));
        mProgressDialog.setCancelable(false);
        mProgressDialog.setIndeterminate(true);

        lnrAllTickets = (LinearLayout) findViewById(R.id.slide_lnr_AllProjects);
        lnrUnassigned = (LinearLayout) findViewById(R.id.slide_lnr_AllUnassigned);
        lnrNotification = (LinearLayout) findViewById(R.id.slide_lnr_AllNotifications);
        lnrLogout = (LinearLayout) findViewById(R.id.slide_lnr_Logout);

        allNotification = new ArrayList<LocalNotification>();

    }// End of getElements()

    void initElements(){

        lnrAllTickets.setOnClickListener(this);
        lnrUnassigned.setOnClickListener(this);
        lnrNotification.setOnClickListener(this);
        lnrLogout.setOnClickListener(this);

        if(Utility.isOnline(mContext)){
            new NotificationList().execute();
        }else{
            Utility.displayMessage(mContext, getString(R.string.internet_error));
        }

    }// End of initElements()


    @Override
    public void onBackPressed() {
        if(mDrawerLayout.isDrawerOpen(GravityCompat.START)){
            mDrawerLayout.closeDrawers();
        }else{
            if(getIntent().getStringExtra("activity") != null){
                allTickets("pending");
            }else{
                super.onBackPressed();
            }
        }
    }// End of onBackPressed()

    void allTickets(String status){

        Intent intent = new Intent(mContext, TicketListActivity.class);
        intent.putExtra("status", status);
        startActivity(intent);
        finish();

    }// End of allTickets()

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            default:
                break;
        }
    }// End of onClick()

    class NotificationList extends AsyncTask<String, String, String> {

        String response = null;
        JSONParser mJsonParser;
        @Override
        protected void onPreExecute() {
            mJsonParser = new JSONParser();
        }

        @Override
        protected String doInBackground(String... params) {
            try{

                HashMap<String, String> param = new HashMap<>();
                JSONObject jsonObject = new JSONObject();

                response = mJsonParser.makeHttpRequest(mContext, Constant.notificationURL,
                        "GET", jsonObject, param);

            }catch (Exception e){
                e.printStackTrace();
            }

            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if(s != null && s.length() > 0){
                try{
                    JSONObject jsonObject = new JSONObject(s);

                    boolean status = jsonObject.optBoolean("status");
                    String message = jsonObject.getString("message");

                    JSONObject jData = jsonObject.getJSONObject("data");
                    if(status){
                        JSONArray allNoti = jData.getJSONArray("notifications");

                        for(int i=0; i < allNoti.length(); i++){
                            JSONObject jsonObj = allNoti.getJSONObject(i);

                            LocalNotification noti = new LocalNotification();

                            noti.setTicket_id(""+jsonObj.getInt("ticket_id"));
                            noti.setUser_name(""+jsonObj.getString("user_name"));
                            noti.setDescription(""+jsonObj.getString("description"));
                            noti.setDate(""+jsonObj.getString("date"));

                            allNotification.add(noti);
                        }

                        mAdapter = new NotificationAdapter(mContext, allNotification);
                        mRecyclerView.setAdapter(mAdapter);

                    }else{
                        Utility.displayMessage(mContext, message);
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }
    }
}
