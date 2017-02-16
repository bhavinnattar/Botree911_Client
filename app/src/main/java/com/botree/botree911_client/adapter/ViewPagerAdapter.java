package com.botree.botree911_client.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.botree.botree911_client.fragment.ClosedFragment;
import com.botree.botree911_client.fragment.InProgressFragment;
import com.botree.botree911_client.fragment.PendingFragment;
import com.botree.botree911_client.fragment.ResolvedFragment;
import com.botree.botree911_client.fragment.UnassignedFragment;

/**
 * Created by bhavin on 9/2/17.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

        String[] tabs = {"Pending","Inprogress","Resolved","Closed","Unassigned"};

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            Fragment fragment=null;

            if(i==0){
                fragment = new PendingFragment();
            }else if(i==1){
                fragment = new InProgressFragment();
            }else if(i==2){
                fragment = new ResolvedFragment();
            }else if(i==3){
                fragment = new ClosedFragment();
            }else if(i==4){
                fragment = new UnassignedFragment();
            }

            Bundle args = new Bundle();
            args.putString("label", tabs[i]);
            fragment.setArguments(args);

            return fragment;
        }

        @Override
        public int getCount() {
            return tabs.length;
        }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs[position] +" (2)";
    }
}

