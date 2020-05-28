package com.amiele.myfutureme.activities.authentication.register;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.amiele.myfutureme.AppRepo;
import com.amiele.myfutureme.database.entity.User;

public class RegisterViewModel extends AndroidViewModel {

    private AppRepo mAppRepo;

    public RegisterViewModel(@NonNull Application application) {
        super(application);
        mAppRepo = new AppRepo(application);

    }

   public void addUser(User user)
   {
        mAppRepo.addUser(user);
   }

    public LiveData<User> getUser(String email) {
        LiveData<User> user = mAppRepo.getUserByEmail(email);
        return user;
    }
}
