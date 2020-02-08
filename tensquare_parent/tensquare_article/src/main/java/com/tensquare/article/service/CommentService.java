package com.tensquare.article.service;

import com.tensquare.article.dao.CommentRepository;
import com.tensquare.article.pojo.Comment;
import com.tensquare.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
    @Resource
    private MongoTemplate mongoTemplate;



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

    public void thumbup(String id) {
        //方式一需要两次查询
//        Comment comment = commentRepository.findById(id).get();
//        comment.setThumbup(comment.getThumbup()+1);
//        commentRepository.save(comment);


        //方式二优化
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));

        Update update = new Update();
        update.inc("thumbup",1);
        mongoTemplate.updateFirst(query,update,"comment");
    }
}