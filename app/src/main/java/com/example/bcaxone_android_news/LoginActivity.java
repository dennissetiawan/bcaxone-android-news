package com.example.bcaxone_android_news;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    TextView usernametv, passwordtv;
    Button loginbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernametv = findViewById(R.id.username);
        passwordtv = findViewById(R.id.password);
        loginbtn = findViewById(R.id.loginbtn);
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    login();
            }

            private void login() {
                String username = usernametv.getText().toString();
                String password = passwordtv.getText().toString();

                if(TextUtils.isEmpty(username) || TextUtils.isEmpty(password)){
                    Toast.makeText(LoginActivity.this,"Please insert Username and Password for login",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(username.equals("admin") && password.equals("admin")){
                    startAndStoreSession();
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(LoginActivity.this, "Username atau Password salah!",Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            private void startAndStoreSession() {
                SessionManagement.getINSTANCE().startUserSession(LoginActivity.this,5);
            }
        });
    }
}