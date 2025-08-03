package com.example.lianxishougan.controller;

import com.example.lianxishougan.pojo.Admin;
import com.example.lianxishougan.pojo.Result;
import com.example.lianxishougan.pojo.User;
import com.example.lianxishougan.service.AdminService;
import com.example.lianxishougan.service.UserService;
import com.example.lianxishougan.utils.JwtUtil;
import com.example.lianxishougan.utils.Md5Util;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @PostMapping("/register")
    public Result register(String username, String password) {
        if (username!=null&&password!=null&&
                username.length()>=5&&password.length()>=5&&
                username.length()<=16&&password.length()<=16){
            Admin a = adminService.findByName(username);
            if (a == null) {
                adminService.register(username,password);
                return Result.success();
            }else {
                return Result.error("用户名已经被占用");
            }
        }else {
            return Result.error("参数不合法");
        }


    }

    @PostMapping("/login")
    public Result<String> login(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password) {
        Admin admin=adminService.findByName(username);
        if (admin==null){
            return Result.error("用户名错误");
        }
        if (Md5Util.getMD5String(password).equals(admin.getPassword())){
            Map<String,Object> map = new HashMap<>();
            map.put("id",admin.getId());
            map.put("username",admin.getUsername());
            String token = JwtUtil.genToken(map);
            return Result.success(token);
        }
        return Result.error("密码错误");
    }
}
