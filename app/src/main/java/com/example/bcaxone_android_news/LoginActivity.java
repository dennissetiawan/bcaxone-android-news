package com.example.bcaxone_android_news;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bcaxone_android_news.repository.LoginRepository;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;

import org.json.JSONArray;

import java.io.IOException;

import model.LoginResponse;
import retrofit.LoginAPIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    TextInputLayout usernameTextView, passwordTextView;
    Button loginButton;
    LoginRepository loginRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginRepository = new LoginRepository();

        usernameTextView = findViewById(R.id.edittext_username);
        passwordTextView = findViewById(R.id.edittext_password);
        loginButton = findViewById(R.id.button_login);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    login();
            }

            private void login() {
                String username = usernameTextView.getEditText().getText().toString();
                String password = passwordTextView.getEditText().getText().toString();

                if(TextUtils.isEmpty(username) || TextUtils.isEmpty(password)){
                    Toast.makeText(LoginActivity.this,"Please insert Username and Password for login",Toast.LENGTH_SHORT).show();
                    return;
                }

                loginRepository.doLogin(username, password, new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        Log.d("LoginActivity","onResponse");
                        if(response.body()==null){
                            Toast.makeText(LoginActivity.this, "Username atau Password salah!",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Log.d("LoginActivity","response: "+response.body().getMessage());
                        startAndStoreSession(response.body().getToken());

                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);


                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Log.d("LoginActivity","failed");
                    }
                });

            }

            private void startAndStoreSession(String userToken) {
                SessionManagement.getInstance().startUserSession(LoginActivity.this,5,userToken);
            }
        });
    }
}