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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long addGoalAsync(Goal goal);

    @Query("SELECT * FROM goals where user_id = :userId")
    LiveData<List<Goal>> loadGoals(int userId);

    @Query("SELECT * FROM goals where goal_id = :goalId")
    LiveData<Goal> loadGoal(int goalId);

    @Query("DELETE FROM goals WHERE goal_id = :goalId")
    void deleteGoal(int goalId);

    @Query("UPDATE goals SET name = :name  WHERE goal_id = :goalId")
    void updateName(String name,int goalId);

    @Query("UPDATE goals SET description = :description  WHERE goal_id = :goalId")
    void updateDescription(String description,int goalId);

    @Query("UPDATE goals SET due_date = :dueDate  WHERE goal_id = :goalId")
    void updateDueDate(String dueDate,int goalId);
}
