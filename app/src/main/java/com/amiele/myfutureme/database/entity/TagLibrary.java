package com.amiele.myfutureme.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "tag_library",
        foreignKeys = {
                @ForeignKey(entity = User.class,
                        parentColumns = "user_id",
                        childColumns = "user_id",
                        onDelete = ForeignKey.CASCADE)},
        indices = {@Index(value = "user_id")
        })
public class TagLibrary {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "tag_library_id")
    private int id;

    @ColumnInfo(name = "user_id")
    private int userId;

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    private int color;

    private String name;

    public TagLibrary()
    {

    }

    @Ignore
    public TagLibrary(String name, int color, int userId)
    {
        this.name = name;
        this.color = color;
        this.userId = userId;
    }
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
