package com.tensquare.notice.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.tensquare.entity.Result;
import com.tensquare.notice.dao.NoticeDao;
import com.tensquare.notice.dao.NoticeFreshDao;
import com.tensquare.notice.client.ArticleClient;
import com.tensquare.notice.client.UserClient;
import com.tensquare.notice.pojo.Notice;
import com.tensquare.notice.pojo.NoticeFresh;
import com.tensquare.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
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
    private ArticleClient articleClient;
    @Autowired
    private UserClient userClient;

    @Autowired
    private NoticeFreshDao noticeFreshDao;

    public Notice findById(String id) {
        Notice notice = noticeDao.selectById(id);
        getNoticeInfo(notice);
        return notice;
    }
    @Transactional
    public void add(Notice notice) {
        //1.设置初始值
        notice.setState("0");
        notice.setCreatetime(new Date());
        String id = idWorker.nextId() + "";
        notice.setId(id);
        noticeDao.insert(notice);

        //2.待推送消息入库，新消息提醒    推送消息改为rabbitmq实现
//        NoticeFresh noticeFresh = new NoticeFresh();
//        noticeFresh.setNoticeId(id);//消息id
//        noticeFresh.setUserId(notice.getReceiverId());//待通知用户的id
//        noticeFreshDao.insert(noticeFresh);
    }

    public Page<Notice> findPage(int page, int size) {
        Page<Notice> pageList = new Page<>(page,size);
        List<Notice> list = noticeDao.selectPage(pageList, new EntityWrapper<Notice>());

        for (Notice notice : list) {
            getNoticeInfo(notice);
        }

        pageList.setRecords(list);
        return pageList;
    }

    private void getNoticeInfo(Notice notice) {
        Result userResult = userClient.findById(notice.getOperatorId());
        HashMap userMap = (HashMap) userResult.getData();
        notice.setOperatorName(userMap.get("nickname").toString());
        //获取文章信息
        if ("article".equals(notice.getTargetType())) {
            Result articleResult = articleClient.findById(notice.getTargetId());
            HashMap articleMap = (HashMap) articleResult.getData();
            notice.setTargetName(articleMap.get("title").toString());
        }
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
