package com.ingzone.book.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import static java.io.File.separator;

/**
 * Created by wjx on 17-7-11.
 */
public class StaticResourceUtil {
    public static void delete(String type,String id){
        new File("/home/wjx/desktop/temp/古籍整理/static" +
                separator +
                type +
                separator +
                id).delete();
    }
    public static String upload(String type, MultipartFile file, String myId) {
        if(file==null)
            return null;
        String id = myId==null?UUID.randomUUID().toString().replaceAll("-",""):myId;
        String path = "/home/wjx/desktop/temp/古籍整理/static" + separator + type + separator + id;
        try {
            file.transferTo(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return id;
    }
}
