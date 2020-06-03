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

    private static LiveData<List<Task>> tasksLiveData;
    private static LiveData<List<Tag>> tagsLiveData;
    private static LiveData<Goal> goalLiveData;

    private static MutableLiveData<Integer> goalIdResultMutableLiveData ;

    private static int goalId;

    public AddGoalViewModel(@NonNull Application application) {
        super(application);
        mAppRepo = new AppRepo(application);
        goalIdResultMutableLiveData = new MutableLiveData<>();
    }

    public LiveData<Goal> getGoal()
    {
        return goalLiveData;
    }

    LiveData<List<Tag>> getAllTags()
    {
        return tagsLiveData;
    }

    LiveData<List<Task>> getAllTasks()
    {
        return tasksLiveData;
    }

    LiveData<Integer> getGoalIdResult() {
        return goalIdResultMutableLiveData;
    }

    static void addGoal(Goal goal)
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
                goalIdResultMutableLiveData.setValue(goalId);
            }
        }.execute();

    }

    static void loadAllLoad()
    {
        tasksLiveData = mAppRepo.loadTasks(goalId);
        tagsLiveData = mAppRepo.loadTags(goalId);
        goalLiveData = mAppRepo.loadGoal(goalId);
    }

    public void setGoalId(int goalId)
    {
        this.goalId = goalId;
    }

    void deleteGoal()
    {
        mAppRepo.deleteGoal(goalId);
    }

    void updateName(String name)
    {
        mAppRepo.updateGoalName(name,goalId);
    }

    void updateDescription(String description)
    {
        mAppRepo.updateGoalDescription(description, goalId);
    }

    void updateDueDate(String dueDate)
    {
        mAppRepo.updateGoalDueDate(dueDate,goalId);
    }

    void addTask(Task task)
    {
        mAppRepo.addTask(task);
    }

}
