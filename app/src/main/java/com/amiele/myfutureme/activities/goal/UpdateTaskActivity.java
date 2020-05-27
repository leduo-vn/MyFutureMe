package com.amiele.myfutureme.activities.goal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
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
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.amiele.myfutureme.R;
import com.amiele.myfutureme.helpers.DateConverter;

import java.util.Calendar;
import java.util.Date;

public class UpdateTaskActivity extends AppCompatActivity {

    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private SubTaskAdapter mAdapter;

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

    private Task mTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_task);

        InitializeView();

        mTask = new Task("Task Name");
        mTask.addSubTask("today is a good day","SUN-09 APR 20",2);
        mTask.addSubTask("i have worked out and talked with my friend","SUN-09 APR 20",3);


        mRvSubTask.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mAdapter = new SubTaskAdapter(this, mTask.getSubTasksList());
        mRvSubTask.setLayoutManager(layoutManager);
        mRvSubTask.setAdapter(mAdapter);

        mLlSubTaskAdd.setOnClickListener(v -> AddSubTask());

        mLlSubTaskDatePick.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    UpdateTaskActivity.this, android.R.style.Theme_DeviceDefault_Dialog_NoActionBar_MinWidth, mDateSetListener,year,month,day);
            datePickerDialog.show();
        });

        mDateSetListener = (view, year, month, dayOfMonth) -> {
            Date date = DateConverter.ConvertFromYearMonthDayToDate(year,month,dayOfMonth);
            mTvSubTaskDow.setText(DateConverter.GetDayOfWeekFromDate(date));
            mTvSubTaskDate.setText(DateConverter.GetDayMonthYearFromDate(date));
            AddSubTask();
        };

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mTvTaskProgress.setText(getString(R.string.update_task_progress_value,progress));
                mIBtnTaskProgressUpdate.setVisibility(View.VISIBLE);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mIBtnTaskProgressUpdate.setOnClickListener(v -> {
            //Store
            mIBtnTaskProgressUpdate.setVisibility(View.INVISIBLE);
        });


        mIBtnTaskNameUpdate.setOnClickListener(arg0 -> {
            if (mVsTaskName.getCurrentView() != mEtTaskName){
                mVsTaskName.showNext();
                mIBtnTaskNameUpdate.setImageResource(android.R.drawable.checkbox_on_background);
            } else if (mVsTaskName.getCurrentView() != mTvTaskName){
                mIBtnTaskNameUpdate.setImageResource((android.R.drawable.ic_menu_edit));
                mVsTaskName.showPrevious();
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
                Toast.makeText(this, "Done selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_delete:
                Toast.makeText(this, "Delete selected", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
    }

    private void AddSubTask()
    {
        mTask.addSubTask(mEtSubTaskDescription.getText().toString(), mTvSubTaskDow.getText().toString()+"-"+ mTvSubTaskDate.getText().toString(),Integer.parseInt(mEtSubTaskHour.getText().toString()));
        mAdapter.notifyDataSetChanged();
    }
}
