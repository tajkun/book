package com.ingzone.book.model.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by 王镜鑫 on 17-8-27.
 * 请关注一下他的个人博客 wangjingxin.top
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ArticleInfoVO extends NameAuthorInfoVO {
    private String fName;
    private String fAuthor;
    private Integer pageCount;
}
