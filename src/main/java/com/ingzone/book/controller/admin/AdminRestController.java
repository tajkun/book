package com.ingzone.book.controller.admin;

import com.ingzone.book.base.Result;
import com.ingzone.book.model.JqGridVO;
import com.ingzone.book.model.Page;
import com.ingzone.book.service.ArticleService;
import com.ingzone.book.service.BookService;
import com.ingzone.book.service.PageService;
import com.ingzone.book.service.UserApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by 王镜鑫 on 17-8-26.
 * 请关注一下他的个人博客 wangjingxin.top
 */
@RestController
@RequestMapping(value = "/admin")
public class AdminRestController {
    @Autowired
    UserApplicationService userApplicationService;
    @Autowired
    BookService bookService;
    @Autowired
    ArticleService articleService;
    @Autowired
    PageService pageService;

    /**
     * 查询用户的申请
     * @param page
     * @return
     */
    @RequestMapping(value = "queryUserApplication", method = RequestMethod.GET)
    public JqGridVO queryUserApplication(Page page) {
        return userApplicationService.query(page);
    }

    /**
     * 处理用户申请
     * @param id
     * @param userId
     * @return
     */
    @RequestMapping(value = "handleUserApplication", method = RequestMethod.POST)
    public Result handleUserApplication(String id, String userId) {
        return userApplicationService.handle(id, userId);
    }

    /**
     * 处理需要审核的书籍
     * @param page
     * @return
     */
    @RequestMapping(value = "queryUnexaminedBook", method = RequestMethod.GET)
    public JqGridVO queryUnexaminedBook(Page page) {
        return bookService.query(0, page, "admin");
    }

    /**
     * 查询需要审核的文章
     * @param page
     * @param bookId
     * @return
     */
    @RequestMapping(value = "queryUnexaminedArticle", method = RequestMethod.GET)
    public JqGridVO queryUnexaminedArticle(Page page, int bookId) {
        return articleService.query(page, bookId, -2);
    }

    /**
     * 处理未审核的文章
     * @param id
     * @param userId
     * @return
     */
    @RequestMapping(value = "handleUnexaminedArticle", method = RequestMethod.POST)
    public Result handleUnexaminedArticle(int id, String userId) {
        return articleService.handleUnexaminedArticle(id, userId);
    }

    /**
     * 处理未审核的书籍
     * @param id
     * @param userId
     * @return
     */
    @RequestMapping(value = "handleUnexaminedBook", method = RequestMethod.POST)
    public Result handleUnexaminedBook(int id, String userId) {
        return bookService.handleUnexamined(id, userId);
    }

    /**
     * 处理专家处理过的文章
     * @param id
     * @param userId
     * @return
     */
    @RequestMapping(value = "handleExpertArticle",method = RequestMethod.POST)
    public Result handleExpertArticle(int id, String userId){
        return articleService.handleFinalArticle(id,userId);
    }
}
