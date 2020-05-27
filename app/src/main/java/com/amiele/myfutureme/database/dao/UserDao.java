package com.amiele.myfutureme.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.amiele.myfutureme.database.entity.User;

@Dao
public interface UserDao {

    /* Throw the SQLiteConstraintException when user existed */
    @Insert(onConflict = OnConflictStrategy.ABORT)
    void addUser(User user);

    @Query("select * from users where user_id = :userId")
    User loadUserSync(int userId);
}
