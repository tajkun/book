package com.ingzone.book.dao;

import com.ingzone.book.model.ArticleTO;
import com.ingzone.book.model.Page;
import com.ingzone.book.model.SearchTO;
import com.ingzone.book.model.dto.ArticleAuthorDTO;
import com.ingzone.book.model.dto.ArticleDTO;
import com.ingzone.book.model.vo.ArticleInfoVO;
import com.ingzone.book.model.vo.ArticleVO;
import com.ingzone.book.model.vo.AuthorVO;
import com.ingzone.book.model.vo.NameAuthorInfoVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 王镜鑫 on 17-8-27.
 * 请关注一下他的个人博客 wangjingxin.top
 */
@Repository
public interface ArticleDao {
    int count(@Param("bookId") int bookId,@Param("status") int status);

    List<ArticleVO> query(@Param("bookId") int bookId,@Param("p") Page page,@Param("status") int status);

    ArticleInfoVO queryInfo(int articleId);

    int updateStatus(@Param("bookId")int bookId,@Param("articleId") int articleId, @Param("n") int n);

    int delete(int articleId);

    int insert(ArticleDTO articleDTO);

    int insertAuthor(ArticleAuthorDTO articleAuthorDTO);

    List<AuthorVO> getAuthor(int id);

    int belong(@Param("id") int id,@Param("user") String user,@Param("status")int status);

    int update(ArticleDTO articleDTO);

    List<ArticleInfoVO> queryMyEdit(@Param("status") int status,@Param("p") Page conversion,@Param("u") String user);

    ArticleTO queryOne(int articleId);

    List<ArticleVO> search(List<SearchTO> searchTOList);

    List<ArticleVO> searchArticle(@Param("p") Page conversion,@Param("k") String key);

    int searchCount(String s);
}
