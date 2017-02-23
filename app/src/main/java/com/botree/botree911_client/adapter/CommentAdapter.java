package com.botree.botree911_client.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.botree.botree911_client.R;
import com.botree.botree911_client.model.Comment;
import com.botree.botree911_client.utility.PreferenceUtility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by bhavin on 2/2/17.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentCellHolder> {

        List<Comment> mList;
        Context mContext;
        SimpleDateFormat serverFormat, displayFormat;
        public CommentAdapter(Context mContext, List<Comment> mList) {
            this.mContext = mContext;
            this.mList = mList;
            serverFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS'Z'");
            displayFormat = new SimpleDateFormat("MM-dd-yyyy");
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

                try {
                    holder.tvDateTime.setText(displayFormat.format(serverFormat.parse(comment.getDate_time())));
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) holder.getView().getLayoutParams();

                if(comment.getUser_id().equalsIgnoreCase(PreferenceUtility.getUserId(mContext
                ))){
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
