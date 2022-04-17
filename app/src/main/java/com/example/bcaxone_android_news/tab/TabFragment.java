package com.example.bcaxone_android_news.tab;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.bcaxone_android_news.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class TabFragment extends Fragment {
    private View root;
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private TabFragmentPageAdapter pagerAdapter;
    public String tabTitles[] = new String[]{"Business", "Entertainment", "General", "Health", "Science", "Sports", "Technology"};

    public static TabFragment newInstance() {
        return new TabFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.tab_host, container, false);
        tabLayout = root.findViewById(R.id.tab_layout_categories);
        viewPager2 = root.findViewById(R.id.tab_viewpager);
        pagerAdapter = new TabFragmentPageAdapter(this);
        viewPager2.setAdapter(pagerAdapter);
        new TabLayoutMediator(tabLayout, viewPager2,
                (tab, position) -> tab.setText(tabTitles[position])
        ).attach();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

}
