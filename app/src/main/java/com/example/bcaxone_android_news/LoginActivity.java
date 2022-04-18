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

    TextView usernameTextView, passwordTextView;
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameTextView = findViewById(R.id.edittext_username);
        passwordTextView = findViewById(R.id.edittext_password);
        loginButton = findViewById(R.id.button_login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    login();
            }

            private void login() {
                String username = usernameTextView.getText().toString();
                String password = passwordTextView.getText().toString();

                if(TextUtils.isEmpty(username) || TextUtils.isEmpty(password)){
                    Toast.makeText(LoginActivity.this,"Please insert Username and Password for login",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(username.equals("admin") && password.equals("1")){
                    startAndStoreSession();
                    //fetch api
                    //insert room

                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(LoginActivity.this, "Username atau Password salah!",Toast.LENGTH_SHORT).show();
                }
            }

            private void startAndStoreSession() {
                SessionManagement.getInstance().startUserSession(LoginActivity.this,5);
            }
        });
    }
}