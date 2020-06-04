package com.amiele.myfutureme.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.amiele.myfutureme.database.entity.Task;

import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM tasks where goal_id = :goalId")
    LiveData<List<Task>> loadTasks(int goalId);

    @Query("SELECT * FROM tasks where task_id = :taskId")
    LiveData<Task> loadTask(int taskId);

    @Query("SELECT * FROM tasks where goal_id IN (:goalIdList)")
    LiveData<List<Task>> loadTasks(List<Integer> goalIdList);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addTask(Task task);

    @Query("UPDATE tasks SET name = :name  WHERE task_id = :taskId")
    void updateName(String name,int taskId);

    @Query("UPDATE tasks SET progress = :progress  WHERE task_id = :taskId")
    void updateProgress(int progress,int taskId);

    @Query("UPDATE tasks SET minute = :minute  WHERE task_id = :taskId")
    void updateMinute(int minute,int taskId);

    @Query("DELETE FROM tasks WHERE task_id = :taskId")
    void deleteTask(int taskId);
}
