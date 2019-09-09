package com.ingzone.book.util;

import com.ingzone.book.model.vo.AuthorVO;

import java.util.List;

/**
 * Created by 王镜鑫 on 17-10-16.
 * 请关注一下他的个人博客 wangjingxin.top
 */
public class AuthorUtil {
    public static String reduce(List<AuthorVO> author) {
        StringBuilder sb = new StringBuilder();
        author.forEach(a-> sb.append(a.getName()));
        return sb.toString().trim();
    }
}
