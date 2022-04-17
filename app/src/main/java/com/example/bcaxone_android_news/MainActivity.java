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

import java.util.Calendar;
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

//        testAPIandRoom(textView);
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
//      Tab Menu
//        getSupportFragmentManager().beginTransaction().replace(R.id.main_container,TabFragment.newInstance()).commitNow();

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

////TabMenu
//    public void tabMenu(View view){
//        getSupportFragmentManager().beginTransaction().replace(R.id.containermenu,TabFragment.newInstance()).commitNow();
//    }

    private void openLoginActivity() {
        Intent intent = new Intent(this,LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
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

    }

}