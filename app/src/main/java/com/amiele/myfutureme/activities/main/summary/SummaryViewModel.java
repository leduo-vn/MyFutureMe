package com.amiele.myfutureme.activities.main.summary;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.amiele.myfutureme.database.AppRepo;
import com.amiele.myfutureme.database.entity.Goal;
import com.amiele.myfutureme.database.entity.SubTask;
import com.amiele.myfutureme.database.entity.Tag;
import com.amiele.myfutureme.database.entity.Task;

import java.util.ArrayList;
import java.util.List;

public class SummaryViewModel extends AndroidViewModel {
    private static AppRepo mAppRepo;
    private static ArrayList<Goal> goalList;
    private static MediatorLiveData<List<Goal>> goalMediatorLiveData;
    MediatorLiveData<List<Task>> taskMediatorLiveData;

    private int userId;

    public SummaryViewModel(@NonNull Application application) {
        super(application);
        mAppRepo = new AppRepo(application);
        goalMediatorLiveData = new MediatorLiveData<>();
        taskMediatorLiveData  = new MediatorLiveData<>();
        goalList = new ArrayList<>();
    }

    public void setUserId(int userId)
    {
        this.userId = userId;
    }
    LiveData<List<SubTask>> subTasks = new MutableLiveData<>();

    public MediatorLiveData<List<Goal>> getGoalMediatorLiveData() {return goalMediatorLiveData;}
    public LiveData<List<SubTask>> getSubTaskLiveData() {return subTasks;}
    public void loadAllLoad()
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

        subTasks = Transformations.switchMap(tasks, input -> {
            ArrayList<Integer> taskIds = new ArrayList<>();
//            for (Goal goal:goalList)
//                    goal.setTaskList(new ArrayList<>());
            for (Task task:input)
            {
                taskIds.add(task.getId());
//                for (Goal goal: goalList)
//                    if (goal.getId() == task.getGoalId())
//                        goal.addTask(task);
            }
            return mAppRepo.loadSubTasks(taskIds);
        });

        LiveData<List<Tag>> tags = Transformations.switchMap(goalIds, input -> mAppRepo.loadTags(input));

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

//        goalMediatorLiveData.addSource(subTasks, subTaskList -> {
//            for (Goal goal:goalList) {
//                goal.setMinute(0);
//                if (goal.getTaskList() != null)
//                    for (Task task : goal.getTaskList())
//                        for (SubTask subTask:subTaskList)
//                            if (subTask.getTaskId()== task.getId())
//                                    goal.setMinute(goal.getMinute()+subTask.getMinute());
//            }
//            goalMediatorLiveData.setValue(goalList);
//        });

        goalMediatorLiveData.addSource(tasks, taskList -> {
            for (Goal goal:goalList)
                goal.setTaskList(new ArrayList<>());
            for (Task task:taskList)
            {
                for (Goal goal: goalList)
                    if (goal.getId() == task.getGoalId()) {
                        goal.addTask(task);
                        break;
                    }
            }
            goalMediatorLiveData.setValue(goalList);
        });

    }
}
