package com.botree.botree911_client.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.botree.botree911_client.fragment.CommentsFragment;
import com.botree.botree911_client.fragment.DetailFragment;
import com.botree.botree911_client.fragment.HistoryFragment;

/**
 * Created by bhavin on 9/2/17.
 */

public class TicketDetailAdapter extends FragmentPagerAdapter {

        String[] tabs = {"Details", "History","Comment"};

        public TicketDetailAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            Fragment fragment=null;

            if(i==0){
                fragment = new DetailFragment();
            }else if(i==1){
                fragment = new HistoryFragment();
            }else if(i==2){
                fragment = new CommentsFragment();
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
        return tabs[position];
    }
}

