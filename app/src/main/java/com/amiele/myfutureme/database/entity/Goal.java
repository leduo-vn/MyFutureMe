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
                        parentColumns = "user_id",
                        childColumns = "user_id",
                        onDelete = ForeignKey.CASCADE)},
        indices = {@Index(value = "user_id")
        })
public class Goal {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "goal_id")
    private int id;

    @ColumnInfo(name = "user_id")
    private int userId;

    private String name;

    private String description;

    @ColumnInfo(name = "due_date")
    private String dueDate;

    private int color;

    public Goal()
    {
    }

    @Ignore
    private ArrayList<Task> taskList;

    @Ignore
    private ArrayList<Tag> tagList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    @Ignore
    public Goal(String name, String description)
    {
        this.name = name;
        this.description = description;
        taskList = new ArrayList<>();
        tagList = new ArrayList<>();
    }

    @Ignore
    public Goal(int userId, String name, String description, String dueDate, int color)
    {
        this.userId = userId;
        this.name= name;
        this.description =description;
        this.dueDate = dueDate;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(ArrayList<Task> taskList) {
        this.taskList = taskList;
    }

    public void addTask(String name)
    {
        taskList.add(new Task(name));
    }

    public ArrayList<Tag> getTagList() {
        return tagList;
    }

    public void setTagList(ArrayList<Tag> tagList) {
        this.tagList = tagList;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int Color) {
        this.color = color;
    }
}
