package com.example.lianxishougan.service;

import com.example.lianxishougan.pojo.Admin;
import com.example.lianxishougan.pojo.User;

public interface AdminService {
    void register(String username, String password);

    Admin findByName(String username);

    void update(Admin admin);

    void updateAvatar(String avatarUrl);

    void updatePwd(String newPwd);
}
