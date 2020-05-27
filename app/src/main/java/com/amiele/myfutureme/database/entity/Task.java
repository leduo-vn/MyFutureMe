package com.amiele.myfutureme.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity(tableName = "tasks",
        foreignKeys = {
                @ForeignKey(entity = User.class,
                        parentColumns = "id",
                        childColumns = "user_id",
                        onDelete = ForeignKey.CASCADE)},
        indices = {@Index(value = "user_id")
        })
public class Task {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "task_id")
    private int mId;

    @ColumnInfo(name = "name")
    private String mName;

    @ColumnInfo(name = "progress")
    private int mProgress;

    @ColumnInfo(name = "user_id")
    private int mUserId;

    @Ignore
    private ArrayList<SubTask> mSubTasksList;

    public Task()
    {

    }

    @Ignore
    public Task( String name, int progress){
        mName = name;
        mProgress = progress;
    }

    @Ignore
    public Task( String name){
        mName = name;
        mSubTasksList = new ArrayList<>();
        mProgress = 0;
    }


    public ArrayList<SubTask> getSubTasksList() {
        return mSubTasksList;
    }

    public void setSubTasksList(ArrayList<SubTask> subTasksList) {
        this.mSubTasksList = subTasksList;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }


    public void addSubTask(String description, String date, int hour)
    {
        mSubTasksList.add(new SubTask(description,date,hour));
    }

    public int getProgress() {
        return mProgress;
    }

    public void setProgress(int progress) {
        this.mProgress = progress;
    }
}
