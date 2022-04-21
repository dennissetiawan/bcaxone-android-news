package com.example.bcaxone_android_news;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bcaxone_android_news.repository.NewsRepository;
import com.example.bcaxone_android_news.room.UserArticleCrossRef;
import com.squareup.picasso.Picasso;

import model.ArticlesItem;

public class NewsDetailFragment extends Fragment {
    ArticlesItem articlesItem;
    private TextView  descNewsDetailTextView, titleNewsDetailTextView, categoryNewsDetailTextView,authorDetailTextView,publishDetailTextView;
    private ImageView imageViewDetail;
    private ImageButton bookmarkImageButton;
    private NewsRepository newsRepository;
    public NewsDetailFragment(ArticlesItem articlesItem) {
        this.articlesItem = articlesItem;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        newsRepository = new NewsRepository(requireActivity().getApplication());


//        getActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
//            @Override
//            public void handleOnBackPressed() {
//
//                Log.d("NewsDetailFragment","press back");
//                getActivity().findViewById(R.id.bottom_navigation).setVisibility(View.VISIBLE);
//                getActivity().onBackPressed();
////                getActivity().getSupportFragmentManager().popBackStack();
//
//            }
//        });


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_news_detail, container, false);

        getActivity().findViewById(R.id.bottom_navigation).setVisibility(View.INVISIBLE);
        Log.d("NewsDetailFragment","setbottomnav invisible");

        titleNewsDetailTextView = root.findViewById(R.id.fragment_news_detail_textview_title);
        descNewsDetailTextView = root.findViewById(R.id.fragment_news_detail_textview_description);
        categoryNewsDetailTextView = root.findViewById(R.id.fragment_news_detail_textview_category);
        authorDetailTextView = root.findViewById(R.id.fragment_news_detail_textview_author);
        publishDetailTextView = root.findViewById(R.id.fragment_news_detail_textview_publish);
        imageViewDetail = root.findViewById(R.id.fragment_news_detail_imageview);
        bookmarkImageButton = root.findViewById(R.id.fragment_news_detail_imagebutton_bookmark);
        bookmarkImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        titleNewsDetailTextView.setText(articlesItem.getTitle());
        descNewsDetailTextView.setText(articlesItem.getDescription());
        categoryNewsDetailTextView.setText(articlesItem.getCategory());
        authorDetailTextView.setText(" "+articlesItem.getAuthor());
        publishDetailTextView.setText(" "+articlesItem.getPublishedAt());
        Picasso.with(imageViewDetail.getContext()).load(articlesItem.getUrlToImage()).noPlaceholder().error(R.drawable.icons8_no_image_100).into(imageViewDetail);

        return root;
    }

    private void addBookmark(int userId , int articleId) {
        newsRepository.room.insert(new UserArticleCrossRef(userId,articleId));
//        Toast.makeText(getContext(),"Add to Bookmark " +item.getTitle(),Toast.LENGTH_LONG).show();
    }


}
