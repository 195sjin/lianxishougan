package com.example.lianxishougan.service;

import com.example.lianxishougan.pojo.Article;
import com.example.lianxishougan.pojo.ArticleInfo;
import com.example.lianxishougan.pojo.PageBean;

public interface ArticleService {
    void add(Article article);

    PageBean<ArticleInfo> list(Integer pageNum, Integer pageSize, Integer categoryId, String state);

    Article detail(Integer id);

    void update(Article article);

    void delete(Integer id);

    void advice(Integer id, String state,String advice);
}
