package com.ncu.neo.GoBuyer;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Neo on 2017/1/10.
 */

public class FollowingAdapter extends FragmentPagerAdapter {
    private String fragments [] = {"商品","懸賞單",};

    public FollowingAdapter(FragmentManager supportFragmentManager, Context applicationContext) {
        super(supportFragmentManager);

    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new ProductFollow();
            case 1:
                return new PostFollow();
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
