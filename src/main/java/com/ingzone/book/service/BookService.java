package com.ingzone.book.service;

import com.ingzone.book.base.Result;
import com.ingzone.book.dao.ArticleDao;
import com.ingzone.book.dao.BookDao;
import com.ingzone.book.model.JqGridVO;
import com.ingzone.book.model.Page;
import com.ingzone.book.model.dto.BookDTO;
import com.ingzone.book.model.vo.ArticleVO;
import com.ingzone.book.model.vo.BookVO;
import com.ingzone.book.model.vo.NameAuthorInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.ingzone.book.base.Success.R_UNSUCCESS;
import static com.ingzone.book.cache.ResultCache.OK;
import static com.ingzone.book.cache.ResultCache.getCache;
import static com.ingzone.book.cache.ResultCache.getDataOk;
import static com.ingzone.book.util.AuthorUtil.reduce;

/**
 * Created by 王镜鑫 on 17-8-26.
 * 请关注一下他的个人博客 wangjingxin.top
 */
@Service
public class BookService {
    @Autowired
    BookDao bookDao;
    @Autowired
    ArticleDao articleDao;
    public JqGridVO query(int status, Page page,String role) {
        Page p = new Page(page);
        List<BookVO> list = bookDao.query(page.conversion(),status,role);
        list.forEach(articleVO -> articleVO.setAuthor(reduce(bookDao.getAuthor(articleVO.getId()))));
        return new JqGridVO(p, bookDao.count(status,role), list);
    }

    public Result handleUnexamined(int id, String userId) {
        int result;
        if (userId == null || "".equals(userId)) { //驳回
            //   result = bookDao.delete(id);
            result = 1;
        }
        else {
            result = bookDao.updateStatus(1, id);
            if(result==1)
                articleDao.updateStatus(id,-1,-1);
        }
        return getCache(result);
    }

    public Result handleExpert(int id, String userId) {
        int status;
        if (userId == null || "".equals(userId))//驳回
            status = 0;
        else
            status = 2;
        return getCache(bookDao.updateStatus(status, id));
    }

    @Transactional
    public Result insert(BookDTO bookDTO) {
        bookDTO.setArticleTotal(bookDTO.getArticle().size());
        bookDTO.setUnexamined(bookDTO.getArticle().size());
        int result = bookDao.insert(bookDTO);//插入书籍
        if (result == 1) {
            bookDTO.getAuthor().forEach(bookAuthorDTO -> {//插入书籍作者
                bookAuthorDTO.setBookId(bookDTO.getId());
                bookDao.insertAuthor(bookAuthorDTO);
            });
            bookDTO.getArticle().forEach(articleDTO -> {//遍历文章
                articleDTO.setBookId(bookDTO.getId());//文章对应的书籍号
                articleDao.insert(articleDTO);//插入文章
                articleDTO.getAuthor().forEach(articleAuthorDTO -> {//插入文章作者
                    articleAuthorDTO.setArticleId(articleDTO.getId());
                    articleDao.insertAuthor(articleAuthorDTO);
                });
            });
        }
        return getCache(result);
    }

    public NameAuthorInfoVO queryInfo(int bookId) {
        NameAuthorInfoVO nameAuthorInfoVO = bookDao.queryInfo(bookId);
        nameAuthorInfoVO.setAuthor(reduce(bookDao.getAuthor(Integer.parseInt(nameAuthorInfoVO.getAuthor()))));
        return nameAuthorInfoVO;
    }

    public JqGridVO searchBook(Page page, String key) {
        Page p = new Page(page);
        List<BookVO> list = bookDao.search(page.conversion(),"%"+key+"%");
        list.forEach(articleVO -> articleVO.setAuthor(reduce(bookDao.getAuthor(articleVO.getId()))));
        return new JqGridVO(p, bookDao.searchCount("%"+key+"%"), list);
    }
}
