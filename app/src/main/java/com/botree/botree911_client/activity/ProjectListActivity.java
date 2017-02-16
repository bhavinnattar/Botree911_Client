package com.botree.botree911_client.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.botree.botree911_client.R;
import com.botree.botree911_client.adapter.ProjectAdapter;
import com.botree.botree911_client.model.Project;
import com.botree.botree911_client.utility.Constant;
import com.botree.botree911_client.utility.JSONParser;
import com.botree.botree911_client.utility.PreferenceUtility;
import com.botree.botree911_client.utility.RecyclerItemClickListener;
import com.botree.botree911_client.utility.Utility;
import com.srx.widget.PullCallback;
import com.srx.widget.PullToLoadView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProjectListActivity extends AppCompatActivity implements View.OnClickListener{

    Context mContext;

    private PullToLoadView mPullToLoadView;
    private ProjectAdapter mAdapter;
    private boolean isLoading = false;
    private boolean isHasLoadedAll = false;

    ProgressDialog mProgressDialog;
//    List<ArticleObject> allData;
    RecyclerView mRecyclerView;

    private DrawerLayout mDrawerLayout;
    private LinearLayout lnrAllProjects, lnrLogout;
    ImageView ivMenu;
    TextView tvTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_list);

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
        tvTitle.setText(getString(R.string.project_list));

        getSupportActionBar().setCustomView(actionbar);
    } // End of setCustomActionBar()

    void getData(){

        mAdapter = new ProjectAdapter(mContext, Constant.allProjects);
        mRecyclerView.setAdapter(mAdapter);
    }

    void getElements(){

        mContext = this;

        mPullToLoadView = (PullToLoadView) findViewById(R.id.pullToLoadView);

        mRecyclerView = mPullToLoadView.getRecyclerView();

        LinearLayoutManager manager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        lnrAllProjects = (LinearLayout) findViewById(R.id.slide_lnr_AllProjects);
        lnrLogout = (LinearLayout) findViewById(R.id.slide_lnr_Logout);

        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setMessage(getString(R.string.please_wait));
        mProgressDialog.setCancelable(false);
        mProgressDialog.setIndeterminate(true);

        Constant.allProjects.trimToSize();

    }// End of getElements()

    void initElements(){

        lnrAllProjects.setOnClickListener(this);
        lnrLogout.setOnClickListener(this);

        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(mContext, mRecyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        // do whatever
                        try{

//                            Project project = Constant.allProjects.get(position);
//
//                            Intent intent = new Intent(mContext, TicketListActivity.class);
//                            intent.putExtra("projectid", project.getId());
//                            startActivity(intent);

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );

        mPullToLoadView.isLoadMoreEnabled(true);

        mPullToLoadView.setPullCallback(new PullCallback() {
            @Override
            public void onLoadMore() {

                mPullToLoadView.setComplete();
                isLoading = false;

//                if(dataObject.getStatus() == AsyncTask.Status.RUNNING || dataObject.getStatus() == AsyncTask.Status.PENDING){
//                    mPullToLoadView.setComplete();
//                    isLoading = false;
//                }else{
//                    currentPoint++;
//                    dataObject = new getData();
//                    dataObject.execute(""+currentPoint);
//                }
            }

            @Override
            public void onRefresh() {
                if(mAdapter!=null) {
                    mPullToLoadView.setComplete();
                    isLoading = false;
//                    isRefresh = false;
//
//                    if(dataObject.getStatus() == AsyncTask.Status.RUNNING || dataObject.getStatus() == AsyncTask.Status.PENDING){
//                        mPullToLoadView.setComplete();
//                        isLoading = false;
//                    }else{
//                        mAdapter.clear();
//                        currentPoint = 1;
//                        dataObject = new getData();
//                        dataObject.execute(""+currentPoint);
//                    }
//
                }
            }

            @Override
            public boolean isLoading() {
                Log.e("main activity", "main isLoading:" + isLoading);
                return isLoading;
            }

            @Override
            public boolean hasLoadedAllItems() {
                return isHasLoadedAll;
            }
        });



    }// End of initElements()

    @Override
    public void onBackPressed() {
        if(mDrawerLayout.isDrawerOpen(GravityCompat.START)){
            mDrawerLayout.closeDrawers();
        }else{
            super.onBackPressed();
        }
    }// End of onBackPressed()

    @Override
    protected void onResume() {
        super.onResume();

        Constant.allProjects.clear();
        Constant.allProjects.trimToSize();
//        new getAllProjects().execute();

        getData();

    }// End of onResume()

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

            case R.id.slide_lnr_AllProjects:
                if(mDrawerLayout.isDrawerOpen(GravityCompat.START)){
                    mDrawerLayout.closeDrawers();
                }
                break;

            case R.id.slide_lnr_Logout:
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

    class getAllProjects extends AsyncTask<String, String, String> {

        String response;
        JSONParser mJsonParser;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mJsonParser = new JSONParser();
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

                response = mJsonParser.makeHttpRequest(mContext, Constant.allProjectURL, "GET", jsonObject, param);

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
                        JSONArray allObjects = data.getJSONArray("projects");

                        for(int i=0; i< allObjects.length(); i++){
                            JSONObject jsonObject = allObjects.getJSONObject(i);

                            Project project = new Project();
                            project.setId(""+jsonObject.getInt("id"));
                            project.setName(""+jsonObject.getString("name"));
                            project.setDescription(""+jsonObject.getString("description"));
                            project.setNoOfTeam(""+jsonObject.getInt("total_member"));

                            Constant.allProjects.add(project);

                        }

                        mAdapter = new ProjectAdapter(mContext, Constant.allProjects);
                        mRecyclerView.setAdapter(mAdapter);
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
