package com.ncu.neo.GoBuyer;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class BuyerViewActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_view);


        ImageView plus = (ImageView) findViewById(R.id.imageView_plus);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(BuyerViewActivity.this, BuyerPostActivity.class);
                intent.putExtra("hasValue",false);
                startActivity(intent);
            }
        });

        ImageView imageView_getRequest = (ImageView)findViewById(R.id.imageView_getrequest);
        imageView_getRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent().setClass(BuyerViewActivity.this, GoerViewActivity.class));
            }
        });



        ImageView imageView_following = (ImageView) findViewById(R.id.imageView_shop_cart);
        imageView_following.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent().setClass(BuyerViewActivity.this, FollowingActivity.class));
            }
        });

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(new BuyerViewAdapter(getSupportFragmentManager(), getApplicationContext()));

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);




        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
        });

    }

//    private void setButton() {
//        ImageView imageView_getRequest = (ImageView)findViewById(R.id.imageView_getrequest);
//        imageView_getRequest.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent().setClass(BuyerViewActivity.this, GoerViewActivity.class));
//            }
//        });
//    }
//
//    private void setImageView() {
//        ImageView plus = (ImageView) findViewById(R.id.imageView_plus);
//        plus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setClass(BuyerViewActivity.this, BuyerPostActivity.class);
//                startActivity(intent);
//            }
//        });
//    }
//
//    private void setViewPager() {
//        viewPager = (ViewPager) findViewById(R.id.viewPager);
//        viewPager.setAdapter(new GoerViewAdapter(getSupportFragmentManager(), getApplicationContext()));
//
//        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
//        tabLayout.setupWithViewPager(viewPager);
//
//
//
//
//        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                viewPager.setCurrentItem(tab.getPosition());
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//                viewPager.setCurrentItem(tab.getPosition());
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//                viewPager.setCurrentItem(tab.getPosition());
//            }
//        });
//    }


}
