package com.example.bcaxone_android_news.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.example.bcaxone_android_news.R;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

public class HomeFragment extends Fragment {

    public String[] tabTitles = new String[]{"Business", "Entertainment", "General", "Health", "Science", "Sports", "Technology"};



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.tab_host, container, false);
        TabLayout tabLayout = root.findViewById(R.id.tab_layout_categories);

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
        setContentFragment(Objects.requireNonNull(tabLayout.getTabAt(0)));

        return root;
    }

    private void setContentFragment(TabLayout.Tab tab) {
        FragmentManager fragmentManager = getParentFragmentManager();
        HomeTabContentFragment fragmentContent = new HomeTabContentFragment(tab.getPosition());
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
