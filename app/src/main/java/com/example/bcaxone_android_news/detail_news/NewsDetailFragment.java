package com.example.bcaxone_android_news.detail_news;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.bcaxone_android_news.R;
import com.example.bcaxone_android_news.SessionManagement;
import com.example.bcaxone_android_news.repository.NewsRepository;
import com.example.bcaxone_android_news.room.UserArticleCrossRef;
import com.squareup.picasso.Picasso;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.List;


import model.ArticlesItem;

public class NewsDetailFragment extends Fragment {
    ArticlesItem articlesItem;
    private TextView  descNewsDetailTextView, titleNewsDetailTextView, categoryNewsDetailTextView,authorDetailTextView,publishDetailTextView;
    private ImageView imageViewDetail;
    private ImageButton bookmarkImageButton;
    private NewsRepository newsRepository;
    private boolean isBookmarked;
    private int userId;
    private int nextArticleId;
    private LocalDateTime publishDateInput;
    private String publishDateOutput;
    private String PublishDateNews;
    public NewsDetailFragment(ArticlesItem articlesItem) {
        this.articlesItem = articlesItem;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isBookmarked = false;
        newsRepository = new NewsRepository(requireActivity().getApplication());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_news_detail, container, false);

        getActivity().findViewById(R.id.bottom_navigation).setVisibility(View.INVISIBLE);
        Log.d("NewsDetailFragment","setbottomnav invisible");

        titleNewsDetailTextView = root.findViewById(R.id.fragment_news_detail_textview_title);
        descNewsDetailTextView = root.findViewById(R.id.fragment_news_detail_textview_description);
        categoryNewsDetailTextView = root.findViewById(R.id.fragment_news_detail_textview_category);
        authorDetailTextView = root.findViewById(R.id.fragment_news_detail_textview_author);
        publishDetailTextView = root.findViewById(R.id.fragment_news_detail_textview_publish);
        imageViewDetail = root.findViewById(R.id.fragment_news_detail_imageview);
        bookmarkImageButton = root.findViewById(R.id.fragment_news_detail_imagebutton_bookmark);
        userId = SessionManagement.getInstance().getUserInSessionId(requireContext());


        newsRepository.room.getUserSavedArticles(userId).observe(getViewLifecycleOwner(), userWithArticles -> {
            List<ArticlesItem> savedArticles = userWithArticles.getArticlesItemList();
            for (ArticlesItem a: savedArticles) {
                if(a.getArticleID()==articlesItem.getArticleID()){
                    isBookmarked = true;
                    setBookmarkedState();
                    return;
                }
            }
            isBookmarked = false;
            setNotBookmarkedState();
        });



        bookmarkImageButton.setOnClickListener(view -> {
            if(isBookmarked){
                deleteBookmark(userId,articlesItem.getArticleID());
            }else{
                addBookmark(userId,articlesItem.getArticleID());
            }
        });

        PublishDateNews = articlesItem.getPublishedAt();

        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        DateTimeFormatter outFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        publishDateInput = LocalDateTime.parse(PublishDateNews,inputFormatter);
         publishDateOutput = outFormatter.format(publishDateInput);


        titleNewsDetailTextView.setText(articlesItem.getTitle());
        descNewsDetailTextView.setText(articlesItem.getDescription());
        categoryNewsDetailTextView.setText(articlesItem.getCategory());
        authorDetailTextView.setText(" "+articlesItem.getAuthor());
        publishDetailTextView.setText(" "+publishDateOutput);
//        Picasso.with(imageViewDetail.getContext()).load(articlesItem.getUrlToImage()).noPlaceholder().error(R.drawable.icons8_no_image_100).into(imageViewDetail);
        Glide.with(imageViewDetail.getContext()).load(articlesItem.getUrlToImage()).error(R.drawable.icons8_no_image_100).placeholder(R.drawable.icons8_buffering_96).into(imageViewDetail);
        return root;
    }

    private void setBookmarkedState(){
        bookmarkImageButton.setImageResource(R.drawable.bookmark_2);
        bookmarkImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteBookmark(userId,articlesItem.getArticleID());
            }
        });
    }

    private void setNotBookmarkedState(){
        bookmarkImageButton.setImageResource(R.drawable.bookmark_1);
        bookmarkImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addBookmark(userId,articlesItem.getArticleID());
            }
        });
    }


    private void addBookmark(int userId , int articleId) {

        if(articlesItem.getArticleID()==0){

            new Thread(this::insertWait).start();
            new Thread(this::notifyGetArticleId).start();
            Log.d("NewsDetailFragment","News don't exist in room, add first");
            return;
        }else {
            newsRepository.room.insert(new UserArticleCrossRef(userId, articleId));
        }
        Toast.makeText(getContext(),"Add to Bookmark " +articlesItem.getTitle(),Toast.LENGTH_LONG).show();
        setBookmarkedState();
    }

    synchronized private void notifyGetArticleId() {
        nextArticleId = newsRepository.room.getArticleSize() + 1;
        notify();
    }
    synchronized private void insertWait() {
        try {
            wait();
            articlesItem.setArticleID(nextArticleId);
            newsRepository.room.insert(articlesItem);
            newsRepository.room.insert(new UserArticleCrossRef(userId, nextArticleId));
            isBookmarked = true;
            setBookmarkedState();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private void deleteBookmark(int userId,int articleId) {
        newsRepository.room.delete(new UserArticleCrossRef(userId,articleId));
        Toast.makeText(getContext(),"Remove from Bookmark " +articlesItem.getTitle(),Toast.LENGTH_LONG).show();
        setNotBookmarkedState();
    }


}
