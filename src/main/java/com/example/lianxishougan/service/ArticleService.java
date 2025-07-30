package com.example.lianxishougan.service;

import com.example.lianxishougan.pojo.Article;
import com.example.lianxishougan.pojo.PageBean;

public interface ArticleService {
    void add(Article article);

    PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state);

    Article detail(Integer id);

    void update(Article article);

    void delete(Integer id);
}
