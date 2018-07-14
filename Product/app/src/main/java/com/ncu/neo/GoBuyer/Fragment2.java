package com.ncu.neo.GoBuyer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Neo on 2017/1/3.
 */
public class Fragment2 extends Fragment {
    private RecyclerView mPostList;

    private DatabaseReference mDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Post");

        View view = inflater.inflate(R.layout.frag2,container,false);
        mPostList = (RecyclerView) view.findViewById(R.id.post_list);
        mPostList.setHasFixedSize(true);
        mPostList.setLayoutManager( new  LinearLayoutManager(getActivity()));

        return  view;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Post,PostViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Post, PostViewHolder>(
                Post.class,
                R.layout.post_row,
                PostViewHolder.class,
                mDatabase
        ) {


            @Override
            protected void populateViewHolder(PostViewHolder viewHolder, Post model, int position) {
                viewHolder.setName(model.getName());
                viewHolder.setPrice(model.getPrice());
            }
        };


        mPostList.setAdapter(firebaseRecyclerAdapter);
    }


    public static class PostViewHolder extends  RecyclerView.ViewHolder {
        View mView;

        public PostViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

        }

        public void setName(String name)
        {
            TextView textView_name = (TextView) mView.findViewById(R.id.textView_name);
            textView_name.setText(name);
        }

        public void setPrice(String price)
        {
            TextView textView_price = (TextView) mView.findViewById(R.id.textView_price);
            textView_price.setText(price);
        }
    }




}
