package com.example.bcaxone_android_news.discover;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.bcaxone_android_news.NewsViewModel;
import com.example.bcaxone_android_news.R;
import com.example.bcaxone_android_news.adapter.ItemDataAdapter;

import java.util.ArrayList;
import java.util.List;

import model.ArticlesItem;

public class DiscoverFragment extends Fragment {
    NewsViewModel newsViewModel;
    RecyclerView recyclerView;
    ItemDataAdapter itemDataAdapter;
    TextView errorTextView;
    EditText searchEditText;
    private ArrayList<ArticlesItem> articlesItemsSource = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_discover, container, false);

        newsViewModel = new ViewModelProvider(requireActivity()).get(NewsViewModel.class);
        recyclerView = root.findViewById(R.id.discover_recyclerview);
        errorTextView = root.findViewById(R.id.discover_textview_item_list_recycle_error);
        searchEditText = root.findViewById(R.id.discover_search_edittext);

        searchEditText.setOnKeyListener((v, keyCode, event) -> {

            if(searchEditText.getText().toString().isEmpty()) {
                Log.d("DiscoverFragment","reset data");
                resetData();
                return false;
            }

            // If the event is a key-down event on the "enter" button
            if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                    (keyCode == KeyEvent.KEYCODE_ENTER)) {
                // Perform action on key press
                Log.d("DiscoverFragment","Enter pressed");
                doSearchItems(searchEditText.getText().toString());
                return true;
            }
            return false;
        });


        itemDataAdapter = new ItemDataAdapter(articlesItemsSource,this);
        recyclerView.setAdapter(itemDataAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return root;
    }


    private void doSearchItems(String query) {
        errorTextView.setVisibility(View.INVISIBLE);
        newsViewModel.getArticleDataWithQuery(query).observe(getViewLifecycleOwner(), articlesItems -> {
            articlesItemsSource.clear();
            if(!articlesItems.isEmpty()){
                articlesItemsSource.addAll(articlesItems);
            }else{
                showErrorNotFound(query);
            }
            hideKeyboard();
            itemDataAdapter.notifyDataSetChanged();
        });

    }

    private void showErrorNotFound(String query) {
        Log.d("SearchFragment", "userwitharticles not found");
        errorTextView.setText(String.format("%s %s", getString(R.string.no_article_with_title), query));
        errorTextView.setVisibility(View.VISIBLE);
    }


    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
    }


    private void resetData() {
        articlesItemsSource.clear();
        itemDataAdapter.notifyDataSetChanged();
    }
}