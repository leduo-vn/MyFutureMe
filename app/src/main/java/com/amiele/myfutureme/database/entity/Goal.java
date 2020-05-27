package com.amiele.myfutureme.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity(tableName = "goals",
        foreignKeys = {
                @ForeignKey(entity = User.class,
                        parentColumns = "id",
                        childColumns = "user_id",
                        onDelete = ForeignKey.CASCADE)},
        indices = {@Index(value = "user_id")
        })
public class Goal {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "goal_id")
    private int mId;

    @ColumnInfo(name = "user_id")
    private int mUserId;

    @ColumnInfo(name = "name")
    private String mName;

    @ColumnInfo(name = "description")
    private String mDescription;

    @ColumnInfo(name = "due_date")
    private String mDueDate;

    @ColumnInfo(name = "color")
    private int mColor;

    public Goal()
    {
    }

    @Ignore
    private ArrayList<Task> mTaskList;

    @Ignore
    private ArrayList<Tag> mTagList;



    @Ignore
    public Goal(String name, String description)
    {
        mName = name;
        mDescription = description;
        mTaskList = new ArrayList<>();
        mTagList = new ArrayList<>();
    }

    public String getName() {
        return mName;
    }

    public String getDescription() {
        return mDescription;
    }

    public ArrayList<Task> getTaskList() {
        return mTaskList;
    }

    public void setTaskList(ArrayList<Task> taskList) {
        this.mTaskList = taskList;
    }

    public void addTask(String name)
    {
        mTaskList.add(new Task(name));
    }

    public ArrayList<Tag> getTagList() {
        return mTagList;
    }

    public void setTagList(ArrayList<Tag> tagList) {
        this.mTagList = tagList;
    }

    public int getColor() {
        return mColor;
    }

    public void setColor(int Color) {
        this.mColor = mColor;
    }
}
