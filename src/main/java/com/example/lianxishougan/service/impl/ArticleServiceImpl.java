package com.example.lianxishougan.service.impl;

import com.example.lianxishougan.mapper.ArticleMapper;
import com.example.lianxishougan.mapper.UserMapper;
import com.example.lianxishougan.pojo.Article;
import com.example.lianxishougan.pojo.ArticleInfo;
import com.example.lianxishougan.pojo.PageBean;
import com.example.lianxishougan.service.ArticleService;
import com.example.lianxishougan.utils.ThreadLocalUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Resource
    private UserMapper userMapper;

    @Override
    public void add(Article article) {
        article.setCreateTime(LocalDateTime.now());
        article.setUpdateTime(LocalDateTime.now());

        Map<Service, Object> map = ThreadLocalUtil.get();
        Integer userId= (Integer) map.get("id");
        article.setCreateUser(userId);

        articleMapper.add(article);
    }

    @Override
    public PageBean<ArticleInfo> list(Integer pageNum, Integer pageSize, Integer categoryId, String state) {

        PageBean<ArticleInfo> pb=new PageBean<>();
        PageBean<ArticleInfo> pc=new PageBean<>();

        PageHelper.startPage(pageNum,pageSize);

        Map<String,Object> map = ThreadLocalUtil.get();
        Integer userId= (Integer) map.get("id");
        Integer isAdmin= (Integer) map.get("is_admin");

        if(isAdmin==1){
            // PageHelper 对后续第一个查询生效，返回的 list 实际是 Page 类型
            List<ArticleInfo> asi = articleMapper.listAllWithUser();
            // 强制转换为 Page 类型（安全，因为 PageHelper 会代理查询）
            Page<ArticleInfo> pa = (Page<ArticleInfo>) asi;

            pc.setTotal(pa.getTotal());
            pc.setItems(pa.getResult());
            return pc;


        }else{
            // 非管理员查询：只查自己的作品
            List<ArticleInfo> articleList = articleMapper.list(userId,categoryId,state);
            // 转换为 Page 类型（PageHelper 代理后的结果）
            Page<ArticleInfo> articlePage = (Page<ArticleInfo>) articleList;

            pb.setTotal(articlePage.getTotal());
            pb.setItems(articlePage.getResult());
            return pb;
        }

    }

    @Override
    public Article detail(Integer id) {
        Article article =articleMapper.findById(id);
        return article;
    }

    @Override
    public void update(Article article) {
        article.setUpdateTime(LocalDateTime.now());
        articleMapper.update(article);
    }

    @Override
    public void delete(Integer id) {
        articleMapper.delete(id);
    }

    @Override
    public void advice(Integer id, String state, String advice) {
        articleMapper.advice(id,state,advice);
    }

    @Override
    public PageBean<ArticleInfo> listAllAdvice(Integer pageNum, Integer pageSize, Integer categoryId, String state) {
        PageBean<ArticleInfo> pb = new PageBean<>();
        PageBean<ArticleInfo> pc = new PageBean<>();

        PageHelper.startPage(pageNum, pageSize);

        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        Integer isAdmin = (Integer) map.get("is_admin");

        if (isAdmin == 1) {
            // PageHelper 对后续第一个查询生效，返回的 list 实际是 Page 类型
            List<ArticleInfo> asi = articleMapper.listAllWithUserAdvice();
            // 强制转换为 Page 类型（安全，因为 PageHelper 会代理查询）
            Page<ArticleInfo> pa = (Page<ArticleInfo>) asi;

            pc.setTotal(pa.getTotal());
            pc.setItems(pa.getResult());
            return pc;


        } else {
            // 非管理员查询：只查自己的作品
            List<ArticleInfo> articleList = articleMapper.listAllAdviceUser(userId,categoryId,state);
            // 转换为 Page 类型（PageHelper 代理后的结果）
            Page<ArticleInfo> articlePage = (Page<ArticleInfo>) articleList;

            pb.setTotal(articlePage.getTotal());
            pb.setItems(articlePage.getResult());
            return pb;
        }
    }
}
