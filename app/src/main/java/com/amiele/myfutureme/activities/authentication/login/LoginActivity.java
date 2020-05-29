package com.amiele.myfutureme.activities.authentication.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.amiele.myfutureme.R;
import com.amiele.myfutureme.activities.authentication.register.RegisterActivity;
import com.amiele.myfutureme.activities.goal.GoalActivity;
import com.amiele.myfutureme.activities.main.MainActivity;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {
    private LoginViewModel mLoginViewModel;
    AutoCompleteTextView usernameEditText;
    TextInputEditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mLoginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        usernameEditText = findViewById(R.id.txt_username);
        passwordEditText = findViewById(R.id.txt_password);


        mLoginViewModel.getLoggedResult().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s=="logged")
                {
                    finish();
                    Intent goalActivity = new Intent(getApplication(), GoalActivity.class);
                    startActivity(goalActivity);
                }
                else return;
            }
        });
        mLoginViewModel.getLoginResult().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s == "success") {
                    DisplayToast("Login success");
                    finish();
                    Intent goalActivity = new Intent(getApplication(), GoalActivity.class);
                    startActivity(goalActivity);
                }
                else
                {
                    DisplayToast(s);
                    return;
                }
            }
        });

        Button loginBtn = findViewById(R.id.btn_login);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                mLoginViewModel.login(email,password);

            }
        });
    }

    private void DisplayToast(String text)
    {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();

    }



    public void onRegisterBtnClicked(View view)
    {
        Intent registerActivity = new Intent(this, RegisterActivity.class);
        startActivity(registerActivity);
    }
}
