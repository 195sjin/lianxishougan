package com.example.lianxishougan.service.impl;

import com.example.lianxishougan.mapper.ArticleMapper;
import com.example.lianxishougan.pojo.Article;
import com.example.lianxishougan.pojo.PageBean;
import com.example.lianxishougan.service.ArticleService;
import com.example.lianxishougan.utils.ThreadLocalUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

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
    public PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state) {

        PageBean<Article> pb=new PageBean<>();

        PageHelper.startPage(pageNum,pageSize);

        Map<String,Object> map = ThreadLocalUtil.get();
        Integer userId= (Integer) map.get("id");
        Integer isAdmin= (Integer) map.get("is_admin");
        List<Article> as;
        if(isAdmin==1){
            as=articleMapper.listAll();
        }else{
            as=articleMapper.list(userId,categoryId,state);
        }

        Page<Article> p=(Page<Article>) as;

        pb.setTotal(p.getTotal());
        pb.setItems(p.getResult());
        return pb;
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
}
