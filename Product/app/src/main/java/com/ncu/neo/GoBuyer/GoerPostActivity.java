package com.ncu.neo.GoBuyer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;

import java.util.HashMap;
import java.util.Map;

public class GoerPostActivity extends AppCompatActivity {

    private Firebase mFirebaseRef;
    private Firebase updateFirebaseRef;
//    private Firebase relationFirebaseRef;
    private Firebase goerDataBase; //for buyer
    private String key;
    private String relationKey;
    private GlobalVariable globalVariable;
    private Post post;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goer_post);

        Intent intent = this.getIntent();
        //取得傳遞過來的資料
        key = intent.getStringExtra("key");
        globalVariable = ((GlobalVariable)getApplicationContext());


        ImageView imageView_back = (ImageView) findViewById(R.id.imageView_backward);  //返回鍵
        imageView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoerPostActivity.this.finish();
            }
        });

        mFirebaseRef = new Firebase("https://gobuyer-86845.firebaseio.com/Post");  //連上資料庫
        updateFirebaseRef = new Firebase("https://gobuyer-86845.firebaseio.com/Post/" + key);
//        relationFirebaseRef = new Firebase("https://gobuyer-86845.firebaseio.com/PostRelationship");
        goerDataBase = new Firebase("https://gobuyer-86845.firebaseio.com/Goer");



        Button button_order = (Button) findViewById(R.id.button_order);
        button_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!post.getIsOrder())
                {

                    Boolean isOrder = true;  // 更新已接單
                    Map<String, Object> isOrderMap = new HashMap<String, Object>();
                    isOrderMap.put("isOrder", isOrder);
                    updateFirebaseRef.updateChildren(isOrderMap);

//                    relationFirebaseRef = relationFirebaseRef.child(relationKey);  //更新relationship buyer欄位
//                    String goer = globalVariable.cur_user;
//                    Map<String, Object> buyerMap = new HashMap<String, Object>();
//                    buyerMap.put("goer", goer);
//                    relationFirebaseRef.updateChildren(buyerMap);

                    insertGoerPost(post);

                    startActivity(new Intent().setClass(GoerPostActivity.this, GoerViewActivity.class));
                }
                else
                {

                }


                //startActivity(new Intent().setClass(GoerPostActivity.this, GoerViewActivity.class));
            }
        });




        Query postQueryRef = mFirebaseRef.orderByChild("key").equalTo(key);  //搜尋post資料
        postQueryRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                updateView(dataSnapshot);
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


    }

    private void insertGoerPost(Post post)
    {
        Firebase newPost = goerDataBase.child(globalVariable.cur_user).child(post.getKey());
        //設定post key值
        newPost.setValue(post);
    }


    private void updateView(DataSnapshot dataSnapshot)
    {
        post = dataSnapshot.getValue(Post.class);
        TextView name = (TextView) findViewById(R.id.textView_name);
        TextView imform = (TextView) findViewById(R.id.textView_imform);
        TextView weight = (TextView) findViewById(R.id.textView_weight);
        TextView price = (TextView) findViewById(R.id.textView_price);
        ImageView post_img = (ImageView) findViewById(R.id.imageView_postImg);
        Button button_post = (Button) findViewById(R.id.button_order);

        TextView status = (TextView) findViewById(R.id.textView_status);
        TextView postNum = (TextView) findViewById(R.id.textView_postnum);
        TextView date = (TextView) findViewById(R.id.textView_date);
        TextView time = (TextView) findViewById(R.id.textView_time);
        TextView store = (TextView) findViewById(R.id.textView_store);
        TextView number = (TextView) findViewById(R.id.textView_number);
        TextView receipt = (TextView) findViewById(R.id.textView_receipt);
        TextView description = (TextView) findViewById(R.id.textView_description);


        name.setText(post.getName());
        imform.setText("資料來源: " + post.getCountry() + "   " + post.getTime());
        weight.setText("參考重量： " + post.getWeight());
        price.setText("NT$ " + post.getPrice());
        if(post.getIsOrder())  button_post.setText("交易中");
        Glide.with(GoerPostActivity.this)
                .load(post.getImageURL())
                .into(post_img);

        if(post.getIsOrder()) status.setText("懸賞單狀態: 交易中");
        else status.setText("懸賞單狀態: 等到Go者接單");

        date.setText("刊登日期： "+ post.getDate());
        time.setText("交貨期限： GO者接單後"+ post.getTime());
        store.setText("指定商店： " + post.getStrName());
        number.setText("購買數量：" + post.getNumber());
        receipt.setText("購買單據： "+ post.getReceipt());
        description.setText("商品說明： " + post.getDescription());



    }
}
