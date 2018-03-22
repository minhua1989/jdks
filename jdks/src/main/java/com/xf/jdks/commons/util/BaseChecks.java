package com.xf.jdks.commons.util;


import com.xf.jdks.annotation.RuleMethod;
import com.xf.jdks.annotation.ValidateClass;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Rio-Lee on 2016/6/10.
 * 基本判断工具类
 */
@ValidateClass
public class BaseChecks {

    /**
     * @param objs 被检查的对象
     * @return 如果传入参数中包含NULL则返回True
     */
    public static boolean hasNullObject(Object... objs){
        for(Object obj:objs) {
            if(obj==null)return true;
        }
        return false;
    }

    /**
     * 检查表名是否正确
     *
     * @param tableName 需要检查的表名
     * @return 正确返回True不正确返回false
     */
    @RuleMethod(ruledesc="检查表名是否正确")
    public static boolean checkTableNameIsRight(String tableName) {
        if (tableName == null || tableName.isEmpty()) return false;
        if (tableName.contains(" ")) return false;
        return true;
    }

    /**
     * 检查是否包含不正确的表名
     *
     * @param tbnames 需要检查的表名
     * @return 包含返回True不包含返回False
     */
    public static boolean checkTableNamesHasWrong(String... tbnames) {
        if (tbnames == null || tbnames.length == 0) return false;
        for (String tbname : tbnames) {
            if (!checkTableNameIsRight(tbname)) return true;
        }
        return false;
    }

    /**
     * 判断传入的字符串中是否存在NULL或空字符串
     *
     * @param strs 需要判断的字符串
     * @return 存在返回Ture不存在返回False
     */
    public static boolean hasEmptyStr(String... strs) {
        if (strs == null || strs.length == 0) return false;
        for (int i = 0, len = strs.length; i < len; i++) {
            if (strs[i] == null || strs[i].isEmpty()) return true;
        }
        return false;
    }

    public static boolean hasEmptyStr(List<String> list) {
        if (list == null || list.isEmpty()) return false;
        String[] strs = new String[list.size()];
        list.toArray(strs);
        return hasEmptyStr(strs);
    }

    /**
     * 去除字符串两端的中英文空格【任俊】
     * @param str 需要去除两端空格的字符串
     * @return 返回去除两端空格的新字符串
     */
    public static String bothEndsStr(String str){
        if(str == null || str.trim().equals(""))return "";
        int startIndex = 0;
        int endIndex = str.length()-1;
        while(startIndex < endIndex && str.charAt(startIndex) == ' '){startIndex++;}
        while(startIndex < endIndex && str.charAt(endIndex) == ' '){endIndex--;}
        return str.substring(startIndex, endIndex+1);
    }


    /**
     * 判断字符串是否有中文【任俊】
     * @param str 验证的字符串
     * @return 包含返回True不包含返回False
     */
    public static boolean isChineseChar(String str) {
        boolean temp = false;
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            temp = true;
        }
        return temp;
    }

}
