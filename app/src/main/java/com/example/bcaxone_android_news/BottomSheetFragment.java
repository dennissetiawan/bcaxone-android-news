package com.example.bcaxone_android_news;


import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.bcaxone_android_news.bookmark.BookmarkFragment;
import com.example.bcaxone_android_news.repository.NewsRepository;
import com.example.bcaxone_android_news.room.UserArticleCrossRef;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Objects;

import model.ArticlesItem;

public class BottomSheetFragment extends BottomSheetDialogFragment {
    ArticlesItem item;
    NewsRepository newsRepository;
    Fragment fromFragment;
    public BottomSheetFragment(ArticlesItem item, Fragment fromFragment) {
        // Required empty public constructor
        this.item = item;
        this.fromFragment = fromFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        newsRepository = new NewsRepository(requireActivity().getApplication());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_modal_bottom_sheet_dialog, container, false);

        if(fromFragment instanceof BookmarkFragment){
            ImageView imageView = root.findViewById(R.id.bottom_sheet_bookmark_imageview);
            TextView textView = root.findViewById(R.id.bottom_sheet_bookmark_textview);
            textView.setText("Remove from Bookmark");
            imageView.setImageResource(android.R.drawable.star_on);
        }

        BottomSheetFragment fragment = this;
        root.findViewById(R.id.bottom_sheet_bookmark).setOnClickListener(view -> {
            int userId = SessionManagement.getInstance().getUserInSessionId(requireContext());
            if(fromFragment instanceof BookmarkFragment){
                deleteBookmark(userId,item.getArticleID());
            }else{
                addBookmark(userId,item.getArticleID());
            }
            fragment.dismiss();
        });
        return root;
    }

    private void addBookmark(int userId , int articleId) {
        newsRepository.room.insert(new UserArticleCrossRef(userId,articleId));
        Toast.makeText(getContext(),"Add to Bookmark " +item.getTitle(),Toast.LENGTH_LONG).show();
    }

    private void deleteBookmark(int userId,int articleId) {
        newsRepository.room.delete(new UserArticleCrossRef(userId,articleId));
        Toast.makeText(getContext(),"Delete Bookmark " +item.getTitle(),Toast.LENGTH_LONG).show();
        ((BookmarkFragment) fromFragment).reloadData();
    }
}
