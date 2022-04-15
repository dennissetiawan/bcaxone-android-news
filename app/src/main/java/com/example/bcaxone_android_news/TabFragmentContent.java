package com.example.bcaxone_android_news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class TabFragmentContent extends Fragment{

    private static final String ARG_PAGE = "ARG_PAGE";
    private int mPage;

    public static Fragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        TabFragmentContent tabFragmentContent = new TabFragmentContent();
        tabFragmentContent.setArguments(args);
        return tabFragmentContent;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.tab_content, container, false);
        TextView tv = root.findViewById(R.id.viewContent);
        tv.setText("Fragment #"+mPage);
        return root;
    }
}
