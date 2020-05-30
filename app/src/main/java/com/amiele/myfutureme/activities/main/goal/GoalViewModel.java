package com.amiele.myfutureme.activities.main.goal;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;

import com.amiele.myfutureme.AppRepo;
import com.amiele.myfutureme.database.entity.Goal;
import com.amiele.myfutureme.database.entity.Tag;
import com.amiele.myfutureme.database.entity.Task;
import com.amiele.myfutureme.database.entity.User;

import java.util.ArrayList;
import java.util.List;

public class GoalViewModel extends AndroidViewModel {

    private static AppRepo mAppRepo;
    private static LiveData<List<Goal>> goals;
    private static LiveData<List<Task>> tasks;
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


    private static MutableLiveData<Boolean> getUserSignOutResult = new MutableLiveData<>();
    public LiveData<Boolean> getUserSignOutResult() {
        return getUserSignOutResult;
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
//        return goals;

            return finalGoal;
    }

    public int getUserId()
    {
        return user.getId();
    }

    public static void updateSignedInUser(boolean status)
    {
        mAppRepo.UpdateUserSignInStatus(user.getId(),false);
    }

    static ArrayList<Goal> goalList;
    static MediatorLiveData<List<Goal>> finalGoal = new MediatorLiveData<>();

    public static void loadAllLoad(int userId)
    {
       // goals = mAppRepo.loadGoals(userId);
       LiveData<List<Integer>> goalIds = Transformations.map(mAppRepo.loadGoals(userId), new Function<List<Goal>, List<Integer>>() {

            @Override
            public List<Integer> apply(List<Goal> input) {
                ArrayList<Integer> goalIds = new ArrayList<>();
                for (Goal goal:input)
                {
                    goalIds.add(goal.getId());

                }
                goalList  = new ArrayList<>(input);
                return goalIds;
            }
        });


       LiveData<List<Task>> tasks = Transformations.switchMap(goalIds, new Function<List<Integer>, LiveData<List<Task>>>() {
           @Override
           public LiveData<List<Task>> apply(List<Integer> input) {
               return mAppRepo.loadTasks(input);
           }
       });

        LiveData<List<Tag>> tags = Transformations.switchMap(goalIds, new Function<List<Integer>, LiveData<List<Tag>>>() {
            @Override
            public LiveData<List<Tag>> apply(List<Integer> input) {
                return mAppRepo.loadTags(input);
            }
        });

        finalGoal.addSource(tasks, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> taskList) {
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
                finalGoal.setValue(goalList);
            }
        });

        finalGoal.addSource(tags, new Observer<List<Tag>>() {
            @Override
            public void onChanged(List<Tag> tags) {
                for (Goal goal:goalList)
                    goal.setTagList(new ArrayList<>());

                for (Tag tag:tags)
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
                finalGoal.setValue(goalList);
            }
        });

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

    }




    public void loadAllTasksOfGoals(List<Integer> goalIds)
    {
        mAppRepo.loadTasks(goalIds);
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
