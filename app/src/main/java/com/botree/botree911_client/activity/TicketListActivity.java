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
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.botree.botree911_client.R;
import com.botree.botree911_client.adapter.TicketAdapter;
import com.botree.botree911_client.adapter.ViewPagerAdapter;
import com.botree.botree911_client.model.Ticket;
import com.botree.botree911_client.utility.Constant;
import com.botree.botree911_client.utility.JSONParser;
import com.botree.botree911_client.utility.PreferenceUtility;
import com.botree.botree911_client.utility.Utility;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

public class TicketListActivity extends AppCompatActivity implements View.OnClickListener {

    Context mContext;

    private TicketAdapter mAdapter;
    private boolean isLoading = false;
    private boolean isHasLoadedAll = false;

    ProgressDialog mProgressDialog;
    //    List<ArticleObject> allData;
    RecyclerView mRecyclerView;
    List<Ticket> mList;

    String projectId = "";
    FloatingActionButton floatingActionButton;

    private DrawerLayout mDrawerLayout;
    private LinearLayout lnrAllTickets, lnrUnassigned, lnrNotification, lnrLogout;
    ImageView ivMenu;
    TextView tvTitle;

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

    void getData(){

        Constant.allTickets.clear();

        Ticket ticket1=new Ticket();
        ticket1.setId("1");
        ticket1.setName("Change Login Screen");
        ticket1.setProject_id("2");
        ticket1.setDescription("add Facebook Integration in Login");
        ticket1.setStatus("Pending");
        ticket1.setAssingee("Bhavin Nattar");
        ticket1.setCreated_at("Jan 21, 2017");
        ticket1.setRaised_by("Olivia Wilde");

        Ticket ticket2=new Ticket();
        ticket2.setId("2");
        ticket2.setName("UI Theme");
        ticket2.setProject_id("3");
        ticket2.setDescription("change Mobile Theme");
        ticket2.setStatus("InProgress");
        ticket2.setAssingee("Piyush Sanepara");
        ticket2.setCreated_at("Jan 1, 2017");
        ticket2.setRaised_by("Scarlett Johansson");

        Ticket ticket3=new Ticket();
        ticket3.setId("3");
        ticket3.setName("Change Animation");
        ticket3.setProject_id("1");
        ticket3.setDescription("change Activity transition Animation");
        ticket3.setStatus("Resolved");
        ticket3.setAssingee("Rahul Sadhu");
        ticket3.setCreated_at("Dec 11, 2016");
        ticket3.setRaised_by("Emma Stone");

        Ticket ticket4=new Ticket();
        ticket4.setId("4");
        ticket4.setName("Change SplashScreen");
        ticket4.setProject_id("2");
        ticket4.setDescription("change SplashScreen Background and Logo");
        ticket4.setStatus("closed");
        ticket4.setAssingee("Rahul Sadhu");
        ticket4.setCreated_at("Dec 11, 2016");
        ticket4.setRaised_by("Emma Stone");

        Ticket ticket5=new Ticket();
        ticket5.setId("5");
        ticket5.setName("First Ticket");
        ticket5.setProject_id("2");
        ticket5.setDescription("This is a First Ticket");
        ticket5.setStatus("Pending");
        ticket5.setAssingee("Bhavin Nattar");
        ticket5.setCreated_at("Jan 02, 2017");
        ticket5.setRaised_by("Olivia Wilde");


        Ticket ticket6=new Ticket();
        ticket6.setId("6");
        ticket6.setName("New Ticket");
        ticket6.setProject_id("3");
        ticket6.setDescription("This is a New Ticket");
        ticket6.setStatus("InProgress");
        ticket6.setAssingee("Piyush Sanepara");
        ticket6.setCreated_at("Jan 1, 2017");
        ticket6.setRaised_by("Scarlett Johansson");

        Ticket ticket7=new Ticket();
        ticket7.setId("7");
        ticket7.setName("Second Ticket");
        ticket7.setProject_id("1");
        ticket7.setDescription("This is a Second Ticket");
        ticket7.setStatus("Resolved");
        ticket7.setAssingee("Rahul Sadhu");
        ticket7.setCreated_at("Dec 01, 2016");
        ticket7.setRaised_by("Emma Stone");

        Ticket ticket8=new Ticket();
        ticket8.setId("8");
        ticket8.setName("Third Ticket");
        ticket8.setProject_id("2");
        ticket8.setDescription("This is a Third Ticket");
        ticket8.setStatus("closed");
        ticket8.setAssingee("Rahul Sadhu");
        ticket8.setCreated_at("Feb 02, 2017");
        ticket8.setRaised_by("Emma Stone");

        Ticket ticket9=new Ticket();
        ticket9.setId("9");
        ticket9.setName("Fourth Ticket");
        ticket9.setProject_id("1");
        ticket9.setDescription("This is a Fourth Ticket");
        ticket9.setStatus("Pending");
        ticket9.setAssingee("");
        ticket9.setCreated_at("Dec 01, 2016");
        ticket9.setRaised_by("Emma Stone");

        Ticket ticket10=new Ticket();
        ticket10.setId("10");
        ticket10.setName("Fifth Ticket");
        ticket10.setProject_id("2");
        ticket10.setDescription("This is a Fifth Ticket");
        ticket10.setStatus("Pending");
        ticket10.setAssingee("");
        ticket10.setCreated_at("Feb 02, 2017");
        ticket10.setRaised_by("Emma Stone");

        Constant.allTickets.add(ticket1);
        Constant.allTickets.add(ticket2);
        Constant.allTickets.add(ticket3);
        Constant.allTickets.add(ticket4);
        Constant.allTickets.add(ticket5);
        Constant.allTickets.add(ticket6);
        Constant.allTickets.add(ticket7);
        Constant.allTickets.add(ticket8);
        Constant.allTickets.add(ticket9);
        Constant.allTickets.add(ticket10);

    }

    void getElements(){

        mContext = this;

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingButton);
        viewPager = (ViewPager)findViewById(R.id.pager) ;

        lnrAllTickets = (LinearLayout) findViewById(R.id.slide_lnr_AllProjects);
        lnrUnassigned = (LinearLayout) findViewById(R.id.slide_lnr_AllUnassigned);
        lnrNotification = (LinearLayout) findViewById(R.id.slide_lnr_AllNotifications);
        lnrLogout = (LinearLayout) findViewById(R.id.slide_lnr_Logout);

        getData();

        pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabs.setTextSize(Utility.dpToPx(14));
        tabs.setShouldExpand(true);
        tabs.setViewPager(viewPager);

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

        tabs.setOnPageChangeListener(onPageChangeListener);


    }// End of getElements()

    void initElements(){

        lnrAllTickets.setOnClickListener(this);
        lnrUnassigned.setOnClickListener(this);
        lnrNotification.setOnClickListener(this);
        lnrLogout.setOnClickListener(this);
        floatingActionButton.setOnClickListener(this);

        projectId = getIntent().getStringExtra("projectid");

    }// End of initElements()

    @Override
    protected void onStart() {
        super.onStart();
        if(once){
            String status = getIntent().getStringExtra("status");
            Log.d("Status", status);
            if(status.equalsIgnoreCase("pending")){
                viewPager.setCurrentItem(0);
            }else if(status.equalsIgnoreCase("inprogress")){
                viewPager.setCurrentItem(1);
            }else if(status.equalsIgnoreCase("resolved")){
                viewPager.setCurrentItem(2);
            }else if(status.equalsIgnoreCase("closed")){
                viewPager.setCurrentItem(3);
            }else{
                viewPager.setCurrentItem(4);
            }
            once = false;
        }
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
                    viewPager.setCurrentItem(4);
                }
                break;

            case R.id.slide_lnr_AllNotifications:
                if(mDrawerLayout.isDrawerOpen(GravityCompat.START)){
                    mDrawerLayout.closeDrawers();
                }
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

    void logout(){

        PreferenceUtility.saveAccessToken(mContext, "");
        PreferenceUtility.saveUserName(mContext, "");
        PreferenceUtility.saveUserEmail(mContext, "");
        PreferenceUtility.saveIsLogin(mContext, false);

        Intent intent = new Intent(mContext, LoginActivity.class);
        startActivity(intent);
        finish();

    }// End of logout()

    void ticketInfo(Ticket ticket){
        Constant.selectedTicket = ticket;
        Intent intent = new Intent(mContext, TicketInfoTabActivity.class);
        mContext.startActivity(intent);

    }// End of activityInfo()

    void createTicket(){

        Intent intent = new Intent(mContext, TicketCreateActivity.class);
        intent.putExtra("projectid", projectId);
        startActivity(intent);

    }// End of createTicket()

    class getAllTickets extends AsyncTask<String, String, String> {

        String response;
        JSONParser mJsonParser;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mJsonParser = new JSONParser();
//            displayProgress();
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
                param.put("project_id", params[0]);

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
                        JSONArray allObjects = data.getJSONArray("ticket");

                        for(int i=0; i< allObjects.length(); i++){
                            JSONObject jsonObject = allObjects.getJSONObject(i);

                            Ticket ticket = new Ticket();
                            ticket.setId(""+jsonObject.getInt("id"));
                            ticket.setName(""+jsonObject.getString("name"));
                            ticket.setDescription(""+jsonObject.getString("description"));
                            ticket.setProject_id(""+jsonObject.getInt("project_id"));
                            ticket.setStatus(""+jsonObject.getString("status"));
                            ticket.setCreated_at(""+jsonObject.getString("created_at"));
                            ticket.setAssingee(""+jsonObject.getString("assingee"));
                            ticket.setRaised_by(""+jsonObject.getString("raised_by"));

                            mList.add(ticket);

                        }

                        mAdapter = new TicketAdapter(mContext, mList);
                        mRecyclerView.setAdapter(mAdapter);
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }

            }

//            isHasLoadedAll = false;
//            isLoading = false;
//            mPullToLoadView.setComplete();

//            closeProgress();

        }

    }// End of getData
}
