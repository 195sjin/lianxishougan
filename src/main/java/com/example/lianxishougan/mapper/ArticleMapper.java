package com.example.lianxishougan.mapper;

import com.example.lianxishougan.pojo.Article;
import com.example.lianxishougan.pojo.ArticleInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ArticleMapper {
    @Insert("insert into article(title,content,cover_img,state,category_id,create_user,create_time,update_time)"+
    "values (#{title},#{content},#{coverImg},#{state},#{categoryId},#{createUser},#{createTime},#{updateTime})")
    void add(Article article);

//    @Select("SELECT a.*" +
//            "FROM article a " +
//            "WHERE create_user=#{userId} and (a.state = '待审批' or a.state='草稿') " +  // 补全单引号，并用空格分隔条件与排序
//            "ORDER BY a.create_time DESC")
    List<ArticleInfo> list(Integer userId,Integer categoryId, String state);

    @Select("select * from article where id=#{id}")
    Article findById(Integer id);

    @Update("update article set title=#{title},content=#{content},cover_img=#{coverImg},state=#{state},category_id=#{categoryId},update_time=#{updateTime} where id=#{id}")
    void update(Article article);

    @Delete("delete from article where id=#{id}")
    void delete(Integer id);

    // 管理员查询：关联用户表，一次性获取用户名
    @Select("SELECT a.*, u.username " +
            "FROM article a " +
            "LEFT JOIN user u ON a.create_user = u.id " +
            "WHERE a.state = '待审批' " +  // 补全单引号，并用空格分隔条件与排序
            "ORDER BY a.update_time DESC")
    List<ArticleInfo> listAllWithUser();

    @Update("update article set state=#{state},advice=#{advice},update_time=now() where id=#{id}")
    void advice(Integer id,String state, String advice);

    //管理员查询已经审批过的数据
    @Select("SELECT a.*, u.username " +
            "FROM article a " +
            "LEFT JOIN user u ON a.create_user = u.id " +
            "WHERE a.state = '审批成功' or a.state='审批失败' " +  // 补全单引号，并用空格分隔条件与排序
            "ORDER BY a.update_time DESC")
    List<ArticleInfo> listAllWithUserAdvice();

    //用户查看自己已经被审批过的作品
//    @Select("SELECT a.*" +
//            "FROM article a " +
//            "WHERE create_user=#{userId} and (a.state = '审批成功' or a.state='审批失败') " +  // 补全单引号，并用空格分隔条件与排序
//            "ORDER BY a.create_time DESC")
    List<ArticleInfo> listAllAdviceUser(Integer userId,Integer categoryId, String state);
}
