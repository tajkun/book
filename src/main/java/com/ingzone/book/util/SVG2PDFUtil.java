package com.ingzone.book.util;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BadPdfFormatException;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import org.apache.batik.transcoder.Transcoder;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.fop.svg.PDFTranscoder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

public class SVG2PDFUtil {
    static final Transcoder pdfTranscoder = new PDFTranscoder();
    static final int PDF_BUF_SIZE = 64*1024;

    public static void convertSVG2PDF(String svg,OutputStream out) throws DocumentException, IOException, TranscoderException {
        ArrayList<String> list = new ArrayList<>(1);
        list.add(svg);
        convertSVG2PDF(list,out);
    }

    public static void convertSVG2PDF(List<String> svgs,OutputStream out) throws DocumentException, IOException, TranscoderException {
        PdfReader pdfReader = readSVG(svgs.get(0));
        Document document = new Document(pdfReader.getPageSize(1));
        PdfCopy copy = new PdfCopy(document, out);
        document.open();
        concatPDF(document,copy,pdfReader);
        Iterator<String> iterator = svgs.iterator();
        iterator.next();//skip first
        while(iterator.hasNext()){
            String svg = iterator.next();
            PdfReader reader = readSVG(svg);
            concatPDF(document,copy,reader);
        }
        document.close();
    }

    private static PdfReader readSVG(String svg) throws IOException, TranscoderException {
        TranscoderInput transcoderInput = new TranscoderInput(new ByteArrayInputStream(svg.getBytes()));
        ByteArrayOutputStream baos = new ByteArrayOutputStream(PDF_BUF_SIZE);
        TranscoderOutput transcoderOutput = new TranscoderOutput(baos);
        pdfTranscoder.transcode(transcoderInput,transcoderOutput);

        return new PdfReader(baos.toByteArray());
    }


    private static void concatPDF(Document document,PdfCopy copy,PdfReader reader) throws IOException, BadPdfFormatException {
        final int pages = reader.getNumberOfPages();
        for(int i = 1;i<= pages;i++){
            document.newPage();
            PdfImportedPage page = copy.getImportedPage(reader,i);
            copy.addPage(page);
        }
    }


}
