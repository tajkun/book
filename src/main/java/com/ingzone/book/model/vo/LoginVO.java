package com.ingzone.book.model.vo;

import lombok.Data;

/**
 * Created by 王镜鑫 on 17-10-10.
 * 请关注一下他的个人博客 wangjingxin.top
 */
@Data
public class LoginVO {
    private String account;
    private String id;
    private String password;
    private Integer role;
    private String nickName;
}
