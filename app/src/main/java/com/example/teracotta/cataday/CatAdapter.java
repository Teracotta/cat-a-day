package com.example.teracotta.cataday;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import android.view.View;

import java.util.List;

public class CatAdapter extends RecyclerView.Adapter<CatAdapter.MyViewHolder> {

    private List<Cat> favouriteCatsList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.favourite_cat_title);
        }
    }


    public CatAdapter(List<Cat> moviesList) {
        this.favouriteCatsList = moviesList;
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
    }

    @Override
    public int getItemCount() {
        return favouriteCatsList.size();
    }
}