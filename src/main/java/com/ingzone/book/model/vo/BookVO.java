package com.ingzone.book.model.vo;

import lombok.Data;

import java.sql.Timestamp;

/**
 * Created by 王镜鑫 on 17-8-26.
 * 请关注一下他的个人博客 wangjingxin.top
 */
@Data
public class BookVO {
    private Integer id;
    private String name;
    private String userId;
    private String nickName;
    private String account;
    private String publishedDate;
    private String author;
    private String writtenDate;
    private Timestamp date;
    private String agree;
    private String reject;
    private Integer unexamined;
    private Integer articleTotal;
    private Integer submit;
}
