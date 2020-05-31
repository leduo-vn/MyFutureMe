package com.amiele.myfutureme.activities.main.goal;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.amiele.myfutureme.AppRepo;
import com.amiele.myfutureme.database.entity.Goal;
import com.amiele.myfutureme.database.entity.Tag;
import com.amiele.myfutureme.database.entity.Task;

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

    private static MutableLiveData<Integer> goalIdResult = new MutableLiveData<>();
    public LiveData<Integer> getGoalIdResult() {
        return goalIdResult;
    }



    public void deleteGoal()
    {
        mAppRepo.deleteGoal(goalId);
    }

    public LiveData<Goal> getGoal()
    {
        return goal;
    }

    private static LiveData<Goal> goal;


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
            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            protected void onPostExecute(Long aLong) {
                super.onPostExecute(aLong);

                goalIdResult.setValue(goalId);
            }
        }.execute();

    }

    public static void loadAllLoad()
    {
        tasks = mAppRepo.loadTasks(goalId);
        tags = mAppRepo.loadTags(goalId);
        goal = mAppRepo.loadGoal(goalId);
    }

    public void updateName(String name)
    {
        mAppRepo.updateGoalName(name,goalId);
    }

    public void updateDescription(String description)
    {
        mAppRepo.updateGoalDescription(description, goalId);
    }

    public void updateDueDate(String dueDate)
    {
        mAppRepo.updateGoalDueDate(dueDate,goalId);
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
