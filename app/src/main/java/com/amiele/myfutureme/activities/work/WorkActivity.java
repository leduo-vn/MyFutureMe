package com.amiele.myfutureme.activities.work;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.amiele.myfutureme.R;

import java.util.ArrayList;

public class WorkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);

        ArrayList<WorkTask> workTaskList = new ArrayList<>();
        ArrayList<Task> tasklist = new ArrayList<>();
        tasklist.add(new Task("Task 1"));
        tasklist.add(new Task("Task 2"));
        tasklist.add(new Task("Task 3"));

        workTaskList.add(new WorkTask("Work Task Name","Task description"));
        workTaskList.add(new WorkTask("Work Task Name 1","Task description 1"));
        workTaskList.add(new WorkTask("Work Task Name 2","Task description 2"));
        workTaskList.add(new WorkTask("Work Task Name 3","Task description 3"));
        workTaskList.get(0).setTaskList(tasklist);
        workTaskList.get(1).setTaskList(tasklist);
        workTaskList.get(2).setTaskList(tasklist);
        workTaskList.get(3).setTaskList(tasklist);


        RecyclerView recyclerView = findViewById(R.id.work_task_recycler_view);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        final WorkTaskAdapter adapter = new WorkTaskAdapter(this,workTaskList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        // Set onClick for Adapter
        // When item clicked, open the fragment of chosen category
        adapter.setOnItemClickListener(new WorkTaskAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                adapter.notifyItemChanged(position);
                Log.i("info", String.valueOf(position));
            }
        });


    }
}
