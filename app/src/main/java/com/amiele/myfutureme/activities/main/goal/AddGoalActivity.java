package com.amiele.myfutureme.activities.main.goal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
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
import android.widget.ViewSwitcher;

import com.amiele.myfutureme.R;
import com.amiele.myfutureme.activities.main.goal.tag.AddTagActivity;
import com.amiele.myfutureme.activities.main.goal.task.TaskAdapter;
import com.amiele.myfutureme.activities.main.goal.task.UpdateTaskActivity;
import com.amiele.myfutureme.database.entity.Goal;
import com.amiele.myfutureme.database.entity.Tag;
import com.amiele.myfutureme.database.entity.Task;
import com.amiele.myfutureme.helpers.DateConverter;
import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class AddGoalActivity extends AppCompatActivity {

    public static final int ADD_TAG_ACTIVITY_REQUEST_CODE = 1;
    public static final int EDIT_TASK_ACTIVITY_REQUEST_CODE = 2;
    private AddGoalViewModel mGoalViewModel;
    private int goalId =-1;
    String userId;
    String action;



    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private TaskAdapter mAdapter;
    private FlexboxLayout fbTag;
    private RecyclerView mRvTask;
    private ImageButton mIBtnDatePick;
    private TextView mEtGoalDueDate;
    private ViewSwitcher mVsGoalName;
    private TextView mTvGoalName;
    private EditText mEtGoalName;
    private ViewSwitcher mVsGoalDescription;
    private TextView mTvGoalDescription;
    private EditText mEtGoalDescription;
    private ImageButton miBtnGoalNameUpdate;
    private ImageButton miBtnGoalDescriptionUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_goal);

        InitializeView();

        action = getIntent().getStringExtra("action");
        mGoalViewModel = new ViewModelProvider(this).get(AddGoalViewModel.class);

        if (action.equals(GoalActivity.GOAL_ACTION_ADD))
            InitializeAddView();
        else
        if (action.equals(GoalActivity.GOAL_ACTION_EDIT))
            InitialEditView();

        ArrayList<Task> mTaskList = new ArrayList<>();

        mRvTask.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mAdapter = new TaskAdapter(mTaskList);
        mRvTask.setLayoutManager(layoutManager);
        mRvTask.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(this::GoToUpdateTaskActivity
        );

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
            String stDate = DateConverter.ConvertFromDateToString(date);
            mGoalViewModel.updateDueDate(stDate);
        };

        miBtnGoalNameUpdate.setOnClickListener(arg0 -> {
            if (mVsGoalName.getCurrentView() != mEtGoalName){
                mVsGoalName.showNext();
                miBtnGoalNameUpdate.setImageResource(android.R.drawable.checkbox_on_background);
            } else if (mVsGoalName.getCurrentView() != mTvGoalName){
                if (!mEtGoalName.getText().toString().equals(mTvGoalName.getText().toString()))
                    mGoalViewModel.updateName(mEtGoalName.getText().toString());
                miBtnGoalNameUpdate.setImageResource((android.R.drawable.ic_menu_edit));
                mVsGoalName.showPrevious();
            }
        });

        miBtnGoalDescriptionUpdate.setOnClickListener(arg0 -> {
            if (mVsGoalDescription.getCurrentView() != mEtGoalDescription){
                mVsGoalDescription.showNext();
                miBtnGoalDescriptionUpdate.setImageResource(android.R.drawable.checkbox_on_background);
            } else if (mVsGoalDescription.getCurrentView() != mTvGoalDescription){
                if (!mEtGoalDescription.getText().toString().equals(mTvGoalDescription.getText().toString()))
                    mGoalViewModel.updateDescription(mEtGoalDescription.getText().toString());
                miBtnGoalDescriptionUpdate.setImageResource((android.R.drawable.ic_menu_edit));
                mVsGoalDescription.showPrevious();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EDIT_TASK_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            DisplayToast("Task is updated");
        }
        else if (requestCode == ADD_TAG_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            DisplayToast("Tag is updated!");
        }
        else DisplayToast("Errors Occurred! Your changes may not be saved");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_goal_menu,menu);
        if (action.equals("EDIT")) {
            menu.findItem(R.id.action_cancel).setTitle("DELETE");
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_done:
                finishActivity();
                return true;
            case R.id.action_cancel:
                mGoalViewModel.deleteGoal();
                finishActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onAddTaskClicked(View view)
    {
        EditText etTaskName = findViewById(R.id.add_goal_et_task_name);
        Task task = new Task(etTaskName.getText().toString(), 0);
        task.setGoalId(goalId);
        mGoalViewModel.addTask(task);
    }

    public void onAddTagBtnClicked(View view)
    {
        Intent addTagActivity = new Intent(this, AddTagActivity.class);
        addTagActivity.putExtra("goal_id",Integer.toString(goalId));
        startActivityForResult(addTagActivity,ADD_TAG_ACTIVITY_REQUEST_CODE);
    }

    private void InitializeView()
    {
        mRvTask = findViewById(R.id.add_goal_rv_task);
        mIBtnDatePick = findViewById(R.id.add_goal_ibtn_date_pick);
        mEtGoalDueDate = findViewById(R.id.add_goal_et_goal_due_date);

        fbTag = findViewById(R.id.add_goal_fl_tag);
        mVsGoalName = findViewById(R.id.add_goal_vs_name);
        mTvGoalName = findViewById(R.id.add_goal_tv_name);
        mEtGoalName = findViewById(R.id.add_goal_et_name);
        miBtnGoalNameUpdate = findViewById(R.id.add_goal_ibtn_name_update);

        mVsGoalDescription = findViewById(R.id.add_goal_vs_description);
        mTvGoalDescription =findViewById(R.id.add_goal_tv_description);
        mEtGoalDescription = findViewById(R.id.add_goal_et_description);
        miBtnGoalDescriptionUpdate = findViewById(R.id.add_goal_ibtn_description_update);

    }

    private void InitialEditView()
    {
        goalId = Integer.parseInt(getIntent().getStringExtra("goal_id"));
        mGoalViewModel.setGoalId(goalId);
        AddGoalViewModel.loadAllLoad();
        DisplayGoal();
    }

    private void InitializeAddView()
    {
        userId = getIntent().getStringExtra("user_id");

        Date d = new Date();
        String currentDate = DateConverter.ConvertFromDateToString(d);
        Goal goal = new Goal(Integer.parseInt(userId),"","",currentDate, currentDate, android.R.color.holo_blue_light);
        AddGoalViewModel.addGoal(goal);

        mGoalViewModel.getGoalIdResult().observe(this, integer -> {
            goalId = integer;
            DisplayGoal();
        }) ;
    }

    private void GoToUpdateTaskActivity(Task task)
    {
        Intent  updateTaskActivity= new Intent(this, UpdateTaskActivity.class);
        updateTaskActivity.putExtra("task_id",Integer.toString(task.getId()));
        startActivityForResult(updateTaskActivity,EDIT_TASK_ACTIVITY_REQUEST_CODE);
    }

    private void DisplayGoal()
    {
        mGoalViewModel.getAllTags().observe(this, tags -> {
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
        });

        mGoalViewModel.getAllTasks().observe(this, taskList -> {
            mAdapter.setTaskList(taskList);
            mAdapter.notifyDataSetChanged();
        });

        mGoalViewModel.getGoal().observe(this, goal -> {
            if (goal!=null) {
                mEtGoalName.setText(goal.getName());
                mEtGoalDescription.setText(goal.getDescription());

                mTvGoalName.setText(goal.getName());
                mTvGoalDescription.setText(goal.getDescription());
                mEtGoalDueDate.setText(goal.getDueDate());
            }
        });
    }

    private void finishActivity()
    {
        Intent returnIntent= new Intent();
        setResult(RESULT_OK,returnIntent);
        finish();
    }

    private  void DisplayToast(String text)
    {
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
    }
}
