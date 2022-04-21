package com.example.bcaxone_android_news.recycler;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bcaxone_android_news.BottomSheetFragment;
import com.example.bcaxone_android_news.R;
import com.example.bcaxone_android_news.repository.NewsRepository;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import model.ArticlesItem;

public class ItemDataAdapter extends RecyclerView.Adapter<ItemDataAdapter.ViewHolder>{
    //TODO: Delete
    String imageNewsURL;
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
        ViewHolder viewHolder = new ViewHolder(view);
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


        Picasso.with(holder.imageView.getContext()).load(imageNewsURL).into(holder.imageView);
        //TODO: default image

        holder.titleNewsTextView.setText(item.getTitle());
        holder.descNewsTextView.setText(item.getDescription());
        holder.publishDateTextView.setText(item.getPublishedAt());
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
        private TextView titleNewsTextView, publishDateTextView, descNewsTextView, threeDotsTextView;
        private ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
            titleNewsTextView = itemView.findViewById(R.id.itemdata_textview_news_title);
            publishDateTextView = itemView.findViewById(R.id.itemdata_textview_news_publish);
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
                Snackbar.make(view,"Title News : " + titleNewsTextView.getText(),Snackbar.LENGTH_SHORT).show();
            }
        }

    }

}
