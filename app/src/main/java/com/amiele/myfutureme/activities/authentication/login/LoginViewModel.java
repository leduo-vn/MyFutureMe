package com.amiele.myfutureme.activities.authentication.login;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.amiele.myfutureme.database.AppRepo;
import com.amiele.myfutureme.database.entity.User;

/**
 * Login View Model is used to handle the actions between Login View and Database
 */
public class LoginViewModel extends AndroidViewModel {

    private static AppRepo mAppRepo;
    private static User user;
    private static MutableLiveData<String> loginResult ;
    private static MutableLiveData<String> loggedResult ;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        mAppRepo = new AppRepo(application);
        loginResult = new MutableLiveData<>();
        loggedResult = new MutableLiveData<>();
        loadSignedUser();
    }

    /**
     * @return LiveData to identify whether login is successful or not
     */
    LiveData<String> getLoginResult() {
        return loginResult;
    }

    /**
     * @return LiveData to identify whether user is logged
     */
    LiveData<String> getLoggedResult() {
        return loggedResult;
    }

    /**
     * Async task to load signed user
     * If signed user existed means the user is already logged in so we could direct the user to main activity
     */
    private static void loadSignedUser() {
        new AsyncTask<Void, Void, User>() {
            @Override
            protected User doInBackground(Void... voids) {
                return mAppRepo.getSignedInUser();
            }

            @Override
            protected void onPostExecute(User user) {
                super.onPostExecute(user);

                if (user!=null)
                    loggedResult.setValue("logged");

            }
        }.execute();
    }

    /**
     * Async task for login user
     * @param email login email
     * @param password login password
     */
    static void login(String email, String password) {
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
