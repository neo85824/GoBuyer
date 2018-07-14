package com.ncu.neo.GoBuyer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.firebase.client.Firebase;

public class CreateAccountActivity extends AppCompatActivity {


    // Creating a Firebase database Reference
    // TODO Need to change “your_reference_path” to your own path
    private Firebase myFirebaseRef;
    private ImageView imageView_back;
    private Button button_signup;
    private EditText editText_account;
    private EditText editText_email;
    private EditText editText_password;
    private EditText editText_conPassword;
//    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        // Setup Firebase library
        Firebase.setAndroidContext(this);
        myFirebaseRef = new Firebase("https://gobuyer-86845.firebaseio.com/");
//        mAuth = FirebaseAuth.getInstance();

        imageView_back = (ImageView) findViewById(R.id.imageView_backward);  //返回鍵
        imageView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateAccountActivity.this.finish();
            }
        });


        button_signup = (Button) findViewById(R.id.button_signup);
        button_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText_account = (EditText) findViewById(R.id.editText_account);
                editText_email = (EditText) findViewById(R.id.editText_email);
                editText_password = (EditText) findViewById(R.id.editText_password);
                editText_conPassword = (EditText) findViewById(R.id.editText_conPassword);
                String account = editText_account.getText().toString();
                String password = editText_password.getText().toString();
                String email = editText_email.getText().toString();

                SaveData(account,password,email);

                Intent intent = new Intent();
                intent.setClass(CreateAccountActivity.this, Login_Activity.class);
                CreateAccountActivity.this.finish();
            }


        });
    }

    private void SaveData(String account, String password, String email)
    {
//        String user_id = mAuth.getCurrentUser().getUid();

        Firebase userRef = myFirebaseRef.child("User");
        User user = new User(account, password, email);
        userRef.push().setValue(user);
    }
}
