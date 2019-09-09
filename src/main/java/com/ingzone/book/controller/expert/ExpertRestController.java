package com.ingzone.book.controller.expert;

import com.ingzone.book.base.Result;
import com.ingzone.book.model.JqGridVO;
import com.ingzone.book.model.Page;
import com.ingzone.book.service.ArticleService;
import com.ingzone.book.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by 王镜鑫 on 17-8-28.
 * 请关注一下他的个人博客 wangjingxin.top
 */
@RestController
@RequestMapping(value = "/expert")
public class ExpertRestController {
    @Autowired
    BookService bookService;
    @Autowired
    ArticleService articleService;

    /*
    @RequestMapping(value = "queryUnexaminedBook", method = RequestMethod.GET)
    public JqGridVO queryUnexaminedBook(Page page) {
        return bookService.query(1, page, "expert");
    }
    */

    /**
     * 查询还没有被专家审核的文章
     * @param page
     * @param bookId
     * @return
     */
    @RequestMapping(value = "queryUnexaminedArticle", method = RequestMethod.GET)
    public JqGridVO queryArticle(Page page, int bookId) {
        return articleService.query(page, bookId, 0);
    }

    /**
     * 处理文章
     * @param articleId
     * @param userId
     * @return
     */
    @RequestMapping(value = "handleArticle", method = RequestMethod.POST)
    public Result handleArticle(int articleId, String userId) {
        return articleService.handleExpertArticle(articleId, userId);
    }
}
