package com.botree.botree911_client.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.botree.botree911_client.R;
import com.botree.botree911_client.model.Comment;
import com.botree.botree911_client.model.History;

import java.util.List;

/**
 * Created by bhavin on 2/2/17.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryCellHolder> {

        List<History> mList;
        Context mContext;

        public HistoryAdapter(Context mContext, List<History> mList) {
            this.mContext = mContext;
            this.mList = mList;
        }

        @Override
        public HistoryCellHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {
            final View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.history_row, viewGroup, false);

            return new HistoryCellHolder(view);
        }

        @Override
        public void onBindViewHolder(HistoryCellHolder holder, int i) {

            try{

                History history = mList.get(i);

                holder.tvHistory.setText("Status changed from " + history.getLast_status() + " to " + history.getCurrent_status());
                holder.tvAlertedBy.setText(history.getUser_name());
                holder.tvDateTime.setText(history.getDate_time());


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
