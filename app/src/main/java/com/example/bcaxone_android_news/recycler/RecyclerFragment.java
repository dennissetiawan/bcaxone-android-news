package com.example.bcaxone_android_news.recycler;



import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bcaxone_android_news.NewsViewModel;
import com.example.bcaxone_android_news.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

import model.ArticlesItem;

public class RecyclerFragment extends Fragment {
    private RecyclerView recyclerView;
    private ItemDataAdapter itemDataAdapter;
    private static ArrayList<ArticlesItem> articlesItems = new ArrayList<>();
    private static ArrayList<ArticlesItem> articlesItemsSource = new ArrayList<>();
    private boolean isFragmentAvail;



    public static RecyclerFragment newInstance(ArrayList<ArticlesItem> paramArticlesItems){

        RecyclerFragment  fragment = new RecyclerFragment();
        Bundle args = new Bundle();
        articlesItems = paramArticlesItems;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        articlesItemsSource = (ArrayList<ArticlesItem>) articlesItems.clone();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView);
        itemDataAdapter = new ItemDataAdapter(articlesItemsSource);
        recyclerView.setAdapter(itemDataAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        initSearchView(menu);
    }

    private void initSearchView(Menu menu) {
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView sv = (SearchView) menu.findItem(R.id.item_search).getActionView();
        sv.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        sv.setIconifiedByDefault(true);
        sv.setMaxWidth(Integer.MAX_VALUE);
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                notifyLists(s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if(TextUtils.isEmpty(s)){
                    resetData();
                    return true;
                }
                return false;
            }
        });
    }

    private void resetData() {
        articlesItemsSource.clear();
        articlesItemsSource.addAll((Collection<? extends ArticlesItem>) articlesItems.clone());
        itemDataAdapter.notifyDataSetChanged();

    }

    private void notifyLists(String query) {
       ArticlesItem articles_Item = doSearchItems(query);
       if(articles_Item != null){
           articlesItemsSource.clear();
           articlesItemsSource.addAll((Collection<? extends ArticlesItem>) articlesItems.clone());
           itemDataAdapter.notifyDataSetChanged();
       }
    }

    private ArticlesItem doSearchItems(String query) {
        ArticlesItem foundItem = null;
        for (ArticlesItem item:articlesItemsSource){
            if(item.getTitle().toLowerCase().contains(query.toLowerCase())){
                foundItem = item;
                break;
            }
        }
        return foundItem;
    }

}
