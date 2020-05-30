package com.amiele.myfutureme.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.amiele.myfutureme.database.entity.User;

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addUser(User user);

    @Query("select * from users where email = :email")
    LiveData<User> getUser(String email);

    @Query("select * from users where email = :email")
    User getUserAsync(String email);

    @Query("select * from users where is_signed_in = 1")
    User getUserSignedIn();

    @Query("UPDATE users SET is_signed_in = :isLogin  WHERE user_id = :userId")
    void updateLoginStatus(int userId, boolean isLogin);
}
