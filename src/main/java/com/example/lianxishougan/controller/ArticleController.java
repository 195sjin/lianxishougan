package com.example.lianxishougan.controller;

import com.example.lianxishougan.pojo.Article;
import com.example.lianxishougan.pojo.ArticleInfo;
import com.example.lianxishougan.pojo.PageBean;
import com.example.lianxishougan.pojo.Result;
import com.example.lianxishougan.service.ArticleService;
import com.example.lianxishougan.utils.JwtUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Resource
    private ArticleService articleService;

    @PostMapping
    public Result add(@RequestBody @Validated Article article) {
        articleService.add(article);
        return Result.success();
    }
    @GetMapping
    public Result<PageBean<ArticleInfo>> list(Integer pageNum,
                                              Integer pageSize,
                                              @RequestParam(required = false) Integer categoryId,
                                              @RequestParam(required = false) String state){
        PageBean<ArticleInfo> pb=articleService.list(pageNum,pageSize,categoryId,state);
        return Result.success(pb);
    }
    @GetMapping("/detail")
    public Result detail(Integer id){
        Article article = articleService.detail(id);
        return Result.success(article);
    }
    @PutMapping
    public Result update(@RequestBody @Validated Article article){
        articleService.update(article);
        return Result.success();

    }
    @DeleteMapping
    public Result delete(Integer id){
        articleService.delete(id);
        return Result.success();
    }

    //文章建议
    @PutMapping("/advice")
    public Result advice(@RequestBody Map<String, Object> params) {
        // 从Map中获取参数
        Integer id = params.get("id") != null ? Integer.parseInt(params.get("id").toString()) : null;
        String state = params.get("state") != null ? params.get("state").toString() : null;
        String advice = params.get("advice") != null ? params.get("advice").toString() : null;

        // 调用服务层方法
        articleService.advice(id, state, advice);
        return Result.success();
    }
}
