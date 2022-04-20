package com.example.bcaxone_android_news.recycler;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bcaxone_android_news.R;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import model.ArticlesItem;

public class ItemDataAdapter extends RecyclerView.Adapter<ItemDataAdapter.ViewHolder>{
    String imageNewsURL,newsDesc;
    private ArrayList<ArticlesItem> items;
    public ItemDataAdapter(ArrayList<ArticlesItem> items){
        this.items = items;

    }

    @NonNull
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_data,parent,false);
        ViewHolder viewHolder = new ViewHolder(view,viewType);
        viewHolder.imageView.setImageBitmap(null);
        return viewHolder;
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        ArticlesItem item = items.get(position);
        imageNewsURL = item.getUrlToImage();
        String descNews;
        descNews = item.getDescription();

        holder.titleNewsTextView.setText(item.getTitle());
        holder.descNewsTextView.setText(descNews);
        Picasso.with(holder.imageView.getContext()).load(imageNewsURL).error(R.drawable.icons8_no_image_100).placeholder(R.drawable.icons8_buffering_96).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView titleNewsTextView, descNewsTextView, descNewsDetail, titleNewsDetail, categoryNewsDetail,authorDetail,publishDetail;
        ImageView imageView, imageViewDetail;

        public ViewHolder(View itemView, int viewType) {
            super(itemView);
                titleNewsTextView = itemView.findViewById(R.id.itemdata_textview_news_title);
                descNewsTextView = itemView.findViewById(R.id.itemdata_textview_news_description);
                imageView = itemView.findViewById(R.id.itemdata_image_news);

//                //Detail News
//                titleNewsDetail = itemView.findViewById(R.id.itemdata_textview_news_title_detail);
//                descNewsDetail = itemView.findViewById(R.id.itemdata_textview_news_description_detail);
//                categoryNewsDetail = itemView.findViewById(R.id.itemdata_textview_news_category_detail);
//                authorDetail = itemView.findViewById(R.id.itemdata_textview_news_author_detail);
//                publishDetail = itemView.findViewById(R.id.itemdata_textview_news_publish_detail);
//                imageViewDetail = itemView.findViewById(R.id.itemdata_image_news_detail);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                ArticlesItem item = items.get(position);

//                titleNewsDetail.setText(item.getTitle());
//                descNewsDetail.setText(item.getDescription());
//                categoryNewsDetail.setText(item.getCategory());
//                authorDetail.setText(item.getAuthor());
//                publishDetail.setText(item.getPublishedAt());
//                Picasso.with(imageViewDetail.getContext()).load(imageNewsURL).noPlaceholder().error(R.drawable.icons8_no_image_100).into(imageViewDetail);

            }
        }
    }



}
