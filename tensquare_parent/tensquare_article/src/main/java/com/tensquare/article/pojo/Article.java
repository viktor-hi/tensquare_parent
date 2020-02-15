package com.tensquare.article.pojo;

/**
 * @author haixin
 * @time 2020/2/4
 */
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@TableName("tb_article")
@Data
public class Article implements Serializable {

    @TableId(type = IdType.INPUT)
    private String id;//ID
    private String columnid;    //专栏ID
    private String userid;      //用户ID
    @NotBlank
    @Size(min = 4,max = 20,message = "标题长度最小为4最大为20")
    private String title;       //标题
    private String content;     //文章正文
    private String image;       //文章封面
    private Date createtime;    //发表日期
    private Date updatetime;    //修改日期
    private String ispublic;    //是否公开
    private String istop;       //是否置顶
    private Integer visits;     //浏览量
    private Integer thumbup;    //点赞数
    private Integer comment;    //评论数
    private String state;       //审核状态
    private String channelid;   //所属频道
    private String url;         //URL
    private String type;        //类型

    //getters and setters
}