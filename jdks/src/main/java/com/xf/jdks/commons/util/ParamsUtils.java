package com.xf.jdks.commons.util;

import java.util.Map;

/**
 * Created by xdp on 2016/6/26.
 * 针对Service层传递进去的参数的修改工具
 */
public class ParamsUtils {

    public static void removeItems(Map<String,Object> arg,String... keys){
        if(arg==null)return;
        if(keys!=null&&keys.length>0){
            for(String key:keys){
                arg.remove(key);
            }
        }
    }
}
