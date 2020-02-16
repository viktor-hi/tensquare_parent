package com.tensquare.notice.pojo;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

/**
 * @author haixin
 * @time 2020/2/16
 */
@TableName("tb_notice_fresh")
@Data
public class NoticeFresh {

    private String userId;
    private String noticeId;

    //set ge
}
