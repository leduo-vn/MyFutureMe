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
import com.amiele.myfutureme.activities.main.goal.GoalActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    private LoginViewModel mLoginViewModel;
    AutoCompleteTextView emailEditText;
    TextInputEditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mLoginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        emailEditText = findViewById(R.id.login_tv_email);
        passwordEditText = findViewById(R.id.login_tv_password);

        mLoginViewModel.getLoggedResult().observe(this, s -> {
            if (s=="logged")
            {
                finish();
                Intent goalActivity = new Intent(getApplication(), GoalActivity.class);
                startActivity(goalActivity);
            }
            else return;
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
        loginBtn.setOnClickListener(v -> {
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            if (ValidateInput(email,password))
                mLoginViewModel.login(email,password);

        });
    }

    private boolean ValidateInput(String email, String password)
    {
        String EMAIL_ADDRESS = "[a-zA-Z0-9+._%\\-]{1,256}" + "@" + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" + "(" + "\\." + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+";
        boolean valid = true;
        if (email.trim().length()==0 || (!Pattern.compile(EMAIL_ADDRESS).matcher(email).matches()))
        {
            valid= false;
            DisplayToast("Email is invalid");
            emailEditText.requestFocus();
        }
        else
        if (password.trim().length()<5) {
            valid = false;
            DisplayToast("Password need to be at least 5 characters");
            passwordEditText.requestFocus();
        }
        return valid;
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
