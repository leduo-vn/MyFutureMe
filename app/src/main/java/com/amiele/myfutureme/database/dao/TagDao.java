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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addTag(Tag tag);

    @Query("DELETE FROM tags WHERE tag_id = :tagId")
    void deleteTag(int tagId);

    @Query("SELECT * FROM tags where goal_id = :goalId")
    LiveData<List<Tag>> loadTags(int goalId);

    @Query("SELECT * FROM tags where goal_id IN (:goalIdList)")
    LiveData<List<Tag>> loadTags(List<Integer> goalIdList);

}
