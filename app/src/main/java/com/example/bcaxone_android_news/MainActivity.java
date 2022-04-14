package com.example.bcaxone_android_news;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.TextView;

import com.kwabenaberko.newsapilib.models.Article;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private NewsViewModel newsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = findViewById(R.id.textview_hello);
        newsViewModel = new ViewModelProvider(MainActivity.this).get(NewsViewModel.class);

        newsViewModel.getArticleData("indonesia").observe(MainActivity.this, new Observer<List<Article>>() {
            @Override
            public void onChanged(List<Article> articles) {
                textView.setText(articles.get(0).getAuthor());
            }
        });
    }
}