package com.example.bcaxone_android_news;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.util.Log;
import android.widget.TextView;

import com.example.bcaxone_android_news.tab.TabFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import model.ArticlesItem;
import retrofit.NewsAPIKeys;

public class MainActivity extends AppCompatActivity {

    private boolean isFragmentAvail;
    private NewsViewModel newsViewModel;
    private ArrayList<ArrayList<ArticlesItem>> categoryArticles = new ArrayList<>();
    public String APICategoryCalls[] = new String[]{NewsAPIKeys.CATEGORY_BUSINESS, NewsAPIKeys.CATEGORY_ENTERTAINMENT, NewsAPIKeys.CATEGORY_GENERAL,
            NewsAPIKeys.CATEGORY_HEALTH, NewsAPIKeys.CATEGORY_SCIENCE, NewsAPIKeys.CATEGORY_SPORTS, NewsAPIKeys.CATEGORY_TECHNOLOGY};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isFragmentAvail = false;

//        TextView textView = findViewById(R.id.textview_hello);
        newsViewModel = new ViewModelProvider(MainActivity.this).get(NewsViewModel.class);

//        testAPIandRoom(textView);
        getDataFromAPI();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.main_container,MenuFragment.newInstance()).commitNow();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }
    //  LOGIN SESSION
    @Override
    protected void onResume() {
        boolean isAllowed = SessionManagement.getInstance().isSessionActive(this, Calendar.getInstance().getTime());

        if(!isAllowed){
            openLoginActivity();
        }
        super.onResume();
    }
    //  LOGOUT SESSION
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_logout:
                SessionManagement.getInstance().endUserSession(MainActivity.this);
                openLoginActivity();
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }

    private void openLoginActivity() {
        Intent intent = new Intent(this,LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void getDataFromAPI(){
        for (int i =0 ;i<7;i++) {
            int finalI = i;
            newsViewModel.getArticleDataTopHeadlines(APICategoryCalls[i],NewsAPIKeys.COUNTRY_INDONESIA).observe(MainActivity.this, new Observer<List<ArticlesItem>>() {
                @Override
                public void onChanged(List<ArticlesItem> articlesItems) {
                    categoryArticles.add((ArrayList<ArticlesItem>) articlesItems);
//                    isFragmentAvail = true;
                    if (finalI ==6){
                        //      Tab Menu
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, TabFragment.newInstance(categoryArticles)).commitNow();
                    }
                }
            });
        }
    }

//    public void onBackPressed() {
//        Fragment f = getSupportFragmentManager().findFragmentById(R.id.main_container);
//        if (!isFragmentAvail)
//            super.onBackPressed();
//        else{
//            getSupportFragmentManager().popBackStack();
//        }
//    }

//    private void testAPIandRoom(TextView textView) {
//        newsViewModel.getArticleDataTopHeadlines("sports","id").observe(MainActivity.this, new Observer<List<ArticlesItem>>() {
//            @Override
//            public void onChanged(List<ArticlesItem> articles) {
//
//                textView.setText(articles.get(0).getTitle());
//                newsViewModel.insertArticleToDB(articles.get(1));
//                Log.d("MainActivity","INSERT DONE!");
//            }
//        });
//
//        newsViewModel.getFromRoomAllArticles().observe(MainActivity.this, new Observer<List<ArticlesItem>>() {
//            @Override
//            public void onChanged(List<ArticlesItem> articles) {
////                Log.d("MainActivity","db size :"+articles.size()+"Get from db: "+articles.get(0).getSource().getName());
//            }
//        });
//
//    }

}