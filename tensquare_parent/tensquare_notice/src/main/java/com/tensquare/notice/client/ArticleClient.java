package com.tensquare.notice.client;

import com.tensquare.entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author haixin
 * @time 2020/2/17
 */
@FeignClient("tensquare-article")
public interface ArticleClient {

    @RequestMapping(value = "/article/{articleId}",method = RequestMethod.GET)
    Result findById(@PathVariable("articleId") String articleId);

}
