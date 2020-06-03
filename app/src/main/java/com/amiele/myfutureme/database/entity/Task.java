package com.amiele.myfutureme.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity(tableName = "tasks",
        foreignKeys = {
                @ForeignKey(entity = Goal.class,
                        parentColumns = "goal_id",
                        childColumns = "goal_id",
                        onDelete = ForeignKey.CASCADE)},
        indices = {@Index(value = "goal_id")
        })
public class Task {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "task_id")
    private int id;

    private String name;

    private int progress;

    private int minute;

    @ColumnInfo(name = "goal_id")
    private int goalId;

    @Ignore
    private ArrayList<SubTask> subTasksList;

    public Task()
    {

    }

    @Ignore
    public Task(int goalId, String name, int progress)
    {
        this.goalId = goalId;
        this.name = name;
        this.progress = progress;
        this.minute = 0;
    }



    @Ignore
    public Task( String name, int progress){
        this.name = name;
        this.progress = progress;
        this.minute = 0;
    }

    @Ignore
    public Task( String name){
        this.name = name;
        subTasksList = new ArrayList<>();
        progress = 0;
        minute = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGoalId() {
        return goalId;
    }

    public void setGoalId(int goalId) {
        this.goalId = goalId;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public ArrayList<SubTask> getSubTasksList() {
        return subTasksList;
    }

    public void setSubTasksList(ArrayList<SubTask> subTasksList) {
        this.subTasksList = subTasksList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void addSubTask(String description, String date, int hour)
    {
        subTasksList.add(new SubTask(description,date,hour));
    }

    public void setTaskInfo(Task task)
    {
        this.id = task.id;
        this.name = task.name;
        this.progress = task.progress;
        this.goalId = task.goalId;
        this.minute = task.minute;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
}
