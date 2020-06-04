package com.amiele.myfutureme.activities.authentication.register;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.amiele.myfutureme.R;
import com.amiele.myfutureme.activities.authentication.login.LoginActivity;
import com.amiele.myfutureme.database.entity.User;
import com.google.android.material.textfield.TextInputEditText;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    private RegisterViewModel mRegisterViewModel;
    AutoCompleteTextView usernameEditText;
    AutoCompleteTextView emailEditText;
    TextInputEditText passwordEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        emailEditText = findViewById(R.id.et_register_email);
        usernameEditText = findViewById(R.id.et_register_username);
        passwordEditText = findViewById(R.id.et_register_password);

        Button cancelBtn = findViewById(R.id.btn_cancel);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Button registerBtn = findViewById(R.id.btn_register);

        mRegisterViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String username = usernameEditText.getText().toString();
                if (ValidateInput(username,email,password)) {
                    User user = new User(username, email, password);
                    mRegisterViewModel.register(user);
                }
            }
        });

        mRegisterViewModel.getRegisterResult().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s=="success")
                {
                    DisplayToast("register success");
                    Intent loginActivity = new Intent(getApplication(), LoginActivity.class);
                    startActivity(loginActivity);
                    finish();
                }
            }
        });
    }

    private boolean ValidateInput(String username, String email, String password)
    {
        String EMAIL_ADDRESS = "[a-zA-Z0-9+._%\\-]{1,256}" + "@" + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" + "(" + "\\." + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+";
        boolean valid = true;
        if (username.trim().length()==0) {
            valid = false;
            DisplayToast("Username is required");
            usernameEditText.requestFocus();
        }
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

}
