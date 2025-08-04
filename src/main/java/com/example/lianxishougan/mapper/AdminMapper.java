package com.example.lianxishougan.mapper;

import com.example.lianxishougan.pojo.Admin;
import com.example.lianxishougan.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface AdminMapper {
    @Select("select * from admin where username=#{username}")
    Admin findByName(String username);

    @Insert("insert into admin(username,password,is_admin,create_time,update_time)"+ "values (#{username},#{password},1,now(),now())")
    void add(String username, String password);

    @Update("update admin set nickname=#{nickname},email=#{email},update_time=#{updateTime} where id=#{id}")
    void update(Admin admin);

    @Update("update admin set user_pic=#{avatarUrl},update_time=now() where id=#{id}")
    void updateAvatar(String avatarUrl, Integer id);

    @Update("update admin set password=#{md5String},update_time=now() where id=#{id}")
    void updatePwd(String md5String, Integer id);
}
