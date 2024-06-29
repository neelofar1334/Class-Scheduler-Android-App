package com.example.c196.entities;

import androidx.room.Entity;

@Entity(tableName = "admin")
public class Admin extends User {
    private String permissions;

    public Admin(String id, String username, String password, String userType, String permissions) {
        super(id, username, password, userType);
        this.permissions = permissions;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }
}
