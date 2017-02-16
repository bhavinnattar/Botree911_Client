package com.botree.botree911_client.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.botree.botree911_client.R;
import com.botree.botree911_client.activity.ProjectInfoActivity;
import com.botree.botree911_client.model.Comment;
import com.botree.botree911_client.model.Project;

import java.util.List;

/**
 * Created by bhavin on 2/2/17.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentCellHolder> {

        List<Comment> mList;
        Context mContext;

        public CommentAdapter(Context mContext, List<Comment> mList) {
            this.mContext = mContext;
            this.mList = mList;
        }

        @Override
        public CommentCellHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {
            final View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.comment_row, viewGroup, false);

            return new CommentCellHolder(view);
        }

        @Override
        public void onBindViewHolder(CommentCellHolder holder, int i) {

            try{

                Comment comment = mList.get(i);

                holder.tvComment.setText(comment.getComment());
                holder.tvCreatedBy.setText(comment.getUser_name());
                holder.tvDateTime.setText(comment.getDate_time());

                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) holder.getView().getLayoutParams();

                Log.d("getUser_name" +" "+i,""+comment.getUser_name());
                if(comment.getUser_name().equalsIgnoreCase("Bhavin Nattar")){
                    params.leftMargin = 70;
                }else{
                    params.rightMargin = 70;
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
