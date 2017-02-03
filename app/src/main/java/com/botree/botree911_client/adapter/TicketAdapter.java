package com.botree.botree911_client.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.botree.botree911_client.R;
import com.botree.botree911_client.model.Ticket;

import java.util.List;

/**
 * Created by bhavin on 2/2/17.
 */

public class TicketAdapter extends RecyclerView.Adapter<TicketCellHolder> {

        List<Ticket> mList;
        Context mContext;

        public TicketAdapter(Context mContext, List<Ticket> mList) {
            this.mContext = mContext;
            this.mList = mList;
        }

        @Override
        public TicketCellHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {
            final View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ticket_row, viewGroup, false);

            return new TicketCellHolder(view);
        }

        @Override
        public void onBindViewHolder(TicketCellHolder holder, int i) {

            try{

                Ticket ticket = mList.get(i);

                holder.tvTitle.setText(ticket.getName());
                holder.tvDescription.setText(ticket.getDescription());
                holder.tvStatus.setText(ticket.getStatus());

            }catch (Exception e){
                e.printStackTrace();
            }
        }

    public void clear() {
        if(mList !=null ){
            mList.clear();
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

}
