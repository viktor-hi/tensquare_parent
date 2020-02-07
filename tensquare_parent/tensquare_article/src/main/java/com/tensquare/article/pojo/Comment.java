package com.tensquare.article.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;

/**
 * @author haixin
 * @time 2020/2/7
 */
@Data
public class Comment implements Serializable {
    @Id
    private String _id;
    private String articleid;
    private String content;
    private String userid;
    private String parentid;
    private Date publishdate;
    private Integer thumbup;
}