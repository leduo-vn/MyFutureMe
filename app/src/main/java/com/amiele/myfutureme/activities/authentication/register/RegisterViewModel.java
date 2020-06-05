package com.amiele.myfutureme.activities.authentication.register;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.amiele.myfutureme.database.AppRepo;
import com.amiele.myfutureme.database.entity.User;

/**
 * Register View Model is used to handle the actions between Register View and Database
 */
public class RegisterViewModel extends AndroidViewModel {

    private static AppRepo mAppRepo;
    private static User user;
    private static MutableLiveData<String> registerResult;

    public RegisterViewModel(@NonNull Application application) {
        super(application);
        mAppRepo = new AppRepo(application);
        registerResult = new MutableLiveData<>();
    }

    /**
     * @return livedata to identify the result of register
     */
    LiveData<String> getRegisterResult() {
        return registerResult;
    }

    /**
     * Async task to register the user
     * @param newUser : information of the new user
     */
    static void register(User newUser) {
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

}
