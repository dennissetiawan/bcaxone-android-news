package com.example.bcaxone_android_news;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.bcaxone_android_news.repository.NewsRepository;
import com.example.bcaxone_android_news.room.UserArticleCrossRef;

import java.util.List;

import model.ArticlesItem;
import model.User;
import model.UserWithArticles;

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
    
    
    public LiveData<UserWithArticles> getFromRoomUserSavedArticles(int userId) {
        return newsRepository.room.getUserSavedArticles(userId);
    }
    public LiveData<User> getFromRoomUser(int userId) {
        return newsRepository.room.getUser(userId);
    }


    public LiveData<List<ArticlesItem>> getFromRoomArticlesWithCategory(String category) {
        return newsRepository.room.getFromRoomArticlesWithCategory(category);
    }
    public LiveData<ArticlesItem> getFromRoomArticlesWithID(int articleID) {
        return newsRepository.room.getFromRoomArticlesWithID(articleID);
    }

    public LiveData<List<ArticlesItem>> getFromRoomArticlesWithTitleContains(String title) {
        return newsRepository.room.getFromRoomArticlesWithTitleContains(title);
    }

    public int getFromRoomArticleSize() {
        return newsRepository.room.getArticleSize();
    }


    public void insertArticleToDB(ArticlesItem articlesItem) {
        newsRepository.room.insert(articlesItem);
    }

    public void insertArticleListToDB(List<ArticlesItem> articlesItemList) {
        newsRepository.room.insert(articlesItemList);
    }


    public void insertSavedArticleToDB(UserArticleCrossRef userArticleCrossRef) {
        newsRepository.room.insert(userArticleCrossRef);
    }

    public void insertUser(User user) {
        newsRepository.room.insert(user);
    }


}
