package com.ncu.neo.GoBuyer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

public class BuyerPost2Activity extends AppCompatActivity {
    private Spinner spinner_receipt;
    private Spinner spinner_getProduct;
    private EditText editText_decription;
    private EditText editText_link;
    private EditText editText_strName;
    private Post post;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_post2);


        post = (Post) getIntent().getSerializableExtra("Post");

        editText_decription = (EditText) findViewById(R.id.editText_description);
        editText_decription.setText(post.getDescription());


        ImageView imageView_back = (ImageView) findViewById(R.id.imageView_backward);  //返回鍵
        imageView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BuyerPost2Activity.this.finish();
            }
        });

        ImageView imageView_getRequest = (ImageView)findViewById(R.id.imageView_getrequest);
        imageView_getRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent().setClass(BuyerPost2Activity.this, GoerViewActivity.class));
            }
        });



        ImageView imageView_following = (ImageView) findViewById(R.id.imageView_shop_cart);
        imageView_following.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent().setClass(BuyerPost2Activity.this, FollowingActivity.class));
            }
        });

        editText_decription = (EditText) findViewById(R.id.editText_description);
        editText_link = (EditText) findViewById(R.id.editText_link);
        editText_strName = (EditText) findViewById(R.id.editText_strName);

        setSpinner();

        Button button_post = (Button) findViewById(R.id.button_post);
        button_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                post.setDescription(editText_decription.getText().toString());
                post.setLink(editText_link.getText().toString());
                post.setStrName(editText_strName.getText().toString());
                post.setReceipt(spinner_receipt.getSelectedItem().toString());
                post.setGetProduct(spinner_getProduct.getSelectedItem().toString());

                Intent intent = new Intent();
                intent.putExtra("Post",post);
                intent.setClass(BuyerPost2Activity.this, BuyerPost3Activity.class);
                startActivity(intent);

            }
        });

    }


    void setSpinner()
    {
        spinner_receipt = (Spinner)findViewById(R.id.spinner_receipt);
        spinner_getProduct = (Spinner)findViewById(R.id.spinner_getProduct);


        ArrayAdapter<CharSequence> receiptList = ArrayAdapter.createFromResource(BuyerPost2Activity.this,
                R.array.receipt,
                android.R.layout.simple_spinner_dropdown_item);
        spinner_receipt.setAdapter(receiptList);

        ArrayAdapter<CharSequence> getProductList = ArrayAdapter.createFromResource(BuyerPost2Activity.this,
                R.array.getProduct,
                android.R.layout.simple_spinner_dropdown_item);
        spinner_getProduct.setAdapter(getProductList);


    }

}
