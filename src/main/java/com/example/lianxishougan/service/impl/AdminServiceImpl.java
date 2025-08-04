package com.example.lianxishougan.service.impl;

import com.example.lianxishougan.mapper.AdminMapper;
import com.example.lianxishougan.pojo.Admin;
import com.example.lianxishougan.pojo.User;
import com.example.lianxishougan.service.AdminService;
import com.example.lianxishougan.utils.Md5Util;
import com.example.lianxishougan.utils.ThreadLocalUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

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


    @Override
    public void update(Admin admin) {
        admin.setUpdateTime(LocalDateTime.now());
        adminMapper.update(admin);
    }

    @Override
    public void updateAvatar(String avatarUrl) {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        adminMapper.updateAvatar(avatarUrl,id);
    }

    @Override
    public void updatePwd(String newPwd) {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        adminMapper.updatePwd(Md5Util.getMD5String(newPwd),id);
    }
}
