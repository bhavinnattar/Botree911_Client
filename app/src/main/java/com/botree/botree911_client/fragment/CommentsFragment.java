package com.botree.botree911_client.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.botree.botree911_client.R;
import com.botree.botree911_client.activity.TicketListActivity;
import com.botree.botree911_client.adapter.CommentAdapter;
import com.botree.botree911_client.adapter.TicketAdapter;
import com.botree.botree911_client.model.Comment;
import com.botree.botree911_client.model.Ticket;
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

public class CommentsFragment extends Fragment {

    Context mContext;

    private PullToLoadView mPullToLoadView;
    private CommentAdapter mAdapter;
    private boolean isLoading = false;
    private boolean isHasLoadedAll = false;

    ProgressDialog mProgressDialog;
    //    List<ArticleObject> allData;
    RecyclerView mRecyclerView;
    List<Comment> mList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_comments, container, false);

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

        mList = new ArrayList<Comment>();

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

        Comment comment1=new Comment();
        comment1.setId("1");
        comment1.setUser_name("Bhavin Nattar");
        comment1.setDate_time("Jan 12,2017");
        comment1.setComment("Hello Please Send me Project Details");

        Comment comment2=new Comment();
        comment2.setId("2");
        comment2.setUser_name("Piyush Sanepara");
        comment2.setDate_time("Jan 12,2017");
        comment2.setComment("ok Please Wait 5 Minutes");

        Comment comment4=new Comment();
        comment4.setId("4");
        comment4.setUser_name("Dave Bukre");
        comment4.setDate_time("Feb 12,2017");
        comment4.setComment("This is a New Comment");

        Comment comment5=new Comment();
        comment5.setId("5");
        comment5.setUser_name("Bhavin Nattar");
        comment5.setDate_time("Feb 15,2017");
        comment5.setComment("I am new Client");

        mList.add(comment1);
        mList.add(comment2);
        mList.add(comment4);
        mList.add(comment5);

        mAdapter = new CommentAdapter(mContext, mList);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onResume() {
        super.onResume();

        mList.clear();
        addData();
//        new getAllComments().execute();

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

    class getAllComments extends AsyncTask<String, String, String> {

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
                        Constant.createTicketURL + "/" + Constant.selectedTicket.getId() + Constant.commentListTicketURL,
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
                        JSONArray allObjects = data.getJSONArray("comments");

                        for(int i=0; i< allObjects.length(); i++){
                            JSONObject jsonObject = allObjects.getJSONObject(i);

                            Comment comment = new Comment();
                            comment.setId(""+jsonObject.getInt("id"));
                            comment.setUser_name(""+jsonObject.getString("user_name"));
                            comment.setComment(""+jsonObject.getString("comment"));
                            comment.setDate_time(""+jsonObject.getString("date_time"));

                            mList.add(comment);

                        }

                        mAdapter = new CommentAdapter(mContext, mList);
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