package com.amiele.myfutureme.activities.work;

import java.util.ArrayList;

public class Task {

    int id;
    String description;
    boolean isComplete;
    ArrayList<SubTask> subTasksList;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    class SubTask{

        int id;
        String description;
        double hour;

        public SubTask( String description){
            this.description = description;
            this.hour = 0;
        }
    }

    public Task( String description){
        this.description = description;
        this.subTasksList = new ArrayList<>();
        this.isComplete = false;
    }

    public void addSubTasks(String description)
    {
        subTasksList.add(new SubTask(description));
    }

    public void setIsComplete(boolean isCompleteValue)
    {
        this.isComplete = isCompleteValue;
    }

}
