package com.example.bcaxone_android_news.room;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import model.ArticlesItem;

public class NewsRoomService {

    private NewsDAO newsDAO;

    public NewsRoomService(Application application) {
        AppDatabase database = AppDatabase.getDatabase(application);
        newsDAO = database.newsDAO();
    }


    public LiveData<List<ArticlesItem>> getFromRoomAllArticles(){
        return newsDAO.selectAllArticles();
    }

    public LiveData<List<ArticlesItem>> getFromRoomArticlesWithCategory(String category){
        return newsDAO.selectArticlesWithCategory(category);
    }

    public void insert(ArticlesItem articlesItem){
        AppDatabase.databaseWriteExecutor.execute(() -> newsDAO.insert(articlesItem));
    }

    public void insert(List<ArticlesItem> articlesItemList){
        AppDatabase.databaseWriteExecutor.execute(() -> newsDAO.insert(articlesItemList));
    }

    public void update(ArticlesItem articlesItem){
        AppDatabase.databaseWriteExecutor.execute(() -> newsDAO.update(articlesItem));
    }

    public void delete(ArticlesItem articlesItem){
        AppDatabase.databaseWriteExecutor.execute(() -> newsDAO.delete(articlesItem));
    }

}
