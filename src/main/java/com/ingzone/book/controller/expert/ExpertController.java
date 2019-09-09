package com.ingzone.book.controller.expert;

import com.ingzone.book.dao.BookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import static com.ingzone.book.util.SessionUtil.setAttribute;

/**
 * Created by 王镜鑫 on 17-8-28.
 * 请关注一下他的个人博客 wangjingxin.top
 */
@Controller
@RequestMapping(value = "/expert")
public class ExpertController {
    @Autowired
    BookDao bookDao;
    @RequestMapping(value = "bookBlank",method = RequestMethod.GET)
    public String bookBlank(){
        return "expert/unexaminedBook";
    }
    @RequestMapping(value = "showArticle",method = RequestMethod.GET)
    public String showArticle(int bookId,Model model){
        model.addAttribute("bookId",bookId);
        model.addAttribute("bookInfo",bookDao.queryInfo(bookId));
        model.addAttribute("url","/expert/queryUnexaminedArticle?bookId="+bookId);
        model.addAttribute("show","false");
        model.addAttribute("hide","true");
        return "common/showArticle";
    }
}
