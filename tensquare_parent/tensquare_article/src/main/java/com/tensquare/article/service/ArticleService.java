package com.tensquare.article.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.tensquare.article.dao.ArticleDao;
import com.tensquare.article.pojo.Article;
import com.tensquare.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * @author haixin
 * @time 2020/2/4
 */
@Transactional
@Service
public class ArticleService {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private IdWorker idWorker;

    public List<Article> findAll(){
        return articleDao.selectList(null);
    }

    public Article findById(String articleId) {
        return articleDao.selectById(articleId);
    }

    public void add(Article article) {
        //1.新增文章
        String authorId = "1";
        article.setId(idWorker.nextId() + "");
        article.setVisits(0);   //浏览量
        article.setThumbup(0);  //点赞数
        article.setComment(0);  //评论数
        article.setUserid(authorId);

        articleDao.insert(article);
    }

    public void update(Article article) {
        articleDao.updateById(article);
    }

    public void delete(String id) {
        articleDao.deleteById(id);
    }

    public Page<Article> searchPage(Map map, int page, int size) {
        Page<Article> articlePage = new Page<>(page,size);
        EntityWrapper<Article> where = new EntityWrapper<>();

        if(!StringUtils.isEmpty(map.get("columnid"))){
            where.eq("columnid",map.get("columnid"));
        }
        if(!StringUtils.isEmpty(map.get("userid"))){
            where.eq("userid",map.get("userid"));
        }
        if(!StringUtils.isEmpty(map.get("title"))){
            // 不用补%, 下同
            where.like("title",map.get("title").toString());
        }
        if(!StringUtils.isEmpty(map.get("content"))){
            where.like("content","%"+map.get("content")+"%");
        }

        List<Article> list = articleDao.selectPage(articlePage,where);
        articlePage.setRecords(list);
        return articlePage;
    }
}
