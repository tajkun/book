package com.ingzone.book.model.dto;

import lombok.Data;

import java.util.List;

import static com.ingzone.book.util.SessionUtil.user;

/**
 * Created by 王镜鑫 on 17-8-28.
 * 请关注一下他的个人博客 wangjingxin.top
 */
@Data
public class ArticleDTO {
    private Integer id;
    private String xml;//xml文件
    private Integer bookId;
    private Integer pageCount;
    private Integer pageStart;
    private List<ArticleAuthorDTO> author;
    private String name;
    private String content;//xml的content部分,仅文字
    private String annotations;//xml的content部分，仅注释
    private String userId = user();
    private String discontinue;
}
