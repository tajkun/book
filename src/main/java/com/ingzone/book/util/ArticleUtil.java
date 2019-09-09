package com.ingzone.book.util;

import com.ingzone.book.model.dto.PageDTO;
import lombok.val;
import org.dom4j.*;
import org.dom4j.io.SAXReader;
import org.jaxen.SimpleNamespaceContext;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by 王镜鑫 on 17-8-28.
 * 请关注一下他的个人博客 wangjingxin.top
 */
public class ArticleUtil {
    private Document document = null;
    private Integer articleId;
    public ArticleUtil(InputStream in, Integer id) {
        try {
            document = new SAXReader().read(in);
            this.articleId = id;
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }
    public List<PageDTO> getPage(){
        val nsMap=new HashMap<String,String>();
        //加入命名空间

        nsMap.put("default","http://www.w3.org/2000/svg");
        val namespaceContext = new SimpleNamespaceContext(nsMap);
        val xPath = DocumentHelper.createXPath("/article/view/default:svg");
        xPath.setNamespaceContext(namespaceContext);
        val element = xPath.selectNodes(document);
        List<PageDTO> page = new ArrayList<>(element.size());
        element.forEach(a->{
            val b =(Element)a;
            PageDTO pageDTO = new PageDTO();
            pageDTO.setSvg(b.asXML());
            pageDTO.setPage(b.attributeValue("page_on"));
            pageDTO.setArticleId(articleId);
            b.asXML();
            page.add(pageDTO);
        });
        return page;
    }
    public String getAnnotations() {
        StringBuilder sb = new StringBuilder();
        val element = document.selectNodes("/article/content/text/section/subsection/paragraph/sentence/word/word_note/char");
        element.forEach(a-> sb.append(((Element)a).getTextTrim()));
        return sb.toString();
    }

    public String getContent() {
        StringBuilder sb = new StringBuilder();
        val element = document.selectNodes("/article/content/text/section/subsection/paragraph/sentence/word/char");
        element.forEach(a-> sb.append(((Element)a).getTextTrim()));
        return sb.toString();
    }
    public static void p(Object o){
        System.out.println(o);
    }
    public static void main(String[] args) throws FileNotFoundException {
        val articleUtil = new ArticleUtil(new FileInputStream(new File("C:\\Users\\gzq94\\IdeaProjects\\book\\模板xml\\春晓.xml")),1);
        p("----------------------------------");
        p(articleUtil.getContent());
        p("----------------------------------");
        p(articleUtil.getAnnotations());
        p("----------------------------------");
        p(articleUtil.getPage());
        p("----------------------------------");
    }

    public String getXML() {
        return document.asXML();
    }
}
