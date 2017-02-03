package com.botree.botree911_client.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.botree.botree911_client.R;
import com.botree.botree911_client.model.Status;
import com.botree.botree911_client.utility.Constant;
import com.botree.botree911_client.utility.JSONParser;
import com.botree.botree911_client.utility.Utility;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class TicketCreateActivity extends Activity implements View.OnClickListener{

    Context mContext;
    ProgressDialog mProgressDialog;

    ArrayList<Status> allStatus;

    Spinner spnrStatus;
    TextView tvCreate, tvCancel;
    EditText etTicketName, etDescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_create);

        getElements();
        initElements();

    }// End of onCreate()

    void getElements(){

        mContext = this;

        spnrStatus = (Spinner) findViewById(R.id.spnrStatus);
        etTicketName = (EditText) findViewById(R.id.et_TicketName);
        etDescription = (EditText) findViewById(R.id.et_TicketDescription);
        tvCreate = (TextView) findViewById(R.id.tvCreate);
        tvCancel = (TextView) findViewById(R.id.tvCancel);

        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setMessage(getString(R.string.please_wait));
        mProgressDialog.setCancelable(false);
        mProgressDialog.setIndeterminate(true);

        allStatus = new ArrayList<Status>();

    }// End of getElements()

    void initElements(){

        tvCreate.setOnClickListener(this);
        tvCancel.setOnClickListener(this);

        new getAllStatus().execute();

    }// End of initElements()

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

            case R.id.tvCancel:
                finish();
                break;

            default:
                break;
        }

    }// End of onClick()


    void createTicket(){

        if(fieldValidation(etTicketName.getText().toString().trim(),
                etDescription.getText().toString().trim())){
            if(Utility.isOnline(mContext)){
                String project_Id = getIntent().getStringExtra("projectid");
                new createTicket().execute(project_Id,
                        etTicketName.getText().toString().trim(),
                        etDescription.getText().toString().trim(),
                        allStatus.get(spnrStatus.getSelectedItemPosition()).getStatusValue());
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
                        JSONObject allStatusobj = data.getJSONObject("ticket_status");

                        Iterator iterator = allStatusobj.keys();

                        ArrayList<String> displayStatus = new ArrayList<String>();

                        while(iterator.hasNext()){
                            String key = (String)iterator.next();
                            int value = allStatusobj.getInt(key);

                            com.botree.botree911_client.model.Status status1 = new com.botree.botree911_client.model.Status();

                            status1.setStatusName(key);
                            status1.setStatusValue(""+value);

                            displayStatus.add(key);

                            allStatus.add(status1);
                        }

                        ArrayAdapter<String> statusAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, displayStatus);
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
