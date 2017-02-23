package com.botree.botree911_client.activity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.botree.botree911_client.R;
import com.botree.botree911_client.utility.Constant;
import com.botree.botree911_client.utility.JSONParser;
import com.botree.botree911_client.utility.PreferenceUtility;
import com.botree.botree911_client.utility.Utility;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import net.danlew.android.joda.JodaTimeAndroid;

import org.joda.time.DateTimeZone;
import org.json.JSONObject;

import java.util.HashMap;

import br.com.forusers.heinsinputdialogs.HeinsInputDialog;
import br.com.forusers.heinsinputdialogs.interfaces.OnInputStringListener;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    Context mContext;

    // Location
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    int requestCode = 1001;
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;
    String[] requestArray = new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    LocationSettingsRequest.Builder builder;
    PendingResult<LocationSettingsResult> result;


    EditText etUserEmail, etPassword;
    TextView txtLogIn, txtForgotPassword;
    ProgressDialog mProgressDialog;
    loginAsync mLoginAsync;

    ImageView ivMenu;
    TextView tvTitle;

    Dialog forgotDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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

//        ivMenu.setOnClickListener(this);
        ivMenu.setVisibility(View.INVISIBLE);
        tvTitle.setText(getString(R.string.signin));

        getSupportActionBar().setCustomView(actionbar);
    } // End of setCustomActionBar()

    @Override
    protected void onResume() {
        super.onResume();
//        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, requestArray, requestCode);
//        } else {
//            Log.e("onResume", "onResume");
//            settingsrequest();
//        }
    }// End of onResume()

    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }// End of onStart()

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }
    }// End of onDestroy()

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {

            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:

                        break;
                    case Activity.RESULT_CANCELED:
                        Log.e("RESULT_CANCELED", "RESULT_CANCELED");
                        settingsrequest();//keep asking if imp or do whatever
                        break;
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (this.requestCode == requestCode && grantResults.length > 0) {
            Log.e("grantResults.length", "" + grantResults.length);
            boolean boolPermission = false;
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    boolPermission = true;
                } else {
                    boolPermission = false;
                    break;
                }
            }

            if (boolPermission) {
                Log.e("onRequestPermissionsRe", "onRequestPermissionsResult");
                requestLocation();
            }
        }
    }// End of onRequestPermissionsResult()

    public void requestLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }// End of requestLocation()

    synchronized void buildGoogleApiClient() {
        Log.d("buildGoogleApiClient","buildGoogleApiClient");
        if(mGoogleApiClient == null){
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }else{
            if(mGoogleApiClient.isConnected()){
                requestLocation();
            }
        }
    }// End of buildGoogleApiClient()

    void getElements(){

        mContext = this;

        JodaTimeAndroid.init(this);

        etUserEmail = (EditText) findViewById(R.id.et_email);
        etPassword = (EditText) findViewById(R.id.et_password);
        txtLogIn = (TextView) findViewById(R.id.txt_Login);
        txtForgotPassword = (TextView) findViewById(R.id.txt_forgotPassword);

        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setMessage(getString(R.string.please_wait));
        mProgressDialog.setCancelable(false);
        mProgressDialog.setIndeterminate(true);

    }// End of getElements()

    void initElements(){

        buildGoogleApiClient();

        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(100); // Update location every second
        mLocationRequest.setFastestInterval(5 * 1000);

        builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);
        builder.setAlwaysShow(true);

        txtLogIn.setOnClickListener(this);
        txtForgotPassword.setOnClickListener(this);

        etPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
//                    login();
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });

    }// End of initElements()

    void displayProgress(){

        if(mProgressDialog != null && !mProgressDialog.isShowing()){
            mProgressDialog.show();
        }

    }// End of displayProgress()

    void closeProgress(){

        if(mProgressDialog != null && !LoginActivity.this.isFinishing() && mProgressDialog.isShowing()){
            mProgressDialog.dismiss();
        }

    }// End of closeProgress()

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.txt_Login:
                login();
                break;

            case R.id.txt_forgotPassword:
                forgotPassword();
                break;

            default:
                break;
        }

    }// End of onClick()

    void forgotPassword(){

        showDialog();

    }// End of forgotpassword()

    public void showDialog(){
        forgotDialog = new Dialog(mContext);
        forgotDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        forgotDialog.setCancelable(false);
        forgotDialog.setContentView(R.layout.layout_forgetpassword);

        final EditText et_Email = (EditText) forgotDialog.findViewById(R.id.et_Email);

        TextView tvOk = (TextView) forgotDialog.findViewById(R.id.tv_Ok);
        TextView tvCancel = (TextView) forgotDialog.findViewById(R.id.tv_Cancel);

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgotDialog.dismiss();
            }
        });

        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Utility.isOnline(mContext)){
                    String email = et_Email.getText().toString().trim();
                    if(Utility.isEmailValid(email)){
                        new ForgetPassword().execute(email);
                    }else{
                        et_Email.setError(getString(R.string.email_error));
                    }
                }else{
                    Utility.displayMessage(mContext, getString(R.string.internet_error));
                }
            }
        });

        forgotDialog.show();

    }// End of showDialog()

    boolean fieldValidation(String email, String password){

        if(!Utility.isEmailValid(email)){
            etUserEmail.setError(getString(R.string.email_error));
            return false;
        }

        if(!Utility.isValidString(password)){
            etPassword.setError(getString(R.string.password_error));
            return false;
        }

        return true;

    }// End of fieldValidation()

    void login(){

        if(fieldValidation(etUserEmail.getText().toString().trim(),
                etPassword.getText().toString().trim())){

            PreferenceUtility.saveUserEmail(mContext, "client@gmail.com");
            PreferenceUtility.saveUserName(mContext, "John"+" "+"");
            PreferenceUtility.saveIsLogin(mContext, true);

            if(Utility.isOnline(mContext)){

                if(mLoginAsync == null || mLoginAsync.getStatus() == AsyncTask.Status.FINISHED){
                    mLoginAsync = new loginAsync();
                    mLoginAsync.execute(etUserEmail.getText().toString().trim(), etPassword.getText().toString().trim());
                }

            }else{
                Utility.displayMessage(mContext, getString(R.string.internet_error));
            }
        }

    }// End of login()

    class ForgetPassword extends AsyncTask<String, String, String>{

        String responseString = "";
        JSONParser jsonParser;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            displayProgress();
            jsonParser = new JSONParser();
        }

        @Override
        protected String doInBackground(String... params) {
            try{

                JSONObject jsonObject = new JSONObject();
                HashMap<String, String> param = new HashMap<>();

                jsonObject.put("device_token", Utility.getDeviceId(mContext));
                jsonObject.put("email", params[0]);

                responseString = jsonParser.makeHttpRequest(mContext, Constant.forgotPasswordURL, "POST", jsonObject, param);

            }catch (Exception e){
                e.printStackTrace();
            }

            return responseString;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            closeProgress();
            if(s != null && s.length() > 0){

                try {

                    JSONObject jsonObject = new JSONObject(s);

                    if (jsonObject != null) {

                        boolean isSuccess = jsonObject.getBoolean("status");
                        String message = jsonObject.getString("message");
                        Log.d("Status", "" + isSuccess);
                        if (isSuccess) {
                            Utility.displayMessage(mContext, message);
                            if(forgotDialog != null){
                                forgotDialog.dismiss();
                            }
                        }else{
                            Utility.displayMessage(mContext, message);
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    class loginAsync extends AsyncTask<String, String, String>{

        String responseString = "";
        JSONParser jsonParser;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            displayProgress();
            jsonParser = new JSONParser();
        }

        @Override
        protected String doInBackground(String... params) {

            try{

                JSONObject jsonObject = new JSONObject();
                HashMap<String, String> param = new HashMap<>();

                JSONObject user = new JSONObject();

                user.put("email", params[0]);
                user.put("password", params[1]);

                jsonObject.put("user",user);
                jsonObject.put("device_token", Utility.getDeviceId(mContext));
                jsonObject.put("device_type", Constant.device_type);
                jsonObject.put("location", DateTimeZone.getDefault().getID());

                responseString = jsonParser.makeHttpRequest(mContext, Constant.loginURL, "POST", jsonObject, param);

            }catch (Exception e){
                e.printStackTrace();
            }

            return responseString;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            closeProgress();
            if(s != null && s.length() > 0){
                Log.d("response", s);

                try{

                    JSONObject jsonObject = new JSONObject(s);

                    if(jsonObject != null){

                        boolean isSuccess = jsonObject.getBoolean("status");
                        String message = jsonObject.getString("message");
                        Log.d("Status", ""+isSuccess);
                        if(isSuccess){
                            JSONObject data = jsonObject.getJSONObject("data");
                            if(data != null){
                                JSONObject user = data.getJSONObject("user");

                                String userEmail = user.getString("email");
                                String userId = user.getString("user_id");
                                String accessToken = user.getString("access_token");
                                String firstName = user.getString("first_name");
                                String lastName = user.getString("last_name");

                                PreferenceUtility.saveUserId(mContext, userId);
                                PreferenceUtility.saveAccessToken(mContext, accessToken);
                                PreferenceUtility.saveUserEmail(mContext, userEmail);
                                PreferenceUtility.saveUserName(mContext, firstName+" "+lastName);
                                PreferenceUtility.saveIsLogin(mContext, true);

                                Intent intent = new Intent(mContext, TicketCreateActivity.class);
                                startActivity(intent);
                                finish();

                            }else{

                            }
                        }else{
                            Utility.displayMessage(mContext, message);
                        }
                    }

                }catch(Exception e){
                    e.printStackTrace();
                }
            }else{
                Utility.displayMessage(mContext, getString(R.string.server_error));
            }
        }
    }// End of loginAsync

    public void settingsrequest() {

        Log.d("settingsrequest()", "settingsrequest()");

        result = LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());

        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                final LocationSettingsStates state = result.getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied. The client can initialize location
                        // requests here.

                        buildGoogleApiClient();
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied. But could be fixed by showing the user
                        // a dialog.

                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult((Activity) mContext, REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }

                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way to fix the
                        // settings so we won't show the dialog.
                        break;
                }
            }
        });
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        requestLocation();
    }// End of onConnected()

    @Override
    public void onConnectionSuspended(int i) {

    }// End of onConnectionSuspended()

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        buildGoogleApiClient();
    }// End of onConnectionFailed()

    @Override
    public void onLocationChanged(Location location) {
        PreferenceUtility.saveLocation(mContext,
                String.valueOf(location.getLatitude()),
                String.valueOf(location.getLongitude())
        );
    }// End of onLocationChanged()
}
