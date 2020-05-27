package com.amiele.myfutureme.activities.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.amiele.myfutureme.R;
import com.amiele.myfutureme.activities.motivation.MotivationActivity;
import com.amiele.myfutureme.activities.goal.GoalActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onTodoActionClicked(View view)
    {
        Intent workActivity = new Intent(this, GoalActivity.class);
        startActivity(workActivity);
    }

    public void onLifeStyleActionClicked(View view)
    {

    }

    public void onMotivationActionClicked(View view)
    {
        Intent motivationActivity = new Intent(this, MotivationActivity.class);
        startActivity(motivationActivity);
    }

    public void onSummaryActionClicked(View view)
    {

    }
}
