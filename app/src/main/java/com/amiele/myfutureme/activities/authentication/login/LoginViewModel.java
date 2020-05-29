package com.amiele.myfutureme.activities.authentication.login;

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
        loginResult = new MutableLiveData<>();
        loggedResult = new MutableLiveData<>();
        loadSignedUser();
    }

    private static MutableLiveData<String> loginResult ;
    public LiveData<String> getLoginResult() {
        return loginResult;
    }

    private static MutableLiveData<String> loggedResult ;
    public LiveData<String> getLoggedResult() {
        return loggedResult;
    }

    private static void loadSignedUser() {
        new AsyncTask<Void, Void, User>() {
            @Override
            protected User doInBackground(Void... voids) {
                User user = mAppRepo.getSignedInUser();
                return user;
            }

            @Override
            protected void onPostExecute(User user) {
                super.onPostExecute(user);

                if (user!=null)
                    loggedResult.setValue("logged");

            }
        }.execute();
    }

    public static void login(String email,String password) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                user = mAppRepo.getUserAsyncByEmail(email);
                if (user != null && user.getPassword().equals(password))
                {
                    user.setSignedIn(true);
                    mAppRepo.UpdateUserSignInStatus(user.getId(),true);
                }

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

    }

}
