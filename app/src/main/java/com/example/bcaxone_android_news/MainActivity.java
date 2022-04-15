package com.example.bcaxone_android_news;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

import model.ArticlesItem;

public class MainActivity extends AppCompatActivity {

    private NewsViewModel newsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = findViewById(R.id.textview_hello);
        newsViewModel = new ViewModelProvider(MainActivity.this).get(NewsViewModel.class);

        testAPIandRoom(textView);
    }

    private void testAPIandRoom(TextView textView) {
        newsViewModel.getArticleDataTopHeadlines("sports","id").observe(MainActivity.this, new Observer<List<ArticlesItem>>() {
            @Override
            public void onChanged(List<ArticlesItem> articles) {

                textView.setText(articles.get(0).getTitle());
                newsViewModel.insertArticleToDB(articles.get(1));
                Log.d("MainActivity","INSERT DONE!");
            }
        });

        newsViewModel.getFromRoomAllArticles().observe(MainActivity.this, new Observer<List<ArticlesItem>>() {
            @Override
            public void onChanged(List<ArticlesItem> articles) {
                Log.d("MainActivity","db size :"+articles.size()+"Get from db: "+articles.get(0).getSource().getName());
            }
        });

//        newsViewModel.getFromRoomAllSourceAndArticlesItem().observe(MainActivity.this,
//                new Observer<List<SourceAndArticlesItem>>() {
//                    @Override
//                    public void onChanged(List<SourceAndArticlesItem> sourceAndArticlesItems) {
//                        textView.setText(sourceAndArticlesItems.get(0).articlesItem.getTitle() + "\n" + sourceAndArticlesItems.get(0).source.getName());
//                    }
//                });
    }
}