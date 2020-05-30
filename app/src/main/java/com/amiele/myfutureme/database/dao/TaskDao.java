package com.amiele.myfutureme.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.amiele.myfutureme.database.entity.Goal;
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

    @Query("SELECT * FROM tasks where goal_id = :goalId")
    List<Task> loadTasksSync(int goalId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Task> taskList);

    /* Throw the SQLiteConstraintException when user existed */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addTask(Task task);
}
