package com.example.bcaxone_android_news.tab;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bcaxone_android_news.NewsViewModel;
import com.example.bcaxone_android_news.R;
import com.example.bcaxone_android_news.recycler.ItemDataAdapter;

import java.util.ArrayList;
import java.util.List;

import model.ArticlesItem;
import retrofit.NewsAPIKeys;

public class TabFragmentContent extends Fragment{

    private RecyclerView recyclerView;
    private ItemDataAdapter itemDataAdapter;
    private static ArrayList<ArrayList<ArticlesItem>> cacheDataArrays = new ArrayList<>();
    private  ArrayList<ArticlesItem> articlesMasterData;
    private  ArrayList<ArticlesItem> articlesItemsSource;
    private boolean isFragmentAvail;
    private int pageNumber;
    private NewsViewModel newsViewModel;

    public static String pageAPIKeyCategories[] = new String[]{NewsAPIKeys.CATEGORY_BUSINESS, NewsAPIKeys.CATEGORY_ENTERTAINMENT, NewsAPIKeys.CATEGORY_GENERAL,
            NewsAPIKeys.CATEGORY_HEALTH, NewsAPIKeys.CATEGORY_SCIENCE, NewsAPIKeys.CATEGORY_SPORTS, NewsAPIKeys.CATEGORY_TECHNOLOGY};

    public TabFragmentContent(int position) {
        pageNumber = position;
    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);


        articlesMasterData = new ArrayList<>();
        articlesItemsSource = new ArrayList<>();

        Log.d("DSW","onCreate"+ pageAPIKeyCategories[pageNumber]);
    }


    private void generateDataWithoutRoom() {
        if(cacheDataArrays.isEmpty()){
            for (int i = 0; i < 7; i++) {
                cacheDataArrays.add(new ArrayList<>());
            }
        }

        if(cacheDataArrays.get(pageNumber).size()==0){
            newsViewModel.getArticleDataTopHeadlines(pageAPIKeyCategories[pageNumber],NewsAPIKeys.COUNTRY_US)
                    .observe(getViewLifecycleOwner(), articlesItemsFromAPI -> {

                        cacheDataArrays.set(pageNumber, (ArrayList<ArticlesItem>) articlesItemsFromAPI);

                        articlesMasterData.clear();
                        articlesMasterData.addAll(articlesItemsFromAPI);
                        resetData();
                    });
        }else{
            articlesMasterData.clear();
            articlesMasterData.addAll(cacheDataArrays.get(pageNumber));
            resetData();
        }
    }

    private void generateData() {
        Log.d("TabFragmentContent","generateData()"+ pageAPIKeyCategories[pageNumber]);
        newsViewModel.getFromRoomArticlesWithCategory(pageAPIKeyCategories[pageNumber]).
                observe(getViewLifecycleOwner(), articlesItems -> {
            Log.d("TabFragmentContent","newsViewModel.observer1"+ pageAPIKeyCategories[pageNumber]);
            if(articlesItems.isEmpty()){
                Log.d("TabFragmentContent","getDataFromAPI"+ pageAPIKeyCategories[pageNumber]);

                newsViewModel.getArticleDataTopHeadlines(pageAPIKeyCategories[pageNumber],NewsAPIKeys.COUNTRY_US)
                        .observe(getViewLifecycleOwner(), articlesItemsFromAPI -> {
                    Log.d("TabFragmentContent","newsViewModel.observer2"+ pageAPIKeyCategories[pageNumber]);
                    addAllToRoom(articlesItemsFromAPI);
                    resetData();
                });
            } else {
                Log.d("TabFragmentContent","getDataFromCache"+ pageAPIKeyCategories[pageNumber]+ "\n" +articlesItems.get(0).getCategory() +"\n" + articlesItems.size()+"\n" +articlesItems.get(0).getTitle());
                articlesMasterData.clear();
                articlesMasterData.addAll(articlesItems);
                resetData();
            }
        });
    }

    private void addAllToRoom(List<ArticlesItem> articlesItemsFromAPI) {
        ArrayList<ArticlesItem> articlesItemListWithCategory = new ArrayList<>();
        for (ArticlesItem a: articlesItemsFromAPI) {
            a.setCategory(pageAPIKeyCategories[pageNumber]);
            articlesItemListWithCategory.add(a);
        }
        Log.d("TabFragmentContent","addAllToRoom"+ pageAPIKeyCategories[pageNumber] + "\n" +articlesItemListWithCategory.get(0).getCategory() +"\n" + articlesItemListWithCategory.size()+"\n" +articlesItemListWithCategory.get(0).getTitle());
        newsViewModel.insertArticleListToDB(articlesItemListWithCategory);
        Log.d("TabFragmentContent","insertToDB Done");
        articlesMasterData.clear();
        articlesMasterData.addAll(articlesItemListWithCategory);
        Log.d("TabFragmentContent","addAllToRoom"+ pageAPIKeyCategories[pageNumber] +"DONE");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.tab_content, container, false);
        newsViewModel = new ViewModelProvider(requireActivity()).get(NewsViewModel.class);
        recyclerView = root.findViewById(R.id.recyclerView);
        itemDataAdapter = new ItemDataAdapter(articlesItemsSource);
        recyclerView.setAdapter(itemDataAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        generateDataWithoutRoom();
        generateData();
        return root;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        initSearchView(menu);
    }

    private void initSearchView(Menu menu) {
        SearchManager searchManager = (SearchManager) requireActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView sv = (SearchView) menu.findItem(R.id.item_search).getActionView();
        sv.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().getComponentName()));
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
        Log.d("TabFragmentContent","reset data" + articlesMasterData.size());
        articlesItemsSource.clear();
        articlesItemsSource.addAll(articlesMasterData);
        itemDataAdapter.notifyDataSetChanged();

    }

    private void notifyLists(String query) {
        ArticlesItem articles_Item = doSearchItems(query);
        if(articles_Item != null){
            articlesItemsSource.clear();
            articlesItemsSource.addAll(articlesMasterData);
            itemDataAdapter.notifyDataSetChanged();
        }
        else{
//            final Snackbar snackbar = Snackbar.make(getView().findViewById(R.id.recyclerView),"Data tidak dapat ditemukan",Snackbar.LENGTH_INDEFINITE);
//            snackbar.setAction("Ok", new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    snackbar.dismiss();
//                }
//            });
//            snackbar.show();
//            articlesItemsSource.clear();
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
