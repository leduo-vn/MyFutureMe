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
import com.amiele.myfutureme.database.entity.Tag;
import com.amiele.myfutureme.database.entity.Task;
import com.amiele.myfutureme.database.entity.User;

import java.util.List;

public class AddGoalViewModel extends AndroidViewModel {

    private static AppRepo mAppRepo;

    private static LiveData<List<Task>> tasks;
    private static LiveData<List<Tag>> tags;
    private int userId;
    private static int goalId;


    public void setGoalId(int goalId)
    {
        this.goalId = goalId;
    }

    public void setUserId(int userId)
    {
        this.userId = userId;
    }



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



    public void deleteGoal(int goalId)
    {
        mAppRepo.deleteGoal(goalId);
    }

    public static void addGoal(Goal goal)
    {
        new AsyncTask<Void, Void, Long>() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            protected Long doInBackground(Void... voids) {
                Long result = mAppRepo.addGoal(goal);
                goalId=Math.toIntExact(result);
                loadAllLoad();
                return result;
                //return null;
            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            protected void onPostExecute(Long aLong) {
                super.onPostExecute(aLong);

                goalIdResult.setValue(goalId);
            }
        }.execute();
       // return mAppRepo.addGoal(goal);

    }

    public void addAllTasks(List<Task> taskList)
    {
        mAppRepo.addAllTasks(taskList);
    }
    public static void loadAllLoad()
    {
        tasks = mAppRepo.loadTasks(goalId);
        tags = mAppRepo.loadTags(goalId);
    }


    public void addTask(Task task)
    {
        mAppRepo.addTask(task);
    }

    public LiveData<List<Tag>> getAllTags()
    {
        return tags;
    }

    public LiveData<List<Task>> getAllTasks()
    {
        return tasks;
    }
}
