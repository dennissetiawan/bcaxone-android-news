package com.example.bcaxone_android_news.tab;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

import model.ArticlesItem;

public class TabFragmentPageAdapter extends FragmentStateAdapter{
    private final int COUNT = 7;


    public TabFragmentPageAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return new TabFragmentContent(position);
    }

    @Override
    public int getItemCount() {
        return COUNT;
    }
}
