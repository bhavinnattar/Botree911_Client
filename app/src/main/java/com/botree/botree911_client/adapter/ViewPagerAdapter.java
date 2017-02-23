package com.botree.botree911_client.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.botree.botree911_client.fragment.TicketFragment;
import com.botree.botree911_client.utility.Constant;

/**
 * Created by bhavin on 9/2/17.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment=null;

            fragment = new TicketFragment(Constant.allTickets.get(position));

            return fragment;
        }

        @Override
        public int getCount() {
            return Constant.allTickets.size();
        }

    @Override
    public CharSequence getPageTitle(int position) {
        if(position == (Constant.allTickets.size() - 1)){
            return "Unassigned "+ " ("+ Constant.allTickets.get(position).size() +")";
        }else{
            return Constant.allStatus.get(position).getStatusName()+ " ("+ Constant.allTickets.get(position).size() +")";
        }

    }
}

