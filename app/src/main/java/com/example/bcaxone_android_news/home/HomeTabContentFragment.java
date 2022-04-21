package com.example.bcaxone_android_news.home;

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
import com.example.bcaxone_android_news.adapter.ItemDataAdapter;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import model.ArticlesItem;
import retrofit.NewsAPIKeys;

public class HomeTabContentFragment extends Fragment{

    private RecyclerView recyclerView;
    private ItemDataAdapter itemDataAdapter;
    private static ArrayList<ArrayList<ArticlesItem>> cacheDataArrays = new ArrayList<>();
    private  ArrayList<ArticlesItem> articlesMasterData;
    private  ArrayList<ArticlesItem> articlesItemsSource;
    private int pageNumber;
    private NewsViewModel newsViewModel;

    public static String pageAPIKeyCategories[] = new String[]{NewsAPIKeys.CATEGORY_BUSINESS, NewsAPIKeys.CATEGORY_ENTERTAINMENT, NewsAPIKeys.CATEGORY_GENERAL,
            NewsAPIKeys.CATEGORY_HEALTH, NewsAPIKeys.CATEGORY_SCIENCE, NewsAPIKeys.CATEGORY_SPORTS, NewsAPIKeys.CATEGORY_TECHNOLOGY};

    public HomeTabContentFragment(int position) {
        pageNumber = position;
    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        articlesMasterData = new ArrayList<>();
        articlesItemsSource = new ArrayList<>();

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
        View root = inflater.inflate(R.layout.item_list_recycle_content, container, false);
        newsViewModel = new ViewModelProvider(requireActivity()).get(NewsViewModel.class);
        recyclerView = root.findViewById(R.id.recyclerView);
        itemDataAdapter = new ItemDataAdapter(articlesItemsSource,this);
        recyclerView.setAdapter(itemDataAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        generateDataWithoutRoom();
        generateData();
        return root;
    }


    private void resetData() {
        Log.d("TabFragmentContent","reset data" + articlesMasterData.size());
        articlesItemsSource.clear();
        articlesItemsSource.addAll(articlesMasterData);
        itemDataAdapter.notifyDataSetChanged();

    }



    /*************************************************/
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


}
