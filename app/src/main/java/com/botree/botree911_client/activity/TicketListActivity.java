package com.botree.botree911_client.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.botree.botree911_client.R;
import com.botree.botree911_client.adapter.ViewPagerAdapter;
import com.botree.botree911_client.model.Ticket;
import com.botree.botree911_client.utility.Constant;
import com.botree.botree911_client.utility.JSONParser;
import com.botree.botree911_client.utility.PreferenceUtility;
import com.botree.botree911_client.utility.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class TicketListActivity extends AppCompatActivity implements View.OnClickListener {

    Context mContext;

    ProgressDialog mProgressDialog;

    FloatingActionButton floatingActionButton;

    private DrawerLayout mDrawerLayout;
    private LinearLayout lnrAllTickets, lnrUnassigned, lnrNotification, lnrLogout;
    ImageView ivMenu;
    TextView tvTitle, tvUserName, tvEmail;

    ViewPagerAdapter pagerAdapter;
    ViewPager viewPager;
    ViewPager.OnPageChangeListener onPageChangeListener;

    boolean once = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_list);

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
        tvTitle.setText(getString(R.string.all_tickets));

        getSupportActionBar().setCustomView(actionbar);
    } // End of setCustomActionBar()

    void getElements(){

        mContext = this;

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingButton);
        viewPager = (ViewPager)findViewById(R.id.pager) ;

        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setMessage(getString(R.string.please_wait));
        mProgressDialog.setCancelable(false);
        mProgressDialog.setIndeterminate(true);

        lnrAllTickets = (LinearLayout) findViewById(R.id.slide_lnr_AllProjects);
        lnrUnassigned = (LinearLayout) findViewById(R.id.slide_lnr_AllUnassigned);
        lnrNotification = (LinearLayout) findViewById(R.id.slide_lnr_AllNotifications);
        lnrLogout = (LinearLayout) findViewById(R.id.slide_lnr_Logout);

        tvUserName = (TextView) findViewById(R.id.tv_UserName);
        tvEmail = (TextView) findViewById(R.id.tv_UserEmail);

        tvUserName.setText(PreferenceUtility.getUserName(mContext));
        tvEmail.setText(PreferenceUtility.getUserEmail(mContext));

        onPageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        };

        new getAllTickets().execute();


    }// End of getElements()

    void initElements(){

        lnrAllTickets.setOnClickListener(this);
        lnrUnassigned.setOnClickListener(this);
        lnrNotification.setOnClickListener(this);
        lnrLogout.setOnClickListener(this);
        floatingActionButton.setOnClickListener(this);

    }// End of initElements()

    @Override
    protected void onStart() {
        super.onStart();
//        if(once){
//            String status = getIntent().getStringExtra("status");
//            Log.d("Status", status);
//            if(status.equalsIgnoreCase("pending")){
//                viewPager.setCurrentItem(0);
//            }else if(status.equalsIgnoreCase("inprogress")){
//                viewPager.setCurrentItem(1);
//            }else if(status.equalsIgnoreCase("resolved")){
//                viewPager.setCurrentItem(2);
//            }else if(status.equalsIgnoreCase("closed")){
//                viewPager.setCurrentItem(3);
//            }else{
//                viewPager.setCurrentItem(4);
//            }
//            once = false;
//        }
    }

    @Override
    public void onBackPressed() {
        if(mDrawerLayout.isDrawerOpen(GravityCompat.START)){
            mDrawerLayout.closeDrawers();
        }else{
            super.onBackPressed();
        }
    }// End of onBackPressed()

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.iv_menu:
                if(mDrawerLayout.isDrawerOpen(GravityCompat.START)){
                    mDrawerLayout.closeDrawers();
                }else{
                    mDrawerLayout.openDrawer(Gravity.LEFT);
                }
                break;

            case R.id.floatingButton:
                createTicket();
                break;

            case R.id.slide_lnr_AllProjects:
                if(mDrawerLayout.isDrawerOpen(GravityCompat.START)){
                    mDrawerLayout.closeDrawers();
                }
                break;

            case R.id.slide_lnr_AllUnassigned:
                if(mDrawerLayout.isDrawerOpen(GravityCompat.START)){
                    mDrawerLayout.closeDrawers();
                }
                if(viewPager != null){
                    viewPager.setCurrentItem(Constant.allStatus.size());
                }
                break;

            case R.id.slide_lnr_AllNotifications:
                if(mDrawerLayout.isDrawerOpen(GravityCompat.START)){
                    mDrawerLayout.closeDrawers();
                }
                notification();
                break;

            case R.id.slide_lnr_Logout:
                if(mDrawerLayout.isDrawerOpen(GravityCompat.START)){
                    mDrawerLayout.closeDrawers();
                }
                logout();
                break;

            default:
                break;

        }

    }// End of onClick()

    void notification(){

        Intent intent = new Intent(mContext, NotificationListActivity.class);
        intent.putExtra("activity", "ticketlist");
        startActivity(intent);

    }// End of notification()

    void logout(){

        PreferenceUtility.saveAccessToken(mContext, "");
        PreferenceUtility.saveUserName(mContext, "");
        PreferenceUtility.saveUserEmail(mContext, "");
        PreferenceUtility.saveIsLogin(mContext, false);

        Intent intent = new Intent(mContext, LoginActivity.class);
        startActivity(intent);
        finish();

    }// End of logout()

    void displayProgress(){

        if(mProgressDialog != null && !mProgressDialog.isShowing()){
            mProgressDialog.show();
        }

    }// End of displayProgress()

    void closeProgress(){

        if(mProgressDialog != null && mProgressDialog.isShowing()){
            mProgressDialog.dismiss();
        }

    }// End of closeProgress()

    void createTicket(){

        Intent intent = new Intent(mContext, TicketCreateActivity.class);
        startActivity(intent);

    }// End of createTicket()

    class getAllTickets extends AsyncTask<String, String, String> {

        String response;
        JSONParser mJsonParser;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mJsonParser = new JSONParser();
            Constant.allTickets.clear();
            Constant.allTickets.trimToSize();
            displayProgress();
//            if(currentPoint == 1 && isRefresh){
//                progressDialog.show();
//            }
//
//            isLoading = true;
        }

        @Override
        protected String doInBackground(String... params) {

            try{

                HashMap<String, String> param = new HashMap<>();

                JSONObject jsonObject = new JSONObject();

                response = mJsonParser.makeHttpRequest(mContext, Constant.allTicketURL, "GET", jsonObject, param);

            }catch (Exception e){
                e.printStackTrace();
            }

            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if(s!=null && s.length() > 0){

                try{

                    JSONObject jObject = new JSONObject(s);
                    boolean status = jObject.getBoolean("status");

                    if(status){
                        JSONObject data = jObject.getJSONObject("data");
                        JSONObject allObjects = data.getJSONObject("tickets");


                        Iterator<String> iter = allObjects.keys();
                        while (iter.hasNext()) {
                            String key = iter.next();
                            try {
                                JSONArray jsonArray = allObjects.getJSONArray(key);

                                ArrayList<Ticket> tickets = new ArrayList<Ticket>();

                                for(int i=0; i<jsonArray.length(); i++){
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                                    Ticket ticket = new Ticket();
                                    ticket.setId(""+jsonObject.getInt("id"));
                                    ticket.setName(""+jsonObject.getString("name"));
                                    ticket.setDescription(""+jsonObject.getString("description"));
                                    ticket.setProject_id(""+jsonObject.getInt("project_id"));
                                    ticket.setStatus(""+jsonObject.getString("status"));
                                    ticket.setStatus_id(""+jsonObject.getInt("status_id"));
                                    ticket.setHistory_count(""+jsonObject.getInt("history_count"));
                                    ticket.setComment_count(""+jsonObject.getInt("comment_count"));
                                    ticket.setCreated_at(""+jsonObject.getString("created_at"));
                                    ticket.setAssingee(""+jsonObject.getString("assingee"));
                                    ticket.setRaised_by(""+jsonObject.getString("raised_by"));

                                    tickets.add(ticket);

                                }

                                Constant.allTickets.add(tickets);

                            } catch (JSONException e) {
                                // Something went wrong!
                            }
                        }

                        pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
                        viewPager.setAdapter(pagerAdapter);

                        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
                        tabs.setTextSize(Utility.dpToPx(14));
                        tabs.setShouldExpand(true);
                        tabs.setViewPager(viewPager);
                        tabs.setOnPageChangeListener(onPageChangeListener);

                        if(getIntent().getStringExtra("status").equalsIgnoreCase("unassigned")){
                            viewPager.setCurrentItem(Constant.allStatus.size());
                        }
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }

            }

//            isHasLoadedAll = false;
//            isLoading = false;
//            mPullToLoadView.setComplete();

            closeProgress();

        }

    }// End of getData
}
