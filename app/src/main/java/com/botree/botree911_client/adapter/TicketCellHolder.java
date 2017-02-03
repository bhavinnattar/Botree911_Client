package com.botree.botree911_client.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.botree.botree911_client.R;

/**
 * Created by bhavin on 2/2/17.
 */

public class TicketCellHolder extends RecyclerView.ViewHolder {

    TextView tvTitle;
    TextView tvDescription;
    TextView tvStatus;

    public TicketCellHolder(View itemView) {
        super(itemView);
        tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
        tvDescription = (TextView) itemView.findViewById(R.id.tv_description);
        tvStatus = (TextView) itemView.findViewById(R.id.tv_status);
    }
}
