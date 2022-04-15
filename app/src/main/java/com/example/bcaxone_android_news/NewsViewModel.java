package com.example.bcaxone_android_news;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.bcaxone_android_news.repository.NewsRepository;

import java.util.List;

import model.ArticlesItem;

public class NewsViewModel extends AndroidViewModel {
    private NewsRepository newsRepository;
    private MutableLiveData<List<ArticlesItem>> articleData;



    public NewsViewModel(@NonNull Application application) {
        super(application);
        newsRepository = new NewsRepository(application);
    }

    public MutableLiveData<List<ArticlesItem>> getArticleDataWithQuery(String query) {
        articleData = newsRepository.getEverything(query);
        return articleData;
    }
    public MutableLiveData<List<ArticlesItem>> getArticleDataTopHeadlines(String category,String country) {
        articleData = newsRepository.getTopHeadlines(category,country);
        return articleData;
    }
    public LiveData<List<ArticlesItem>> getFromRoomAllArticles() {
        return newsRepository.room.getFromRoomAllArticles();
    }

    public void insertArticleToDB(ArticlesItem articlesItem) {
        newsRepository.room.insert(articlesItem);

    }


}
