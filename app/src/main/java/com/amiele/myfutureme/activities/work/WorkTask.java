package com.amiele.myfutureme.activities.work;

import java.util.ArrayList;

public class WorkTask {
    int id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    String name;
    String description;
    String dueDate;
    ArrayList<Task> taskList;

    public WorkTask(String name, String description)
    {
        this.name = name;
        this.description =description;
     //   this.dueDate = dueDate;
        this.taskList = new ArrayList<>();
    }

    public WorkTask()
    {
        this.taskList = new ArrayList<>();

    }

    public void addTask(String description)
    {
        taskList.add(new Task(description));
    }

}
