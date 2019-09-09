package com.ingzone.book.model;

import lombok.Data;

import java.util.List;

/**
 * Created by 王镜鑫 on 17-8-26.
 * 请关注一下他的个人博客 wangjingxin.top
 */
@Data
public class JqGridVO {
    private Integer page;
    private Integer record;
    private List rows;
    private Integer total;

    public JqGridVO(Page page,int record,List rows){
        this.page = page.getPage();
        this.record = record;
        this.rows = rows;
        this.total = record/page.getRows()+record%page.getRows()==0?0:1;
    }
    public JqGridVO(Page page){
        this.page = page.getPage();
        this.total = record/page.getRows()+record%page.getRows()==0?0:1;
    }
}
