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

public class BuyerProductActivity extends AppCompatActivity {
    private String key;
    private Firebase mFirebaseRef;
    private GlobalVariable globalVariable;
    private Post post;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_product);


        Intent intent = this.getIntent();
        //取得傳遞過來的資料
        key = intent.getStringExtra("key");
        globalVariable = ((GlobalVariable)getApplicationContext());

        ImageView imageView_back = (ImageView) findViewById(R.id.imageView_backward);  //返回鍵
        imageView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BuyerProductActivity.this.finish();
            }
        });

        mFirebaseRef = new Firebase("https://gobuyer-86845.firebaseio.com/Recommend");  //連上資料庫


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

        Button button_post = (Button) findViewById(R.id.button_post);
        button_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("Post",post);
//                intent.putExtra("hasValue",true);
                intent.setClass(BuyerProductActivity.this, BuyerPostHasValueActivity.class);
                startActivity(intent);
            }
        });


    }

    private void updateView(DataSnapshot dataSnapshot)
    {
        post = dataSnapshot.getValue(Post.class);
        TextView name = (TextView) findViewById(R.id.textView_name);
        TextView price = (TextView) findViewById(R.id.textView_price);
        TextView country = (TextView) findViewById(R.id.textView_country);
        TextView weight = (TextView) findViewById(R.id.textView_weight);
        TextView description = (TextView) findViewById(R.id.textView_description);
        ImageView post_img = (ImageView) findViewById(R.id.imageView_postImg);


        Glide.with(BuyerProductActivity.this)
                .load(post.getImageURL())
                .into(post_img);

        name.setText(post.getName());
        price.setText("NT$ " + post.getPrice());
        country.setText("國家： " + post.getCountry());
        weight.setText("參考重量： " + post.getWeight());
        description.setText("商品說明： " + post.getDescription());


    }
}
