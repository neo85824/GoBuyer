package com.ncu.neo.GoBuyer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ChooseActivity extends AppCompatActivity {
    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);

        TextView buyer = (TextView) findViewById(R.id.textView_buyer);
        TextView goer = (TextView) findViewById(R.id.textView_goer);




        buyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(ChooseActivity.this, BuyerViewActivity.class);


                startActivity(intent);
            }
        });


        goer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(ChooseActivity.this,GoerViewActivity.class);

                startActivity(intent);
            }
        });
    }
}
