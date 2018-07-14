package com.ncu.neo.GoBuyer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Neo on 2017/1/3.
 */

public  class PostViewHolder extends RecyclerView.ViewHolder {
    public final TextView text_name;
    public final TextView text_country;
    public final TextView text_price;
    public final TextView textView_follow;
    public TextView textView_inform;
    private DatabaseReference postDataBase;
    private DatabaseReference productDataBase;
    private DatabaseReference mDataBase;

    View mView;






    public PostViewHolder(final View itemView) {
        super(itemView);
        text_name = (TextView) itemView.findViewById(R.id.textView_name);
        text_country = (TextView) itemView.findViewById(R.id.textView_country);
        text_price = (TextView) itemView.findViewById(R.id.textView_price);
        textView_follow = (TextView) itemView.findViewById(R.id.textView_follow);
        textView_inform = (TextView)  itemView.findViewById(R.id.textView_imform);

        postDataBase = FirebaseDatabase.getInstance().getReference().child("FollowPost");
        productDataBase = FirebaseDatabase.getInstance().getReference().child("FollowProduct");
        mDataBase = FirebaseDatabase.getInstance().getReference().child("Post");


        mView = itemView;


    }


    public void setName(String name) {
        text_name.setText(name);
    }

    public void setPostStatus(String status)
    {
        textView_inform.setText(status);
    }

    public void setInformBg(Boolean isOrder)
    {
        if(isOrder) textView_inform.setBackgroundResource(R.drawable.button_inform_gray);
        else  textView_inform.setBackgroundResource(R.drawable.button_inform);
    }

    public void setInformBtn(final String post_key)
    {
        mDataBase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(post_key))
                {
                    Post post = dataSnapshot.child(post_key).getValue(Post.class);

                        if(post.getIsOrder())
                        {
                            setPostStatus("交易中");
                            setInformBg(post.getIsOrder());
                        }
                        else{
                            setPostStatus("懸賞單詳情");
                            setInformBg(post.getIsOrder());
                        }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public void setPostFollowBtn(final String posy_key, final String user)
    {
        postDataBase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!dataSnapshot.child(user).hasChild(posy_key))
                {
                    textView_follow.setText("我要追蹤");
                }
                else
                    textView_follow.setText("取消追蹤");

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void setProductFollowBtn(final String posy_key, final String user)
    {
        productDataBase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!dataSnapshot.child(user).hasChild(posy_key))
                {
                    textView_follow.setText("我要追蹤");
                }
                else
                    textView_follow.setText("取消追蹤");

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public void setCountry(String country) {
        text_country.setText(country);

    }

    public void setPrice(String price) { text_price.setText(price+"元"); }

    public void setImage(Context ctx, String imageURL)
    {
        ImageView post_image = (ImageView) itemView.findViewById(R.id.imageView_productImage);
        Glide.with(ctx)
                .load(imageURL)
                .into(post_image);
//        Picasso.with(ctx).load(imageURL).into(post_image);

    }
}