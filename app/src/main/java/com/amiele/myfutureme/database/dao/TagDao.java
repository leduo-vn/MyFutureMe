package com.amiele.myfutureme.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.amiele.myfutureme.database.entity.Tag;

import java.util.List;

@Dao
public interface TagDao {

    @Query("SELECT * FROM tags where goal_id = :goalId")
    LiveData<List<Tag>> loadTags(int goalId);

    @Query("SELECT * FROM tags where goal_id = :goalId")
    List<Tag> loadTagsSync(int goalId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Tag> tagList);
}
