package com.amiele.myfutureme.activities.goal;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.amiele.myfutureme.AppRepo;
import com.amiele.myfutureme.database.entity.User;

public class UserViewModel extends AndroidViewModel {

    private User user = null;
    private AppRepo mAppRepo;

    public UserViewModel(@NonNull Application application) {
        super(application);
        mAppRepo = new AppRepo(application);
    }

//    public void setCurrentUser(String email)
//    {
//        user = mAppRepo.getUserByEmail(email);
//    }

    public User getCurrentUser()
    {
        return user;
    }
}
