package com.example.bcaxone_android_news;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.kwabenaberko.newsapilib.NewsApiClient;
import com.kwabenaberko.newsapilib.models.Article;
import com.kwabenaberko.newsapilib.models.request.EverythingRequest;
import com.kwabenaberko.newsapilib.models.response.ArticleResponse;

import java.io.IOException;
import java.util.List;


public class NewsRepository {
    public static final String API_KEY = "8850862c9aea4bceaf5f19cd22d362ef0";
    NewsApiClient newsApiClient;
    private MutableLiveData<List<Article>> articlesData = new MutableLiveData<>();

    public NewsRepository(Application application){
        newsApiClient = new NewsApiClient(API_KEY);
    }


    MutableLiveData<List<Article>> getAllArticles(String query){
        newsApiClient.getEverything(new EverythingRequest.Builder().q(query).build(), new NewsApiClient.ArticlesResponseCallback() {
            @Override
            public void onSuccess(ArticleResponse articleResponse) {
                articlesData.setValue( articleResponse.getArticles());
            }

            @Override
            public void onFailure(Throwable throwable) {
                if(throwable instanceof IOException){
                    //TODO: show toast or something for connection error
                    Log.e(NewsRepository.class.getName(),"Network Error");
                }else{
                    Log.e(NewsRepository.class.getName(),"NewsRepository General Error " + throwable.getMessage());
                }
            }
        });
        return articlesData;
    }



}
