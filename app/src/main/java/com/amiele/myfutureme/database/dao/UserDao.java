package com.amiele.myfutureme.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import com.amiele.myfutureme.database.entity.User;

@Dao
public interface UserDao {

    /* Throw the SQLiteConstraintException when user existed */
    @Insert(onConflict = OnConflictStrategy.ABORT)
    void addUser(User user);


}
