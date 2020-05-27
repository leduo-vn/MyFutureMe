package com.amiele.myfutureme.database.entity;

import androidx.room.Entity;

@Entity(tableName = "tags")
public class Tag {

    private int mId;
    private String mName;
    private int mColor;

    public Tag(String name, int color)
    {
        mName = name;
        mColor = color;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public int getColor() {
        return mColor;
    }

    public void setColor(int color) {
        this.mColor = color;
    }
}
