package com.amiele.myfutureme.database.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class User {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id")
    private int id;

    private String username;

    private String email;

    private String password;

    @ColumnInfo(name = "is_signed_in")
    private boolean isSignedIn;

    public User()
    {

    }

    @Ignore
    public User(@NonNull String username, @NonNull String email, @NonNull String password)
    {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public void setId(int mId) {
        this.id = mId;
    }
    public int getId() {
        return id;
    }

    public boolean isSignedIn() {
        return isSignedIn;
    }

    public void setSignedIn(boolean signedIn) {
        isSignedIn = signedIn;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
