package com.example.teracotta.cataday;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FavouritesCatPage extends AppCompatActivity {
    private List<Cat> catsList = new ArrayList<>();
    private RecyclerView recyclerView;
    private CatAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favourite_cats);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new CatAdapter(catsList, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        prepareCatData();
    }

    private void prepareCatData() {
        Cat sampleCat = new Cat("test1", "test2", "test3", "test4", "https://b.thumbs.redditmedia.com/_6M___Gd9z3H4e8bef80VZI2ojQvUNBYqgH_1kMDqeA.jpg");
        catsList.add(sampleCat);

        mAdapter.notifyDataSetChanged();
    }
}
