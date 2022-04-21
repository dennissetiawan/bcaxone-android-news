package com.example.bcaxone_android_news.search;

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
import com.example.bcaxone_android_news.SessionManagement;
import com.example.bcaxone_android_news.adapter.ItemDataAdapter;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import model.ArticlesItem;

public class SearchFragment extends Fragment implements SearchView.OnQueryTextListener{
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
//        reloadData();
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

        newsViewModel.getFromRoomArticlesWithTitleContains(query).observe(getViewLifecycleOwner(), new Observer<List<ArticlesItem>>() {
            @Override
            public void onChanged(List<ArticlesItem> articlesItems) {
                articlesItemsSource.clear();
                if(!articlesItems.isEmpty()){
                    articlesItemsSource.addAll(articlesItems);
                }else{
                    showErrorToast();
                }
                hideKeyboard();
                itemDataAdapter.notifyDataSetChanged();
            }
        });

    }

    private void showErrorToast() {
        final Snackbar snackbar = Snackbar.make(getView().findViewById(R.id.recyclerView),"No news found with that title",Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("Ok", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snackbar.dismiss();
            }
        });
        snackbar.show();
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


}
