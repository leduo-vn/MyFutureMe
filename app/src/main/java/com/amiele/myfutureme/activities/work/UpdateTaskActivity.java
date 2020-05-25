package com.amiele.myfutureme.activities.work;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.amiele.myfutureme.R;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class UpdateTaskActivity extends AppCompatActivity {
    DatePickerDialog.OnDateSetListener mDateSetListener;
    SubTaskAdapter adapter;
    Task task;
    ProgressBar mprogressBar;
    SeekBar seekBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_task);

        task = new Task("Task Name");
        task.addSubTask("today is a good day","SUN-09 APR 20",2);
        task.addSubTask("i have worked out and talked with my friend","SUN-09 APR 20",3);

        RecyclerView recyclerView = findViewById(R.id.sub_task_recycler_view);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        adapter = new SubTaskAdapter(task.getSubTasksList());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        LinearLayout llAddSubTask = findViewById(R.id.action_add_sub_task);
        llAddSubTask.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                AddSubTask();
            }
        });


        LinearLayout datePickerBtn = findViewById(R.id.action_date_picker);
        datePickerBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        UpdateTaskActivity.this, android.R.style.Theme_DeviceDefault_Dialog_NoActionBar_MinWidth, mDateSetListener,year,month,day);
             //   datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String text= Integer.toString(year)+"-"+ Integer.toString(month+1)+"-"+Integer.toString(dayOfMonth);
                Date date = ConvertFromStringToDate(text);
                AddSubTask();
            }
        };

        TextView textView = findViewById(R.id.txt_progress);
        mprogressBar = findViewById(R.id.update_progress_bar);
        mprogressBar.setSecondaryProgress(30);
        seekBar = findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
             //   progressBar.setSecondaryProgress(progress);
                textView.setText("" + Integer.toString(progress) + "%");

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }


    private void AddSubTask()
    {
        TextView etDOW = findViewById(R.id.et_dow);
        TextView etDate = findViewById(R.id.et_date);
        EditText etHour = findViewById(R.id.et_hour);
        EditText etDescription = findViewById(R.id.et_sub_task_description);
        task.addSubTask(etDescription.getText().toString(), etDOW.getText().toString()+"-"+etDate.getText().toString(),Integer.parseInt(etHour.getText().toString()));

        adapter.notifyDataSetChanged();
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
            TextView etDOW = findViewById(R.id.et_dow);
            etDOW.setText(getTheDOW(date));
            TextView etDate = findViewById(R.id.et_date);
            etDate.setText(getTheDate(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


}
