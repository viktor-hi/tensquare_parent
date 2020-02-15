package com.tensquare.article.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.tensquare.article.pojo.Article;
import com.tensquare.article.service.ArticleService;
import com.tensquare.entity.PageResult;
import com.tensquare.entity.Result;
import com.tensquare.entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author haixin
 * @time 2020/2/4
 */
@RestController
@RequestMapping("/article")
@CrossOrigin
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @RequestMapping(value = "/{articleId}",method = RequestMethod.GET)
    public Result findById(@PathVariable("articleId") String articleId) {
        Article article = articleService.findById(articleId);
        return new Result(true, StatusCode.OK, "文章查询成功", article);
    }

    //新增文章
    @RequestMapping( method = RequestMethod.POST)
    public Result add(@Validated @RequestBody Article article) {
        articleService.add(article);
        return new Result(true, StatusCode.OK, "添加成功");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Result update(@PathVariable String id, @RequestBody Article article) {
        article.setId(id);
        articleService.update(article);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable String id) {
        articleService.delete(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    /**
     * 文章分页
     * @param map
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/search/{page}/{size}",method = RequestMethod.POST)
    public Result search(@RequestBody Map map, @PathVariable(value = "page") int page, @PathVariable(value = "size")int size) {
        Page<Article> pageList = articleService.searchPage(map,page,size);
        PageResult pageResult = new PageResult(pageList.getTotal(),pageList.getRecords());
        return new Result(true, StatusCode.OK, "分页查询成功",pageResult);
    }
}
