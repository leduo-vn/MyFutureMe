package com.amiele.myfutureme.activities.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.amiele.myfutureme.R;
import com.amiele.myfutureme.activities.work.WorkActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onTodoActionClicked(View view)
    {
        Intent workActivity = new Intent(this, WorkActivity.class);
        startActivity(workActivity);
    }

    public void onLifeStyleActionClicked(View view)
    {

    }

    public void onMotivationActionClicked(View view)
    {

    }

    public void onSummaryActionClicked(View view)
    {

    }
}
