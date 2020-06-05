package com.amiele.myfutureme.activities.main.goal;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.amiele.myfutureme.database.AppRepo;
import com.amiele.myfutureme.database.entity.Goal;
import com.amiele.myfutureme.database.entity.Tag;
import com.amiele.myfutureme.database.entity.Task;

import java.util.List;

/**
 * Add Goal View Model is used to handle the actions between Add Goal View and Database
 */
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

    // Live Data to observe the goal information from database
    public LiveData<Goal> getGoal()
    {
        return goalLiveData;
    }

    // Live data to observe tags information from database
    LiveData<List<Tag>> getAllTags()
    {
        return tagsLiveData;
    }

    // Live data to observe tasks information from database
    LiveData<List<Task>> getAllTasks()
    {
        return tasksLiveData;
    }

    //Live data to observe the result of get goal Id when create a new goal
    LiveData<Integer> getGoalIdResult() {
        return goalIdResultMutableLiveData;
    }

    // Async task to add new goal and assign the id
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

    // Load tasks, tags, and goal from database using goalId
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
