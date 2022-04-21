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


    public LiveData<List<ArticlesItem>> getFromRoomAllArticles(){
        return newsDAO.selectAllArticles();
    }

    public LiveData<List<ArticlesItem>> getFromRoomArticlesWithCategory(String category){
        return newsDAO.selectArticlesWithCategory(category);
    }

    public LiveData<List<ArticlesItem>> getFromRoomArticlesWithTitleContains(String title){
        return newsDAO.selectArticlesWithTitleContains(title);
    }


    public LiveData<UserWithArticles> getUserSavedArticles(int userId){
        return newsDAO.selectUserWithArticles(userId);
    }

    public LiveData<User> getUser(int userId){
        return newsDAO.selectUser(userId);
    }


    public void insert(UserArticleCrossRef userArticleCrossRef){
        AppDatabase.databaseWriteExecutor.execute(() -> newsDAO.insert(userArticleCrossRef));
    }
    public void delete(UserArticleCrossRef userArticleCrossRef){
        AppDatabase.databaseWriteExecutor.execute(() -> newsDAO.delete(userArticleCrossRef));
    }

    public void insert(User user){
        AppDatabase.databaseWriteExecutor.execute(() -> newsDAO.insert(user));
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
