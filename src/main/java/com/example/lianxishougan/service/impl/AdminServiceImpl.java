package com.example.lianxishougan.service.impl;

import com.example.lianxishougan.mapper.AdminMapper;
import com.example.lianxishougan.pojo.Admin;
import com.example.lianxishougan.pojo.User;
import com.example.lianxishougan.service.AdminService;
import com.example.lianxishougan.utils.Md5Util;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    @Resource
    private AdminMapper adminMapper;

    @Override
    public void register(String username, String password) {
        String md5String = Md5Util.getMD5String(password);
        adminMapper.add(username,md5String);
    }

    @Override
    public Admin findByName(String username) {
        Admin admin = adminMapper.findByName(username);
        return admin;
    }
}
