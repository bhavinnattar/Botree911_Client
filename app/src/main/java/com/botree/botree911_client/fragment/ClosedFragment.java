package com.botree.botree911_client.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.botree.botree911_client.R;
import com.botree.botree911_client.activity.TicketInfoTabActivity;
import com.botree.botree911_client.adapter.TicketAdapter;
import com.botree.botree911_client.model.Ticket;
import com.botree.botree911_client.utility.Constant;
import com.botree.botree911_client.utility.RecyclerItemClickListener;
import com.srx.widget.PullCallback;
import com.srx.widget.PullToLoadView;

import java.util.ArrayList;

/**
 * Created by bhavin on 9/2/17.
 */

public class ClosedFragment extends Fragment implements View.OnClickListener{

    Context mContext;
    ProgressDialog mProgressDialog;

    private PullToLoadView mPullToLoadView;
    private TicketAdapter mAdapter;
    private boolean isLoading = false;
    private boolean isHasLoadedAll = false;
    RecyclerView mRecyclerView;

    ArrayList<Ticket> allTicket;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_closed, container, false);

        getElements(rootView);
        initElements();

        return rootView;
    }

    void getElements(View rootView) {

        mContext = getActivity();

        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setMessage(getString(R.string.please_wait));
        mProgressDialog.setCancelable(false);
        mProgressDialog.setIndeterminate(true);

        mPullToLoadView = (PullToLoadView) rootView.findViewById(R.id.pullToLoadView);

        mRecyclerView = mPullToLoadView.getRecyclerView();

        LinearLayoutManager manager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);

        allTicket = new ArrayList<Ticket>();

    }// End of getElements()

    void initElements(){

        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(mContext, mRecyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        try{

                            Constant.selectedTicket = allTicket.get(position);

                            Intent intent = new Intent(mContext, TicketInfoTabActivity.class);
                            startActivity(intent);

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
            }

            @Override
            public void onRefresh() {
                if(mAdapter!=null) {
                    mPullToLoadView.setComplete();
                    isLoading = false;
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

        getData();

        mAdapter = new TicketAdapter(mContext, allTicket);
        mRecyclerView.setAdapter(mAdapter);

    }// End of initElements()

    void getData(){

        for(int i = 0; i < Constant.allTickets.size(); i++){
            Ticket ticket = Constant.allTickets.get(i);
            if(ticket.getStatus().equalsIgnoreCase("closed")){
                allTicket.add(ticket);
            }
        }

    }

    @Override
    public void onClick(View v) {

    }
}