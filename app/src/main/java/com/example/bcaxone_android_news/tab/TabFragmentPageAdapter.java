package com.example.bcaxone_android_news.tab;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

import model.ArticlesItem;

public class TabFragmentPageAdapter extends FragmentStateAdapter{
    private final int COUNT = 7;
    private ArrayList<ArrayList<ArticlesItem>> categoryArticles;

    public TabFragmentPageAdapter(@NonNull Fragment fragment, ArrayList<ArrayList<ArticlesItem>> categoryArticles) {
        super(fragment);
        this.categoryArticles = categoryArticles;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return TabFragmentContent.newInstance(categoryArticles.get(position));

    }

    @Override
    public int getItemCount() {
        return COUNT;
    }
}
