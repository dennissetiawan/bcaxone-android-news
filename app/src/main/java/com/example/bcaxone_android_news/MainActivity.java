package com.example.bcaxone_android_news;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.util.Log;
import android.widget.TextView;

import com.example.bcaxone_android_news.bookmark.BookmarkFragment;
import com.example.bcaxone_android_news.home.HomeFragment;

import com.example.bcaxone_android_news.room.UserArticleCrossRef;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.Calendar;
import java.util.List;

import model.ArticlesItem;
import model.User;
import model.UserWithArticles;

public class MainActivity extends AppCompatActivity {

    private boolean isFragmentAvail;
    private NewsViewModel newsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isFragmentAvail = false;

//        TextView textView = findViewById(R.id.textview_hello);
        newsViewModel = new ViewModelProvider(MainActivity.this).get(NewsViewModel.class);

//        testAPIandRoom(textView);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.main_container,MenuFragment.newInstance()).commitNow();
        }

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener((NavigationBarView.OnItemSelectedListener) item -> {
            int id = item.getItemId();
            Log.d("MainActivity","bottom navigation "+id);
            switch (id){
                case R.id.page_1:
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_container,new HomeFragment()).commitNow();
                    break;
                case R.id.page_2:
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_container, BookmarkFragment.newInstance()).commitNow();
                    break;
            }
            return true;
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
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

        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new HomeFragment()).commitNow();
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


    private void testAPIandRoom(TextView textView) {
        newsViewModel.getArticleDataTopHeadlines("sports","id").observe(MainActivity.this, new Observer<List<ArticlesItem>>() {
            @Override
            public void onChanged(List<ArticlesItem> articles) {
                Log.d("MainActivity","testapionchanged");
                textView.setText(articles.get(0).getTitle());
                for (ArticlesItem a: articles) {
                    newsViewModel.insertArticleToDB(a);
                }
                Log.d("MainActivity","INSERT DONE!");
            }
        });


        newsViewModel.getFromRoomAllArticles().observe(MainActivity.this, new Observer<List<ArticlesItem>>() {
            @Override
            public void onChanged(List<ArticlesItem> articles) {
                Log.d("MainActivity","db size :"+articles.size());
            }
        });

        newsViewModel.insertUser(new User("test","test"));
        newsViewModel.insertSavedArticleToDB(new UserArticleCrossRef(1,1));
        newsViewModel.insertSavedArticleToDB(new UserArticleCrossRef(1,2));
        newsViewModel.getFromRoomUserSavedArticles(1).observe(MainActivity.this, new Observer<UserWithArticles>() {
            @Override
            public void onChanged(UserWithArticles userWithArticles) {
                Log.d("MainActivity","get user"+userWithArticles+" with articles ");
            }
        });

    }

    @Override
    public void onBackPressed() {

        if(getFragmentManager().getBackStackEntryCount() > 0){
            getFragmentManager().popBackStackImmediate();
        }else {
            Log.d("MainActivity","super back pressed");
            super.onBackPressed();
        }
    }
}