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

    class SubTask{

        int id;

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setHour(int hour) {
            this.hour = hour;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getDate_DOW(){
            String[] items = date.split("-");
            return items[0];
        }

        public String getDate_Date(){
            String[] items = date.split("-");
            return items[1];
        }

        String description;

        public int getHour() {
            return hour;
        }

        public String getDate() {
            return date;
        }

        int hour;
        String date;

        public SubTask( String description){
            this.description = description;
            this.hour = 0;
            this.date="";
        }

        public SubTask(String description, String date, int hour)
        {
            this.description= description;
            this.date = date;
            this.hour = hour;
        }
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
