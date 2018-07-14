package com.ncu.neo.GoBuyer;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Neo on 2017/1/3.
 */
public class GoerViewAdapter extends FragmentPagerAdapter {
    private String fragments [] = {"待承接懸賞單","已承接懸賞單",};

    public GoerViewAdapter(FragmentManager supportFragmentManager, Context applicationContext) {
        super(supportFragmentManager);

    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new AllPost();
            case 1:
                return new GoerPost();
//            case 2:
//                return new Fragment1();
            default:
                return null;
        }
    }



    @Override
    public int getCount() {
        return fragments.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragments[position];
    }
}
