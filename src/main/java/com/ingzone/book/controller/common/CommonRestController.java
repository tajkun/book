package com.ingzone.book.controller.common;

import com.ingzone.book.base.Result;
import com.ingzone.book.model.JqGridVO;
import com.ingzone.book.model.Page;
import com.ingzone.book.service.ArticleService;
import com.ingzone.book.service.BookService;
import com.ingzone.book.service.CommonService;
import com.ingzone.book.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by 王镜鑫 on 17-10-10.
 * 请关注一下他的个人博客 wangjingxin.top
 */
@RestController
@RequestMapping(value = "/common")
public class CommonRestController {
    @Autowired
    CommonService commonService;
    @Autowired
    ArticleService articleService;
    @Autowired
    PageService pageService;
    @Autowired
    BookService bookService;
    @RequestMapping(value = "queryArticle", method = RequestMethod.GET)
    public JqGridVO queryArticle(Page page, int bookId, int status) {
        return articleService.query(page, bookId, status);
    }

    @RequestMapping(value = "queryPage", method = RequestMethod.GET)
    public Result queryPage(int articleId) {
        return pageService.query(articleId);
    }
    @RequestMapping(value = "queryHPage",method = RequestMethod.GET)
    public Result queryHPage(String ids,int articleId){
        return pageService.queryH(ids,articleId);
    }
    @RequestMapping(value = "rSearch", method = RequestMethod.GET)
    public JqGridVO search(String key, String type, Page page) {
        return commonService.search(key, type, page);
    }
    @RequestMapping(value = "searchBook",method = RequestMethod.GET)
    public JqGridVO searchBook(Page page,String key){
        return bookService.searchBook(page,key);
    }
    @RequestMapping(value = "searchArticle",method = RequestMethod.GET)
    public JqGridVO searchArticle(Page page,String key){
        return articleService.searchArticle(page,key);
    }
}
