package com.amiele.myfutureme.database.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class User {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int mId;

    @ColumnInfo(name = "username")
    private String mUsername;

    @ColumnInfo(name = "email")
    private String mEmail;

    @ColumnInfo(name = "password")
    private String mPassword;

    public User(@NonNull String username, @NonNull String email, @NonNull String password)
    {
        mUsername = username;
        mEmail = email;
        mPassword = password;
    }

    public int getId() {
        return mId;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        this.mUsername = username;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        this.mEmail = email;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        this.mPassword = password;
    }
}
