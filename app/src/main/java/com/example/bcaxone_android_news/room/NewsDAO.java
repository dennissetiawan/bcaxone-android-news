package com.example.bcaxone_android_news.room;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.RewriteQueriesToDropUnusedColumns;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import model.ArticlesItem;
import model.User;
import model.UserWithArticles;

@Dao
public interface NewsDAO {
    @Transaction
    @Query("SELECT * FROM Articles")
    LiveData<List<ArticlesItem>> selectAllArticles();

    @Transaction
    @Query("SELECT * FROM Articles WHERE category = :category")
    LiveData<List<ArticlesItem>> selectArticlesWithCategory(String category);

    @Transaction
    @Query("SELECT * FROM Articles WHERE lower(title) LIKE lower('%'||:title||'%') ")
    LiveData<List<ArticlesItem>> selectArticlesWithTitleContains(String title);


    @Transaction
    @Query("SELECT * FROM User WHERE userID=:userId")
    public LiveData<UserWithArticles> selectUserWithArticles(int userId);


    @Query("SELECT * FROM User WHERE userID=:userId")
    public LiveData<User> selectUser(int userId);

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(UserArticleCrossRef userArticleCrossRef);

    @Delete
    void delete(UserArticleCrossRef userArticleCrossRef);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ArticlesItem articlesItem);

    @Update
    void update(ArticlesItem articlesItem);

    @Delete
    void delete(ArticlesItem articlesItem);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert (List<ArticlesItem> articlesItemsList);

}
