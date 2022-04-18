package com.example.bcaxone_android_news.room;

import androidx.room.Entity;

@Entity(primaryKeys = {"userID","articleID"})
public class UserArticleCrossRef {
    public long userID;
    public long articleID;

    public UserArticleCrossRef(long userID, long articleID) {
        this.userID = userID;
        this.articleID = articleID;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public long getArticleID() {
        return articleID;
    }

    public void setArticleID(long articleID) {
        this.articleID = articleID;
    }
}