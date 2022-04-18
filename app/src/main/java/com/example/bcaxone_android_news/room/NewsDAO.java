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

@Dao
public interface NewsDAO {
    @Transaction
    @Query("SELECT * FROM Articles")
    LiveData<List<ArticlesItem>> selectAllArticles();

    @Transaction
    @Query("SELECT * FROM Articles WHERE category = :category")
    LiveData<List<ArticlesItem>> selectArticlesWithCategory(String category);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ArticlesItem articlesItem);

    @Update
    void update(ArticlesItem articlesItem);

    @Delete
    void delete(ArticlesItem articlesItem);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert (List<ArticlesItem> articlesItemsList);

}
