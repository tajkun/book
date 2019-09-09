package com.ingzone.book.model;

import lombok.Data;


/**
 * Created by 王镜鑫 on 17-10-27.
 * 请关注一下他的个人博客 wangjingxin.top
 */
@Data
public class SearchTO {
    private Integer id;
    private Double score;
    private String content;
    private String ids;
    public SearchTO(int id,double score,String content){
        this.id = id;
        this.score = score;
        this.content = content;
    }
}
