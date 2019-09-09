package com.ingzone.book.util;

import java.sql.*;

/**
 * Created by 王镜鑫 on 17-7-16.
 * 请关注一下他的个人博客 wangjingxin.top
 */
public class T2XUtil {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        String table = "config";
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://wangjingxin.top:3306/book?useUnicode=true&characterEncoding=UTF-8", "book", "book2017");
        PreparedStatement p = connection.prepareStatement("DESC " + table);
        ResultSet set = p.executeQuery();
        while (set.next()) {
            String s = set.getString(1);
            System.out.println("private Integer "+s+";");
            char[] cc = s.toCharArray();
            StringBuilder sb = new StringBuilder("");
            for (char c : cc) {
                String cs = c + "";
                if (cs.matches("[a-z]")) {
                    sb.append(cs.toUpperCase());
                } else sb.append("_" + cs);
            }
       //     System.out.println("private static Integer "+sb.toString()+" = null;");
            //System.out.println(sb.toString()+" = configDomain.get"+s.substring(0,1).toUpperCase()+s.substring(1,s.length())+"();");
        }
    }
}
