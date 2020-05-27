package com.amiele.myfutureme.database.entity;

import java.util.ArrayList;

public class Goal {

    private int mId;
    private String mName;
    private String mDescription;
    private String mDueDate;
    private ArrayList<Task> mTaskList;

    private int mColor;

    private ArrayList<Tag> mTagList;

    public Goal()
    {
        this.mTaskList = new ArrayList<>();

    }

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
