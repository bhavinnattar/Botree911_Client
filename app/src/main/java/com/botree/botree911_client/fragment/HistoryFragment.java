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

    private PullToLoadView mPullToLoadView;
    private HistoryAdapter mAdapter;
    private boolean isLoading = false;
    private boolean isHasLoadedAll = false;

    ProgressDialog mProgressDialog;
    //    List<ArticleObject> allData;
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

        mPullToLoadView = (PullToLoadView) rootView.findViewById(R.id.pullToLoadView);

        mRecyclerView = mPullToLoadView.getRecyclerView();

        LinearLayoutManager manager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);

        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setMessage(getString(R.string.please_wait));
        mProgressDialog.setCancelable(false);
        mProgressDialog.setIndeterminate(true);

        mList = new ArrayList<History>();

    }// End of getElements()

    void initElements(){

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

    void addData(){

        History history1=new History();
        history1.setId("1");
        history1.setUser_name("Bhavin Nattar");
        history1.setCurrent_status("Pending");
        history1.setLast_status("InProgress");
        history1.setDate_time("Feb 14,2017");

        History history2=new History();
        history2.setId("2");
        history2.setUser_name("Piyush Sanepara");
        history2.setCurrent_status("closed");
        history2.setLast_status("InProgress");
        history2.setDate_time("Jan 10,2017");

        History history3=new History();
        history3.setId("3");
        history3.setUser_name("Sadhu Rahul");
        history3.setCurrent_status("InProgress");
        history3.setLast_status("Pending");
        history3.setDate_time("Dec 25,2016");

        History history4=new History();
        history4.setId("4");
        history4.setUser_name("Bhavin Nattar");
        history4.setCurrent_status("Resolved");
        history4.setLast_status("Pending");
        history4.setDate_time("Jan 20,2017");


        History history5=new History();
        history5.setId("5");
        history5.setUser_name("Piyush Sanepara");
        history5.setCurrent_status("InProgress");
        history5.setLast_status("Pending");
        history5.setDate_time("Jan 01,2017");

        mList.add(history1);
        mList.add(history2);
        mList.add(history3);
        mList.add(history4);
        mList.add(history5);

        mAdapter = new HistoryAdapter(mContext, mList);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onResume() {
        super.onResume();

        mList.clear();
        addData();
//        new getAllHistory().execute();

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
                        JSONArray allObjects = data.getJSONArray("history");

                        for(int i=0; i< allObjects.length(); i++){
                            JSONObject jsonObject = allObjects.getJSONObject(i);

                            History history = new History();
                            history.setId(""+jsonObject.getInt("id"));
                            history.setUser_name(""+jsonObject.getString("user_name"));
                            history.setLast_status(""+jsonObject.getString("last_status"));
                            history.setCurrent_status(""+jsonObject.getString("current_status"));
                            history.setDate_time(""+jsonObject.getString("date_time"));

                            mList.add(history);

                        }

                        mAdapter = new HistoryAdapter(mContext, mList);
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