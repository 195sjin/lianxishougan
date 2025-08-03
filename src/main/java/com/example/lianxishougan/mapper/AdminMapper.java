package com.example.lianxishougan.mapper;

import com.example.lianxishougan.pojo.Admin;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AdminMapper {
    @Select("select * from admin where username=#{username}")
    Admin findByName(String username);

    @Insert("insert into admin(username,password,create_time,update_time)"+ "values (#{username},#{password},now(),now())")
    void add(String username, String password);
}
