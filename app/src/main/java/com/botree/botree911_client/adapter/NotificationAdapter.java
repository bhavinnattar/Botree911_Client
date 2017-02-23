package com.botree.botree911_client.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.botree.botree911_client.R;
import com.botree.botree911_client.model.History;
import com.botree.botree911_client.model.LocalNotification;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by bhavin on 2/2/17.
 */

public class NotificationAdapter extends RecyclerView.Adapter<NotificationCellHolder> {

        List<LocalNotification> mList;
        Context mContext;
        SimpleDateFormat serverFormat, displayFormat;
        public NotificationAdapter(Context mContext, List<LocalNotification> mList) {
            this.mContext = mContext;
            this.mList = mList;
            serverFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS'Z'");
            displayFormat = new SimpleDateFormat("MM-dd-yyyy");
        }

        @Override
        public NotificationCellHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {
            final View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.notification_row, viewGroup, false);

            return new NotificationCellHolder(view);
        }

        @Override
        public void onBindViewHolder(NotificationCellHolder holder, int i) {

            try{

                LocalNotification noti = mList.get(i);

                holder.tvNotification.setText(noti.getDescription());
                holder.tvUserName.setText(noti.getUser_name());

                try {
                    holder.tvDateTime.setText(displayFormat.format(serverFormat.parse(noti.getDate())));
                } catch (ParseException e) {
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
