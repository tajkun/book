package com.ingzone.book.model.vo;

import lombok.Data;

import java.sql.Timestamp;

/**
 * Created by 王镜鑫 on 17-8-26.
 * 请关注一下他的个人博客 wangjingxin.top
 */
@Data
public class ApplicationVO {
    private Integer id;
    private Timestamp date;
    private String userId;
    private String account;
    private String nickName;
    private Timestamp registerDate;
    private String agree;
    private String reject;
}
