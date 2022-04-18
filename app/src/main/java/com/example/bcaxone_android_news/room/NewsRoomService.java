package com.example.bcaxone_android_news.room;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import model.ArticlesItem;
import model.User;
import model.UserWithArticles;

public class NewsRoomService {

    private NewsDAO newsDAO;

    public NewsRoomService(Application application) {
        AppDatabase database = AppDatabase.getDatabase(application);
        newsDAO = database.newsDAO();
    }


    public LiveData<List<ArticlesItem>> getAllArticles(){
        return newsDAO.selectAllArticles();
    }


    public LiveData<List<UserWithArticles>> getSavedArticles(int userId){
        return newsDAO.selectUserWithArticles(userId);
    }


    public void insert(UserArticleCrossRef userArticleCrossRef){
        AppDatabase.databaseWriteExecutor.execute(() -> newsDAO.insert(userArticleCrossRef));
    }

    public void insert(User user){
        AppDatabase.databaseWriteExecutor.execute(() -> newsDAO.insert(user));
    }

    public void insert(ArticlesItem articlesItem){
        AppDatabase.databaseWriteExecutor.execute(() -> newsDAO.insert(articlesItem));
    }

    public void update(ArticlesItem articlesItem){
        AppDatabase.databaseWriteExecutor.execute(() -> newsDAO.update(articlesItem));
    }

    public void delete(ArticlesItem articlesItem){
        AppDatabase.databaseWriteExecutor.execute(() -> newsDAO.delete(articlesItem));
    }

}
