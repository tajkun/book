package com.ingzone.book.dao;

import com.ingzone.book.model.Page;
import com.ingzone.book.model.vo.ApplicationVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 王镜鑫 on 17-8-26.
 * 请关注一下他的个人博客 wangjingxin.top
 */
@Repository
public interface ApplicationDao {
    List<ApplicationVO> query(Page page);

    int count();

    int delete(String id);
}
