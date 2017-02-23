package com.botree.botree911_client.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.botree.botree911_client.R;
import com.botree.botree911_client.adapter.CommentAdapter;
import com.botree.botree911_client.adapter.HistoryAdapter;
import com.botree.botree911_client.model.Comment;
import com.botree.botree911_client.model.History;
import com.botree.botree911_client.utility.Constant;
import com.botree.botree911_client.utility.JSONParser;
import com.srx.widget.PullCallback;
import com.srx.widget.PullToLoadView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by bhavin on 7/2/17.
 */

public class HistoryFragment extends Fragment {

    Context mContext;

    private HistoryAdapter mAdapter;

    ProgressDialog mProgressDialog;
    RecyclerView mRecyclerView;
    List<History> mList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_history, container, false);

        getElements(rootView);
        initElements();

        return rootView;
    }

    void getElements(View rootView){

        mContext = getActivity();

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);

        LinearLayoutManager manager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);

        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setMessage(getString(R.string.please_wait));
        mProgressDialog.setCancelable(false);
        mProgressDialog.setIndeterminate(true);

        mList = new ArrayList<History>();

    }// End of getElements()

    void initElements(){


    }// End of initElements()

    @Override
    public void onResume() {
        super.onResume();

        mList.clear();
//        addData();
        new getAllHistory().execute();

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

    class getAllHistory extends AsyncTask<String, String, String> {

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

                response = mJsonParser.makeHttpRequest(mContext,
                        Constant.createTicketURL + "/" + Constant.selectedTicket.getId() + Constant.historyListTicketURL,
                        "GET", jsonObject, param);

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
                        JSONArray allObjects = data.getJSONArray("ticket_history");

                        for(int i=0; i< allObjects.length(); i++){
                            JSONObject jsonObject = allObjects.getJSONObject(i);

                            String id = ""+jsonObject.getInt("id");
                            String username = ""+jsonObject.getString("user_name");
                            String datetime = ""+jsonObject.getString("date_time");

                            JSONArray historyChange = jsonObject.getJSONArray("change_history");

                            for(int j=0; j< historyChange.length(); j++){

                                JSONObject jMessage = historyChange.getJSONObject(j);

                                History history = new History();
                                history.setId(id);
                                history.setUser_name(username);
                                history.setDate_time(datetime);
                                history.setMessage(jMessage.getString("body"));

                                mList.add(history);

                            }

                        }

                        mAdapter = new HistoryAdapter(mContext, mList);
                        mRecyclerView.setAdapter(mAdapter);
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            closeProgress();
        }

    }// End of getData

}