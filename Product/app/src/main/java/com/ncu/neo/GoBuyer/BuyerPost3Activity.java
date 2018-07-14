package com.ncu.neo.GoBuyer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BuyerPost3Activity extends AppCompatActivity {

    private DatabaseReference mDataBase;  //for post
    private DatabaseReference buyerDataBase;
    private ProgressDialog mProgress;
    private Button button_post;
    private GlobalVariable globalVariable;

    private Post post;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_post3);
        mProgress = new ProgressDialog(this);  //處理dialog
        mDataBase = FirebaseDatabase.getInstance().getReference().child("Post");  //firebase post reference
        buyerDataBase = FirebaseDatabase.getInstance().getReference().child("Buyer");  //firebase

        globalVariable = ((GlobalVariable)getApplicationContext());


        post = (Post)getIntent().getSerializableExtra("Post");

        ImageView imageView_back = (ImageView) findViewById(R.id.imageView_backward);  //返回鍵
        imageView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BuyerPost3Activity.this.finish();
            }
        });

        ImageView imageView_getRequest = (ImageView)findViewById(R.id.imageView_getrequest);
        imageView_getRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent().setClass(BuyerPost3Activity.this, GoerViewActivity.class));
            }
        });



        ImageView imageView_following = (ImageView) findViewById(R.id.imageView_shop_cart);
        imageView_following.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent().setClass(BuyerPost3Activity.this, FollowingActivity.class));
            }
        });


        button_post = (Button) findViewById(R.id.button_post);
        button_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData(post);
                mProgress.dismiss();

                startActivity(new Intent().setClass(BuyerPost3Activity.this, BuyerViewActivity.class));
            }
        });


    }


    private void insertBuyerPost(Post post)
    {
        DatabaseReference newPost = buyerDataBase.child(globalVariable.cur_user).child(post.getKey());
        //設定post key值
        newPost.setValue(post);
    }

    //上傳資料至資料庫
    void saveData(Post post)
    {
        DatabaseReference newPost = mDataBase.push();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis()) ; // 獲取當前時間
        String date = formatter.format(curDate);

        post.setKey(newPost.getKey());
        post.setDate(date);

        insertBuyerPost(post);
        newPost.setValue(post);
        mProgress.setMessage("Posting...");
        mProgress.show();


    }
}
