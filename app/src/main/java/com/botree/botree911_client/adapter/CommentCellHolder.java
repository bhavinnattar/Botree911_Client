package com.botree.botree911_client.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.botree.botree911_client.R;

/**
 * Created by bhavin on 2/2/17.
 */

public class CommentCellHolder extends RecyclerView.ViewHolder {

    TextView tvComment;
    TextView tvCreatedBy;
    TextView tvDateTime;
    View view;

    public CommentCellHolder(View itemView) {
        super(itemView);
        view = itemView;
        tvComment = (TextView) itemView.findViewById(R.id.tv_Comment);
        tvCreatedBy = (TextView) itemView.findViewById(R.id.tv_CommentedBy);
        tvDateTime = (TextView) itemView.findViewById(R.id.tv_DateTime);
    }

    View getView(){
        return this.view;
    }
}
