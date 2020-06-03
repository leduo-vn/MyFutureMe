package com.amiele.myfutureme.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "tags",
        foreignKeys = {
        @ForeignKey(entity = Goal.class,
                        parentColumns = "goal_id",
                        childColumns = "goal_id",
                        onDelete = ForeignKey.CASCADE),
            @ForeignKey(entity = TagLibrary.class,
                parentColumns = "tag_library_id",
                childColumns = "tag_library_id",
                onDelete = ForeignKey.CASCADE)},
        indices = {@Index(value = {"goal_id","tag_library_id"})
        })
public class Tag {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "tag_id")
    private int id;

    @ColumnInfo(name = "tag_library_id")
    private int tagLibraryId;

    private String name;

    private int color;

    @ColumnInfo(name = "goal_id")
    private int goalId;

    @Ignore
    private boolean isChosen;

    public Tag()
    {
    }

    @Ignore
    public Tag(TagLibrary tag)
    {
        this.name = tag.getName();
        this.color = tag.getColor();
        this.tagLibraryId = tag.getId();
        this.isChosen = false;
    }

    @Ignore
    public Tag(String name, int color)
    {
        this.name = name;
        this.color = color;
    }

    public boolean isChosen() {
        return isChosen;
    }

    public void setChosen(boolean chosen) {
        isChosen = chosen;
    }

    public int getTagLibraryId() {
        return tagLibraryId;
    }

    public void setTagLibraryId(int tagLibraryId) {
        this.tagLibraryId = tagLibraryId;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
