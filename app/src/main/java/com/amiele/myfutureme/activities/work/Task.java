package com.amiele.myfutureme.activities.work;

import java.util.ArrayList;

public class Task {

    int id;
    String name;
    boolean isComplete;

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    int progress;

    public ArrayList<SubTask> getSubTasksList() {
        return subTasksList;
    }

    public void setSubTasksList(ArrayList<SubTask> subTasksList) {
        this.subTasksList = subTasksList;
    }

    ArrayList<SubTask> subTasksList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Task( String description){
        this.name = description;
        this.subTasksList = new ArrayList<>();
        this.isComplete = false;
        this.progress = 0;

    }

    public void addSubTask(String description)
    {
        subTasksList.add(new SubTask(description));
    }
    public void addSubTask(String description, String date, int hour)
    {
        subTasksList.add(new SubTask(description,date,hour));
    }

    public void setIsComplete(boolean isCompleteValue)
    {
        this.isComplete = isCompleteValue;
    }

}
