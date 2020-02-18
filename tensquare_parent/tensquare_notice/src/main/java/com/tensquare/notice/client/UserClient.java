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
@FeignClient("tensquare-user")
public interface UserClient {
    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
    Result findById(@PathVariable(value = "userId") String userId);
}
