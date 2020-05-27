package com.amiele.myfutureme.activities.goal;

public class SubTask {

    private int mId;
    private String mDescription;
    private int mMinute;
    private String mDate;

    private static final String DATE_SEPARATOR ="-";

    public SubTask( String description){
        mDescription = description;
        mMinute = 0;
        mDate ="";
    }

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
