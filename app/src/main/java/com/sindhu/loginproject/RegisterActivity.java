package com.sindhu.loginproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    Button goToLoginActivity, registerButton;
    EditText name,email,password,phone;

    String userName,mailId,passWord,contact;
    int status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        goToLoginActivity = (Button) findViewById(R.id.goToLoginActivity);
        registerButton = (Button) findViewById(R.id.registerButton);

        name = (EditText) findViewById(R.id.name_register);
        email = (EditText) findViewById(R.id.email_register);
        password = (EditText) findViewById(R.id.password_register);
        phone = (EditText) findViewById(R.id.phoneNumber_register);

        final DatabaseHelper db = new DatabaseHelper(this);

        goToLoginActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //calling LoginActivity
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);                                     // removing transition effects
                startActivity(intent);
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                userName = name.getText().toString();
                mailId = email.getText().toString();
                passWord = password.getText().toString();
                contact = phone.getText().toString();

                UserInfo userInfo = new UserInfo(userName,mailId,passWord,contact);

                status = db.dataValidation(userInfo);

                switch (status) {
                    case 0:
                        //success, go to register
                        int no = db.register(userInfo);
                        if(no < 1) {
                            name.setText("");
                            email.setText("");
                            password.setText("");
                            phone.setText("");
                            Toast.makeText(RegisterActivity.this, "Registration Success!", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(RegisterActivity.this, "Email registered already!", Toast.LENGTH_LONG).show();
                        }
                        break;
                    case 1:
                        //Null in Name
                        Toast.makeText(RegisterActivity.this, "Name cannot be empty!", Toast.LENGTH_LONG).show();
                        break;
                    case 2:
                        //Null in Email
                        Toast.makeText(RegisterActivity.this, "Email cannot be empty!", Toast.LENGTH_LONG).show();
                        break;
                    case 3:
                        //Null in Password
                        Toast.makeText(RegisterActivity.this, "Password cannot be empty!", Toast.LENGTH_LONG).show();
                        break;
                    case 4:
                        //Null in Contact
                        Toast.makeText(RegisterActivity.this, "Contact cannot be empty!", Toast.LENGTH_LONG).show();
                        break;
                    case 5:
                        //error in Name
                        Toast.makeText(RegisterActivity.this, "Invalid User Name!", Toast.LENGTH_LONG).show();
                        break;
                    case 6:
                        //error in Email
                        Toast.makeText(RegisterActivity.this, "Invalid Email ID!", Toast.LENGTH_LONG).show();
                        break;
                    case 7:
                        //error in password
                        Toast.makeText(RegisterActivity.this, "Invalid Password!", Toast.LENGTH_LONG).show();
                        break;
                    case 8:
                        //error in contact
                        Toast.makeText(RegisterActivity.this, "Invalid Contact Number!", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        });
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
