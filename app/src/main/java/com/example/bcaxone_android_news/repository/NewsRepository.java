package com.example.bcaxone_android_news.repository;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;


import retrofit.NewsAPIKeys;
import com.example.bcaxone_android_news.room.NewsRoomService;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import model.ArticlesItem;
import model.NewsAPIResponse;
import retrofit.NewsAPIService;
import retrofit.RetrofitInstance;
import retrofit2.Call;


public class NewsRepository {
    //room
    public NewsRoomService room;

    //networking
    NewsAPIService newsApiService;
    private String NEWS_API_KEY;
    private Map<String, String> query;
    private MutableLiveData<List<ArticlesItem>> articlesData = new MutableLiveData<>();
    private final ExecutorService networkExecutor = Executors.newFixedThreadPool(4);
    private final Executor mainThread = new Executor(){
        private final Handler handler = new Handler(Looper.getMainLooper());
        @Override
        public void execute(Runnable runnable) {
            handler.post(runnable);
        }
    };

    public NewsRepository(Application application){
        RetrofitInstance retrofitInstance = new RetrofitInstance(NewsAPIService.BASE_URL);
        newsApiService = retrofitInstance.getNewsAPIService();

        query = new HashMap<>();
        NEWS_API_KEY = NewsAPIKeys.SECRET_API_KEY;
        query.put("apiKey", NEWS_API_KEY);

        room = new NewsRoomService(application);
    }

    public void getArticlesFromNetwork(Call<NewsAPIResponse> newsAPIResponseCall){
        Log.e("NewsRepository","getArticlesFromNetwork");
        networkExecutor.execute(() -> {
            try{
                List<ArticlesItem> articles = Objects.requireNonNull(newsAPIResponseCall.execute().body()).getArticles();
                Log.e("NewsRepository","execute");
                mainThread.execute(() -> {
                    Log.e("NewsRepository","setValue");
                    articlesData.setValue(articles);
                });
            }
            catch (IOException e){
                Log.e("NewsRepository","IO exception in API Call");
                //TODO: Error handling snackbar
                e.printStackTrace();
            } catch (Exception e){
                Log.e("NewsRepository","General exception in API Call");
                e.printStackTrace();
            }
        });
    }

    private Map<String, String> createQuery(){
        query = new HashMap<>();
        query.put("apiKey", NEWS_API_KEY);
        return query;
    }


    public MutableLiveData<List<ArticlesItem>> getEverything(String q){
        articlesData = new MutableLiveData<>();
        query = createQuery();
        query.put("q",q);
        query.values().removeAll(Collections.singleton(null));

        Call<NewsAPIResponse> newsAPIResponseCall = newsApiService.getEverything(query);
        getArticlesFromNetwork(newsAPIResponseCall);
        return articlesData;
    }

    public MutableLiveData<List<ArticlesItem>> getTopHeadlines(String category, String country){
        Log.e("NewsRepository","getTopHeadlines");
        articlesData = new MutableLiveData<>();
        query = createQuery();
        query.put("country",country);
        query.put("category",category);
        query.values().removeAll(Collections.singleton(null));

        Call<NewsAPIResponse> newsAPIResponseCall = newsApiService.getTopHeadlines(query);
        getArticlesFromNetwork(newsAPIResponseCall);
        Log.e("NewsRepository","Return articlesData");
        return articlesData;
    }


}
