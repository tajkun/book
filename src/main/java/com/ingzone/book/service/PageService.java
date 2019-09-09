package com.ingzone.book.service;

import com.ingzone.book.base.Result;
import com.ingzone.book.dao.PageDao;
import com.ingzone.book.model.Page;
import com.ingzone.book.model.vo.PageVO;
import lombok.val;
import org.dom4j.*;
import org.dom4j.io.SAXReader;
import org.jaxen.SimpleNamespaceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static com.ingzone.book.cache.ResultCache.getDataOk;

/**
 * Created by 王镜鑫 on 17-8-28.
 * 请关注一下他的个人博客 wangjingxin.top
 */
@Service
public class PageService {
    @Autowired
    PageDao pageDao;

    public Result query(int articleId) {
        return getDataOk(pageDao.query(articleId));
    }

    public Result queryH(String ids, int articleId) {
        String[] id = ids.split(" ");
        List<PageVO> pages = (List<PageVO>) query(articleId).getData();
        return getDataOk(handler(id, pages));
    }

    private List<PageVO> handler(String[] id, List<PageVO> pages) {
        int n = 0;//
        Document document;
        try {
            document = DocumentHelper.parseText(pages.get(n).getSvg());
            val nsMap = new HashMap<String, String>();
            nsMap.put("default", "http://www.w3.org/2000/svg");          //加入命名空间
            val namespaceContext = new SimpleNamespaceContext(nsMap);
            for (int i=0;i<id.length;i++) {
                String ii = id[i];
                val xPath = DocumentHelper.createXPath("/default:svg/default:text[@id='#']".replaceFirst("#",ii));
                xPath.setNamespaceContext(namespaceContext);
                val element = (Element)xPath.selectSingleNode(document);
                if(element!=null){//证明含有这个
                    element.addAttribute("fill","red");
                }else{
                    pages.get(n).setSvg(document.asXML());
                    document = DocumentHelper.parseText(pages.get(++n).getSvg());
                    i--;
                }
            }
            pages.get(n).setSvg(document.asXML());
            return pages;
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return pages;
    }
}
