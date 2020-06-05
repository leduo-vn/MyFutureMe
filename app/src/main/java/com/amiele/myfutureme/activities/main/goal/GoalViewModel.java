package com.amiele.myfutureme.activities.main.goal;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.amiele.myfutureme.database.AppRepo;
import com.amiele.myfutureme.database.entity.Goal;
import com.amiele.myfutureme.database.entity.Tag;
import com.amiele.myfutureme.database.entity.Task;
import com.amiele.myfutureme.database.entity.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Goal View Model is used to handle the actions between Goal View and Database
 */
public class GoalViewModel extends AndroidViewModel {

    private static AppRepo mAppRepo;
    private static User user;
    private static ArrayList<Goal> goalList;
    private static MediatorLiveData<List<Goal>> goalMediatorLiveData;
    private static MutableLiveData<Boolean> userResultMutableLiveData;

    public GoalViewModel(@NonNull Application application) {
        super(application);
        mAppRepo = new AppRepo(application);
        goalMediatorLiveData = new MediatorLiveData<>();
        userResultMutableLiveData = new MutableLiveData<>();
        loadUser();
    }

    // Live data to observe the load signed user
    LiveData<Boolean> getUserResult() {
        return userResultMutableLiveData;
    }

    // Live data to observe the goals
    LiveData<List<Goal>> getAllGoals() { return goalMediatorLiveData; }

    // If user signout - modify the status inside the database
    static void updateSignedInUserToSignedOut()
    {
        mAppRepo.UpdateUserSignInStatus(user.getId(),false);
    }

    // Load signed in user
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
                userResultMutableLiveData.setValue(true);
            }
        }.execute();
    }

    public int getUserId()
    {
        return user.getId();
    }

    // Load all information of the goals and their related tasks
    private static void loadAllLoad(int userId)
    {
       LiveData<List<Integer>> goalIds = Transformations.map(mAppRepo.loadGoals(userId), input -> {
           ArrayList<Integer> goalIds1 = new ArrayList<>();
           for (Goal goal:input)
           {
               goalIds1.add(goal.getId());

           }
           goalList  = new ArrayList<>(input);
           return goalIds1;
       });

       LiveData<List<Task>> tasks = Transformations.switchMap(goalIds, input -> mAppRepo.loadTasks(input));

       LiveData<List<Tag>> tags = Transformations.switchMap(goalIds, input -> mAppRepo.loadTags(input));

       //if there is any update of tasks, Assign goals with new task lists
       goalMediatorLiveData.addSource(tasks, taskList -> {
            for (Goal goal:goalList)
            {
                goal.setTaskList(new ArrayList<>());
            }

            for (Task task:taskList)
            {
                for (Goal goal:goalList)
                {
                    if (goal.getId() == task.getGoalId())
                    {
                        goal.addTask(task);
                        break;
                    }
                }
            }
            goalMediatorLiveData.setValue(goalList);
        });

       // if there is any update with tags, Assign goals with new tag list
        goalMediatorLiveData.addSource(tags, tags1 -> {
            for (Goal goal:goalList)
                goal.setTagList(new ArrayList<>());

            for (Tag tag: tags1)
            {
                for (Goal goal:goalList)
                {
                    if (goal.getId() == tag.getGoalId())
                    {
                        goal.addTag(tag);
                        break;
                    }
                }
            }
            goalMediatorLiveData.setValue(goalList);
        });

    }

}
//        LiveData<List<Goal>> finalGoal = Transformations.map(tasks, new Function<List<Task>, List<Goal>>() {
//            @Override
//            public List<Goal> apply(List<Task> input) {
//                ArrayList<Goal> goals = new ArrayList<>(goalList);
//
//                for (Task task:input)
//                {
//                    for (Goal goal: goals)
//                    {
//                        if (goal.getId() == task.getGoalId())
//                        {
//                            goal.addTask(task);
//                            break;
//                        }
//                    }
//                }
//
//                return goals;
//            }
//        });
//        goals = finalGoal;