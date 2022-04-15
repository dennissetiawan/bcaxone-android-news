package com.example.bcaxone_android_news;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

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

        testAPIandRoom(textView);
////      LOGOUT SESSION BUTTON
//        Button button = findViewById(R.id.logoutbtn);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                SessionManagement.getINSTANCE().endUserSession(MainActivity.this);
//                openLoginActivity();
//            }
//        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.containermenu,MenuFragment.newInstance()).commitNow();
        }
    }
    //  LOGIN SESSION
    @Override
    protected void onResume() {
        boolean isAllowed = SessionManagement.getINSTANCE().idSessionActive(this, Calendar.getInstance().getTime());
        if(!isAllowed){
            openLoginActivity();
        }
        super.onResume();
    }
    //  LOGOUT MENU
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.btnLogout:
                SessionManagement.getINSTANCE().endUserSession(MainActivity.this);
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