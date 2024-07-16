package com.karimi.c196.Stubs;

import androidx.lifecycle.LiveData;

import com.karimi.c196.DAO.AdminDAO;
import com.karimi.c196.entities.Admin;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StubAdminDAO implements AdminDAO {
    private final Map<String, Admin> adminMap = new HashMap<>();

    @Override
    public void insert(Admin admin) {
        adminMap.put(admin.getUsername(), admin);
    }

    @Override
    public LiveData<Admin> getAdminByUsername(String username) {
        return null;
    }

    @Override
    public LiveData<List<Admin>> getAllAdmins() {
        return null;
    }

}