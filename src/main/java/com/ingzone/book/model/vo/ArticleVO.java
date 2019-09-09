package com.ingzone.book.model.vo;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by 王镜鑫 on 17-8-27.
 * 请关注一下他的个人博客 wangjingxin.top
 */
@Data
public class ArticleVO {
    private Integer bookId;
    private String bookName;
    private String bookAuthor;
    private Integer id;
    private String account;
    private String nickName;
    private String name;
    private String author;
    private Timestamp date;
    private Timestamp expertDate;
    private String show;
    private Integer agree;
    private Integer reject;
    private Integer export;
}
