package com.example.bcaxone_android_news.tab;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.bcaxone_android_news.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

import model.ArticlesItem;

public class TabFragment extends Fragment {
    private View root;
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private TabFragmentPageAdapter pagerAdapter;
    public String tabTitles[] = new String[]{"Business", "Entertainment", "General", "Health", "Science", "Sports", "Technology"};


    public static TabFragment newInstance() {
        TabFragment tabFragment = new TabFragment();
        return tabFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.tab_host, container, false);
        tabLayout = root.findViewById(R.id.tab_layout_categories);

        for (String title: tabTitles) {
            tabLayout.addTab(tabLayout.newTab().setText(title));
        }
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                setContentFragment(tab);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        //set initial tab
        tabLayout.selectTab(tabLayout.getTabAt(0));
        setContentFragment(tabLayout.getTabAt(0));

        return root;
    }

    private void setContentFragment(TabLayout.Tab tab) {
        FragmentManager fragmentManager = getParentFragmentManager();
        TabFragmentContent fragmentContent = new TabFragmentContent(tab.getPosition());
        fragmentManager.beginTransaction()
                .replace(R.id.tab_content_container,fragmentContent)
                .setReorderingAllowed(true)
                .commit();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

}
