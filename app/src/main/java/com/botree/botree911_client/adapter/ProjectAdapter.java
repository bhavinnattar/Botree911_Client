package com.botree.botree911_client.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.botree.botree911_client.R;
import com.botree.botree911_client.model.Project;

import java.util.List;

/**
 * Created by bhavin on 2/2/17.
 */

public class ProjectAdapter extends RecyclerView.Adapter<ProjectCellHolder> {

        List<Project> mList;
        Context mContext;

        public ProjectAdapter(Context mContext, List<Project> mList) {
            this.mContext = mContext;
            this.mList = mList;
        }

        @Override
        public ProjectCellHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {
            final View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.project_row, viewGroup, false);

            return new ProjectCellHolder(view);
        }

        @Override
        public void onBindViewHolder(ProjectCellHolder holder, int i) {

            try{

                Project project = mList.get(i);

                holder.tvTitle.setText(project.getName());
                holder.tvDescription.setText(project.getDescription());
                holder.tvNoOfTeam.setText(project.getNoOfTeam());

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
