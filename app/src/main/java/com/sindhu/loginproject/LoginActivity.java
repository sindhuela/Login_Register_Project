package com.sindhu.loginproject;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    Button goToSignupActivity,login;
    EditText email,passWord;

    String mailId,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        goToSignupActivity = (Button) findViewById(R.id.goToSignupActivity);
        login = (Button) findViewById(R.id.loginButton);
        email = (EditText) findViewById(R.id.email_login);
        passWord = (EditText) findViewById(R.id.password_login);

        final DatabaseHelper db = new DatabaseHelper(this);

        goToSignupActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //calling RegisterActivity
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);                                     // removing transition effects
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mailId = email.getText().toString();
                password = passWord.getText().toString();

                UserInfo userInfo = db.getData(mailId);

                if(password.equals(userInfo.getPassword())) {

                    //clearing existing text
                    email.setText("");
                    passWord.setText("");

                    //calling LoginWelcomeActivity
                    Intent intent = new Intent(LoginActivity.this, LoginWelcomeActivity.class);
                    intent.putExtra("email",mailId);                                                        // passing mail id to next activity
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);                                        // clearing activity from activity stack
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);                                     // removing transition effects
                    Toast.makeText(LoginActivity.this,"Login Success!" ,Toast.LENGTH_LONG).show();          // displaying success message
                    startActivity(intent);                                                                  // calling next activity
                } else {
                    Toast.makeText(LoginActivity.this,"Login Failed!" ,Toast.LENGTH_LONG).show();           // displaying error message
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
