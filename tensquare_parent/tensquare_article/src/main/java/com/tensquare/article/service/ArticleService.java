package com.tensquare.article.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;

import com.tensquare.article.client.NoticeClient;
import com.tensquare.article.dao.ArticleDao;
import com.tensquare.article.pojo.Article;
import com.tensquare.notice.pojo.Notice;
import com.tensquare.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    @Autowired
    private NoticeClient noticeClient;

    @Autowired
    private RedisTemplate redisTemplate;

    public List<Article> findAll(){
        return articleDao.selectList(null);
    }

    public Article findById(String articleId) {
        return articleDao.selectById(articleId);
    }

    public void add(Article article) {
        //新增文章
        String authorId = "1";
        article.setId(idWorker.nextId() + "");
        article.setVisits(0);   //浏览量
        article.setThumbup(0);  //点赞数
        article.setComment(0);  //评论数
        article.setUserid(authorId);


        Set<String> members = redisTemplate.opsForSet().members("article_author_" + authorId);
        for (String userId : members) {
            Notice notice = new Notice();
            notice.setReceiverId(userId);
            notice.setOperatorId(authorId);
            notice.setAction("publish");
            notice.setTargetType("article");
            notice.setTargetId(article.getId());
            notice.setCreatetime(new Date());
            notice.setType("sys");
            notice.setState("0");
            noticeClient.add(notice);

        }
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

    /**
     * 文章订阅
     * @param userId
     * @param articleId
     * @return
     */
    public boolean subscribe(String userId, String articleId) {
        String authorId = articleDao.selectById(articleId).getUserid();

        //当前用户关注的作者set
        String userKey = "article_subscribe_"+userId;
        //该作者被关注的用户set
        String authorKey = "article_author_"+authorId;

        Boolean isMember = redisTemplate.opsForSet().isMember(userKey, authorId);

        //判断是否已经订阅
        if(isMember){
            redisTemplate.opsForSet().remove(userKey,authorId);
            redisTemplate.opsForSet().remove(authorKey,userId);
            return false;
        }else{
            redisTemplate.opsForSet().add(userKey,authorId);
            redisTemplate.opsForSet().add(authorKey,userId);
            return true;
        }
    }

    /**
     * 文章点赞数加一
     * @param articleId
     */
    public void thumbup(String articleId, String userId) {
        Article article = articleDao.selectById(articleId);
        article.setThumbup(article.getThumbup()+1);
        articleDao.updateById(article);

        //消息通知
        Notice notice = new Notice();
        notice.setReceiverId(article.getUserid());
        notice.setOperatorId(userId);
        notice.setAction("thumbup");
        notice.setTargetType("article");
        notice.setTargetId(articleId);
        notice.setCreatetime(new Date());
        notice.setType("user");
        notice.setState("0");

        noticeClient.add(notice);
    }
}
