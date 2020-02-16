package com.tensquare.notice.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.tensquare.notice.dao.NoticeDao;
import com.tensquare.notice.dao.NoticeFreshDao;
import com.tensquare.notice.pojo.Notice;
import com.tensquare.notice.pojo.NoticeFresh;
import com.tensquare.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author haixin
 * @time 2020/2/16
 */
@Service
public class NoticeService {

    @Autowired
    private NoticeDao noticeDao;
    @Autowired
    private IdWorker idWorker;

    @Autowired
    private NoticeFreshDao noticeFreshDao;
    public Notice findById(String id) {
        return noticeDao.selectById(id);
    }


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

    public Page<Notice> findPage(int page, int size) {
        Page<Notice> pagelist = new Page<>(page, size);
        List<Notice> notices = noticeDao.selectPage(pagelist, new EntityWrapper<Notice>());
        pagelist.setRecords(notices);
        return pagelist;
    }

    public void update(Notice notice) {
        noticeDao.updateById(notice);
    }

    public Page<NoticeFresh> freshPage(int userId, int page, int size) {
        Page<NoticeFresh> pagelist = new Page<>(page, size);
        EntityWrapper<NoticeFresh> objectEntityWrapper = new EntityWrapper<>();
        objectEntityWrapper.eq("userId",userId);
        List<NoticeFresh> noticeFreshes = noticeFreshDao.selectPage(pagelist, objectEntityWrapper);
        pagelist.setRecords(noticeFreshes);
        return pagelist;
    }
}
