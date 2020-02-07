package com.tensquare.article.controller;

import com.tensquare.article.pojo.Comment;
import com.tensquare.article.service.CommentService;
import com.tensquare.entity.Result;
import com.tensquare.entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author haixin
 * @time 2020/2/7
 */
@RestController
@RequestMapping("/comment")
@CrossOrigin
public class CommentController {

    @Autowired
    private CommentService commentService;

    //新增
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Comment comment) {
        commentService.save(comment);
        return new Result(true, StatusCode.OK, "新增成功");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable String id) {
        commentService.deleteById(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Result update(@PathVariable String id,
                         @RequestBody Comment comment) {
        comment.set_id(id);
        commentService.update(comment);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        List<Comment> list = commentService.findAll();
        return new Result(true, StatusCode.OK, "查询成功", list);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable String id) {
        Comment comment = commentService.findById(id);
        return new Result(true, StatusCode.OK, "查询成功", comment);
    }

    @RequestMapping(value = "/article/{articleId}", method = RequestMethod.GET)
    public Result findByarticleId(@PathVariable String articleId) {
        List<Comment> list = commentService.findByarticleId(articleId);
        return new Result(true, StatusCode.OK, "查询成功", list);
    }
}
