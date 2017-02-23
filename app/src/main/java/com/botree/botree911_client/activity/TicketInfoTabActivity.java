package com.botree.botree911_client.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.botree.botree911_client.R;
import com.botree.botree911_client.adapter.TicketDetailAdapter;
import com.botree.botree911_client.adapter.ViewPagerAdapter;
import com.botree.botree911_client.fragment.CommentsFragment;
import com.botree.botree911_client.fragment.DetailFragment;
import com.botree.botree911_client.fragment.HistoryFragment;
import com.botree.botree911_client.utility.PreferenceUtility;
import com.botree.botree911_client.utility.Utility;

public class TicketInfoTabActivity extends AppCompatActivity implements View.OnClickListener{

    Context mContext;

    private DrawerLayout mDrawerLayout;
    private LinearLayout lnrAllTickets, lnrUnassigned, lnrNotification, lnrLogout;
    ImageView ivMenu;
    TextView tvTitle, tvUserName, tvEmail;

    TicketDetailAdapter pagerAdapter;
    ViewPager viewPager;
    ViewPager.OnPageChangeListener onPageChangeListener;

    boolean once = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_info_tab);

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
        tvTitle.setText(getString(R.string.ticket_info));

        getSupportActionBar().setCustomView(actionbar);
    } // End of setCustomActionBar()

    void getElements(){
        mContext = this;

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        viewPager = (ViewPager)findViewById(R.id.pager) ;

        lnrAllTickets = (LinearLayout) findViewById(R.id.slide_lnr_AllProjects);
        lnrUnassigned = (LinearLayout) findViewById(R.id.slide_lnr_AllUnassigned);
        lnrNotification = (LinearLayout) findViewById(R.id.slide_lnr_AllNotifications);
        lnrLogout = (LinearLayout) findViewById(R.id.slide_lnr_Logout);

        tvUserName = (TextView) findViewById(R.id.tv_UserName);
        tvEmail = (TextView) findViewById(R.id.tv_UserEmail);

        tvUserName.setText(PreferenceUtility.getUserName(mContext));
        tvEmail.setText(PreferenceUtility.getUserEmail(mContext));

        pagerAdapter = new TicketDetailAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabs.setShouldExpand(true);
        tabs.setTextSize(Utility.dpToPx(14));
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

    }// End of initElements()

    @Override
    public void onBackPressed() {
        if(mDrawerLayout.isDrawerOpen(GravityCompat.START)){
            mDrawerLayout.closeDrawers();
        }else{
            allTickets("pending");
        }
    }// End of onBackPressed()

    @Override
    protected void onResume() {
        super.onResume();

        if(once){
            if(viewPager != null){
                String type = getIntent().getStringExtra("type");
                if(type == null){
                    viewPager.setCurrentItem(0);
                }else if(type.equalsIgnoreCase("history")){
                    viewPager.setCurrentItem(1);
                }else if(type.equalsIgnoreCase("comment")){
                    viewPager.setCurrentItem(2);
                }
                once = false;
            }
        }
    }

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

            case R.id.slide_lnr_AllUnassigned:
                if(mDrawerLayout.isDrawerOpen(GravityCompat.START)){
                    mDrawerLayout.closeDrawers();
                }
                allTickets("unassigned");
                break;

            case R.id.slide_lnr_AllNotifications:
                if(mDrawerLayout.isDrawerOpen(GravityCompat.START)){
                    mDrawerLayout.closeDrawers();
                }
                notification();
                break;

            case R.id.slide_lnr_AllProjects:
                if(mDrawerLayout.isDrawerOpen(GravityCompat.START)){
                    mDrawerLayout.closeDrawers();
                }
                allTickets("pending");
                break;

            case R.id.slide_lnr_Logout:
                if(mDrawerLayout.isDrawerOpen(GravityCompat.START)){
                    mDrawerLayout.closeDrawers();
                }
                logout();

            default:
                break;

        }
    }// End of onClick()

    void notification(){
        Intent intent = new Intent(mContext, NotificationListActivity.class);
        startActivity(intent);
    }

    void allTickets(String status){

        Log.d("Status Created", status);
        Intent intent = new Intent(mContext, TicketListActivity.class);
        intent.putExtra("status", status);
        startActivity(intent);
        finish();

    }// End of allTickets()

    void logout(){

        PreferenceUtility.saveAccessToken(mContext, "");
        PreferenceUtility.saveUserName(mContext, "");
        PreferenceUtility.saveUserEmail(mContext, "");
        PreferenceUtility.saveIsLogin(mContext, false);

        Intent intent = new Intent(mContext, LoginActivity.class);
        startActivity(intent);
        finish();

    }// End of logout()
}
