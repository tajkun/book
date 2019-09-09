package com.ingzone.book.controller.common;

import com.ingzone.book.service.ArticleService;
import com.ingzone.book.service.BookService;
import com.ingzone.book.service.CommonService;
import com.ingzone.book.util.SessionUtil;
import com.itextpdf.text.DocumentException;
import lombok.NonNull;
import org.apache.batik.transcoder.TranscoderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by 王镜鑫 on 17-8-27.
 * 请关注一下他的个人博客 wangjingxin.top
 */
@Controller
@RequestMapping(value = "/common")
public class CommonController {
    @Autowired
    CommonService commonService;
    @Autowired
    BookService bookService;
    @Autowired
    ArticleService articleService;
    @RequestMapping(value = "login",method = RequestMethod.POST)
    public String login(String account,String password){
        return commonService.login(account,password);
    }
    @RequestMapping(value = "logout",method = RequestMethod.GET)
    public String logout(){
        SessionUtil.destroySession();
        return "redirect:/";
    }
    @RequestMapping(value = "showArticle",method = RequestMethod.GET)
    public String showArticles(int bookId, Model model,int status){
        model.addAttribute("bookId",bookId);
        model.addAttribute("status",status);
        model.addAttribute("bookInfo",bookService.queryInfo(bookId));
        model.addAttribute("url","/common/queryArticle?bookId="+bookId+"&status="+status);
        return "common/showArticle";
    }
    @RequestMapping(value = "showPage",method = RequestMethod.GET)
    public String showPages(int articleId,Model model){
        model.addAttribute("url","/common/queryPage");
        model.addAttribute("articleId",articleId);
        model.addAttribute("articleInfo",articleService.queryInfo(articleId));
        return "common/showPage";
    }
    @RequestMapping(value = "showHPage",method = RequestMethod.GET)
    public String showHPages(int articleId,Model model,String ids){
        model.addAttribute("url","/common/queryHPage?ids="+ids);
        model.addAttribute("articleId",articleId);
        model.addAttribute("articleInfo",articleService.queryInfo(articleId));
        return "common/showPage";
    }


    @RequestMapping(value = "/exportPDF", method = RequestMethod.GET)
    public void exportPDF(@NonNull int articleId, HttpServletResponse resp) {
        try {
            articleService.export(articleId,resp);
        } catch (Exception ex){
            ex.printStackTrace();
            throw new RuntimeException();
        }
    }

    @RequestMapping(value = "search",method = RequestMethod.GET)
    public String search(int type,String key,Model model){
        switch (type){
            case 1://书籍
                model.addAttribute("url","/common/searchBook?key="+key);
                return "/common/searchBook";
            case 2://文章
                model.addAttribute("url","/common/searchArticle?key="+key);
                return "/common/showArticle";
            case 3://正文
                model.addAttribute("url","/common/rSearch?key="+key+"&type=content");
                return "/common/searchArticle";
            case 4://注释
                model.addAttribute("url","/common/rSearch?key="+key+"&type=annotations");
                return "/common/searchArticle";
        }
        return "redirect:/search.html";
    }
}
