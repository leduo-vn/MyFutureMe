package com.amiele.myfutureme.activities.main.goal.task;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import com.amiele.myfutureme.AppRepo;
import com.amiele.myfutureme.database.entity.SubTask;
import com.amiele.myfutureme.database.entity.Task;

import java.util.ArrayList;
import java.util.List;

public class UpdateTaskViewModel extends AndroidViewModel {
    private static AppRepo mAppRepo;
    private int taskId;

    public UpdateTaskViewModel(@NonNull Application application) {
        super(application);
        mAppRepo = new AppRepo(application);
    }

    public void setTaskId(int taskId)
    {
        this.taskId = taskId;
    }

    static MediatorLiveData<Task> mediatorLiveData = new MediatorLiveData<>();

    static Task finalTask;

    public LiveData<Task> getTask() {return mediatorLiveData;}
    public void loadTask()
    {
        LiveData<Task> taskLiveData = mAppRepo.loadTask(taskId);

        LiveData<List<SubTask>> subTasksLiveData = mAppRepo.loadSubTasks(taskId);

        mediatorLiveData.addSource(taskLiveData, new Observer<Task>() {
            @Override
            public void onChanged(Task task) {
                finalTask = task;
                mediatorLiveData.setValue(finalTask);
            }
        });

        mediatorLiveData.addSource(subTasksLiveData, new Observer<List<SubTask>>() {
            @Override
            public void onChanged(List<SubTask> subTasks) {
                finalTask.setSubTasksList((ArrayList<SubTask>) subTasks);
                mediatorLiveData.setValue(finalTask);
            }
        });
    }

    public void addSubTask(SubTask subTask)
    {
        subTask.setTaskId(taskId);
        mAppRepo.addSubTask(subTask);
    }
}
