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

public class ProjectCellHolder extends RecyclerView.ViewHolder {

    TextView tvTitle;
    TextView tvDescription;
    TextView tvNoOfTeam;
    TextView tvAllTicket;
    TextView tvDetails;
    TextView tvStartDate;
    LinearLayout lnrTicketStatus;

    public ProjectCellHolder(View itemView) {
        super(itemView);
        tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
        tvDescription = (TextView) itemView.findViewById(R.id.tv_description);
        tvNoOfTeam = (TextView) itemView.findViewById(R.id.teamTv_member);
        tvAllTicket = (TextView) itemView.findViewById(R.id.tv_AllTickets);
        tvDetails = (TextView) itemView.findViewById(R.id.tv_Details);
        tvStartDate = (TextView) itemView.findViewById(R.id.tv_StartDate);
        lnrTicketStatus = (LinearLayout) itemView.findViewById(R.id.lnr_TicketStatus);
    }
}
