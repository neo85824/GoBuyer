package com.ncu.neo.GoBuyer;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class BuyerPostActivity extends AppCompatActivity {
    // Creating a Firebase database Reference
    // TODO Need to change “your_reference_path” to your own path
    private Spinner spinner_country;
    private Spinner spinner_catalog;
    private Spinner spinner_number;
    private Spinner spinner_weight;
    private Spinner spinner_time;
    private EditText editText_name;
    private EditText editText_price;
    private EditText editText_weight;
    private EditText editText_time;
    private Button button_post;


    private GlobalVariable globalVariable;


    private static final int GALLERY_REQUEST = 1;
    private Uri mImageUri = null;
    private ImageView mSelectImage;
    private boolean hasPost = false;  //從商品頁面進入

    private StorageReference mStorage;
    private ProgressDialog mProgress;
    private DatabaseReference mDataBase;  //for post

    private Post post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_post);

        setSpinner(); //設定spinner資料



        globalVariable = ((GlobalVariable)getApplicationContext());  //取得global 變數

        ImageView imageView_back = (ImageView) findViewById(R.id.imageView_backward);  //返回鍵設定
        imageView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BuyerPostActivity.this.finish();
            }
        });

        ImageView imageView_getRequest = (ImageView)findViewById(R.id.imageView_getrequest);
        imageView_getRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent().setClass(BuyerPostActivity.this, GoerViewActivity.class));
            }
        });



        ImageView imageView_following = (ImageView) findViewById(R.id.imageView_shop_cart);
        imageView_following.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent().setClass(BuyerPostActivity.this, FollowingActivity.class));
            }
        });




        mSelectImage = (ImageView) findViewById(R.id.imageView_upload);
        mStorage = FirebaseStorage.getInstance().getReference();   //firebase storage
        mProgress = new ProgressDialog(this);  //處理dialog
        mDataBase = FirebaseDatabase.getInstance().getReference().child("Post");  //firebase post reference

        mSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {   //上傳照片
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALLERY_REQUEST);
            }
        });


        button_post = (Button) findViewById(R.id.button_post);
        button_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText_name = (EditText) findViewById(R.id.editText_name);
                editText_price = (EditText) findViewById(R.id.editText_price);
                editText_weight = (EditText) findViewById(R.id.editText_weight);
                editText_time = (EditText) findViewById(R.id.editText_time);

                if(!TextUtils.isEmpty(editText_name.getText()) && !TextUtils.isEmpty(editText_price.getText()) && mImageUri != null) {


                    final String name = editText_name.getText().toString();
                    final String price = editText_price.getText().toString();
                    final String number = spinner_number.getSelectedItem().toString();
                    final String country = spinner_country.getSelectedItem().toString();
                    final String catalog = spinner_catalog.getSelectedItem().toString();
                    final String weight = editText_weight.getText().toString() + spinner_weight.getSelectedItem().toString();
                    final String time = editText_time.getText().toString() + spinner_time.getSelectedItem().toString();


                        StorageReference filepath = mStorage.child("Post_Images").child(String.valueOf(mImageUri.getPathSegments())); //Firebase Storage Reference
                        filepath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {   //照片上傳至資料庫成功
                                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                                post = new Post(name, price, number, country, catalog, weight, time, downloadUrl.toString(), globalVariable.cur_user);
                                Intent intent = new Intent();
                                intent.putExtra("Post",post);
                                intent.setClass(BuyerPostActivity.this, BuyerPost2Activity.class);
                                startActivity(intent);

                            }
                        });

                 }
                else
                {
                    new AlertDialog.Builder(BuyerPostActivity.this)
                        .setTitle("送出錯誤")
                        .setMessage("請完成表格內容與上傳圖片")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                            }
                        })

                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

                }

            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {  //上傳照片
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK)
        {
            mImageUri = data.getData();

            mSelectImage.setImageURI(mImageUri);
        }
    }


    //上傳資料至資料庫
    void saveData(final String name, final String price, final String number, final String country, final String catalog, final String weight, final String time)
    {
        DatabaseReference newPost = mDataBase.push();

        mProgress.setMessage("Posting...");
        mProgress.show();


    }

    void setSpinner()
    {
        spinner_number = (Spinner)findViewById(R.id.spinner_number);
        spinner_country = (Spinner)findViewById(R.id.spinner_country);
        spinner_catalog = (Spinner)findViewById(R.id.spinner_catalog);
        spinner_weight = (Spinner) findViewById(R.id.spinner_weight);
        spinner_time = (Spinner)findViewById(R.id.spinner_time);


        ArrayAdapter<CharSequence> numberList = ArrayAdapter.createFromResource(BuyerPostActivity.this,
                R.array.number,
                android.R.layout.simple_spinner_dropdown_item);
        spinner_number.setAdapter(numberList);

        ArrayAdapter<CharSequence> countryList = ArrayAdapter.createFromResource(BuyerPostActivity.this,
                R.array.country,
                android.R.layout.simple_spinner_dropdown_item);
        spinner_country.setAdapter(countryList);

        ArrayAdapter<CharSequence> catalog1List = ArrayAdapter.createFromResource(BuyerPostActivity.this,
            R.array.catalog,
            android.R.layout.simple_spinner_dropdown_item);
        spinner_catalog.setAdapter(catalog1List);


        ArrayAdapter<CharSequence> weightList = ArrayAdapter.createFromResource(BuyerPostActivity.this,
                R.array.weight,
                android.R.layout.simple_spinner_dropdown_item);
        spinner_weight.setAdapter(weightList);


        ArrayAdapter<CharSequence> timelist = ArrayAdapter.createFromResource(BuyerPostActivity.this,
                R.array.time,
                android.R.layout.simple_spinner_dropdown_item);
        spinner_time.setAdapter(timelist);
    }

}
