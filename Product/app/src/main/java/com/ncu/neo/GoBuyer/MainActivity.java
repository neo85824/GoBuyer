package com.ncu.neo.GoBuyer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;


public class MainActivity extends AppCompatActivity {

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        OfficialCountManTimeThread official_count_mantime_thread = new  OfficialCountManTimeThread();
        official_count_mantime_thread.start();




        RelativeLayout rLayout = (RelativeLayout) findViewById(R.id.activity_main);
        rLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Login_Activity.class);
                startActivity(intent);
            }
        });
    }
}

