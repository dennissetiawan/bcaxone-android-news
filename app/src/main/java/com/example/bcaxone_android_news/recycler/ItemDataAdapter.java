package com.example.bcaxone_android_news.recycler;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.bcaxone_android_news.R;
import com.google.android.material.snackbar.Snackbar;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import model.ArticlesItem;

public class ItemDataAdapter extends RecyclerView.Adapter<ItemDataAdapter.ViewHolder>{
    String ImageNewsURL;
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
        ImageNewsURL = item.getUrlToImage();
        LoadImageFromWebOperations(ImageNewsURL);

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
        ImageView ImageNewsView;

        public ViewHolder(View itemView) {
            super(itemView);
            TitleNews = itemView.findViewById(R.id.itemdata_textview_news_title);
            PublishDate = itemView.findViewById(R.id.itemdata_textview_news_publish);
            DescNews = itemView.findViewById(R.id.itemdata_textview_news_description);
            ImageNewsView = itemView.findViewById(R.id.itemdata_image_news);
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

    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            return null;
        }
}}
