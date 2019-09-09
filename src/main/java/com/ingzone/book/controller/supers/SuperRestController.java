package com.ingzone.book.controller.supers;

import com.ingzone.book.base.Result;
import com.ingzone.book.model.Page;
import com.ingzone.book.model.dto.ArticleDTO;
import com.ingzone.book.model.dto.BookDTO;
import com.ingzone.book.service.ArticleService;
import com.ingzone.book.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by 王镜鑫 on 17-8-28.
 * 请关注一下他的个人博客 wangjingxin.top
 */
@RestController
@RequestMapping(value = "/super")
public class SuperRestController {
    @Autowired
    BookService bookService;
    @Autowired
    ArticleService articleService;
    @RequestMapping(value = "applyBook",method = RequestMethod.POST)
    public Result applyBook(@RequestBody BookDTO bookDTO){
        return bookService.insert(bookDTO);
    }
    @RequestMapping(value = "publish",method = RequestMethod.POST)
    public Result publish(ArticleDTO articleDTO, MultipartFile xmlFile){
        return articleService.insert(articleDTO,xmlFile);
    }
    @RequestMapping(value = "queryMyEdit",method = RequestMethod.GET)
    public Result queryMyEdit(int status,Page page){
        return articleService.queryMyEdit(status,page);
    }
}
