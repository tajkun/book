package com.ingzone.book.util;

import com.ingzone.book.model.Page;
import com.ingzone.book.model.SearchTO;
import lombok.Data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

/**
 * Created by 王镜鑫 on 17-9-27.
 * 请关注一下他的个人博客 wangjingxin.top
 */
public class SearchUtil {
    // private static final String HOST_PORT = "http://127.0.0.1:9200";
    private static final String HOST_PORT = "http://localhost:9200";
    @Data
    private static class query{
        private String query;
        private Integer boost;
        query(String query,int boost){
            this.query = query;
            this.boost = boost;
        }
    }
    @Data
    private static class High{
        private List<String> pre_tags = new ArrayList<>();
        private List<String> post_tags = new ArrayList<>();
        private Map<String,Object> fields = new HashMap<>();
        High(String pre, String post, String f){
            pre(pre);
            post(post);
            f(f);
        }
        public void pre(String s){
            pre_tags.add(s);
        }
        public void post(String s){
            post_tags.add(s);
        }
        public void f(String s){
            fields.put(s,new HashMap<>());
        }
    }
    private static String getPutBody(String content){
        Map<String,String> data = new HashMap<>(1);
        data.put("content",content);
        return JSON.toJSONString(data);
    }
    private static String queryDataBody(String key,Page page,String type){
        Map<String,Object> match2 = new HashMap<>();
        Map<String,Object> b_match = new HashMap<>();
        match2.put("content",new query(key,2));
        b_match.put("match",match2);
        List<Object> should = new ArrayList<>();
        should.add(b_match);
        Map<String,Object> map = new HashMap<>();
        map.put("should",should);
        Map<String,Object> bool = new HashMap<>();
        bool.put("bool",map);
        Map<String,Object> query = new HashMap<>();
        query.put("query",bool);
        query.put("from",page.getPage());
        query.put("size",page.getRows());
        query.put("highlight",new High("#","#",type));
        return JSON.toJSONString(query);
    }
    public static void put(int id,String content, String type){
        try {
            String s = Jsoup.connect(HOST_PORT+"/books/"+type+"/"+id)
                    .ignoreContentType(true)
                    .method(Connection.Method.PUT)
                    .requestBody(getPutBody(content))
                    .execute()
                    .body();
            System.out.println(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static Map<String,Object> query(String key, String type, Page page){
        Map<String,Object> map = new HashMap(2);
        List<SearchTO> list = new ArrayList<>();
        try {
            String s = Jsoup.connect(HOST_PORT+"/books/"+type+"/_search?_source=false")
                    .ignoreContentType(true)
                    .method(Connection.Method.POST)
                    .requestBody(queryDataBody(key,page,type))
                    .execute()
                    .body();
            JSON.parseObject(s).getJSONObject("hits").getJSONArray("hits").forEach(a-> list.add(new SearchTO(((JSONObject)a).getInteger("_id"),((JSONObject)a).getDouble("_score"),((JSONObject)a).getJSONObject("highlight").getJSONArray("content").get(0).toString())));
            map.put("total",JSON.parseObject(s).getJSONObject("hits").getInteger("total"));
            map.put("list",list);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }
    public static void delete(String id,String type){
        try {
            String s = Jsoup.connect(HOST_PORT+"/books/"+type+"/"+id)
                    .ignoreContentType(true)
                    .method(Connection.Method.DELETE)
                    .execute()
                    .body();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
