package com.amiele.myfutureme.activities.work;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.amiele.myfutureme.R;

import java.util.ArrayList;

public class AddTagActivity extends AppCompatActivity {
    ArrayList<Tag> tagList = new ArrayList<>();
    TagAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tag);

        tagList.add(new Tag("Friend","#EEDBAA"));
        tagList.add(new Tag("LifeStyle","#BDEEAA"));
        tagList.add(new Tag("Job","#86EED1"));

        RecyclerView recyclerView = findViewById(R.id.tag_recycler_view);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        adapter = new TagAdapter(tagList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}
