import com.alibaba.fastjson.JSONArray;
import com.ingzone.book.model.dto.PageDTO;
import com.ingzone.book.util.ArticleUtil;
import com.ingzone.book.util.SVG2PDFUtil;
import com.itextpdf.text.DocumentException;
import org.apache.batik.transcoder.TranscoderException;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

public class SVG2PDFUtilTest {


    public static void main(String[] args) throws IOException, DocumentException, TranscoderException {
        ArticleUtil articleUtil = new ArticleUtil(new FileInputStream("模板xml/春晓.xml"),1);
        List<PageDTO> pages = articleUtil.getPage();
        List<String> svgs = pages.stream().map(PageDTO::getSvg).collect(Collectors.toList());
        SVG2PDFUtil.convertSVG2PDF(svgs,new FileOutputStream("pdf.pdf"));

        //exportSVG(new File("模板xml/春晓.xml"));
    }

    private static void exportSVG(File article) throws IOException {
        ArticleUtil articleUtil = new ArticleUtil(new FileInputStream(article),1);
        List<PageDTO> pages = articleUtil.getPage();
        for(PageDTO page:pages){
            try(ByteArrayInputStream input = new ByteArrayInputStream(page.getSvg().getBytes());
                FileOutputStream fos = new FileOutputStream(page.getPage()+".svg")) {
                byte[] buf = new byte[1024];
                int len;
                while ((len = input.read(buf)) != -1) {
                    fos.write(buf, 0, len);
                    fos.flush();
                }
            }
        }
    }
}
