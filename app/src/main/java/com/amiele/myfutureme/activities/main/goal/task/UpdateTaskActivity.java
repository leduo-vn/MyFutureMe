package com.amiele.myfutureme.activities.main.goal.task;

import androidx.annotation.NonNull;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.amiele.myfutureme.R;
import com.amiele.myfutureme.database.entity.SubTask;
import com.amiele.myfutureme.helpers.DateConverter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

/**
 * The activity is used to update the information about task
 * User could update the description, progress
 * User could update sub-tasks and date and working time towards each sub-task
 */
public class UpdateTaskActivity extends AppCompatActivity {

    private UpdateTaskViewModel mUpdateTaskViewModel;

    private SubTaskAdapter mAdapter;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private SeekBar mSeekBar;
    private TextView mTvSubTaskDow;
    private TextView mTvSubTaskDate;
    private EditText mEtSubTaskHour;
    private EditText mEtSubTaskDescription;
    private ViewSwitcher mVsTaskName;
    private TextView mTvTaskName;
    private EditText mEtTaskName;
    private ImageButton mIBtnTaskNameUpdate;
    private LinearLayout mLlSubTaskAdd;
    private LinearLayout mLlSubTaskDatePick;
    private TextView mTvTaskProgress;
    private ImageButton mIBtnTaskProgressUpdate;
    private RecyclerView mRvSubTask;
    private TextView mTvTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_task);

        InitializeView();
        int taskId = Integer.parseInt(Objects.requireNonNull(getIntent().getStringExtra("task_id")));


        mUpdateTaskViewModel = new ViewModelProvider(this).get(UpdateTaskViewModel.class);
        mUpdateTaskViewModel.setTaskId(taskId);

        // Set adapter and layout for the subtask recycle view
        ArrayList<SubTask> subTask = new ArrayList<>();
        mRvSubTask.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mAdapter = new SubTaskAdapter(subTask);
        mRvSubTask.setLayoutManager(layoutManager);
        mRvSubTask.setAdapter(mAdapter);

        // set onCLick listerner to add new subtask
        mLlSubTaskAdd.setOnClickListener(v -> AddSubTask());

        // set Onclick listener to active the date picker
        mLlSubTaskDatePick.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    UpdateTaskActivity.this, android.R.style.Theme_DeviceDefault_Dialog_NoActionBar_MinWidth, mDateSetListener,year,month,day);
            datePickerDialog.show();
        });

        // Display the date to the view
        mDateSetListener = (view, year, month, dayOfMonth) -> {
            Date date = DateConverter.ConvertFromYearMonthDayToDate(year,month,dayOfMonth);
            mTvSubTaskDow.setText(DateConverter.GetDayOfWeekFromDate(date));
            mTvSubTaskDate.setText(DateConverter.GetDayMonthYearFromDate(date));
        };

        // Execute the delete subtask s
        mAdapter.setOnItemClickListener((subTask1, action) -> {
            if (action.equals("DELETE"))
                mUpdateTaskViewModel.deleteSubTask(subTask1.getId());
        });

        // Change the value of the text view to show the seekbar progress
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mTvTaskProgress.setText(String.format(Locale.US,"%d",progress));
                mIBtnTaskProgressUpdate.setVisibility(View.VISIBLE);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        // Update progress of seek bar as the progress of task
        mIBtnTaskProgressUpdate.setOnClickListener(v -> {
            mUpdateTaskViewModel.updateProgress(mTvTaskProgress.getText().toString());
            mIBtnTaskProgressUpdate.setVisibility(View.INVISIBLE);
        });

        // set listener to switch from edit text to text view mode and vice versa for Goal name
        mIBtnTaskNameUpdate.setOnClickListener(arg0 -> {
            if (mVsTaskName.getCurrentView() != mEtTaskName){
                mVsTaskName.showNext();
                mIBtnTaskNameUpdate.setImageResource(android.R.drawable.checkbox_on_background);
            } else if (mVsTaskName.getCurrentView() != mTvTaskName){
                if (!mEtTaskName.getText().toString().equals(mTvTaskName.getText().toString()))
                    mUpdateTaskViewModel.updateName(mEtTaskName.getText().toString());

                mIBtnTaskNameUpdate.setImageResource((android.R.drawable.ic_menu_edit));
                mVsTaskName.showPrevious();
            }
        });

        // load and observe the task information from the database
        // display the task infromation and tis subtasks
        mUpdateTaskViewModel.loadTask();
        mUpdateTaskViewModel.getTask().observe(this, task -> {
            if (task!=null) {
                // task name
                mEtTaskName.setText(task.getName());
                mTvTaskName.setText(task.getName());
                mTvTaskProgress.setText(String.format(Locale.US,"%d",task.getProgress()));

                //task progress
                mSeekBar.setProgress(task.getProgress());
                mIBtnTaskProgressUpdate.setVisibility(View.INVISIBLE);

                // Calculate the total time contributed towards the task
                int minute=0;
                if (task.getSubTasksList()!=null)
                for (SubTask subtask: task.getSubTasksList())
                    minute += subtask.getMinute();
                mTvTime.setText(String.format(Locale.US,"%d",minute));
                mAdapter.setSubTaskList(task.getSubTasksList());
                mAdapter.notifyDataSetChanged();

                // update the task database with the time
                if (minute != task.getMinute())
                {
                    mUpdateTaskViewModel.updateMinute(minute);
                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.update_task_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_done:
                FinishActivity();
                return true;
            case R.id.action_delete:
                mUpdateTaskViewModel.deleteTask();
                FinishActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void FinishActivity()
    {
        Intent returnIntent= new Intent();
        setResult(RESULT_OK,returnIntent);
        finish();
    }
    private void InitializeView()
    {
        mTvSubTaskDow = findViewById(R.id.update_task_et_sub_task_dow);
        mTvSubTaskDate = findViewById(R.id.update_task_et_sub_task_date);
        mEtSubTaskHour = findViewById(R.id.update_task_et_sub_task_hour);
        mEtSubTaskDescription = findViewById(R.id.update_task_et_sub_task_description);
        mVsTaskName = findViewById(R.id.update_task_vs_task_name);
        mSeekBar = findViewById(R.id.update_task_seekBar);
        mTvTaskName =  findViewById(R.id.update_task_tv_task_name);
        mEtTaskName = findViewById(R.id.update_task_et_task_name);
        mIBtnTaskNameUpdate = findViewById(R.id.update_task_ibtn_task_name_update);
        mLlSubTaskAdd = findViewById(R.id.update_task_ll_sub_task_add);
        mTvTaskProgress = findViewById(R.id.update_task_tv_progress);
        mIBtnTaskProgressUpdate = findViewById(R.id.update_task_ibtn_progress_update);
        mLlSubTaskDatePick = findViewById(R.id.update_task_ll_date_pick);
        mRvSubTask = findViewById(R.id.update_task_rv_sub_task);
        mTvTime = findViewById(R.id.update_task_tv_task_time);
    }

    //get information from the view to add sub-task(name, time, date)
    private void AddSubTask()
    {
        String description = mEtSubTaskDescription.getText().toString() ;
        String date = mTvSubTaskDow.getText().toString()+"-"+ mTvSubTaskDate.getText().toString();
        int hour = 0;
        if (mEtSubTaskHour.getText().toString().length()>0)
        {
            hour = Integer.parseInt(mEtSubTaskHour.getText().toString());
        }
        SubTask subTask = new SubTask(description, date, hour);
        mUpdateTaskViewModel.addSubTask(subTask);
        mEtSubTaskDescription.setText("");
        mEtSubTaskHour.setText("");
    }
}
