package com.example.bcaxone_android_news.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.example.bcaxone_android_news.MainActivity;
import com.example.bcaxone_android_news.R;
import com.example.bcaxone_android_news.SessionManagement;
import com.example.bcaxone_android_news.repository.LoginRepository;
import com.example.bcaxone_android_news.repository.NewsRepository;
import com.google.android.material.textfield.TextInputLayout;

import model.LoginResponse;
import model.LoginUserData;
import model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    TextInputLayout usernameTextView, passwordTextView;
    Button loginButton;
    LoginRepository loginRepository;
    NewsRepository newsRepository  = new NewsRepository(getApplication());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginRepository = new LoginRepository();

        usernameTextView = findViewById(R.id.edittext_username);
        passwordTextView = findViewById(R.id.edittext_password);
        loginButton = findViewById(R.id.button_login);

        loginButton.setOnClickListener(view -> loginActionButton());

    }

    private void loginActionButton() {
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
                LoginResponse loginResponse = response.body();
                if(loginResponse==null){
                    Toast.makeText(LoginActivity.this, "Wrong Username or Password",Toast.LENGTH_SHORT).show();
                    return;
                }
                doLogin(loginResponse);
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.d("LoginActivity","failed");
            }
        });

    }

    private void doLogin(LoginResponse loginResponse) {
        Log.d("LoginActivity","response: "+ loginResponse.getMessage());
        startAndStoreSession(loginResponse.getToken(),Integer.parseInt(loginResponse.getLoginUserData().getId()));

        Log.d("LoginActivity","search user with id: "+ loginResponse.getLoginUserData().getId());
        LoginUserData loginUserData = loginResponse.getLoginUserData();

        newsRepository.room.getUser(Integer.parseInt(loginUserData.getId())).observe(LoginActivity.this,
                user -> {
                    Log.d("LoginActivity","observer onchanged");
                    if (user==null){
                        Log.d("LoginActivity","userfromdb not found");
                        User newUser = new User(Integer.parseInt(loginUserData.getId()),loginUserData.getUsername());
                        newsRepository.room.insert(newUser);
                    }else{
                        Log.d("LoginActivity","userfromdb found");
                    }
                });


        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void startAndStoreSession(String userToken, int userId) {
        SessionManagement.getInstance().startUserSession(LoginActivity.this,60,userToken,userId);
    }

}