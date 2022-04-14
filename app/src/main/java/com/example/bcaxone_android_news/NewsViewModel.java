package com.example.bcaxone_android_news;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.kwabenaberko.newsapilib.models.Article;

import java.util.List;

public class NewsViewModel extends AndroidViewModel {
    private NewsRepository newsRepository;
    private MutableLiveData<List<Article>> articleData;


    public NewsViewModel(@NonNull Application application) {
        super(application);
        newsRepository = new NewsRepository(application);

    }

    public MutableLiveData<List<Article>> getArticleData(String query) {
        articleData = newsRepository.getAllArticles(query);
        return articleData;
    }
}
