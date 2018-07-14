package com.ncu.neo.GoBuyer;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Neo on 2017/1/3.
 */

public class BuyerPost extends android.support.v4.app.Fragment
{
    private View view;
    private Context c;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager manager;
    private DatabaseReference mFirebaseRef;
    private DatabaseReference followFirebaseRef;
    private FirebaseRecyclerAdapter<Post, PostViewHolder> firebaseRecyclerAdapter;
    private GlobalVariable globalVariable;
    private boolean mProcessFollow = false;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        globalVariable = (GlobalVariable)getContext().getApplicationContext();

        view = inflater.inflate(R.layout.frag1, container, false);
        mFirebaseRef = FirebaseDatabase.getInstance().getReference().child("Buyer").child(globalVariable.cur_user);
        followFirebaseRef = FirebaseDatabase.getInstance().getReference().child("FollowPost");
        c = getContext();

        //Initializes Recycler View and Layout Manager.
        mRecyclerView = (RecyclerView) view.findViewById(R.id.post_list);
        manager = new LinearLayoutManager(c);
        mRecyclerView.setHasFixedSize(true);
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Post, PostViewHolder>(
                Post.class,
                R.layout.post_row,
                PostViewHolder.class,
                mFirebaseRef) {
            @Override
            protected void populateViewHolder(final PostViewHolder postViewHolder, final Post post, int i) {
                final  String post_key = post.getKey();

                postViewHolder.setName(post.getName());
                postViewHolder.setCountry(post.getCountry());
                postViewHolder.setPrice(post.getPrice());
                postViewHolder.setImage(getActivity().getApplicationContext(), post.getImageURL());
                postViewHolder.setPostFollowBtn(post_key, globalVariable.cur_user);

                postViewHolder.setInformBtn(post_key);

                if(!post.getIsOrder())
                {
                    postViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent();
                            intent.setClass(c , GoerPostActivity.class);
                            intent.putExtra("key", post_key);
                            c.startActivity(intent);
                        }
                    });
                }
//                if(post.getIsOrder())
//                {
//                    postViewHolder.setPostStatus("交易中");
//                    postViewHolder.setInformBg(post.getIsOrder());
//                }
//                else{
//                    postViewHolder.setPostStatus("懸賞單詳情");
//                    postViewHolder.setInformBg(post.getIsOrder());
//                    postViewHolder.mView.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Intent intent = new Intent();
//                            intent.setClass(c , GoerPostActivity.class);
//                            intent.putExtra("key", post_key);
//                            startActivity(intent);
//                        }
//                    });
//                }



                postViewHolder.textView_follow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mProcessFollow = true;
                        followFirebaseRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if(mProcessFollow) {
                                    if (dataSnapshot.child(globalVariable.cur_user).hasChild(post_key)) {
                                        followFirebaseRef.child(globalVariable.cur_user).child(post_key).removeValue();
                                        new AlertDialog.Builder(getContext())
                                                .setTitle("已取消追蹤")
                                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        // continue with delete
                                                    }
                                                })
                                                .show();
                                        mProcessFollow = false;
                                    }
                                    else {
                                        DatabaseReference newFollow = followFirebaseRef.child(globalVariable.cur_user).child(post_key);
                                        newFollow.setValue(post);
                                        new AlertDialog.Builder(getContext())
                                                .setTitle("追蹤成功")
                                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        // continue with delete
                                                    }
                                                })
                                                .show();
                                        mProcessFollow = false;
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });




                    }
                });
                Log.d("TAG", "populateViewHolder called");
            }
        };

        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(c, DividerItemDecoration.VERTICAL_LIST));


        return view;
    }


    public class DividerItemDecoration extends RecyclerView.ItemDecoration
    {

        private  final int[] ATTRS = new int[] { android.R.attr.listDivider };

        public static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;

        public static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;



        private Drawable mDivider;

        private int mOrientation;

        public DividerItemDecoration(Context context, int orientation)
        {
            final TypedArray a = context.obtainStyledAttributes(ATTRS);
            mDivider = a.getDrawable(0);
            a.recycle();
            setOrientation(orientation);
        }

        public void setOrientation(int orientation)
        {
            if (orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST)
            {
                throw new IllegalArgumentException("invalid orientation");
            }
            mOrientation = orientation;
        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent)
        {
            if (mOrientation == VERTICAL_LIST) {
                drawVertical(c, parent);
            } else {
                drawHorizontal(c, parent);
            }
        }

        public void drawVertical(Canvas c, RecyclerView parent)
        {
            final int left = parent.getPaddingLeft();
            final int right = parent.getWidth() - parent.getPaddingRight();

            final int childCount = parent.getChildCount();

            for (int i = 0; i < childCount; i++)
            {
                final View child = parent.getChildAt(i);
                RecyclerView v = new RecyclerView(
                        parent.getContext());
                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                        .getLayoutParams();
                final int top = child.getBottom() + params.bottomMargin;
                final int bottom = top + mDivider.getIntrinsicHeight();
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }

        public void drawHorizontal(Canvas c, RecyclerView parent)
        {
            final int top = parent.getPaddingTop();
            final int bottom = parent.getHeight() - parent.getPaddingBottom();

            final int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++)
            {
                final View child = parent.getChildAt(i);
                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                        .getLayoutParams();
                final int left = child.getRight() + params.rightMargin;
                final int right = left + mDivider.getIntrinsicHeight();
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }

        @Override
        public void getItemOffsets(Rect outRect, int itemPosition,
                                   RecyclerView parent)
        {
            if (mOrientation == VERTICAL_LIST)
            {
                outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
            } else
            {
                outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
            }
        }
    }

}
