package com.amiele.myfutureme.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.amiele.myfutureme.database.dao.GoalDao;
import com.amiele.myfutureme.database.dao.SubTaskDao;
import com.amiele.myfutureme.database.dao.TagDao;
import com.amiele.myfutureme.database.dao.TagLibraryDao;
import com.amiele.myfutureme.database.dao.TaskDao;
import com.amiele.myfutureme.database.dao.UserDao;
import com.amiele.myfutureme.database.entity.Goal;
import com.amiele.myfutureme.database.entity.SubTask;
import com.amiele.myfutureme.database.entity.Tag;
import com.amiele.myfutureme.database.entity.TagLibrary;
import com.amiele.myfutureme.database.entity.Task;
import com.amiele.myfutureme.database.entity.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;

@Database(entities = {User.class, Goal.class, Task.class, Tag.class, SubTask.class, TagLibrary.class},version = 13, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "app_db";

    public abstract UserDao userDao();
    public abstract GoalDao goalDao();
    public abstract TaskDao taskDao();
    public abstract TagDao tagDao();
    public abstract SubTaskDao subTaskDao();
    public abstract TagLibraryDao tagLibraryDao();

    private static volatile AppDatabase sINSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static AppDatabase getDatabase(final Context context) {
        if (sINSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (sINSTANCE == null) {
                    sINSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return sINSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }
    };
}
