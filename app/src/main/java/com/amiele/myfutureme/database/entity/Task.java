package com.amiele.myfutureme.database.entity;

import java.util.ArrayList;

public class Task {

    private int mId;
    private String mName;
    private int mProgress;
    private ArrayList<SubTask> mSubTasksList;

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
