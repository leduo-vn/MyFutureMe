package com.amiele.myfutureme.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


import com.amiele.myfutureme.database.entity.SubTask;

import java.util.List;

@Dao
public interface SubTaskDao {

    @Query("SELECT * FROM sub_tasks where task_id = :taskId")
    LiveData<List<SubTask>> loadSubTasks(int taskId);

    @Query("SELECT * FROM sub_tasks where task_id = :taskId")
    List<SubTask> loadSubTasksSync(int taskId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<SubTask> tagList);
}
