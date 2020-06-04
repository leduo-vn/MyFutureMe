package com.amiele.myfutureme.activities.authentication.register;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.amiele.myfutureme.database.AppRepo;
import com.amiele.myfutureme.database.entity.User;

public class RegisterViewModel extends AndroidViewModel {

    private static AppRepo mAppRepo;
    private static User user;

    public RegisterViewModel(@NonNull Application application) {
        super(application);
        mAppRepo = new AppRepo(application);

    }

    private static MutableLiveData<String> registerResult = new MutableLiveData<>();
    public LiveData<String> getRegisterResult() {
        return registerResult;
    }

    public static void register(User newUser) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                user = mAppRepo.getUserAsyncByEmail(newUser.getEmail());
                if (user == null)
                    mAppRepo.addUser(newUser);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if (user!=null) registerResult.setValue("existed");
                else {
                    registerResult.setValue("success");
                }
            }
        }.execute();

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
