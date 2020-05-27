package com.amiele.myfutureme.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.amiele.myfutureme.database.dao.UserDao;
import com.amiele.myfutureme.database.entity.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {User.class},version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "app_db";
    public abstract UserDao userDao();

    private static volatile AppDatabase sINSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static AppDatabase getDatabase(final Context context) {
        if (sINSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (sINSTANCE == null) {
                    sINSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, DATABASE_NAME)
                            .build();
                }
            }
        }
        return sINSTANCE;
    }
}
