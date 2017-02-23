package com.botree.botree911_client.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.botree.botree911_client.R;

/**
 * Created by bhavin on 2/2/17.
 */

public class NotificationCellHolder extends RecyclerView.ViewHolder {

    TextView tvNotification;
    TextView tvUserName;
    TextView tvDateTime;

    public NotificationCellHolder(View itemView) {
        super(itemView);

        tvNotification = (TextView) itemView.findViewById(R.id.tv_Notification);
        tvUserName = (TextView) itemView.findViewById(R.id.tv_UserName);
        tvDateTime = (TextView) itemView.findViewById(R.id.tv_DateTime);
    }
}
