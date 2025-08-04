package com.example.lianxishougan.controller;

import com.example.lianxishougan.pojo.Admin;
import com.example.lianxishougan.pojo.Result;
import com.example.lianxishougan.pojo.User;
import com.example.lianxishougan.service.AdminService;
import com.example.lianxishougan.service.UserService;
import com.example.lianxishougan.utils.JwtUtil;
import com.example.lianxishougan.utils.Md5Util;
import com.example.lianxishougan.utils.ThreadLocalUtil;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
            map.put("is_admin",admin.getIsAdmin());
            System.out.println("======================================================="+admin.getIsAdmin());
            String token = JwtUtil.genToken(map);
            return Result.success(token);
        }
        return Result.error("密码错误");
    }


    @GetMapping("/adminInfo")
    public Result<Admin> adminInfo(/*@RequestHeader(name = "Authorization") String token*/){
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        Admin admin = adminService.findByName(username);
        return Result.success(admin);
    }

    @PutMapping("/update")
    public Result upadte(@RequestBody @Validated Admin admin){
        adminService.update(admin);
        return Result.success();
    }

    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam @URL String avatarUrl){
        adminService.updateAvatar(avatarUrl);
        return Result.success();
    }

    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody Map<String,String> params){
        String oldPwd = params.get("old_Pwd");
        String newPwd = params.get("new_Pwd");
        String rePwd  = params.get("re_Pwd");

        if (!StringUtils.hasLength(oldPwd) || !StringUtils.hasLength(newPwd) || !StringUtils.hasLength(rePwd)){
            return Result.error("缺少必要的参数");
        }

        Map<String,Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        Admin admin = adminService.findByName(username);
        if (!admin.getPassword().equals(Md5Util.getMD5String(oldPwd))){
            return Result.error("原密码不正确");
        }

        if (!rePwd.equals(newPwd)){
            return Result.error("两次填写的密码不一样");
        }

        adminService.updatePwd(newPwd);
        return Result.success();
    }
}
