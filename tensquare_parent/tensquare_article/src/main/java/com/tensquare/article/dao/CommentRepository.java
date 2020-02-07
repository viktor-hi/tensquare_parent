package com.tensquare.article.dao;

import com.tensquare.article.pojo.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * @author haixin
 * @time 2020/2/7
 */
public interface CommentRepository extends MongoRepository<Comment,String> {

    List<Comment> findByArticleid(String articleId);

}
