package com.amiele.myfutureme.activities.authentication.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.amiele.myfutureme.AppRepo;
import com.amiele.myfutureme.R;
import com.amiele.myfutureme.activities.authentication.register.RegisterActivity;
import com.amiele.myfutureme.activities.goal.LoginViewModel;
import com.amiele.myfutureme.activities.goal.UserViewModel;
import com.amiele.myfutureme.activities.main.MainActivity;
import com.amiele.myfutureme.database.entity.User;
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

        mLoginViewModel.getUpdateUserStatusResult().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean){
                }
                else return;
            }
        });

        mLoginViewModel.getLoginResult().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s == "success") {
                    GotoMainActivity();
                }
                else return;
            }
        });

        Button loginBtn = findViewById(R.id.btn_login);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                mLoginViewModel.login(email,password);

               // ExecuteLogin(email,password);


            }
        });
    }

    private void ExecuteLogin(String email,String password)
    {
//        User user =
//        if (user == null) DisplayToast("null");
//        else{
//                if (!user.getPassword().equals(password))
//                {
//                    DisplayToast("Wrong Passowrd");
//                }
//                else {
//                    mLoginViewModel.updateSignedInUser(user,true);
//                    GotoMainActivity();
//
//               }
//        }

    }

    private void GotoMainActivity()
    {
       DisplayToast("here");

        Intent mainActivity = new Intent(this, MainActivity.class);
        startActivity(mainActivity);


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
