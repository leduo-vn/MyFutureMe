package com.amiele.myfutureme.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.amiele.myfutureme.database.entity.Goal;

import java.util.List;

@Dao
public interface GoalDao {

    @Query("SELECT * FROM goals where user_id = :userId")
    LiveData<List<Goal>> loadGoals(int userId);

    @Query("SELECT * FROM goals where user_id = :userId")
    List<Goal> loadGoalsSync(int userId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Goal> goalList);

}
