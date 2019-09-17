package com.ingzone.book.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wjx on 17-7-9.
 */
public class DatabaseUtil {
    static class Field {
        String name;
        String type;

        @Override
        public String toString() {
            if (name.equals("id")) {
                if (type.contains("int")) {
                    return name + " " + type + " NOT NULL AUTO_INCREMENT,";
                }
            }
            return name + " " + type + ",";
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("/home/wjx/my/work/java/book/数据库.md"))));
        String s;
        String table = null;
        List<Field> fieldList = new ArrayList();
        while ((s = br.readLine()) != null) {
            if (s.contains(":")) {
                if (table == null) {
                    table = s.split(":")[1];
                } else {
                    insert(table, fieldList);
                 //   if ("trade".equals(table))
                   //     getBeanDefine(fieldList);
                    //  getAllColumn(fieldList,!true);
                    table = s.split(":")[1];
                    fieldList.clear();
                }
            } else {
                Field field = new Field();
                field.name = s.split("\\s+")[1];
                field.type = s.split("\\s+")[2];
                fieldList.add(field);
            }
        }
    }

    private static void getBeanDefine(List<Field> fieldList) {
        final String[] s = {""};
        String one = "private # #;\n";
        fieldList.forEach(a -> s[0] += one.replaceFirst("#", a.type.contains("int") ? "Integer" : (a.type.contains("varchar") ? "String" : "Timestamp")).replaceFirst("#", a.name));
        System.out.println(s[0]);
    }

    private static void insert(String table, List<Field> fieldList) throws SQLException, ClassNotFoundException {
        String s = "create table # (#)".replaceFirst("#", table);
        final String[] in = {""};
        fieldList.forEach(a -> in[0] += a);
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/book?useUnicode=true&characterEncoding=UTF-8", "root", "root");
        String sql = s.replaceFirst("#", in[0] + "PRIMARY KEY (`id`)") + " ENGINE=InnoDB DEFAULT CHARSET=utf8";
        System.out.println(sql);
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.execute();
    }

    private static void getAllColumn(List<Field> fieldList, boolean yes) {
        final String[] all = {""};
        String s = yes ? "\t\t\t%,\n" : "\t\t\t#{%},\n";
        fieldList.forEach(a -> {
            all[0] += s.replaceFirst("%", a.name);
        });
        System.out.println(all[0]);
    }
}
