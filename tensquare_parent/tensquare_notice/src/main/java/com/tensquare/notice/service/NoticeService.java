package com.tensquare.notice.service;

import com.tensquare.notice.dao.NoticeDao;
import com.tensquare.notice.dao.NoticeFreshDao;
import com.tensquare.notice.pojo.Notice;
import com.tensquare.notice.pojo.NoticeFresh;
import com.tensquare.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author haixin
 * @time 2020/2/16
 */
@Service
public class NoticeService {

    @Autowired
    private NoticeDao noticeDao;
    public Notice findById(String id) {
        return noticeDao.selectById(id);
    }

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private NoticeFreshDao noticeFreshDao;

    public void add(Notice notice) {
        //1.设置初始值
        notice.setState("0");
        notice.setCreatetime(new Date());
        String id = idWorker.nextId() + "";
        notice.setId(id);
        noticeDao.insert(notice);

        //2.待推送消息入库，新消息提醒
        NoticeFresh noticeFresh = new NoticeFresh();
        noticeFresh.setNoticeId(id);//消息id
        noticeFresh.setUserId(notice.getReceiverId());//待通知用户的id
        noticeFreshDao.insert(noticeFresh);
    }
}
