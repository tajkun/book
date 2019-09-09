package com.ingzone.book.dao;

import com.ingzone.book.model.Page;
import com.ingzone.book.model.dto.AuthorDTO;
import com.ingzone.book.model.dto.BookAuthorDTO;
import com.ingzone.book.model.dto.BookDTO;
import com.ingzone.book.model.vo.AuthorVO;
import com.ingzone.book.model.vo.BookVO;
import com.ingzone.book.model.vo.NameAuthorInfoVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 王镜鑫 on 17-8-26.
 * 请关注一下他的个人博客 wangjingxin.top
 */
@Repository
public interface BookDao {
    int count(@Param("status") int status,@Param("role") String role);

    List<BookVO> query(@Param("p") Page page, @Param("status") int status,@Param("role") String role);

    int delete(int id);

    int updateStatus(@Param("n") int n,@Param("id") int id);

    NameAuthorInfoVO queryInfo(int bookId);

    int queryUnexamined(int bookId);

    int updateExpertDate(int bookId);

    int descUnexamined(int articleId);

    int insert(BookDTO bookDTO);

    int insertAuthor(BookAuthorDTO authorDTO);

    List<AuthorVO> getAuthor(int id);

    List<BookVO> search(@Param("p") Page conversion,@Param("k") String key);

    int searchCount(String key);
}
