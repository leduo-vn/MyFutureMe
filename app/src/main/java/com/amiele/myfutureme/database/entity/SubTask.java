package com.amiele.myfutureme.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "sub_tasks",
        foreignKeys = {
                @ForeignKey(entity = User.class,
                        parentColumns = "id",
                        childColumns = "task_id",
                        onDelete = ForeignKey.CASCADE)},
        indices = {@Index(value = "task_id")
        })public class SubTask {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "sub_task_id")
    private int mId;

    @ColumnInfo(name = "task_id")
    private int mTaskId;

    @ColumnInfo(name = "description")
    private String mDescription;

    @ColumnInfo(name = "minute")
    private int mMinute;

    @ColumnInfo(name = "date")
    private String mDate;

    @Ignore
    private static final String DATE_SEPARATOR ="-";

    public SubTask()
    {
    }

    @Ignore
    public SubTask( String description){
        mDescription = description;
        mMinute = 0;
        mDate ="";
    }

    @Ignore
    public SubTask(String description, String date, int minute)
    {
        mDescription = description;
        mDate = date;
        mMinute = minute;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getDate_DOW(){
        String[] items = mDate.split(DATE_SEPARATOR);
        return items[0];
    }

    public String getDate_Date(){
        String[] items = mDate.split(DATE_SEPARATOR);
        return items[1];
    }

    public int getMinute() {
        return mMinute;
    }

}
