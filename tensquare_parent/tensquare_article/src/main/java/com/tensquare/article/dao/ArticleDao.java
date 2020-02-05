package com.tensquare.article.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.tensquare.article.pojo.Article;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author haixin
 * @time 2020/2/4
 */
@Mapper
public interface ArticleDao extends BaseMapper<Article> {
}
