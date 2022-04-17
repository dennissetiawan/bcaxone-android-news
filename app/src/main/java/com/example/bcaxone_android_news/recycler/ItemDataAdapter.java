package com.example.bcaxone_android_news.recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.bcaxone_android_news.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import model.ArticlesItem;

public class ItemDataAdapter extends RecyclerView.Adapter<ItemDataAdapter.ViewHolder>{

    private ArrayList<ArticlesItem> items;
    public ItemDataAdapter(ArrayList<ArticlesItem> items){
        this.items = items;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_data,parent,false);
        ViewHolder viewHolder  = new ViewHolder(view);
        return viewHolder;
    }

    public void onBindViewHolder(ViewHolder holder, int position){
        ArticlesItem item = items.get(position);

        holder.TitleNews.setText(item.getTitle());
        holder.DescNews.setText(item.getDescription());
        holder.PublishDate.setText(item.getPublishedAt());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView TitleNews, PublishDate, DescNews;

        public ViewHolder(View itemView) {
            super(itemView);
            TitleNews = itemView.findViewById(R.id.itemdata_textview_news_title);
            PublishDate = itemView.findViewById(R.id.itemdata_textview_news_publish);
            DescNews = itemView.findViewById(R.id.itemdata_textview_news_description);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                ArticlesItem item = items.get(position);
                Snackbar.make(view,"Title News : " + TitleNews.getText(),Snackbar.LENGTH_SHORT).show();
            }
        }
    }
}
