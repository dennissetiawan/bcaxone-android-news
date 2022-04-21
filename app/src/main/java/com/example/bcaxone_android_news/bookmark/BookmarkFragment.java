package com.example.bcaxone_android_news.bookmark;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bcaxone_android_news.NewsViewModel;
import com.example.bcaxone_android_news.R;
import com.example.bcaxone_android_news.SessionManagement;
import com.example.bcaxone_android_news.recycler.ItemDataAdapter;

import java.util.ArrayList;

import model.ArticlesItem;

public class BookmarkFragment extends Fragment {

    NewsViewModel newsViewModel;
    RecyclerView recyclerView;
    ItemDataAdapter itemDataAdapter;
    TextView errorTextView;
    private  ArrayList<ArticlesItem> articlesItemsSource = new ArrayList<>();

    public static BookmarkFragment newInstance() {
        BookmarkFragment bookmarkFragment = new BookmarkFragment();
        return bookmarkFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.item_list_recycle_content, container, false);
        newsViewModel = new ViewModelProvider(requireActivity()).get(NewsViewModel.class);
        recyclerView = root.findViewById(R.id.recyclerView);
        errorTextView = root.findViewById(R.id.textview_item_list_recycle_error);
        itemDataAdapter = new ItemDataAdapter(articlesItemsSource,this);
        recyclerView.setAdapter(itemDataAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        reloadData();
        return root;
    }


     public void reloadData() {
        int userId = SessionManagement.getInstance().getUserInSessionId(getContext());
        Log.d("BookmarkFragment", "get shared article for user id : "+userId);
        newsViewModel.getFromRoomUserSavedArticles(userId).observe(getViewLifecycleOwner(), userWithArticles -> {
            Log.d("BookmarkFragment", "userwitharticles called");
            if(userWithArticles == null || userWithArticles.getArticlesItemList().isEmpty()) {
                Log.d("BookmarkFragment", "userwitharticles not found");
                errorTextView.setText(R.string.no_bookmark);
                errorTextView.setVisibility(View.VISIBLE);
            }
            Log.d("BookmarkFragment", String.valueOf(userWithArticles.getArticlesItemList().size()));
            articlesItemsSource.clear();
            articlesItemsSource.addAll(userWithArticles.getArticlesItemList());
            itemDataAdapter.notifyDataSetChanged();
        });
    }

}
