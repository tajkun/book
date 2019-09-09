package com.ingzone.book.model;

import lombok.Data;

/**
 * Created by wjx on 17-7-8.
 */
@Data
public class Page {
    private Integer page;
    private Integer rows;
    private Integer orderType;
    public Page(){}
    public Page(Page page) {
        this.page = page.page;
        this.rows = page.rows;
    }

    public Page conversion() {
        if (page == null || rows == null) {
            throw new IllegalArgumentException("So stupid!!!There is no page argument please check your request!!!");
        }
        page = (page - 1) * rows;
        return this;
    }

    public void setRows(Integer rows) {
        this.rows = rows > 10 ? 10 : rows;
    }

}
