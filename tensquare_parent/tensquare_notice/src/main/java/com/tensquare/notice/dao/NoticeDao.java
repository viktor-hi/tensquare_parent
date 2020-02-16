package com.tensquare.notice.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.tensquare.notice.pojo.Notice;
import org.springframework.stereotype.Repository;

/**
 * @author haixin
 * @time 2020/2/16
 */
@Repository
public interface NoticeDao extends BaseMapper<Notice> {
}