package com.xf.jdks.commons.util;

import com.xf.jdks.commons.global.DataMap;

import java.io.BufferedReader;
import java.io.Reader;
import java.sql.Clob;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by lirushan on 2016/7/6.
 */
public class CollectionUtils {


    public static List<Map<String,Object>> margeMapByList(List<Map<String,Object>>... lists){
        List<Map<String,Object>> rs = new ArrayList<>();
        if(lists!=null&&lists.length>0){
            for(int i=0,len=lists[0].size();i<len;i++){
                Map<String,Object> data = new DataMap<>();
                rs.add(data);
            }
        }
        for(List<Map<String,Object>> list:lists){
            for(int i=0,len=rs.size();i<len;i++){
                for(Map.Entry<String,Object> entry:list.get(i).entrySet()){
                    rs.get(i).put(entry.getKey(),entry.getValue());
                }
            }
        }
        return rs;
    }

    public static Map<String,Object> castToDataMap(Map<String,Object> map){
        Map<String,Object> dataMap = new DataMap<>();
        for(Map.Entry<String,Object> entry:map.entrySet()){
            dataMap.put(entry.getKey(),entry.getValue());
        }
        return dataMap;
    }


    public static String hbMapValues(Map<String,Object> dest, String...keys){
        StringBuffer rs = new StringBuffer();
        for(String key:keys){
            Object value = dest.get(key);
            if(value!=null)rs.append(value.toString().trim());
        }
        return rs.toString();
    }

    public static void removeMapValueByKeys(Map<String,Object> dest,String...key){
        for(String k:key){
            dest.remove(k);
        }
    }

    public static String[] toArray(List<String> list){
        String[] rs = new String[list.size()];
        for(int i=0,len=list.size();i<len;i++){
            rs[i] = list.get(i);
        }
        return rs;
    }

    public static void castStringToDate(String fmt,Map<String,Object> dest,String... keys) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(fmt);
        for(String key:keys){
            String curr = (String) dest.get(key);
            if(curr!=null){
                Date currDate =  simpleDateFormat.parse(curr);
                dest.put(key,currDate);
            }
        }
    }

    public static Map<String,Object> copyValueByKeys(Map<String,Object> map,String... keys){
        Map<String,Object> rs = new DataMap<>();
        for(String key:keys){
            rs.put(key,map.get(key));
        }
        return rs;
    }

    public static Object[] getMapValueByKeys(Map<String,Object> map,String... keys){
        Object[] rs = new Object[keys.length];
        for(int i=0,len=keys.length;i<len;i++){
           /* if(keys[i].equalsIgnoreCase("msg")){
                try {
                    Clob clob = new SerialClob(map.get(keys[i]).toString().toCharArray());
                    rs[i] = clob;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                continue;
            }*/
            rs[i] = map.get(keys[i]);
        }
        return rs;
    }

    /**
     * 将集合中值为NULL或者值为空字符串的数据过滤掉
     * @param params 需要过滤的集合
     * @return 过滤后的集合
     */
    public static Map<String,Object> removeEmptyKeyByParams(Map<String,Object> params){
        Map<String,Object> map = new DataMap<>();
        for(Map.Entry<String,Object> entry:params.entrySet()){
            if(entry.getValue()!=null&&!entry.getValue().toString().isEmpty())map.put(entry.getKey(),entry.getValue());
        }
        return map;
    }


    /**
     * 将传入的集合中的键，转换为小写并返回
     * @param dest 需要转换的集合
     * @return 已转换的集合
     */
    public static Map<String,Object> converMapKeyToLowerCaseByMap(Map<String,Object> dest){
        Map<String,Object> current = new DataMap<>();
        for(Map.Entry<String,Object> entry:dest.entrySet()){
            current.put(entry.getKey(),entry.getValue());
        }
        return current;
    }


    /**
     * 将传入的集合中的键，转换为大写并返回
     * @param dest 需要转换的集合
     * @return 已转换的集合
     */
    public static Map<String,Object> converMapKeyToUpperCaseByMap(Map<String,Object> dest){
        Map<String,Object> current = new HashMap<>();
        for(Map.Entry<String,Object> entry:dest.entrySet()){
            current.put(entry.getKey().toUpperCase(),entry.getValue());
        }
        return current;
    }

    /**
     * 将传入的集合中的键，转换为小写并返回
     * @param dest 需要转换的集合
     * @return 已转换的集合
     */
    public static Map<String,Object> converMapKeyToLowerCaseByStrMap(Map<String,String> dest){
        Map<String,Object> current = new DataMap<>();
        for(Map.Entry<String,String> entry:dest.entrySet()){
            current.put(entry.getKey(),entry.getValue());
        }
        return current;
    }

    /**
     * 将传入的集合列表中的键，转换为小写并返回
     * @param dest 需要转换的集合列表
     * @return 已转换的集合列表
     */
    public static List<Map<String,Object>> converMapKeyToLowerCase(List<Map<String,Object>> dest){
        List<Map<String,Object>> result = new ArrayList<>();
        for(Map<String,Object> item:dest){
            Map<String,Object> current = new DataMap<>();
            for(Map.Entry<String,Object> entry:item.entrySet()){
                current.put(entry.getKey(),entry.getValue());
            }
            result.add(current);
        }
        return result;
    }

    /**
     * 将传入的集合列表中的键，转换为大写并返回
     * @param dest 需要转换的集合列表
     * @return 已转换的集合列表
     */
    public static List<Map<String,Object>> converMapKeyToUpperCase(List<Map<String,Object>> dest){
        List<Map<String,Object>> result = new ArrayList<>();
        for(Map<String,Object> item:dest){
            Map<String,Object> current = new HashMap<>();
            for(Map.Entry<String,Object> entry:item.entrySet()){
                current.put(entry.getKey().toUpperCase(),entry.getValue());
            }
            result.add(current);
        }
        return result;
    }

    /**
     * 将待复制的集合列表中的每一个元素复制一份并返回
     * @param dest 待复制的集合列表
     * @return 列表的副本
     */
    public static List<Map<String,Object>> cloneDataList(List<Map<String,Object>> dest){
        List<Map<String,Object>> rs = new ArrayList<>();
        for(Map<String,Object> item:dest){
            Map<String,Object> curr = new HashMap<>();
            for(Map.Entry<String,Object> map:item.entrySet()){
                curr.put(map.getKey(),map.getValue());
            }
            rs.add(curr);
        }
        return rs;
    }

    /**
     * @param desc 待复制的集合
     * @return 复制的副本
     */
    public static Map<String,Object> cloneMapData(Map<String,Object> desc){
        Map<String,Object> map = new HashMap<>();
        for(Map.Entry<String,Object> m:desc.entrySet()){
            map.put(m.getKey(),m.getValue());
        }
        return map;
    }

    /**
     * 根据指定字段名 转换clob类型为String
     * @param keys 字段名
     * @param result 结果集
     * @return
     */
    public static List<Map<String,Object>> convertClob2Str(List<Map<String,Object>> result,String... keys){
        for (Map<String,Object> map:result){
            for(String key:keys){
                if(map.get(key) instanceof Clob){
                    map.put(key,CollectionUtils.clob2Str((Clob)map.get(key)));
                }
            }
        }
        return result;
    }

    //clob类型转String类型 ,如果不转换查询会报json类型转换错误
    private static String clob2Str(Clob val){
        StringBuffer sb = new StringBuffer();
        try {
            Reader reader = val.getCharacterStream();
            BufferedReader bos = new BufferedReader(reader);
            String line = bos.readLine();
            while(!BaseChecks.hasEmptyStr(line)){
                sb.append(line);
                line = bos.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
