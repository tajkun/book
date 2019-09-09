package com.ingzone.book.model.dto;

import lombok.Data;

import java.util.List;

import static com.ingzone.book.util.SessionUtil.user;

/**
 * Created by 王镜鑫 on 17-8-28.
 * 请关注一下他的个人博客 wangjingxin.top
 */
@Data
public class BookDTO {
    private Integer id;
    private String userId = user();
    private Integer articleTotal;
    private String writtenDate;
    private List<BookAuthorDTO> author;
    private String name;
    private String publishedDate;
    private List<ArticleDTO> article;
    private Integer unexamined;
}
