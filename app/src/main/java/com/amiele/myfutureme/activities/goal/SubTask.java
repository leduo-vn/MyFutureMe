package com.amiele.myfutureme.activities.goal;

public class SubTask {
    int id;
    String description;
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


    public int getHour() {
        return hour;
    }

    public String getDate() {
        return date;
    }



}
