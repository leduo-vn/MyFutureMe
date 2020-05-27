package com.amiele.myfutureme.activities.work;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amiele.myfutureme.R;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import petrov.kristiyan.colorpicker.ColorPicker;

public class AddWorkTaskActivity extends AppCompatActivity {
    ArrayList<Task> taskList = new ArrayList<>();
    ArrayList<Tag> tagList = new ArrayList<>();
    DatePickerDialog.OnDateSetListener mDateSetListener;
    TextView tvTagName;
    TaskAdapter adapter;
    TagAdapter tagAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_work_task);

        tagList.add(new Tag("Friend",Color.parseColor("#EEDBAA")));
        tagList.add(new Tag("LifeStyle",Color.parseColor("#BDEEAA")));
        tagList.add(new Tag("Job",Color.parseColor("#86EED1")));

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


        ImageButton datePickerBtn = findViewById(R.id.action_date_picker);
        datePickerBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        AddWorkTaskActivity.this, android.R.style.Theme_DeviceDefault_Dialog_NoActionBar_MinWidth, mDateSetListener,year,month,day);
                //   datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String text= Integer.toString(year)+"-"+ Integer.toString(month+1)+"-"+Integer.toString(dayOfMonth);
                DisplayToast(text);
                Date date = ConvertFromStringToDate(text);
                EditText et_date = findViewById(R.id.et_goal_due_date);
                String stDate = getTheDOW(date)+", " + getTheDate(date);
                et_date.setText(stDate);

            }
        };

//
//        RecyclerView tagRecyclerView = findViewById(R.id.tag_recycler_view);
//        recyclerView.setHasFixedSize(true);

//        FlexboxLayoutManager tagLayoutManager = new FlexboxLayoutManager(this);
//        tagLayoutManager.setFlexDirection(FlexDirection.ROW);
//        tagLayoutManager.setJustifyContent(JustifyContent.FLEX_START);
//        tagLayoutManager.setFlexWrap(FlexWrap.WRAP);
//        tagAdapter = new TagAdapter(tagList);
//        tagRecyclerView.setLayoutManager(tagLayoutManager);
//        tagRecyclerView.setAdapter(tagAdapter);

    }


    private String getTheDOW(Date date)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EE");
        return dateFormat.format(date);
    }

    private String getTheDate(Date date)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yy");
        return dateFormat.format(date);
    }

    private Date ConvertFromStringToDate(String text)
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(text);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
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
