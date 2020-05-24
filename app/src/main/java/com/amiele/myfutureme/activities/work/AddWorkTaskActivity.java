package com.amiele.myfutureme.activities.work;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.amiele.myfutureme.R;

import java.util.ArrayList;

public class AddWorkTaskActivity extends AppCompatActivity {
    ArrayList<Task> taskList = new ArrayList<>();
    TaskAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_work_task);

        taskList.add(new Task("Task 1"));
        taskList.add(new Task("Task 2"));
        taskList.add(new Task("Task 3"));

        RecyclerView recyclerView = findViewById(R.id.task_recycler_view);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        adapter = new TaskAdapter(taskList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    public void onAddTaskClicked(View view)
    {
        taskList.add(new Task("new task"));
        adapter.notifyDataSetChanged();
    }
}
