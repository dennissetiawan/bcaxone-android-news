package com.example.bcaxone_android_news.bookmark;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bcaxone_android_news.NewsViewModel;
import com.example.bcaxone_android_news.R;
import com.example.bcaxone_android_news.recycler.ItemDataAdapter;

import java.util.ArrayList;

import model.ArticlesItem;

public class BookmarkFragment extends Fragment {

    NewsViewModel newsViewModel;
    RecyclerView recyclerView;
    ItemDataAdapter itemDataAdapter;

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
        itemDataAdapter = new ItemDataAdapter(articlesItemsSource);
        recyclerView.setAdapter(itemDataAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        getSavedArticlesData();
        return root;
    }


    private void getSavedArticlesData() {
        newsViewModel.getFromRoomUserSavedArticles(1).observe(getViewLifecycleOwner(), userWithArticles -> {
            Log.d("BookmarkFragment", "userwitharticles called");
            if(userWithArticles == null) {
                Log.d("BookmarkFragment", "userwitharticles not found");
                return;
            }
            Log.d("BookmarkFragment", String.valueOf(userWithArticles.getArticlesItemList().size()));
            articlesItemsSource.clear();
            articlesItemsSource.addAll(userWithArticles.getArticlesItemList());
            itemDataAdapter.notifyDataSetChanged();
        });
    }

}
