package com.botree.botree911_client.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.botree.botree911_client.R;
import com.botree.botree911_client.activity.TicketCreateActivity;
import com.botree.botree911_client.model.Project;
import com.botree.botree911_client.model.ProjectOld;
import com.botree.botree911_client.model.Status;
import com.botree.botree911_client.model.Ticket;
import com.botree.botree911_client.utility.Constant;
import com.botree.botree911_client.utility.JSONParser;
import com.botree.botree911_client.utility.Utility;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by bhavin on 7/2/17.
 */

public class DetailFragment extends Fragment implements View.OnClickListener{

    Context mContext;
    ProgressDialog mProgressDialog;

    ArrayList<Status> allStatus;
    ArrayList<String> allProjectName;
    ArrayList<Project> allProject;

    Spinner spnrProjects, spnrStatus;
    EditText etTicketName, etDescription;
    TextView tvEditTicket, tvSummary, tvDescription;

    Ticket ticket;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        getElements(rootView);
        initElements();

        return rootView;
    }

    void getElements(View rootView){

        mContext = getActivity();

        spnrStatus = (Spinner) rootView.findViewById(R.id.spnrStatus);
        spnrProjects = (Spinner) rootView.findViewById(R.id.spnrProjects);
        etTicketName = (EditText) rootView.findViewById(R.id.et_TicketName);
        etDescription = (EditText) rootView.findViewById(R.id.et_TicketDescription);
        tvEditTicket = (TextView) rootView.findViewById(R.id.tvEdit);
        tvSummary = (TextView) rootView.findViewById(R.id.tv_Summary);
        tvDescription = (TextView) rootView.findViewById(R.id.tv_Desc);

        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setMessage(getString(R.string.please_wait));
        mProgressDialog.setCancelable(false);
        mProgressDialog.setIndeterminate(true);

        allStatus = new ArrayList<Status>();
        allProjectName = new ArrayList<String>();
        allProject = new ArrayList<Project>();
        ticket= new Ticket();

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

        getInfo();

    }// End of getElements()

    void initElements(){

        tvEditTicket.setOnClickListener(this);

        new getAllProject().execute();

    }// End of initElements()

    void getInfo(){

        ticket = Constant.selectedTicket;

        etTicketName.setText(ticket.getName());
        etDescription.setText(ticket.getDescription());

    }

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

            case R.id.tvEdit:
                editTicket();
                break;

            default:
                break;
        }

    }// End of onClick()

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

    void editTicket(){

        if(fieldValidation(etTicketName.getText().toString().trim(),
                etDescription.getText().toString().trim())){
            if(Utility.isOnline(mContext)){
                new EditTicket().execute(allProject.get(spnrProjects.getSelectedItemPosition()).getId(),
                        etTicketName.getText().toString().trim(),
                        etDescription.getText().toString().trim(),
                        allStatus.get(spnrStatus.getSelectedItemPosition()).getStatusValue());
            }else{
                Utility.displayMessage(mContext, getString(R.string.internet_error));
            }
        }

    }// End of editTicket()

    int getCurrentStatus(){
        int index = 0;
        for(int i=0; i< allStatus.size(); i++){
            Status status = allStatus.get(i);
            if(status.getStatusValue().equalsIgnoreCase(ticket.getStatus_id())){
                index = i;
                break;
            }
        }
        return index;
    }

    int getCurrentProject(){
        int index = 0;
        for(int i=0; i< allProject.size(); i++){
            Project project = allProject.get(i);
            if(project.getId().equalsIgnoreCase(ticket.getProject_id())){
                index = i;
                break;
            }
        }
        return index;
    }

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

                        ArrayAdapter<String> statusAdapter = new ArrayAdapter<String>(mContext, R.layout.spinner_row, R.id.tv_title, displayStatus);
                        spnrStatus.setAdapter(statusAdapter);

                        spnrStatus.setSelection(getCurrentStatus());
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }

            }
            closeProgress();
        }
    }// End of getAllStatus

    class getAllProject extends AsyncTask<String, String, String> {

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
                        JSONArray allStatusobj = data.getJSONArray("projects");
                        ArrayList<String> displayProject = new ArrayList<String>();

                        for(int i=0; i < allStatusobj.length(); i++){
                            JSONObject jsonObject = allStatusobj.getJSONObject(i);

                            Project project = new Project();

                            project.setId(""+jsonObject.getInt("project_id"));
                            project.setName(""+jsonObject.getString("name"));

                            displayProject.add(jsonObject.getString("name"));

                            allProject.add(project);
                        }

                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(mContext, R.layout.spinner_row, R.id.tv_title, displayProject);
                        spnrProjects.setAdapter(arrayAdapter);

                        spnrProjects.setSelection(getCurrentProject());
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            new getAllStatus().execute();
        }
    }// End of getAllProject

    class EditTicket extends AsyncTask<String, String, String> {

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

                response = mJsonParser.makeHttpRequest(mContext, Constant.editTicketURL +
                        DetailFragment.this.ticket.getId(), "PATCH", jsonObject, param);

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