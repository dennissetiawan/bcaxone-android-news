package com.example.bcaxone_android_news.adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bcaxone_android_news.BottomSheetFragment;

import com.example.bcaxone_android_news.MainActivity;
import com.example.bcaxone_android_news.NewsDetailFragment;

import com.example.bcaxone_android_news.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import model.ArticlesItem;

public class ItemDataAdapter extends RecyclerView.Adapter<ItemDataAdapter.ViewHolder>{
    String imageNewsURL,newsDesc;
    private ArrayList<ArticlesItem> items;
    Context context;
    Fragment fromFragment;

    public ItemDataAdapter(ArrayList<ArticlesItem> items,Fragment fromFragment){
        this.items = items;
        this.fromFragment = fromFragment;

    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @NonNull
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        context = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_data,parent,false);
        ViewHolder viewHolder = new ViewHolder(view,viewType);
        viewHolder.imageView.setImageBitmap(null);
        return viewHolder;
    }

    public void showBottomSheetDialog(ArticlesItem item) {
        BottomSheetFragment dialog = new BottomSheetFragment(item,fromFragment);
        dialog.show(((AppCompatActivity) context).getSupportFragmentManager(),dialog.getTag());
    }

    public void onBindViewHolder(ViewHolder holder, int position){
        ArticlesItem item = items.get(position);
        imageNewsURL = item.getUrlToImage();
        String descNews;
        descNews = item.getDescription();

        holder.titleNewsTextView.setText(item.getTitle());
        holder.descNewsTextView.setText(descNews);
        Picasso.with(holder.imageView.getContext()).load(imageNewsURL).error(R.drawable.icons8_no_image_100).placeholder(R.drawable.icons8_buffering_96).into(holder.imageView);
        holder.threeDotsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheetDialog(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView titleNewsTextView, descNewsTextView, descNewsDetail, titleNewsDetail, categoryNewsDetail,authorDetail,publishDetail, threeDotsTextView;
        private ImageView imageView, imageViewDetail;

        public ViewHolder(View itemView, int viewType) {
            super(itemView);
            titleNewsTextView = itemView.findViewById(R.id.itemdata_textview_news_title);
            descNewsTextView = itemView.findViewById(R.id.itemdata_textview_news_description);
            imageView = itemView.findViewById(R.id.itemdata_image_news);
            threeDotsTextView = itemView.findViewById(R.id.item_data_more);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                ArticlesItem item = items.get(position);
                NewsDetailFragment newsDetailActivity = new NewsDetailFragment(item);
                FragmentManager fragmentManager = fromFragment.getParentFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.main_container, newsDetailActivity)
//                        .addToBackStack(null)
                        .commitNow();
                MainActivity.isHome = false;
            }
        }
    }



}
