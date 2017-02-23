package com.botree.botree911_client.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.botree.botree911_client.R;
import com.botree.botree911_client.activity.TicketInfoTabActivity;
import com.botree.botree911_client.model.Ticket;
import com.botree.botree911_client.utility.Constant;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by bhavin on 2/2/17.
 */

public class TicketAdapter extends RecyclerView.Adapter<TicketCellHolder> {

        List<Ticket> mList;
        Context mContext;
        SimpleDateFormat serverFormat, displayFormat;
        public TicketAdapter(Context mContext, List<Ticket> mList) {
            this.mContext = mContext;
            this.mList = mList;
            serverFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS'Z'");
            displayFormat = new SimpleDateFormat("MM-dd-yyyy");
        }

        @Override
        public TicketCellHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {
            final View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ticket_row, viewGroup, false);

            return new TicketCellHolder(view);
        }

        @Override
        public void onBindViewHolder(TicketCellHolder holder, final int i) {

            try{

                holder.lnrHistory.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_DOWN){
                            Constant.selectedTicket = mList.get(i);

                            Intent intent = new Intent(mContext, TicketInfoTabActivity.class);
                            intent.putExtra("type","history");
                            mContext.startActivity(intent);

                        }
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        return true;
                    }
                });

                holder.lnrComment.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_DOWN){
                            Constant.selectedTicket = mList.get(i);

                            Intent intent = new Intent(mContext, TicketInfoTabActivity.class);
                            intent.putExtra("type","comment");
                            mContext.startActivity(intent);

                        }
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        return true;
                    }
                });

                holder.tvAssignee.setSelected(true);

                Ticket ticket = mList.get(i);

                holder.tvTitle.setText(ticket.getName());
                holder.tvDescription.setText(ticket.getDescription());
                holder.tvAssignee.setText(ticket.getAssingee());
                holder.tvHistory.setText(ticket.getHistory_count());
                holder.tvComment.setText(ticket.getComment_count());

                try {
                    holder.tvStatus.setText(displayFormat.format(serverFormat.parse(ticket.getCreated_at())));
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

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
