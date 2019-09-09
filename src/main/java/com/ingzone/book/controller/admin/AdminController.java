package com.ingzone.book.controller.admin;

import com.ingzone.book.service.ArticleService;
import com.ingzone.book.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by 王镜鑫 on 17-8-26.
 * 请关注一下他的个人博客 wangjingxin.top
 */
@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    @Autowired
    ArticleService articleService;
    @Autowired
    BookService bookService;
    @RequestMapping(value = "applicationBlank",method = RequestMethod.GET)
    public String applicationBlank(){
        return "admin/userApplication";
    }
    @RequestMapping(value = "bookBlank",method = RequestMethod.GET)
    public String bookBlank(){
        return "admin/unexaminedBook";
    }
    @RequestMapping(value = "articleBlank",method = RequestMethod.GET)
    public String articleBlank(int id,Model model){
        model.addAttribute("bookId",id);
        return "admin/unexaminedArticle";
    }
    @RequestMapping(value = "expertBlank",method = RequestMethod.GET)
    public String expertBlank() {
        return "admin/expertBook";
    }
}
