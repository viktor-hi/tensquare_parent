package com.tensquare.article.service;

import com.tensquare.article.dao.CommentRepository;
import com.tensquare.article.pojo.Comment;
import com.tensquare.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author haixin
 * @time 2020/2/7
 */
@Service
public class CommentService {
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private CommentRepository commentRepository;

    public void save(Comment comment) {
        String id = idWorker.nextId() + "";
        comment.set_id(id);
        //初始化数据
        comment.setPublishdate(new Date());
        comment.setThumbup(0);
        commentRepository.save(comment);
    }

    public void deleteById(String id) {
        commentRepository.deleteById(id);
    }

    public void update(Comment comment) {
        commentRepository.save(comment);
    }

    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    public Comment findById(String id) {
        return commentRepository.findById(id).get();
    }

    public List<Comment> findByarticleId(String articleId) {
        return commentRepository.findByArticleid(articleId);
    }
}