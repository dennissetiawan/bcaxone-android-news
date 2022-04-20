package com.example.bcaxone_android_news.repository;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.bcaxone_android_news.room.NewsRoomService;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import model.ArticlesItem;
import model.LoginResponse;
import model.NewsAPIResponse;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit.LoginAPIService;
import retrofit.NewsAPIKeys;
import retrofit.NewsAPIService;
import retrofit.RetrofitInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepository {
    LoginAPIService loginAPIService;
    private String LOGIN_API_KEY;

    public LoginRepository(){
        RetrofitInstance retrofitInstance = new RetrofitInstance(LoginAPIService.URL);
        loginAPIService = retrofitInstance.getLoginAPIService();
        LOGIN_API_KEY = "454041184B0240FBA3AACD15A1F7A8BB";
    }

    public void doLogin(String username , String password, Callback<LoginResponse> callback){
        RequestBody usernameBody = RequestBody.create(MediaType.parse("text/plain"), username);
        RequestBody passwordBody = RequestBody.create(MediaType.parse("text/plain"), password);
        loginAPIService.loginPostRequest(LOGIN_API_KEY,usernameBody,passwordBody).enqueue(callback);

    }


}
