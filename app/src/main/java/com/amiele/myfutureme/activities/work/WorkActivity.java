package com.amiele.myfutureme.activities.work;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.amiele.myfutureme.R;

import java.util.ArrayList;

public class WorkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);

        ArrayList<WorkTask> workTaskList = new ArrayList<>();
        ArrayList<Task> tasklist = new ArrayList<>();
        tasklist.add(new Task("abc"));
        tasklist.add(new Task("abcc"));
        tasklist.add(new Task("abccc"));

        workTaskList.add(new WorkTask("Name 1"," descripption 1 "));
        workTaskList.add(new WorkTask("Name 11"," descripption 11 "));
        workTaskList.add(new WorkTask("Name 111"," descripption 111 "));
        workTaskList.add(new WorkTask("Name 1111"," descripption 1111 "));
        workTaskList.get(0).addTaskList(tasklist);
        workTaskList.get(1).addTaskList(tasklist);
        workTaskList.get(2).addTaskList(tasklist);
        workTaskList.get(3).addTaskList(tasklist);


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
//        ProgressBar progressBar = findViewById(R.id.progressBar1);
//        progressBar.setSecondaryProgress(50);
//        progressBar.setProgress(100);

    }
}
