package com.botree.botree911_client.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.botree.botree911_client.R;
import com.botree.botree911_client.activity.ProjectInfoActivity;
import com.botree.botree911_client.activity.TicketListActivity;
import com.botree.botree911_client.model.ProjectOld;
import com.botree.botree911_client.utility.Constant;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by bhavin on 2/2/17.
 */

public class ProjectAdapter extends RecyclerView.Adapter<ProjectCellHolder> {

        List<ProjectOld> mList;
        Context mContext;

        public ProjectAdapter(Context mContext, List<ProjectOld> mList) {
            this.mContext = mContext;
            this.mList = mList;
        }

        @Override
        public ProjectCellHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {
            final View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.project_row, viewGroup, false);

            return new ProjectCellHolder(view);
        }

        @Override
        public void onBindViewHolder(ProjectCellHolder holder, final int position) {

            try{

                holder.tvDetails.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_DOWN){
                            Constant.selectedProjectOld = Constant.allProjectOlds.get(position);
                            actvityInfo();
                        }
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        return true;
                    }
                });

                holder.tvAllTicket.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_DOWN){
                            Constant.selectedProjectOld = Constant.allProjectOlds.get(position);

                            allTickets("pending", Constant.selectedProjectOld.getId());
                        }
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        return true;
                    }
                });

                ProjectOld projectOld = mList.get(position);

                holder.tvTitle.setText(projectOld.getName());
                holder.tvDescription.setText(projectOld.getSpocPerson());
                holder.tvNoOfTeam.setText(projectOld.getNoOfTeam());
                holder.tvStartDate.setText(projectOld.getStartDate());

                getAllStatus(holder.lnrTicketStatus, projectOld.getTicketStatus(), projectOld.getId());


            }catch (Exception e){
                e.printStackTrace();
            }
        }

    void getAllStatus(LinearLayout ll, String allStatus, final String projectId){
        try{

            JSONArray jsonArray = new JSONArray(allStatus);

            for(int i=0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                TextView tv = new TextView(mContext);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(65, 55);
                params.setMargins(5,0,3,0);
                tv.setLayoutParams(params);
                tv.setGravity(Gravity.CENTER);

                tv.setText(jsonObject.getString("Value"));
                tv.setPadding(3,0,0,0);
                tv.setTextColor(Color.WHITE);
                tv.setTextSize(11.5f);
                if(i==0){
                    tv.setBackground(mContext.getResources().getDrawable(R.drawable.rounded_red));
                    tv.setTag("pending");
                }else if(i==1){
                    tv.setBackground(mContext.getResources().getDrawable(R.drawable.rounded_orange));
                    tv.setTag("inprogress");
                }else if(i==2){
                    tv.setBackground(mContext.getResources().getDrawable(R.drawable.rounded_blue));
                    tv.setTag("resolved");
                }else if(i==3){
                    tv.setBackground(mContext.getResources().getDrawable(R.drawable.rounded_button));
                    tv.setTag("closed");
                }

                tv.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            String status = (String) v.getTag();
                            allTickets(status, projectId);
                        }
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        return true;
                    }
                });

                ll.addView(tv);

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

    void allTickets(String status, String projectId){

        Intent intent = new Intent(mContext, TicketListActivity.class);
        intent.putExtra("projectid", projectId);
        intent.putExtra("status", status);
        mContext.startActivity(intent);

    }// End of allTickets()

    void actvityInfo(){

        Intent intent = new Intent(mContext, ProjectInfoActivity.class);
        mContext.startActivity(intent);

    }// End of activityInfo()

}
