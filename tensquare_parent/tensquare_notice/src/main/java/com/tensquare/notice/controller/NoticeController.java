package com.tensquare.notice.controller;

import com.tensquare.entity.Result;
import com.tensquare.entity.StatusCode;
import com.tensquare.notice.pojo.Notice;
import com.tensquare.notice.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author haixin
 * @time 2020/2/16
 */
@RestController
@RequestMapping("/notice")
@CrossOrigin
public class NoticeController {
    @Autowired
    private NoticeService noticeService;

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Result findById(@PathVariable(value = "id") String id) {
        Notice notice =  noticeService.findById(id);
        return new Result(true, StatusCode.OK, "查询成功",notice);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Notice notice) {
        noticeService.add(notice);
        return new Result(true, StatusCode.OK, "新增成功");
    }
}

