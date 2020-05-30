package com.amiele.myfutureme.activities.goal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amiele.myfutureme.R;
import com.amiele.myfutureme.database.entity.Goal;
import com.amiele.myfutureme.database.entity.Tag;
import com.amiele.myfutureme.database.entity.Task;
import com.amiele.myfutureme.database.entity.User;
import com.amiele.myfutureme.helpers.DateConverter;
import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class AddGoalActivity extends AppCompatActivity {
    private ArrayList<Task> mTaskList;
    private ArrayList<Tag> mTagList;
    private AddGoalViewModel mGoalViewModel;
    private int goalId =-1;

    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private TaskAdapter mAdapter;

    private RecyclerView mRvTask;
    private ImageButton mIBtnDatePick;
    private EditText mEtGoalDueDate;

    private void InitializeView()
    {
        mRvTask = findViewById(R.id.add_goal_rv_task);
        mIBtnDatePick = findViewById(R.id.add_goal_ibtn_date_pick);
        mEtGoalDueDate = findViewById(R.id.add_goal_et_goal_due_date);
    }

    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_goal);

        userId = getIntent().getStringExtra("id");

        InitializeView();

        mTagList = new ArrayList<>();
        mTagList.add(new Tag("Friend",Color.parseColor("#EEDBAA")));
        mTagList.add(new Tag("LifeStyle",Color.parseColor("#BDEEAA")));
        mTagList.add(new Tag("Job",Color.parseColor("#86EED1")));

        mTaskList = new ArrayList<>();

        mRvTask.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mAdapter = new TaskAdapter(mTaskList);
        mRvTask.setLayoutManager(layoutManager);
        mRvTask.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(task -> DisplayToast(task.getName()));

        mIBtnDatePick.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    AddGoalActivity.this, android.R.style.Theme_DeviceDefault_Dialog_NoActionBar_MinWidth, mDateSetListener,year,month,day);
            datePickerDialog.show();
        });

        mDateSetListener = (view, year, month, dayOfMonth) -> {
            Date date = DateConverter.ConvertFromYearMonthDayToDate(year,month,dayOfMonth);
            String stDate = DateConverter.GetDayOfWeekFromDate(date)+", " + DateConverter.GetDayMonthYearFromDate(date);
            mEtGoalDueDate.setText(stDate);
        };

        mGoalViewModel = new ViewModelProvider(this).get(AddGoalViewModel.class);
        mGoalViewModel.setUserId(Integer.parseInt(userId));
        Goal goal = new Goal(Integer.parseInt(userId),"Goal name","goal description","Sun-21 Apr 20", android.R.color.holo_blue_light);
        mGoalViewModel.addGoal(goal);

        mGoalViewModel.getGoalIdResult().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                goalId = integer;
                getTag();
            }
        }) ;



    }

    private void getTag()
    {
        FlexboxLayout fbTag = findViewById(R.id.add_goal_fl_tag);


        mGoalViewModel.getAllTags().observe(this, new Observer<List<Tag>>() {
            @Override
            public void onChanged(List<Tag> tags) {
                if (fbTag.getChildCount() > 0)
                    fbTag.removeAllViews();
                for (Tag tag : tags) {
                    TextView tv = new TextView(getApplication());
                    tv.setText(tag.getName());
                    tv.setBackgroundColor(tag.getColor());
                    //tv.setBackground(mActivity.getDrawable(R.drawable.rounded_purple_border));
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.setMargins(10, 10, 10, 10);
                    tv.setLayoutParams(params);
                    fbTag.addView(tv);
                }
            }
        });

        mGoalViewModel.getAllTasks().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> taskList) {
                mAdapter.setTaskList(taskList);
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    private void returnToParent()
    {
        Intent returnIntent= new Intent();
        setResult(RESULT_OK,returnIntent);
        finish();
    }
    

    private void DeleteGoal()
    {
        mGoalViewModel.deleteGoal(goalId);
        returnToParent();
    }

    public void onAddTaskClicked(View view)
    {
        Task task = new Task("new task", 50);
        task.setGoalId(goalId);
        mGoalViewModel.addTask(task);//  mTaskList.add(new Task("new task",50));
       // mAdapter.notifyDataSetChanged();
    }
    public static final int ADD_TAG_ACTIVITY_REQUEST_CODE = 1;

    public void onAddTagBtnClicked(View view)
    {
        Intent addTagActivity = new Intent(this, AddTagActivity.class);
        addTagActivity.putExtra("goal_id",Integer.toString(goalId));
        startActivityForResult(addTagActivity,ADD_TAG_ACTIVITY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_TAG_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            UpdateTag();
            DisplayToast("success!");
        } else {
            DisplayToast("error");
        }
    }

    private void UpdateTag()
    {

    }

    private  void DisplayToast(String text)
    {
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
    }


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
               // AddTaskandTag();
                returnToParent();
                Toast.makeText(this, "Done selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_cancel:
                DeleteGoal();
                Toast.makeText(this, "Cancel selected", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
