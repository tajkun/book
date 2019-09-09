package com.ingzone.book.service;

import com.ingzone.book.dao.ArticleDao;
import com.ingzone.book.dao.BookDao;
import com.ingzone.book.dao.PageDao;
import com.ingzone.book.dao.UserDao;
import com.ingzone.book.model.JqGridVO;
import com.ingzone.book.model.Page;
import com.ingzone.book.model.SearchTO;
import com.ingzone.book.model.vo.ArticleVO;
import com.ingzone.book.model.vo.LoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static com.ingzone.book.util.AuthorUtil.reduce;
import static com.ingzone.book.util.SearchUtil.query;
import static com.ingzone.book.util.SessionUtil.setAttribute;
import static com.ingzone.book.util.SessionUtil.setCookies;
import static java.lang.String.valueOf;

/**
 * Created by 王镜鑫 on 17-10-10.
 * 请关注一下他的个人博客 wangjingxin.top
 */
@Service
public class CommonService {

    @Autowired
    UserDao userDao;
    @Autowired
    PageDao pageDao;
    @Autowired
    ArticleDao articleDao;
    @Autowired
    BookDao bookDao;
    public String login(String account, String password) {
        LoginVO loginVO = userDao.queryLogin(account);
        if (loginVO == null)
            return "redirect:index.jsp";
        //   if(Hash.getSha256(password+loginVO.getId()).equals(loginVO.getPassword())){
        if (password.equals(loginVO.getPassword())) {
            setAttribute("role", loginVO.getRole());
            setAttribute("id", loginVO.getId());
            setAttribute("account", loginVO.getAccount());
            setAttribute("nickName", loginVO.getNickName());
            Cookie nickName = new Cookie("nickName",loginVO.getNickName());
            nickName.setPath("/");
            Cookie role = new Cookie("role",valueOf(loginVO.getRole()));
            role.setPath("/");
            Cookie a = new Cookie("account",loginVO.getAccount());
            a.setPath("/");
            setCookies(nickName,role,a);
            switch (loginVO.getRole()) {
                case 0:
                    return "redirect:/";
                case 1:
                    return "redirect:/";
                case 2:
                    return "redirect:/admin/applicationBlank";
                case 3:
                    return "redirect:/expert/bookBlank";
            }
        }
        return "redirect:/index.jsp";
    }

    public JqGridVO search(String key, String type, Page page) {
        Page p = new Page(page);
        Map<String, Object> result = query(key, type, page.conversion());
        List<SearchTO> searchTOList = (List<SearchTO>) result.get("list");//这个是若干个文章
        if(searchTOList.size()==0)
            return null;
        searchTOList.forEach(list -> list.setIds(getId(list.getId(),list.getContent())));
        List<ArticleVO> list = articleDao.search(searchTOList);
        Map<Integer, ArticleVO> map = new HashMap<>();
        Map<Integer,String> author = new HashMap<>();
        list.forEach(vo -> {
            vo.setAuthor(reduce(articleDao.getAuthor(vo.getId())));
            if(map.get(vo.getBookId())!=null){
                vo.setBookAuthor(author.get(vo.getBookId()));
            }else {
                vo.setBookAuthor(reduce(bookDao.getAuthor(vo.getBookId())));
                author.put(vo.getBookId(),vo.getBookAuthor());
            }
            map.put(vo.getId(), vo);
        });
        List<ArticleVO> r = new ArrayList<>(list.size());
        searchTOList.forEach(id -> {
            ArticleVO vo = map.get(id.getId());
            vo.setShow(id.getIds());
            r.add(map.get(id.getId()));
        });
        JqGridVO j = new JqGridVO(p, Integer.parseInt(result.get("total") + ""), r);
        return j;
    }

    private String getId(int id,String s) {
        StringBuilder stringBuilder = new StringBuilder(id+"#");
        int n = 0;
        boolean start = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '#') {
                start = !start;
            } else {//如果是字
                n++;
                if (start)
                    stringBuilder.append(n).append(" ");
            }
        }
        return stringBuilder.toString().trim();
    }
}