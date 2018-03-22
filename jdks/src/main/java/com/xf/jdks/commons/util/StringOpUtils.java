package com.xf.jdks.commons.util;

/**
 * Created by root on 16-10-28.
 */
public class StringOpUtils {

    public static String stringFix(Integer value,String ch,Integer len){
        String v = value.toString();
        StringBuffer preFix = new StringBuffer();
        for (int i=0;i<len-v.length();i++){
            preFix.append(ch);
        }
        preFix.append(v);
        return preFix.toString();
    }

    public static String stringZeroFix(Integer value,Integer len){
        return stringFix(value,"0",len);
    }
}
