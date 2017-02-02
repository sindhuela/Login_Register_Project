package com.sindhu.loginproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class LoginWelcomeActivity extends AppCompatActivity {

    TextView displayName,displayMailId,displayContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_welcome);

        displayName = (TextView) findViewById(R.id.name_welcome);
        displayMailId = (TextView) findViewById(R.id.email_welcome);
        displayContact = (TextView) findViewById(R.id.phoneNumber_welcome);

        Bundle bundle = getIntent().getExtras();
        String email = bundle.getString("email");

        UserInfo userInfo = new DatabaseHelper(this).getData(email);
        displayName.setText(userInfo.getName());
        displayMailId.setText(userInfo.getMailId());
        displayContact.setText(userInfo.getPhone());

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
