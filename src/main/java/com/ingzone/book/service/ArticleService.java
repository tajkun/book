package com.ingzone.book.service;

import com.ingzone.book.base.Result;
import com.ingzone.book.dao.ArticleDao;
import com.ingzone.book.dao.BookDao;
import com.ingzone.book.dao.PageDao;
import com.ingzone.book.model.ArticleTO;
import com.ingzone.book.model.JqGridVO;
import com.ingzone.book.model.Page;
import com.ingzone.book.model.dto.ArticleDTO;
import com.ingzone.book.model.vo.ArticleInfoVO;
import com.ingzone.book.model.vo.ArticleVO;
import com.ingzone.book.model.vo.PageVO;
import com.ingzone.book.util.ArticleUtil;
import com.ingzone.book.util.SVG2PDFUtil;
import com.itextpdf.text.DocumentException;
import lombok.val;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static com.ingzone.book.cache.ResultCache.*;
import static com.ingzone.book.util.AuthorUtil.reduce;
import static com.ingzone.book.util.SearchUtil.put;
import static com.ingzone.book.util.SessionUtil.user;

/**
 * Created by 王镜鑫 on 17-8-27.
 * 请关注一下他的个人博客 wangjingxin.top
 */
@Service
public class ArticleService {

    Log log = LogFactory.getLog(this.getClass());

    public static final String PDF_DIR = "/home/wjx/desktop/";
    public static final String PDF_SUFFIX = ".pdf";

    @Autowired
    ArticleDao articleDao;
    @Autowired
    BookDao bookDao;
    @Autowired
    PageDao pageDao;

    public JqGridVO query(Page page, int bookId, int status) {
        //TODO 权限认证
        Page p = new Page(page);
        val list = articleDao.query(bookId, page.conversion(), status);
        Map<Integer,String> map = new HashMap<>();
        return new JqGridVO(p, articleDao.count(bookId, status), author(list));
    }


    @Transactional
    public Result insert(ArticleDTO articleDTO, MultipartFile xmlFile) {
        if (articleDao.belong(articleDTO.getId(), user(), -1) != 1)//鉴权
            return PERMISSION_DENIED;
        ArticleUtil articleUtil = null;
        try {
            articleUtil = new ArticleUtil(xmlFile.getInputStream(), articleDTO.getId());//初始化这个xml
        } catch (IOException e) {
            e.printStackTrace();
        }
        articleDTO.setXml(articleUtil.getXML());//获取原xml
        articleDTO.setContent(articleUtil.getContent());//获取content
        articleDTO.setAnnotations(articleUtil.getAnnotations());//获取注释
        val pages = articleUtil.getPage();//获取每一页
        int result = articleDao.update(articleDTO);
            if (result == 1) {
            pages.forEach(pageDTO -> {
                pageDTO.setArticleId(articleDTO.getId());
                pageDao.insert(pageDTO);
            });
        }
        return OK;
    }

    public ArticleInfoVO queryInfo(int articleId) {
        val articleInfoVO = articleDao.queryInfo(articleId);
        articleInfoVO.setAuthor(reduce(articleDao.getAuthor(articleId)));
        articleInfoVO.setFAuthor(reduce(bookDao.getAuthor(Integer.parseInt(articleInfoVO.getFAuthor()))));
        return articleInfoVO;
    }

    public Result queryMyEdit(int status, Page page) {
        List<ArticleInfoVO> list = articleDao.queryMyEdit(status, page.conversion(), user());
        list.forEach(l -> {
            l.setAuthor(reduce(articleDao.getAuthor(l.getId())));
            l.setFAuthor(reduce(bookDao.getAuthor(Integer.parseInt(l.getFAuthor()))));
        });
        return getDataOk(list);
    }

    @Transactional //初步审核
    public Result handleUnexaminedArticle(int id, String userId) {
        int result;
        if (null == userId || "".equals(userId)) {
            //TODO 驳回，通知修改
            result = 1;
        } else {
            result = articleDao.updateStatus(-1, id, -1);//修改为正在编辑
        }
        return getCache(result);
    }

    @Transactional //专家审核
    public Result handleExpertArticle(int articleId, String userId) {
        int result;
        if ("".equals(userId) || null == userId) {//驳回
            result = 1;
        } else {//同意
            result = articleDao.updateStatus(-1, articleId, 1);
        }
        return getCache(result);
    }

    @Transactional // 最终审核
    public Result handleFinalArticle(int articleId, String userId) {
        int result = bookDao.descUnexamined(articleId);
        if (result == 1) {
            if ("".equals(userId) || null == userId) {//驳回
                result = 1;
            } else {//同意
                result = articleDao.updateStatus(-1, articleId, 2);
                if(result==1){//建立索引
                    ArticleTO articleTO = articleDao.queryOne(articleId);
                    put(articleId,articleTO.getContent(),"content");
                    put(articleId,articleTO.getAnnotations(),"annotations");
                }
                //异步生成PDF
                new Thread(()->generatePDF(articleId)).start();
            }
        }
        return getCache(result);
    }

    public JqGridVO searchArticle(Page page, String key) {
        Page p = new Page(page);
        val list = articleDao.searchArticle(page.conversion(),"%"+key+"%");
        return new JqGridVO(p, articleDao.searchCount("%"+key+"%"), author(list));
    }
    public List<ArticleVO> author(List<ArticleVO> list){
        Map<Integer,String> map = new HashMap<>();
        list.forEach(articleVO -> {
            articleVO.setAuthor(reduce(articleDao.getAuthor(articleVO.getId())));
            if(map.get(articleVO.getBookId())!=null){
                articleVO.setBookAuthor(map.get(articleVO.getBookId()));
            }else {
                articleVO.setBookAuthor(reduce(bookDao.getAuthor(articleVO.getBookId())));
                map.put(articleVO.getBookId(),articleVO.getBookAuthor());
            }
        });
        return list;
    }

    //生成pdf到文件中
    private void generatePDF(int articleId){
        Path path = Paths.get(PDF_DIR, articleId + PDF_SUFFIX);
        File file = new File(PDF_DIR+File.separator+articleId+PDF_SUFFIX);
        try(FileOutputStream out = new FileOutputStream(file)){
            List<PageVO> pages = pageDao.query(articleId);
            List<String> svgs = pages.stream().map(PageVO::getSvg).collect(Collectors.toList());
            SVG2PDFUtil.convertSVG2PDF(svgs,out);
            file.renameTo(new File(PDF_DIR+File.separator+articleId+PDF_SUFFIX));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            if(log.isErrorEnabled())
                log.error("找不到存放文件的目录");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (TranscoderException e) {
            e.printStackTrace();
        }

    }


    //导出一篇文章的PDF
    public void export(int articleId, HttpServletResponse resp) throws IOException, TranscoderException, DocumentException {
        File file = new File(PDF_DIR+File.separator+articleId+PDF_SUFFIX);
        if(!file.exists()){
            generatePDF(articleId);
        }
        String articleName = queryInfo(articleId).getName();
        resp.setContentType("application/pdf");
        resp.setHeader("Content-Disposition","attachment;filename="+ URLEncoder.encode(articleName,"UTF-8")+".pdf");
        Files.copy(file.toPath(),resp.getOutputStream());
    }
}
