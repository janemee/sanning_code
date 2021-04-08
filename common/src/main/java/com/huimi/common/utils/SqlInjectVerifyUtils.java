package com.huimi.common.utils;

/**
 * @author ylzzZ
 * sql注入效验
 */
public class SqlInjectVerifyUtils {

    public static boolean injectVerify(String str) {
        //字符串转换为小写
        str = str.toLowerCase();
        //自定义效验格式
       /* String badStr = "'|and|exec|execute|insert|select|delete|update|count|drop|*|%|chr|mid|master|truncate|" +
                "char|declare|sitename|net user|xp_cmdshell|;|or|-|+|,|like'|and|exec|execute|insert|create|drop|" +
                "table|from|grant|use|group_concat|column_name|" +
                "information_schema.columns|table_schema|union|where|select|delete|update|order|by|count|*|" +
                "chr|mid|master|truncate|char|declare|or|;|-|--|+|,|like|//|/|%|#";*/
        String verifyFormat = "'|count|like|and|exec|execute|union|create|insert|select|delete|update|drop|*|" +
                "chr|mid|master|truncate|char|declare|xp_|--|+|where|or|by|%|from|order|use";
        String [] arr = verifyFormat.split("\\|");
        for (String s : arr) {
            if (str.contains(s)) {
                return true;
            }
        }
        return false;
    }
}
