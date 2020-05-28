package com.amiele.myfutureme.activities.goal;

import android.app.Application;
import android.os.AsyncTask;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.amiele.myfutureme.AppRepo;
import com.amiele.myfutureme.R;
import com.amiele.myfutureme.database.entity.User;

public class LoginViewModel extends AndroidViewModel {

    private static AppRepo mAppRepo;
    private static User user;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        mAppRepo = new AppRepo(application);

    }

    private static MutableLiveData<String> loginResult = new MutableLiveData<>();
    public LiveData<String> getLoginResult() {
        return loginResult;
    }

    private static MutableLiveData<Boolean> updateUserStatusResult = new MutableLiveData<>();
    public LiveData<Boolean> getUpdateUserStatusResult() {
        return updateUserStatusResult;
    }

    public static User login(String email,String password) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                user = mAppRepo.getUserAsyncByEmail(email);
                if (user != null && user.getPassword() == password)
                    mAppRepo.UpdateUserSignInStatus(user.getId(),true);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if (user==null) loginResult.setValue("not existed");
                else {

                    if (!user.getPassword().equals(password))
                        loginResult.setValue("wrong pass");
                    else loginResult.setValue("success");
                }
            }
        }.execute();

        return user;
    }

    public static void updateSignedInUser(boolean status)
    {
        mAppRepo.UpdateUserSignInStatus(user.getId(),status);
    }
}
