package com.xf.jdks.commons.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * 导入导出Excel模板操作工具
 * Created by Riolee on 2016/5/28.
 */
public class TemplateHelper {

    private static Properties tempConf = new Properties();//模板配置文件

    /*
     *  交易名，模板所涵的字段列表，有序。里面的MAP为EN 和 ZH   EN为表名|字段名    ZH为中文
     * */
    private static Map<String,List<Map<String,String>>> templateMap = new HashMap<>();



    /**
     * 加载配置文件
     * @param ins
     * @throws IOException 
     */
    public static void loadFile(InputStream ins) throws IOException{
    	if(tempConf.size()==0)tempConf.load(ins);
    	
    }


    /**
     * 将解析出来的EXCEL记录转换成List
     * @param args 整体数据集合
     * @param traceName  模板类型
     * @return
     */
    public static List<Map<String,Object>> converToResultList(String[][] args,String traceName){
    	List<Map<String,Object>> resultList = new ArrayList<>();
    	//拿到对应的模板
    	List<Map<String,String>> temp = getTraceTemplate(traceName);
    	if(args!=null&&args.length>0){
    		
    		for(int i=0,ilen=args.length;i<ilen;i++){
    			Map<String , Object> record = new HashMap<>();
    			for(int j=0,jlen=args[0].length;j<jlen;j++){
    				String key = temp.get(j).get("en");
    				record.put(key, args[i][j]);
    			}
    			resultList.add(record);
    		}
    	}
        return resultList;
    }

    /**
     * 初始化，加载配置文件
     * @throws UnsupportedEncodingException 
     */
    public static void initLoad() throws UnsupportedEncodingException{
    	templateMap = new HashMap<>();
    	for(String key:tempConf.stringPropertyNames()){
    		String val = tempConf.getProperty(key);
    		List<Map<String,String>> trace = new ArrayList<>();
    		String[] col = val.split(",");
    		for(int i=0,len=col.length;i<len;i++){
    			Map<String,String> item = new HashMap<String,String>();
    			item.put("en", col[i].split("-")[1]);
    			item.put("zh", new String(col[i].split("-")[0].getBytes("ISO-8859-1"),"utf-8" ));
    			trace.add(i, item);
    		}
    		templateMap.put(key, trace);
    	}
    }


    /**
     * 按交易名称范围搜索字段对应的中文名
     * @param traceName
     * @param colnum 此参数形式为   表名|字段名
     * @return
     */
    private static String findColnumChineseNameForTrace(String traceName,String colnum){
    	String rs = colnum;
    	List<Map<String,String>> list = getTraceTemplate(traceName);
    	for(Map<String,String> item:list){
    		if(item.get("en")!=null&&item.get("en").equals(colnum))return item.get("zh");
    	}
        return rs;
    }

    /**
     * 按交易及表范围搜索字段对应的中文名
     * @param traceName
     * @param tableName
     * @param colnum
     * @return
     */
    public static String findColnumChineseNameForTrace(String traceName,String tableName,String colnum){
        return findColnumChineseNameForTrace(traceName, tableName+"|"+colnum);
    }

    /**
     * 获取交易对应的表头模板
     * @param traceName
     * @return
     */
    public static List<Map<String,String>> getTraceTemplate(String traceName){
        return templateMap.get(traceName);
    }
}
