package model;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import com.example.bcaxone_android_news.room.UserArticleCrossRef;

import java.util.List;

public class UserWithArticles {
    @Embedded public User user;

    @Relation(parentColumn = "userID" ,entityColumn = "articleID" ,
            associateBy = @Junction(UserArticleCrossRef.class)
    )
    private List<ArticlesItem> articlesItemList;

    public List<ArticlesItem> getArticlesItemList() {
        return articlesItemList;
    }

    public void setArticlesItemList(List<ArticlesItem> articlesItemList) {
        this.articlesItemList = articlesItemList;
    }
}
