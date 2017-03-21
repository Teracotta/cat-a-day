package com.example.teracotta.cataday;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import android.view.View;

import com.bumptech.glide.Glide;

import java.util.List;

public class CatAdapter extends RecyclerView.Adapter<CatAdapter.MyViewHolder> {

    private List<Cat> favouriteCatsList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, author;
        public ImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.favourite_cat_title);
            author = (TextView) view.findViewById(R.id.favourite_cat_author);
            thumbnail = (ImageView) view.findViewById(R.id.favourite_cat_thumbnail);
        }
    }


    public CatAdapter(List<Cat> favouriteCatsList, Context context) {
        this.favouriteCatsList = favouriteCatsList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.favourite_cat_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Cat favouriteCat = favouriteCatsList.get(position);
        holder.title.setText(favouriteCat.getSubmissionTitle());
        holder.author.setText(favouriteCat.getSubmissionAuthor());
        Glide.with(context).load(favouriteCat.getThumbnailURL()).into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return favouriteCatsList.size();
    }
}