package com.botree.botree911_client.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.botree.botree911_client.R;

/**
 * Created by bhavin on 2/2/17.
 */

public class HistoryCellHolder extends RecyclerView.ViewHolder {

    TextView tvHistory;
    TextView tvAlertedBy;
    TextView tvDateTime;

    public HistoryCellHolder(View itemView) {
        super(itemView);

        tvHistory = (TextView) itemView.findViewById(R.id.tv_History);
        tvAlertedBy = (TextView) itemView.findViewById(R.id.tv_AlertedBy);
        tvDateTime = (TextView) itemView.findViewById(R.id.tv_DateTime);
    }
}
