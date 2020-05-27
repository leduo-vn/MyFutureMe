package com.amiele.myfutureme.activities.authentication.register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.amiele.myfutureme.R;
import com.amiele.myfutureme.activities.authentication.login.LoginActivity;
import com.amiele.myfutureme.activities.main.MainActivity;

public class RegisterActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void onRegisterBtnClicked(View view)
    {


        Intent loginActivity = new Intent(this, LoginActivity.class);
        startActivity(loginActivity);
    }
}
