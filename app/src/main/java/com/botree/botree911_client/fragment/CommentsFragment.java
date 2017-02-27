package com.botree.botree911_client.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.botree.botree911_client.R;
import com.botree.botree911_client.adapter.CommentAdapter;
import com.botree.botree911_client.model.Comment;
import com.botree.botree911_client.utility.Constant;
import com.botree.botree911_client.utility.JSONParser;
import com.botree.botree911_client.utility.Utility;
import com.srx.widget.PullToLoadView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by bhavin on 7/2/17.
 */

public class CommentsFragment extends Fragment {

    Context mContext;

    private CommentAdapter mAdapter;

    ProgressDialog mProgressDialog;
    RecyclerView mRecyclerView;
    List<Comment> mList;

    EditText etComment;
    ImageView ivSend;

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

        etComment = (EditText) rootView.findViewById(R.id.et_comment);
        ivSend = (ImageView) rootView.findViewById(R.id.ivsend);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setReverseLayout(true);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setMessage(getString(R.string.please_wait));
        mProgressDialog.setCancelable(false);
        mProgressDialog.setIndeterminate(true);

        mList = new ArrayList<Comment>();

    }// End of getElements()

    void initElements(){

        ivSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Utility.isOnline(mContext)){
                    if(fieldValidation()){
                        new addComment().execute(etComment.getText().toString().trim());
                    }
                }else{
                    Utility.displayMessage(mContext, getString(R.string.internet_error));
                }
            }
        });

    }// End of initElements()

    boolean fieldValidation(){

        if(!Utility.isValidString(etComment.getText().toString().trim())){
            etComment.setError(getString(R.string.error_comment));
            return false;
        }
        return true;
    }// End of fieldValidation()

    @Override
    public void onResume() {
        super.onResume();

        mList.clear();
        new getAllComments().execute();

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
                            comment.setUser_id(""+jsonObject.getString("user_id"));
                            comment.setUser_name(""+jsonObject.getString("user_name"));
                            comment.setComment(""+jsonObject.getString("comment"));
                            comment.setDate_time(""+jsonObject.getString("date_time"));

                            mList.add(comment);

                        }

//                        Collections.reverse(mList);

                        mAdapter = new CommentAdapter(mContext, mList);
                        mRecyclerView.setAdapter(mAdapter);
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            closeProgress();

        }

    }// End of getData

    class addComment extends AsyncTask<String, String, String>{

        String response = null;
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
                ticket.put("description", params[0]);

                jsonObject.put("ticket", ticket);
                response = mJsonParser.makeHttpRequest(mContext,
                        Constant.createTicketURL + "/" + Constant.selectedTicket.getId() + Constant.addCommentTicketURL,
                        "POST", jsonObject, param);

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
                            etComment.setText("");
                            Utility.displayMessage(mContext, message);
                            mList.clear();
                            new getAllComments().execute();
                        }else{
                            Utility.displayMessage(mContext, message);
                        }

                } catch (Exception e){
                    e.printStackTrace();
                }
            }

            closeProgress();
        }
    }

}