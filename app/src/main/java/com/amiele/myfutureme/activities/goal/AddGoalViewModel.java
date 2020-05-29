package com.amiele.myfutureme.activities.goal;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Build;
import android.util.AndroidException;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.amiele.myfutureme.AppRepo;
import com.amiele.myfutureme.database.entity.Goal;
import com.amiele.myfutureme.database.entity.Task;
import com.amiele.myfutureme.database.entity.User;

import java.util.List;

public class AddGoalViewModel extends AndroidViewModel {

    private static AppRepo mAppRepo;
    private LiveData<List<Goal>> goals;

    private LiveData<List<Task>> tasks;
    private Goal goal;
    private User user;


    public AddGoalViewModel(@NonNull Application application) {
        super(application);
        mAppRepo = new AppRepo(application);

    }

    public LiveData<User> getUser(String email) {
        return mAppRepo.getUserByEmail(email);
    }

    private static MutableLiveData<Integer> goalIdResult = new MutableLiveData<>();
    public LiveData<Integer> getGoalIdResult() {
        return goalIdResult;
    }

    private static MutableLiveData<Boolean> updateUserStatusResult = new MutableLiveData<>();
    public LiveData<Boolean> getUpdateUserStatusResult() {
        return updateUserStatusResult;
    }

    public void deleteGoal(int goalId)
    {
        mAppRepo.deleteGoal(goalId);
    }

    public static void addGoal(Goal goal)
    {
        new AsyncTask<Void, Void, Long>() {

            @Override
            protected Long doInBackground(Void... voids) {
                return mAppRepo.addGoal(goal);
                //return null;
            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            protected void onPostExecute(Long aLong) {
                super.onPostExecute(aLong);
                goalIdResult.setValue(Math.toIntExact(aLong));
            }
        }.execute();
       // return mAppRepo.addGoal(goal);

    }

    public void addAllTasks(List<Task> taskList)
    {
        mAppRepo.addAllTasks(taskList);
    }
    public void loadAllLoad(int userId)
    {
        goals = mAppRepo.loadGoals(userId);
    }

    public LiveData<List<Goal>> getAllGoals()
    {
        return goals;
    }
}
