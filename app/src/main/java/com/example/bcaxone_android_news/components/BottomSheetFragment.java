package com.example.bcaxone_android_news.components;


import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.bcaxone_android_news.R;
import com.example.bcaxone_android_news.SessionManagement;
import com.example.bcaxone_android_news.bookmark.BookmarkFragment;
import com.example.bcaxone_android_news.discover.DiscoverFragment;
import com.example.bcaxone_android_news.repository.NewsRepository;
import com.example.bcaxone_android_news.room.UserArticleCrossRef;
import com.example.bcaxone_android_news.service.NotificationTimerService;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import model.ArticlesItem;

public class BottomSheetFragment extends BottomSheetDialogFragment {
    ArticlesItem item;
    NewsRepository newsRepository;
    Fragment fromFragment;
    int userId;
    boolean isBookmarked;
    private int nextArticleId;
    TextView textView;
    ImageView imageView;
    private final Executor mainThread = new Executor(){
        private final Handler handler = new Handler(Looper.getMainLooper());
        @Override
        public void execute(Runnable runnable) {
            handler.post(runnable);
        }
    };
    public BottomSheetFragment(ArticlesItem item, Fragment fromFragment) {
        // Required empty public constructor
        this.item = item;
        this.fromFragment = fromFragment;
        isBookmarked = false;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        newsRepository = new NewsRepository(requireActivity().getApplication());
        userId = SessionManagement.getInstance().getUserInSessionId(requireContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_modal_bottom_sheet_dialog, container, false);
        textView = root.findViewById(R.id.bottom_sheet_bookmark_textview);
        imageView = root.findViewById(R.id.bottom_sheet_bookmark_imageview);
        View bookmarkItem = root.findViewById(R.id.bottom_sheet_bookmark);
        View shareItem = root.findViewById(R.id.bottom_sheet_share);
        View remindItem = root.findViewById(R.id.bottom_sheet_remindme);

        remindItem.setOnClickListener(view -> setReminder());
        shareItem.setOnClickListener(view -> share());

        newsRepository.room.getUserSavedArticles(userId).observe(getViewLifecycleOwner(), userWithArticles -> {
            List<ArticlesItem> savedArticles = userWithArticles.getArticlesItemList();
            for (ArticlesItem a: savedArticles) {
                if(a.getArticleID()==item.getArticleID()){
                    isBookmarked = true;
                    setBookmarkedState(textView, imageView);
                    return;
                }
            }
        });



        if(fromFragment instanceof BookmarkFragment || isBookmarked){
            setBookmarkedState(textView, imageView);
        }


        bookmarkItem.setOnClickListener(view -> {
            if(fromFragment instanceof BookmarkFragment || isBookmarked){
                deleteBookmark(userId,item.getArticleID());
            }else{
                addBookmark(userId,item.getArticleID());
            }
            this.dismiss();
        });
        return root;
    }

    private void setBookmarkedState(TextView textView, ImageView imageView) {
        textView.setText("Remove from Bookmark");
        imageView.setImageResource(R.drawable.icons8_delete_bookmark_50);
    }

    private void addBookmark(int userId , int articleId) {
        if(item.getArticleID()==0){
            new Thread(this::insertWait).start();
            new Thread(this::notifyGetArticleId).start();
            Log.d("NewsDetailFragment","News don't exist in room, add first");
            return;
        }else {
            newsRepository.room.insert(new UserArticleCrossRef(userId, articleId));
        }
        Toast.makeText(getContext(),"Add to Bookmark " +item.getTitle(),Toast.LENGTH_LONG).show();
        mainThread.execute(() -> setBookmarkedState(textView, imageView));

    }



    synchronized private void notifyGetArticleId() {
        nextArticleId = newsRepository.room.getArticleSize() + 1;
        notify();
    }
    synchronized private void insertWait() {
        try {
            wait();
            item.setArticleID(nextArticleId);
            newsRepository.room.insert(new UserArticleCrossRef(userId, nextArticleId));
            isBookmarked = true;
            mainThread.execute(() -> setBookmarkedState(textView, imageView));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private void deleteBookmark(int userId,int articleId) {
        newsRepository.room.delete(new UserArticleCrossRef(userId,articleId));
        Toast.makeText(getContext(),"Remove fromde Bookmark " +item.getTitle(),Toast.LENGTH_LONG).show();
        if(fromFragment instanceof BookmarkFragment) {
            ((BookmarkFragment) fromFragment).reloadData();
        }
    }

    private void share(){
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = String.format("%s\n %s \n%s", item.getTitle(),item.getDescription(), item.getUrl());
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "News Sharing");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    private void setReminder(){
        Intent reminderIntent = new Intent(getActivity(), NotificationTimerService.class);
        reminderIntent.putExtra(NotificationTimerService.SERVICE_NOTIFICATION_NEWS_ID,item.getArticleID());
        reminderIntent.putExtra(NotificationTimerService.SERVICE_NOTIFICATION_NEWS_TITLE,item.getTitle());
        getActivity().startService(reminderIntent);
        this.dismiss();
    }
}
