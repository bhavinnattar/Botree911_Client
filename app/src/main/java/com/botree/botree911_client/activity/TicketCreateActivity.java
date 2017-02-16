package com.botree.botree911_client.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.botree.botree911_client.R;
import com.botree.botree911_client.model.Project;
import com.botree.botree911_client.model.Status;
import com.botree.botree911_client.utility.Constant;
import com.botree.botree911_client.utility.JSONParser;
import com.botree.botree911_client.utility.PreferenceUtility;
import com.botree.botree911_client.utility.Utility;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class TicketCreateActivity extends AppCompatActivity implements View.OnClickListener{

    Context mContext;
    ProgressDialog mProgressDialog;

    ArrayList<Status> allStatus;
    ArrayList<String> allProjectName;

    Spinner spnrProjects, spnrStatus;
    TextView tvCreate;
    EditText etTicketName, etDescription;

    private DrawerLayout mDrawerLayout;
    private LinearLayout lnrAllTickets, lnrUnassigned, lnrNotification, lnrLogout;

    ImageView ivMenu;
    TextView tvTitle, tvSkip, tvSummary, tvDescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_create);

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
        tvSkip = (TextView) actionbar.findViewById(R.id.actionbar_rightTv);

        ivMenu.setOnClickListener(this);
        tvSkip.setOnClickListener(this);
        tvTitle.setText(getString(R.string.create_ticket));
        tvSkip.setText(getString(R.string.skip));

        getSupportActionBar().setCustomView(actionbar);
    } // End of setCustomActionBar()

    void getElements(){

        mContext = this;

        spnrStatus = (Spinner) findViewById(R.id.spnrStatus);
        spnrProjects = (Spinner) findViewById(R.id.spnrProjects);
        etTicketName = (EditText) findViewById(R.id.et_TicketName);
        etDescription = (EditText) findViewById(R.id.et_TicketDescription);
        tvCreate = (TextView) findViewById(R.id.tvCreate);
        tvSummary = (TextView) findViewById(R.id.tv_Summary);
        tvDescription = (TextView) findViewById(R.id.tv_Desc);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        lnrAllTickets = (LinearLayout) findViewById(R.id.slide_lnr_AllProjects);
        lnrUnassigned = (LinearLayout) findViewById(R.id.slide_lnr_AllUnassigned);
        lnrNotification = (LinearLayout) findViewById(R.id.slide_lnr_AllNotifications);
        lnrLogout = (LinearLayout) findViewById(R.id.slide_lnr_Logout);

        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setMessage(getString(R.string.please_wait));
        mProgressDialog.setCancelable(false);
        mProgressDialog.setIndeterminate(true);

        allStatus = new ArrayList<Status>();
        allProjectName = new ArrayList<String>();

        SpannableStringBuilder builderSummary = new SpannableStringBuilder();
        SpannableStringBuilder builderDesc = new SpannableStringBuilder();
        SpannableString str1= new SpannableString(getString(R.string.summary));
        str1.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.defaultGrey)), 0, str1.length(), 0);

        SpannableString str2= new SpannableString("*");
        str2.setSpan(new ForegroundColorSpan(Color.RED), 0, str2.length(), 0);

        SpannableString str3= new SpannableString(getString(R.string.description));
        str3.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.defaultGrey)), 0, str3.length(), 0);

        builderSummary.append(str1);
        builderSummary.append(str2);
        builderDesc.append(str3);
        builderDesc.append(str2);

        tvSummary.setText(builderSummary);
        tvDescription.setText(builderDesc);


    }// End of getElements()

    void initElements(){

        lnrAllTickets.setOnClickListener(this);
        lnrUnassigned.setOnClickListener(this);
        lnrNotification.setOnClickListener(this);
        lnrLogout.setOnClickListener(this);

        tvCreate.setOnClickListener(this);

        getProjectNames();

//        new getAllStatus().execute();

    }// End of initElements()

    @Override
    public void onBackPressed() {
        if(mDrawerLayout.isDrawerOpen(GravityCompat.START)){
            mDrawerLayout.closeDrawers();
        }else{
            super.onBackPressed();
            allTickets("pending");
        }
    }// End of onBackPressed()

    void getProjectNames(){

        for(int i=0; i < Constant.allProjects.size(); i++){
            Project project = Constant.allProjects.get(i);
            allProjectName.add(project.getName());
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(mContext, R.layout.spinner_row, R.id.tv_title, allProjectName);
        spnrProjects.setAdapter(arrayAdapter);

        ArrayList<String> displayStatus = new ArrayList<String>();
        displayStatus.add("Pending");
        displayStatus.add("InProgress");
        displayStatus.add("Resolved");
        displayStatus.add("Closed");

        spnrStatus.setEnabled(false);
        ArrayAdapter<String> statusAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, displayStatus);
        spnrStatus.setAdapter(statusAdapter);

    }// getProjectNames()

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

            case R.id.tvCreate:
                createTicket();
                break;

            case R.id.actionbar_rightTv:
                allTickets("pending");
                break;

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

    void allTickets(String status){

        Log.d("Status Created", status);
        Intent intent = new Intent(mContext, TicketListActivity.class);
        intent.putExtra("status", status);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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


    void createTicket(){

        if(fieldValidation(etTicketName.getText().toString().trim(),
                etDescription.getText().toString().trim())){
            if(Utility.isOnline(mContext)){
//                String project_Id = getIntent().getStringExtra("projectid");
//                new createTicket().execute(Constant.allProjects.get(spnrProjects.getSelectedItemPosition()).getId(),
//                        etTicketName.getText().toString().trim(),
//                        etDescription.getText().toString().trim(),
//                        allStatus.get(spnrStatus.getSelectedItemPosition()).getStatusValue());

                Utility.displayMessage(mContext, "Ticket Created Successfully.");
                allTickets("pending");
            }else{
                Utility.displayMessage(mContext, getString(R.string.internet_error));
            }
        }

    }// End of createTicket()

    boolean fieldValidation(String name, String description){

        if(!Utility.isValidString(name)){
            etTicketName.setError(getString(R.string.ticketname_error));
            return false;
        }

        if(!Utility.isValidString(description)){
            etDescription.setError(getString(R.string.ticket_description_error));
            return false;
        }

        return true;
    }// End of fieldValidation()

    class getAllStatus extends AsyncTask<String, String, String> {

        String response;
        JSONParser mJsonParser;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mJsonParser = new JSONParser();
        }

        @Override
        protected String doInBackground(String... params) {

            try{

                HashMap<String, String> param = new HashMap<>();
                JSONObject jsonObject = new JSONObject();

                response = mJsonParser.makeHttpRequest(mContext, Constant.allStatusURL, "GET", jsonObject, param);

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
                        JSONArray allStatusobj = data.getJSONArray("ticket_status");
                        ArrayList<String> displayStatus = new ArrayList<String>();

                        for(int i=0; i < allStatusobj.length(); i++){
                            JSONObject jsonObject = allStatusobj.getJSONObject(i);

                            com.botree.botree911_client.model.Status status1 = new com.botree.botree911_client.model.Status();

                            status1.setStatusName(jsonObject.getString("name"));
                            status1.setStatusValue(""+jsonObject.getInt("value"));

                            displayStatus.add(jsonObject.getString("name"));

                            allStatus.add(status1);
                        }

                        ArrayAdapter<String> statusAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, displayStatus);
                        spnrStatus.setAdapter(statusAdapter);
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }

            }

        }
    }// End of getAllStatus

    class createTicket extends AsyncTask<String, String, String> {

        String response;
        JSONParser mJsonParser;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mJsonParser = new JSONParser();
            displayProgress();
        }

        @Override
        protected String doInBackground(String... params) {

            try{

                HashMap<String, String> param = new HashMap<>();
                JSONObject jsonObject = new JSONObject();

                JSONObject ticket = new JSONObject();

                ticket.put("project_id", Integer.parseInt(params[0]));
                ticket.put("name", params[1]);
                ticket.put("description", params[2]);
                ticket.put("status", Integer.parseInt(params[3]));
                ticket.put("holder_type", Constant.holder_type);

                jsonObject.put("ticket", ticket);

                response = mJsonParser.makeHttpRequest(mContext, Constant.createTicketURL, "POST", jsonObject, param);

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
                    String message = jObject.getString("message");

                    if(status){
                        Utility.displayMessage(mContext, message);
                        finish();
                    }else{
                        Utility.displayMessage(mContext, message);
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            closeProgress();

        }
    }// End of createTicket

}
