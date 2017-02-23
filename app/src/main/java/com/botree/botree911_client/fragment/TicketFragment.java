package com.botree.botree911_client.fragment;

import android.annotation.SuppressLint;
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
import com.botree.botree911_client.adapter.ProjectAdapter;
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

@SuppressLint("ValidFragment")
public class TicketFragment extends Fragment implements View.OnClickListener{

    Context mContext;

    private TicketAdapter mAdapter;
    RecyclerView mRecyclerView;

    ArrayList<Ticket> allTicket;

    public TicketFragment(){

    }

    public TicketFragment(ArrayList<Ticket> allticket){
        allTicket = new ArrayList<Ticket>();
        allTicket.addAll(allticket);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tickets, container, false);

        getElements(rootView);
        initElements();

        return rootView;
    }

    void getElements(View rootView){

        mContext = getActivity();

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);

        LinearLayoutManager manager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);

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

        mAdapter = new TicketAdapter(mContext, allTicket);
        mRecyclerView.setAdapter(mAdapter);

    }// End of initElements()

    @Override
    public void onClick(View v) {

    }
}
