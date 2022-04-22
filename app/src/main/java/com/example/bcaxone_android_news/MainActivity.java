package com.example.bcaxone_android_news;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;

import com.example.bcaxone_android_news.bookmark.BookmarkFragment;
import com.example.bcaxone_android_news.detail_news.NewsDetailFragment;
import com.example.bcaxone_android_news.discover.DiscoverFragment;
import com.example.bcaxone_android_news.home.HomeFragment;

import com.example.bcaxone_android_news.login.LoginActivity;
import com.example.bcaxone_android_news.search.SearchFragment;
import com.example.bcaxone_android_news.service.NotificationTimerService;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Calendar;

import model.ArticlesItem;

public class MainActivity extends AppCompatActivity {

    private NewsViewModel newsViewModel;
    BottomNavigationView bottomNavigationView;
    public static boolean isHome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isHome = true;
        newsViewModel = new ViewModelProvider(MainActivity.this).get(NewsViewModel.class);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            Log.d("MainActivity","bottom navigation "+id);
            switch (id){
                case R.id.bottom_navigation_item_page_headlines:
                    isHome = true;
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_container,new HomeFragment()).commitNow();
                    break;
                case R.id.bottom_navigation_item_page_bookmark:
                    isHome = false;
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_container,new BookmarkFragment()).commitNow();
                    break;
                case R.id.bottom_navigation_item_page_discover:
                    isHome = false;
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_container,new DiscoverFragment()).commitNow();
                    break;
            }
            return true;
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);

        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        SearchView sv = (SearchView) menu.findItem(R.id.item_search).getActionView();

        sv.setOnSearchClickListener(view -> {
            SearchFragment searchFragment = new SearchFragment();
            Log.d("MainActivity","search click");
            sv.setOnQueryTextListener(searchFragment);
            sv.setOnCloseListener(searchFragment);
            getSupportFragmentManager().beginTransaction().replace(R.id.main_container, searchFragment).commitNow();
            isHome = false;
        });

        sv.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        sv.setIconifiedByDefault(true);
        sv.setMaxWidth(Integer.MAX_VALUE);

        return true;
    }
    //  LOGIN SESSION
    @Override
    protected void onResume() {
        super.onResume();
        boolean isAllowed = SessionManagement.getInstance().isSessionActive(this, Calendar.getInstance().getTime());
        if(!isAllowed){
            openLoginActivity();
            return;
        }
        Intent sourceIntent = getIntent();
        int articleId = sourceIntent.getIntExtra(NotificationTimerService.SERVICE_NOTIFICATION_NEWS_ID,-1);
        Log.d("MainActivity","article id from notification "+articleId);
        if(articleId != -1){
            sourceIntent.putExtra(NotificationTimerService.SERVICE_NOTIFICATION_NEWS_ID,-1);
            openDetailNewsWithId(articleId);
        }else{
            getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new HomeFragment()).commitNow();
        }

    }

    private void openDetailNewsWithId(int articleId){
        newsViewModel.getFromRoomArticlesWithID(articleId).observe(this, articlesItem -> {
            if(articlesItem != null){
                isHome = false;
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new NewsDetailFragment(articlesItem)).commitNow();
            }
        });

    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.item_logout) {
            SessionManagement.getInstance().endUserSession(MainActivity.this);
            openLoginActivity();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if(!isHome){
            isHome = true;
            bottomNavigationView.setVisibility(View.VISIBLE);
            bottomNavigationView.setSelectedItemId(R.id.bottom_navigation_item_page_headlines);
        }else{
            super.onBackPressed();
        }
    }
}