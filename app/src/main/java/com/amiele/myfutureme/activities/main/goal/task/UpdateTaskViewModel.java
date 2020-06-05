package com.amiele.myfutureme.activities.main.goal.task;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.amiele.myfutureme.database.AppRepo;
import com.amiele.myfutureme.database.entity.SubTask;
import com.amiele.myfutureme.database.entity.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Update Task View Model is used to handle the actions between Update Task View and Database
 */
public class UpdateTaskViewModel extends AndroidViewModel {
    private static AppRepo mAppRepo;

    private int taskId;
    private static MediatorLiveData<Task> taskMediatorLiveData ;
    private static Task task = new Task();

    public UpdateTaskViewModel(@NonNull Application application) {
        super(application);
        mAppRepo = new AppRepo(application);
        taskMediatorLiveData = new MediatorLiveData<>();
    }

    //Live data to observe tasks from database
    public LiveData<Task> getTask() {return taskMediatorLiveData;}

    void setTaskId(int taskId)
    {
        this.taskId = taskId;
    }

    // Load task information and its sub-tasks from database
    void loadTask()
    {
        LiveData<Task> taskLiveData = mAppRepo.loadTask(taskId);

        LiveData<List<SubTask>> subTasksLiveData = mAppRepo.loadSubTasks(taskId);

        taskMediatorLiveData.addSource(taskLiveData, task -> {
            if (task!=null) {
                UpdateTaskViewModel.task.setTaskInfo(task);
                taskMediatorLiveData.setValue(UpdateTaskViewModel.task);
            }
        });

        taskMediatorLiveData.addSource(subTasksLiveData, subTasks -> {
            if (subTasks!=null) {
                task.setSubTasksList((ArrayList<SubTask>) subTasks);
                taskMediatorLiveData.setValue(task);
            }
        });
    }

    void updateName(String name)
    {
        mAppRepo.updateTaskName(name,taskId);
    }
    void updateMinute(int minute)
    {
        mAppRepo.updateTaskMinute(minute,taskId);
    }

    void updateProgress(String progress)
    {
        int value = 0;
        if (progress.length()>0)
            value = Integer.parseInt(progress);

        mAppRepo.updateTaskProgress(value,taskId);
    }

    void deleteSubTask(int subTaskId)
    {
        mAppRepo.deleteSubTask(subTaskId);
    }

    void deleteTask()
    {
        mAppRepo.deleteTask(taskId);
    }

    void addSubTask(SubTask subTask)
    {
        subTask.setTaskId(taskId);
        mAppRepo.addSubTask(subTask);
    }
}
