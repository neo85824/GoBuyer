package com.ncu.neo.GoBuyer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;

public class Login_Activity extends AppCompatActivity {


    private Firebase myFirebaseRef;
    private ProgressDialog mProgress;

//    FirebaseAuth mAuth;
//    private FirebaseAuth.AuthStateListener mAuthListener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);

        // Setup Firebase library
        Firebase.setAndroidContext(this);

        myFirebaseRef = new Firebase("https://gobuyer-86845.firebaseio.com/User");  //連上資料庫
        mProgress = new ProgressDialog(this);  //處理dialog



        Button button_login = (Button) findViewById(R.id.button_login);  //登入按鍵
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {   //認證帳密資料
                EditText account = (EditText) findViewById(R.id.editText_username);
                EditText password = (EditText) findViewById(R.id.editText_password);
                String string_account = account.getText().toString();
                String string_password = password.getText().toString();


                Verify(string_account,string_password);


            }
        });

        TextView textView_Create = (TextView) findViewById(R.id.textView_create);
        textView_Create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Login_Activity.this, CreateAccountActivity.class);
                startActivity(intent);
            }
        });

    }



    void Verify(final String account, final String password)
    {


        final TextView log = (TextView) findViewById(R.id.textView_log);
        final boolean[] findAccount =  new boolean[1];


        Query queryRef = myFirebaseRef.orderByChild("account").equalTo(account);  //找到對應account

        queryRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                User user = dataSnapshot.getValue(User.class);
                if(user.getPassword().equals(password))  {   //成功登入
                    //log.setText("Login success");
                    mProgress.setMessage("登入中");
                    mProgress.show();

                    GlobalVariable globalVariable = ((GlobalVariable)getApplicationContext());
                    globalVariable.cur_user = account;

                    mProgress.dismiss();

                    Intent intent = new Intent();
                    intent.setClass(Login_Activity.this, ChooseActivity.class);
                    startActivity(intent);

                }
                else
                {
                    log.setText("Login failed : Wrong Account or Password"+ "  Correct Password:" + user.getPassword() );
                }

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
                log.setText("Login failed : Wrong Account or Password"); //預設為錯誤

            }
        });


    }

}
