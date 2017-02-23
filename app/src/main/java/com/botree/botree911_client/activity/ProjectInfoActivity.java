package com.botree.botree911_client.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.botree.botree911_client.R;
import com.botree.botree911_client.utility.Constant;

import org.json.JSONArray;
import org.json.JSONObject;

public class ProjectInfoActivity extends AppCompatActivity implements View.OnClickListener{

    TextView name, desc, startDate, spocPerson, teamMember, client, projectManagers, teamLeader, developer;
    LinearLayout lnrStatus;
    Context mContext;

    ImageView ivMenu;
    TextView tvTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_info);

        setCustomActionBar();

        mContext = this;

        name = (TextView) findViewById(R.id.tv_projectName);
        desc = (TextView) findViewById(R.id.tv_projectDesc);
        startDate = (TextView) findViewById(R.id.tv_StartDate);
        spocPerson = (TextView) findViewById(R.id.tv_SpocPerson);
        teamMember = (TextView) findViewById(R.id.tv_TeamMembers);
        client = (TextView) findViewById(R.id.tv_Client);
        projectManagers = (TextView) findViewById(R.id.tv_ProjectMangers);
        teamLeader = (TextView) findViewById(R.id.tv_TeamLeaders);
        developer = (TextView) findViewById(R.id.tv_Developers);

        lnrStatus = (LinearLayout) findViewById(R.id.lnr_TicketStatus);

        name.setText(Constant.selectedProjectOld.getName());
        desc.setText(Constant.selectedProjectOld.getDescription());
        startDate.setText(Constant.selectedProjectOld.getStartDate());
        spocPerson.setText(Constant.selectedProjectOld.getSpocPerson());
        teamMember.setText(Constant.selectedProjectOld.getNoOfTeam());

        getClient(client, Constant.selectedProjectOld.getClient());
        getClient(projectManagers, Constant.selectedProjectOld.getProject_manager());
        getClient(teamLeader, Constant.selectedProjectOld.getTeam_leader());
        getClient(developer, Constant.selectedProjectOld.getTeam_member());
        getAllStatus(lnrStatus, Constant.selectedProjectOld.getTicketStatus());
    }

    void setCustomActionBar(){

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        ActionBar.LayoutParams p = new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        p.gravity = Gravity.CENTER;

        View actionbar = getLayoutInflater().inflate(R.layout.custom_actionbar, null);
        ivMenu = (ImageView) actionbar.findViewById(R.id.iv_menu);
        tvTitle = (TextView) actionbar.findViewById(R.id.actionbar_title);

        ivMenu.setOnClickListener(this);
        tvTitle.setText(getString(R.string.project_info));

        getSupportActionBar().setCustomView(actionbar);
    } // End of setCustomActionBar()

    void getClient(TextView editText, String clients){
        try{

            JSONArray jsonArray = new JSONArray(clients);
            String client = "";
            for(int i=0; i< jsonArray.length();i++){
                client += jsonArray.getString(i) +", ";
            }
            editText.setText(client);
        }catch (Exception e){

        }
    }

    void getAllStatus(LinearLayout ll, String allStatus){
        try{

            JSONArray jsonArray = new JSONArray(allStatus);

            for(int i=0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                TextView tv = new TextView(mContext);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(65, 55);
                params.setMargins(0,0,5,0);
                tv.setLayoutParams(params);
                tv.setGravity(Gravity.CENTER);

                tv.setText(jsonObject.getString("Value"));
                tv.setPadding(0,0,3,0);
                tv.setTextColor(Color.WHITE);
                tv.setTextSize(11.5f);
                if(i==0){
                    tv.setBackground(mContext.getResources().getDrawable(R.drawable.rounded_red));
                }else if(i==1){
                    tv.setBackground(mContext.getResources().getDrawable(R.drawable.rounded_orange));
                }else if(i==2){
                    tv.setBackground(mContext.getResources().getDrawable(R.drawable.rounded_blue));
                }else if(i==3){
                    tv.setBackground(mContext.getResources().getDrawable(R.drawable.rounded_button));
                }

                ll.addView(tv);

            }

        }catch (Exception e){

        }
    }

    @Override
    public void onClick(View v) {

    }
}
