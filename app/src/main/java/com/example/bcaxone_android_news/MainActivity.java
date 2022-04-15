package com.example.bcaxone_android_news;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
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

        newsViewModel.getArticleDataTopHeadlines("sports","id").observe(MainActivity.this, new Observer<List<ArticlesItem>>() {
            @Override
            public void onChanged(List<ArticlesItem> articles) {
                textView.setText(articles.get(0).getTitle());
            }
        });
    }
}