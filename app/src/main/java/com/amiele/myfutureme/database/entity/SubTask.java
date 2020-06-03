package com.amiele.myfutureme.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "sub_tasks",
        foreignKeys = {
                @ForeignKey(entity = Task.class,
                        parentColumns = "task_id",
                        childColumns = "task_id",
                        onDelete = ForeignKey.CASCADE)},
        indices = {@Index(value = "task_id")
        })public class SubTask {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "sub_task_id")
    private int id;

    @ColumnInfo(name = "task_id")
    private int taskId;

    private String description;

    private int minute;

    private String date;

    @Ignore
    private static final String DATE_SEPARATOR ="-";

    public SubTask()
    {
    }

    @Ignore
    public SubTask( String description){
        this.description = description;
        minute = 0;
        date ="";
    }

    @Ignore
    public SubTask(String description, String date, int minute)
    {
        this.description = description;
        this.date = date;
        this.minute = minute;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public String getDescription() {
        return description;
    }

    public String getDate_DOW(){
        String[] items = date.split(DATE_SEPARATOR);
        return items[0];
    }

    public String getDate_Date(){
        String[] items = date.split(DATE_SEPARATOR);
        return items[1];
    }

    public int getMinute() {
        return minute;
    }

}
