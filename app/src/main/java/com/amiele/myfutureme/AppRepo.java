package com.amiele.myfutureme;

import android.app.Application;
import android.database.sqlite.SQLiteConstraintException;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.amiele.myfutureme.database.AppDatabase;
import com.amiele.myfutureme.database.dao.GoalDao;
import com.amiele.myfutureme.database.dao.SubTaskDao;
import com.amiele.myfutureme.database.dao.TagDao;
import com.amiele.myfutureme.database.dao.TagLibraryDao;
import com.amiele.myfutureme.database.dao.TaskDao;
import com.amiele.myfutureme.database.dao.UserDao;
import com.amiele.myfutureme.database.entity.Goal;
import com.amiele.myfutureme.database.entity.TagLibrary;
import com.amiele.myfutureme.database.entity.Task;
import com.amiele.myfutureme.database.entity.User;

import java.util.List;

public class AppRepo {


    private UserDao mUserDao;
    private GoalDao mGoalDao;
    private TaskDao mTaskDao;
    private TagDao mTagDao;
    private SubTaskDao mSubTaskDao;
    private TagLibraryDao mtagLibraryDao;


    public  AppRepo(Application application)
    {
        AppDatabase db = AppDatabase.getDatabase(application);
        mUserDao = db.userDao();
        mGoalDao = db.goalDao();
        mTaskDao = db.taskDao();
        mTagDao = db.tagDao();
        mSubTaskDao = db.subTaskDao();
        mtagLibraryDao = db.tagLibraryDao();
    }

//    public void addKeyword(Keyword keyword) {
//        new insertAsyncTask(keywordDao).doInBackground(keyword);
//    }
//
//
//    private static class getSignedInUserAsyncTask extends AsyncTask<User, Void, Void> {
//        private UserDao mAsyncUserDao;
//
//        getSignedInUserAsyncTask(UserDao dao) {
//            mAsyncUserDao = dao;
//        }
//
//        @Override
//        protected Void doInBackground(User... users) {
//            return mAsyncUserDao.getUserSignedIn();
////            return null;
//        }
//    }



    public LiveData<User> getUserByEmail(String email) {
        return mUserDao.getUser(email);
    }

    public User getUserAsyncByEmail(String email) {
        return mUserDao.getUserAsync(email);
    }

    public User getSignedInUser() {
        return mUserDao.getUserSignedIn();
    }

    public void UpdateUserSignInStatus(int userId, boolean isSignedIn)
    {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mUserDao.updateLoginStatus(userId,isSignedIn);
        });
    }


    public void addUser(User user) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mUserDao.addUser(user);
        });
    }

    public LiveData<List<Goal>> loadGoals(int userId) {
         return mGoalDao.loadGoals(userId);

    }

    public Long addGoal(Goal goal) {
         return mGoalDao.addGoalAsync(goal);

    }

    public void addAllTasks(List<Task> taskList)
    {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mTaskDao.insertAll(taskList);
        });
    }

    public LiveData<List<Task>> loadTasks(int goalId) {
        return mTaskDao.loadTasks(goalId);
    }

    public LiveData<List<Task>> loadTasks(List<Integer> goalIdList) {
        return mTaskDao.loadTasks(goalIdList);
    }

    public void addTask(Task task) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mTaskDao.addTask(task);
        });
    }

    public void deleteGoal(int goalId)
    {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mGoalDao.deleteGoal(goalId);
        });
    }

    public LiveData<List<TagLibrary>> loadAllLibraryTag()
    {
        return mtagLibraryDao.loadTags();
    }

    public void addLibraryTag(TagLibrary tag)
    {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mtagLibraryDao.addTagLibrary(tag);
        });
    }
}
