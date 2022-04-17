package com.example.bcaxone_android_news.tab;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bcaxone_android_news.R;
import com.example.bcaxone_android_news.recycler.ItemDataAdapter;
import com.example.bcaxone_android_news.recycler.RecyclerFragment;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collection;

import model.ArticlesItem;

public class TabFragmentContent extends Fragment{

    private RecyclerView recyclerView;
    private ItemDataAdapter itemDataAdapter;
    private static ArrayList<ArticlesItem> articlesItems = new ArrayList<>();
    private static ArrayList<ArticlesItem> articlesItemsSource = new ArrayList<>();


    public static TabFragmentContent newInstance(ArrayList<ArticlesItem> paramArticlesItems){

        TabFragmentContent  fragment = new TabFragmentContent();
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.tab_content, container, false);

        recyclerView = root.findViewById(R.id.recyclerView);
        itemDataAdapter = new ItemDataAdapter(articlesItemsSource);
        recyclerView.setAdapter(itemDataAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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
        else{
            final Snackbar snackbar = Snackbar.make(getView().findViewById(R.id.recyclerView),"Data tidak dapat ditemukan",Snackbar.LENGTH_INDEFINITE);
            snackbar.setAction("Ok", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    snackbar.dismiss();
                }
            });
            snackbar.show();
            articlesItemsSource.clear();
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

//    private static final String ARG_PAGE = "ARG_PAGE";
//    public String tabTitles[] = new String[]{"Business News", "Entertainment News", "General News", "Health News", "Science News", "Sports News", "Technology News"};
//    private int mPage;
//
//    public static Fragment newInstance(int page) {
//        Bundle args = new Bundle();
//        args.putInt(ARG_PAGE, page);
//        TabFragmentContent tabFragmentContent = new TabFragmentContent();
//        tabFragmentContent.setArguments(args);
//        return tabFragmentContent;
//    }
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        mPage = getArguments().getInt(ARG_PAGE);
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View root = inflater.inflate(R.layout.tab_content, container, false);
//
//        return root;
//
//    }
}
