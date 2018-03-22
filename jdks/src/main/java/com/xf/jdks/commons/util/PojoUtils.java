package com.xf.jdks.commons.util;


import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Rio-Lee on 2016/6/14.
 * Pojo转换工具
 */
public class PojoUtils implements Serializable{

    /**
     * @param args 需要被转换的集合列表
     * @return 将所有字段转换为大写后返回数据
     */
    public static List<Map<String,Object>> mapKeyToUpperCaseByList(List<Map<String,Object>> args){
        if(args==null)return null;
        List<Map<String,Object>> result = new ArrayList<>();
        for(Map<String,Object> item:args){
            result.add(mapKeyToUpperCase(item));
        }
        return result;
    }

    /**
     * @param args 需要被转换的集合列表
     * @return 将所有字段转换为小写后返回数据
     */
    public static List<Map<String,Object>> mapKeyToLowerCaseByList(List<Map<String,Object>> args){
        if(args==null)return null;
        List<Map<String,Object>> result = new ArrayList<>();
        for(Map<String,Object> item:args){
            result.add(mapKeyToLowerCase(item));
        }
        return result;
    }

    /**
     * @param map 需要被转换的数据
     * @return 将所有字段转换为大写后返回数据
     */
    public static Map<String,Object> mapKeyToUpperCase(Map<String,Object> map){
        return mapKeyCaster(map,true);
    }

    /**
     * @param map 需要被转换的数据
     * @return 将所有字段转换为小写后返回数据
     */
    public static Map<String,Object> mapKeyToLowerCase(Map<String,Object> map){
        return mapKeyCaster(map,false);
    }

    private static Map<String,Object> mapKeyCaster(Map<String,Object> map, boolean isUpperCase){
        if(map==null)return null;
        Map<String,Object> result = new HashMap<>();
        for(Map.Entry<String,Object> item:map.entrySet()){
            result.put(isUpperCase?item.getKey().toUpperCase():item.getKey().toLowerCase(),item.getValue());
        }
        return result;
    }


    /**
     * @param pojo 需要被转换的Pojo
     * @return Map数据
     */
    public static Map<String,Object> converPojoToMap(Object pojo) throws InvocationTargetException, IllegalAccessException {
        if(pojo==null)return null;
        Map<String,Object> result = new HashMap<>();
        Method[] getters = pojo.getClass().getDeclaredMethods();
        for(Method method:getters){
            String methodName = method.getName();
            if(methodName.startsWith("get")&&!methodName.equals("getClass")&&!methodName.equals("getSerializableID")){
                result.put(methodNameToPropName(methodName),method.invoke(pojo));
            }
        }
        return result;
    }

    private static String methodNameToPropName(String methodName){
        return methodName.length()>4?methodName.substring(3,4).toLowerCase()+methodName.substring(4):methodName.substring(3,4).toLowerCase();
    }

    /**
     * @param map       需要转换成Pojo的Map数据集合
     * @param pojoClass 需要转成的Pojo
     * @return Pojo
     */
    public static <T>T converMapToPojo(Map<String, Object> map, Class<T> pojoClass) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        if (map == null||map.isEmpty()) return null;
        T result = pojoClass.newInstance();
        for(Map.Entry<String,Object> item:map.entrySet()){
            String methodName = propNameToMethodName(item.getKey());
            if(item.getValue()==null)continue;
            Method setMethod = pojoClass.getDeclaredMethod(methodName,item.getValue().getClass());
            setMethod.invoke(result,item.getValue());
        }
        return result;
    }

    private static String propNameToMethodName(String propName){
        if(propName==null||propName.isEmpty())throw new IllegalArgumentException();
        String result = "set"+propName.substring(0,1).toUpperCase()+propName.substring(1);
        return result;
    }
}
