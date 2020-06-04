package com.amiele.myfutureme.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.amiele.myfutureme.database.entity.TagLibrary;

import java.util.List;

@Dao
public interface TagLibraryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addTagLibrary(TagLibrary tagLibrary);

    @Query("SELECT * FROM tag_library WHERE user_id = :userId")
    LiveData<List<TagLibrary>> loadTags(int userId);

    @Query("DELETE FROM tag_library WHERE tag_library_id = :tagLibraryId")
    void deleteTagLibrary(int tagLibraryId);
}
