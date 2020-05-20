package com.amiele.myfutureme.activities.work;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ProgressBar;

import com.amiele.myfutureme.R;

public class WorkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);

        ProgressBar progressBar = findViewById(R.id.progressBar1);
//        progressBar.setSecondaryProgress(50);
//        progressBar.setProgress(100);

    }
}
