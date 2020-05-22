package com.amiele.myfutureme.activities.work;

import java.util.ArrayList;

public class WorkTask {
    int id;
    String name;
    String description;
    String dueDate;
    ArrayList<Task> taskList;

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

    public ArrayList<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(ArrayList<Task> taskList) {
        for (Task task:taskList)
        {
            this.taskList.add(new Task(task.description));
        }
    }

    public WorkTask()
    {
        this.taskList = new ArrayList<>();

    }

    public WorkTask(String name, String description)
    {
        this.name = name;
        this.description =description;
        this.taskList = new ArrayList<>();
    }

    public void addTask(String description)
    {
        taskList.add(new Task(description));
    }

}
