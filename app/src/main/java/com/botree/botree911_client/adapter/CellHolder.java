package com.botree.botree911_client.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.botree.botree911_client.R;

/**
 * Created by bhavin on 2/2/17.
 */

public class CellHolder extends RecyclerView.ViewHolder {

    TextView tvTitle;
    TextView tvDescription;
    TextView tvNoOfTeam;

    public CellHolder(View itemView) {
        super(itemView);
        tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
        tvDescription = (TextView) itemView.findViewById(R.id.tv_description);
        tvNoOfTeam = (TextView) itemView.findViewById(R.id.teamTv_member);
    }
}
