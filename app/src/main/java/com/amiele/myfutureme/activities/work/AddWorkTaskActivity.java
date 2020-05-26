package com.amiele.myfutureme.activities.work;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.amiele.myfutureme.R;

import java.util.ArrayList;

import petrov.kristiyan.colorpicker.ColorPicker;

public class AddWorkTaskActivity extends AppCompatActivity {
    ArrayList<Task> taskList = new ArrayList<>();
    ArrayList<Tag> tagList = new ArrayList<>();
    TextView tvTagName;
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

        adapter.setOnItemClickListener(new TaskAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Task task) {
                // adapter.notifyItemChanged;
                //  Log.i("info", String.valueOf(task.getDescription()));
                //Toast.makeText(, task.getDescription(),Toast.LENGTH_SHORT).show();
                DisplayToast(task.getName());
            }
        });




    }

    public void onAddTagBtnClicked(View view)
    {
        Intent addTagActivity = new Intent(this, AddTagActivity.class);
        startActivity(addTagActivity);
    }


    private  void DisplayToast(String text)
    {
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
    }
    public void onAddTaskClicked(View view)
    {
        taskList.add(new Task("new task"));
        adapter.notifyDataSetChanged();
    }


//    public void onCancelClicked(View view)
//    {
//        Intent workActivity = new Intent(this, WorkActivity.class);
//        startActivity(workActivity);
//        finish();
//    }
//
//    public void onAddGoalClicked(View view)
//    {
//        Intent workActivity = new Intent(this, WorkActivity.class);
//        startActivity(workActivity);
//        finish();
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_goal_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_done:
                Toast.makeText(this, "Done selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_cancel:
                Toast.makeText(this, "Cancel selected", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
