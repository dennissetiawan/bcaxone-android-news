package com.example.bcaxone_android_news;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class FragmentPageAdapter extends FragmentStateAdapter{
    private final int COUNT = 7;


    public FragmentPageAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return TabFragmentContent.newInstance(position);
    }

    @Override
    public int getItemCount() {
        return COUNT;
    }
}
