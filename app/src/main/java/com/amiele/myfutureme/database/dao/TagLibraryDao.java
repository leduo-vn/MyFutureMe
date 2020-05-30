package com.amiele.myfutureme.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.amiele.myfutureme.database.entity.Tag;
import com.amiele.myfutureme.database.entity.TagLibrary;
import com.amiele.myfutureme.database.entity.User;

import java.util.List;

@Dao
public interface TagLibraryDao {

    /* Throw the SQLiteConstraintException when user existed */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addTagLibrary(TagLibrary tagLibrary);

    @Query("SELECT * FROM tag_library WHERE user_id = :userId")
    LiveData<List<TagLibrary>> loadTags(int userId);
}
