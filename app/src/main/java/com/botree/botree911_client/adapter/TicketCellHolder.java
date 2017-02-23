package com.botree.botree911_client.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.botree.botree911_client.R;

/**
 * Created by bhavin on 2/2/17.
 */

public class TicketCellHolder extends RecyclerView.ViewHolder {

    TextView tvTitle;
    TextView tvDescription;
    TextView tvStatus;
    TextView tvAssignee;
    TextView tvHistory, tvComment;
    LinearLayout lnrHistory, lnrComment;

    public TicketCellHolder(View itemView) {
        super(itemView);

        tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
        tvDescription = (TextView) itemView.findViewById(R.id.tv_description);
        tvStatus = (TextView) itemView.findViewById(R.id.tv_status);
        tvAssignee = (TextView) itemView.findViewById(R.id.tv_Assignee);
        tvHistory = (TextView) itemView.findViewById(R.id.tv_History);
        tvComment = (TextView) itemView.findViewById(R.id.tv_Comment);
        lnrHistory = (LinearLayout) itemView.findViewById(R.id.lnr_history);
        lnrComment = (LinearLayout) itemView.findViewById(R.id.lnr_Comment);
    }
}
