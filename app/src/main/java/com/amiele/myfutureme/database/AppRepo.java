package com.amiele.myfutureme.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.amiele.myfutureme.database.AppDatabase;
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


    //region User

    public void addUser(User user) {
        AppDatabase.databaseWriteExecutor.execute(() -> mUserDao.addUser(user));
    }

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
        AppDatabase.databaseWriteExecutor.execute(() -> mUserDao.updateLoginStatus(userId,isSignedIn));
    }

    //endregion

    //region LibraryTag

    public void addLibraryTag(TagLibrary tag)
    {
        AppDatabase.databaseWriteExecutor.execute(() -> mtagLibraryDao.addTagLibrary(tag));
    }

    public LiveData<List<TagLibrary>> loadAllLibraryTag(int userId)
    {
        return mtagLibraryDao.loadTags(userId);
    }

    //endregion

    //region Goal
    public LiveData<Goal> loadGoal(int goalId)
    {
        return mGoalDao.loadGoal(goalId);
    }

    public Long addGoal(Goal goal) {
        return mGoalDao.addGoalAsync(goal);
    }

    public LiveData<List<Goal>> loadGoals(int userId) {
         return mGoalDao.loadGoals(userId);
    }

    public void deleteGoal(int goalId)
    {
        AppDatabase.databaseWriteExecutor.execute(() -> mGoalDao.deleteGoal(goalId));
    }

    public void updateGoalName(String name, int goalId)
    {
        AppDatabase.databaseWriteExecutor.execute(() -> mGoalDao.updateName(name,goalId));

    }

    public void updateGoalDescription(String description, int goalId)
    {
        AppDatabase.databaseWriteExecutor.execute(() -> mGoalDao.updateDescription(description,goalId));

    }

    public void updateGoalDueDate(String dueDate, int goalId)
    {
        AppDatabase.databaseWriteExecutor.execute(() -> mGoalDao.updateDueDate(dueDate,goalId));

    }

    //endregion goal

    //region tag

    public void addTag(Tag tag) {
        AppDatabase.databaseWriteExecutor.execute(() -> mTagDao.addTag(tag));
    }

    public void deleteTag(int tagId)
    {
        AppDatabase.databaseWriteExecutor.execute(() -> mTagDao.deleteTag(tagId));
    }

    //endregion

    //region task

    public void addTask(Task task) {
        AppDatabase.databaseWriteExecutor.execute(() -> mTaskDao.addTask(task));
    }

    public LiveData<List<Task>> loadTasks(int goalId) {
        return mTaskDao.loadTasks(goalId);
    }

    public LiveData<List<Task>> loadTasks(List<Integer> goalIdList) {
        return mTaskDao.loadTasks(goalIdList);
    }

    public LiveData<Task> loadTask(int taskId)
    {
        return mTaskDao.loadTask(taskId);
    }

    public void updateTaskName(String name, int taskId)
    {
        AppDatabase.databaseWriteExecutor.execute(() -> mTaskDao.updateName(name,taskId));

    }

    public void updateTaskMinute(int minute, int taskId)
    {
        AppDatabase.databaseWriteExecutor.execute(() -> mTaskDao.updateMinute(minute,taskId));

    }

    public void updateTaskProgress(int progress, int taskId)
    {
        AppDatabase.databaseWriteExecutor.execute(() -> mTaskDao.updateProgress(progress,taskId));

    }

    public void deleteTask(int taskId)
    {
        AppDatabase.databaseWriteExecutor.execute(() -> mTaskDao.deleteTask(taskId));
    }
    //endregion

    //region sub-task

    public void addSubTask(SubTask subTask)
    {
        AppDatabase.databaseWriteExecutor.execute(() -> mSubTaskDao.addSubTask(subTask));
    }

    public LiveData<List<SubTask>> loadSubTasks(int taskId)
    {
        return mSubTaskDao.loadSubTasks(taskId);
    }

    public LiveData<List<SubTask>> loadSubTasks(List<Integer> taskIds)
    {
        return mSubTaskDao.loadSubTasks(taskIds);
    }

    public void deleteSubTask(int subTaskId)
    {
        AppDatabase.databaseWriteExecutor.execute(() -> mSubTaskDao.deleteSubTask(subTaskId));

    }

    //endregion

    //region tag

    public LiveData<List<Tag>> loadTags(int goalId)
    {
        return mTagDao.loadTags(goalId);
    }

    public LiveData<List<Tag>> loadTags(List<Integer> goalList)
    {
        return mTagDao.loadTags(goalList);
    }

    //endregion
}
