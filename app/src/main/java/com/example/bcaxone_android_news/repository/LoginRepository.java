package com.example.bcaxone_android_news.repository;

import model.LoginResponse;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit.LoginAPIService;
import retrofit.RetrofitInstance;
import retrofit2.Callback;

public class LoginRepository {
    LoginAPIService loginAPIService;
    private String LOGIN_API_KEY;

    public LoginRepository(){
        RetrofitInstance retrofitInstance = new RetrofitInstance(LoginAPIService.URL);
        loginAPIService = retrofitInstance.getLoginAPIService();
        LOGIN_API_KEY = "454041184B0240FBA3AACD15A1F7A8BB";
    }

    public void doLogin(String username , String password, Callback<LoginResponse> callback){
        //TODO: Loading screen
        RequestBody usernameBody = RequestBody.create(MediaType.parse("text/plain"), username);
        RequestBody passwordBody = RequestBody.create(MediaType.parse("text/plain"), password);
        loginAPIService.loginPostRequest(LOGIN_API_KEY,usernameBody,passwordBody).enqueue(callback);

    }


}
