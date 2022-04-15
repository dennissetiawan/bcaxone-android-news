package com.example.bcaxone_android_news.room;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import model.ArticlesItem;

public interface NewsDAO {
    @Query("SELECT * FROM ArticlesItem")
    LiveData<List<ArticlesItem>> getAllArticles();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ArticlesItem articlesItem);

    @Update
    void update(ArticlesItem articlesItem);

    @Delete
    void delete(ArticlesItem articlesItem);
}
