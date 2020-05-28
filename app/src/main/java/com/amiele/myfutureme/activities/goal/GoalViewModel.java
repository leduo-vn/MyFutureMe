package com.amiele.myfutureme.activities.goal;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.amiele.myfutureme.AppRepo;
import com.amiele.myfutureme.database.entity.Goal;
import com.amiele.myfutureme.database.entity.Task;
import com.amiele.myfutureme.database.entity.User;

import java.util.List;

public class GoalViewModel extends AndroidViewModel {

    private static AppRepo mAppRepo;
    private static LiveData<List<Goal>> goals;
    private LiveData<User> ldCurrentUser;
    private int userId;
    private static User user;
//    private LiveData<List<Goal>> goals;



    public GoalViewModel(@NonNull Application application) {
        super(application);
        mAppRepo = new AppRepo(application);
        loadUser();

    }

    private static MutableLiveData<Boolean> getUserResult = new MutableLiveData<>();
    public LiveData<Boolean> getUserResult() {
        return getUserResult;
    }

    private static void loadUser() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                user = mAppRepo.getSignedInUser();
                loadAllLoad(user.getId());
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                getUserResult.setValue(true);
            }
        }.execute();
    }



    public LiveData<User> getUser(String email) {
        LiveData<User> user = mAppRepo.getUserByEmail(email);
        return user;
    }

    public LiveData<List<Goal>> getAllGoals()
    {
        return goals;
    }

    public int getUserId()
    {
        return user.getId();
    }

    public static void loadAllLoad(int userId)
    {
        goals = mAppRepo.loadGoals(userId);
    }


    public LiveData<List<Task>> getTasks(int goalId) {
        return mAppRepo.loadTasks(goalId);
    }

    public void addTask(Task task)
    {
        mAppRepo.addTask(task);
    }

    public void addGoal(Goal goal)
    {
        mAppRepo.addGoal(goal);
    }

}
