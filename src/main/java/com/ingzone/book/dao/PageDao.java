package com.ingzone.book.dao;

import com.ingzone.book.model.dto.PageDTO;
import com.ingzone.book.model.vo.PageVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 王镜鑫 on 17-8-28.
 * 请关注一下他的个人博客 wangjingxin.top
 */
@Repository
public interface PageDao {
    List<PageVO> query(@Param("articleId") int articleId);

    int insert(PageDTO pageDTO);
}
