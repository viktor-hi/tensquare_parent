package com.tensquare.article.client;

import com.tensquare.entity.Result;
import com.tensquare.notice.pojo.Notice;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author haixin
 * @time 2020/2/18
 */
@FeignClient("tensquare-notice")
public interface NoticeClient {
    @RequestMapping(value = "/notice",method = RequestMethod.POST)
    Result add(@RequestBody Notice notice);
}
