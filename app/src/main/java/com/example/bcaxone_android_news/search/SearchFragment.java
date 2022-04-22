package com.example.bcaxone_android_news.search;

import android.content.Context;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bcaxone_android_news.NewsViewModel;
import com.example.bcaxone_android_news.R;

import com.example.bcaxone_android_news.adapter.ItemDataAdapter;
import com.example.bcaxone_android_news.home.HomeFragment;


import java.util.ArrayList;
import java.util.List;

import model.ArticlesItem;

public class SearchFragment extends Fragment implements SearchView.OnQueryTextListener, SearchView.OnCloseListener{
    NewsViewModel newsViewModel;
    RecyclerView recyclerView;
    ItemDataAdapter itemDataAdapter;
    TextView errorTextView;
    private ArrayList<ArticlesItem> articlesItemsSource = new ArrayList<>();


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
        return root;
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
    }

    private void resetData() {
        articlesItemsSource.clear();
        itemDataAdapter.notifyDataSetChanged();
    }


    private void doSearchItems(String query) {
        errorTextView.setVisibility(View.INVISIBLE);
        newsViewModel.getFromRoomArticlesWithTitleContains(query).observe(getViewLifecycleOwner(), new Observer<List<ArticlesItem>>() {
            @Override
            public void onChanged(List<ArticlesItem> articlesItems) {
                articlesItemsSource.clear();
                if(!articlesItems.isEmpty()){
                    articlesItemsSource.addAll(articlesItems);
                }else{
                    showErrorNotFound(query);
                }
                hideKeyboard();
                itemDataAdapter.notifyDataSetChanged();
            }
        });

    }

    private void showErrorNotFound(String query) {
        Log.d("SearchFragment", "userwitharticles not found");
        errorTextView.setText(String.format("%s %s", getString(R.string.no_article_with_title), query));
        errorTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        Log.d("SearchFragment","onQuerySubmit");
        doSearchItems(s);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        if(s.isEmpty()){
            resetData();
        }
        return true;
    }


    @Override
    public boolean onClose() {
        Log.d("SearchFragment","search close");
        hideKeyboard();
        getParentFragmentManager().beginTransaction().replace(R.id.main_container, new HomeFragment()).commitNow();
        return false;

    }
}
