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

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    private LoginViewModel mLoginViewModel;
    private AutoCompleteTextView mEtEmail;
    private TextInputEditText mEtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mLoginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        mEtEmail = findViewById(R.id.login_tv_email);
        mEtPassword = findViewById(R.id.login_tv_password);

        // Observe the live data, if received "logged" message means user is already login so we
        // could direct the user to the main activity - Goal Activity
        mLoginViewModel.getLoggedResult().observe(this, s -> {
            if (s.equals("logged"))
            {
                finish();
                Intent goalActivity = new Intent(getApplication(), GoalActivity.class);
                startActivity(goalActivity);
            }

        });

        // Observe the live data for login result
        // Direct user to main activity if login successful or Display the error message
        mLoginViewModel.getLoginResult().observe(this, s -> {
            if (s.equals("success")) {
                DisplayToast("Login success");
                finish();
                Intent goalActivity = new Intent(getApplication(), GoalActivity.class);
                startActivity(goalActivity);
            }
            else
            {
                DisplayToast(s);
            }
        });

        Button loginBtn = findViewById(R.id.btn_login);

        // Set the onClick listener for the "Login" button
        loginBtn.setOnClickListener(v -> {
            String email = mEtEmail.getText().toString();
            String password = mEtPassword.getText().toString();
            //Validate login information before execute the login action
            if (ValidateInput(email,password))
                mLoginViewModel.login(email,password);

        });
    }

    public void onRegisterBtnClicked(View view)
    {
        Intent registerActivity = new Intent(this, RegisterActivity.class);
        startActivity(registerActivity);
    }

    /**
     * Validate the login input
     * @param email typed email
     * @param password typed password
     * @return boolean variable identify whether input is valid
     */
    private boolean ValidateInput(String email, String password)
    {
        String EMAIL_ADDRESS = "[a-zA-Z0-9+._%\\-]{1,256}" + "@" + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" + "(" + "\\." + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+";
        boolean valid = true;
        // Email is required and must follow email pattern
        if (email.trim().length()==0 || (!Pattern.compile(EMAIL_ADDRESS).matcher(email).matches()))
        {
            valid= false;
            DisplayToast("Email is invalid");
            mEtEmail.requestFocus();
        }
        else
        // Password is at least 5 characters
        if (password.trim().length()<5) {
            valid = false;
            DisplayToast("Password need to be at least 5 characters");
            mEtPassword.requestFocus();
        }
        return valid;
    }

    private void DisplayToast(String text)
    {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

}
