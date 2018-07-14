package com.ncu.neo.GoBuyer;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Neo on 2017/1/4.
 */

public class BuyerViewAdapter extends FragmentPagerAdapter {
    private String fragments [] = {"本週必買","熱門代購","已刊登懸賞單"};


    public BuyerViewAdapter(FragmentManager supportFragmentManager, Context applicationContext) {
        super(supportFragmentManager);

    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new Recommend();
            case 1:
                return new Recommend();
            case 2:
                return new BuyerPost();
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
