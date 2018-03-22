package com.xf.jdks.commons.global;


import com.xf.jdks.commons.enums.EStandTables;
import com.xf.jdks.commons.util.BaseChecks;
import com.xf.jdks.exceptions.DictIsNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Rio-Lee on 2016/6/10.
 * 数据字典
 */
public class DataDict {

    private static Map<EStandTables, Map<String, String>> dicts = new HashMap<>();

    private static List<Map<String,Object>> provCitys = new ArrayList<>();

    private static List<Map<String, Object>> dictDriver = new ArrayList<>();

    private static Map<String, String> dictVersion = new HashMap<>();

    private static boolean isLoaded = false;

    public static void loadProvCitys(List<Map<String,Object>> provList){
        provCitys = provList;
    }

    public static String getCityCodeByChinese(String provCode ,String cityChinese) throws DictIsNotFoundException {
        for(Map<String,Object> provCity:provCitys){
            String pCode = (String) provCity.get("parentcity");
            String chName = (String)provCity.get("cityname");
            String code = (String)provCity.get("citycode");
            if(provCode.equals(pCode)&&chName.equals(cityChinese))return code;
        }
        throw new DictIsNotFoundException();
    }

    public static void loadDictDriver(List<Map<String, Object>> driverList) {
        dictDriver = driverList;
    }

    private static EStandTables searchEStandTableByString(String key) {
        EStandTables et = null;
        try {
            et = EStandTables.createOf(key);
        } catch (Exception e) {

        }
        return et;
    }

    private static String searchDictNameByColName(String colName) {
        for (Map<String, Object> item : dictDriver) {
            String col = (String) item.get("colname");
            String dictName = (String) item.get("dictname");
            if (col.equalsIgnoreCase(colName)) return dictName;
        }
        return null;
    }

    /**
     * 中文转代码 （抛异常)
     * @param dataMap
     * @return
     * @throws DictIsNotFoundException
     */
    public static Map<String, Object> doCastChineseToCodeForThrowExcepiton(Map<String, Object> dataMap) throws DictIsNotFoundException {
        Map<String, Object> rs = new DataMap<>();
        for (Map.Entry<String, Object> data : dataMap.entrySet()) {
            String dictName = searchDictNameByColName(data.getKey());
            Object v = data.getValue();
            if (dictName != null) {
                EStandTables est = searchEStandTableByString(dictName);
                if (est != null && v != null && !"".equals(v.toString())) {
                    String newValue = getCodeByChineseValue(est, v.toString());
                    rs.put(data.getKey(), newValue);
                    continue;
                }
            }
            rs.put(data.getKey(), data.getValue());
        }
        return rs;
    }


    /**
     * 代码转中文 （抛异常)
     * @param dataMap
     * @return
     * @throws DictIsNotFoundException
     */
    public static Map<String, Object> doCastCodeToChineseForThrowException(Map<String, Object> dataMap) throws DictIsNotFoundException {
        Map<String, Object> rs = new DataMap<>();
        for (Map.Entry<String, Object> data : dataMap.entrySet()) {
            String dictName = searchDictNameByColName(data.getKey());
            Object v = data.getValue();
            if (dictName != null) {
                EStandTables est = searchEStandTableByString(dictName);
                if (est != null && v != null && !"".equals(v.toString())) {
                    String newValue = getChineseValueByCode(est, v.toString());
                    rs.put(data.getKey(), newValue);
                    continue;
                }
            }
            rs.put(data.getKey(), data.getValue());
        }
        return rs;
    }


    /**
     * 代码转中文
     * @param dataMap
     * @return
     */
    public static Map<String, Object> doCastCodeToChinese(Map<String, Object> dataMap) {
        Map<String, Object> rs = new DataMap<>();
        for (Map.Entry<String, Object> data : dataMap.entrySet()) {
            String dictName = searchDictNameByColName(data.getKey());
            Object v = data.getValue();
            if (dictName != null) {
                EStandTables est = searchEStandTableByString(dictName);
                if (est != null && v != null && !"".equals(v.toString())) {
                    try {
                        String newValue = getChineseValueByCode(est, v.toString());
                        rs.put(data.getKey(), newValue);
                        continue;
                    } catch (DictIsNotFoundException e) {

                    }
                }
            }
            rs.put(data.getKey(), data.getValue());
        }
        return rs;
    }

    /**
     * 中文转代码
     * @param dataMap
     * @return
     */
    public static Map<String, Object> doCastChineseToCode(Map<String, Object> dataMap) {
        Map<String, Object> rs = new DataMap<>();
        for (Map.Entry<String, Object> data : dataMap.entrySet()) {
            String dictName = searchDictNameByColName(data.getKey());
            Object v = data.getValue();
            if (dictName != null) {
                EStandTables est = searchEStandTableByString(dictName);
                if (est != null && v != null && !"".equals(v.toString())) {
                    try {
                        String newValue = getCodeByChineseValue(est, v.toString());
                        rs.put(data.getKey(), newValue);
                        continue;
                    } catch (DictIsNotFoundException e) {

                    }
                }
            }
            rs.put(data.getKey(),v);
        }
        return rs;
    }


    /**
     * 代码转中文
     * @param dataList
     * @return
     */
    public static List<Map<String, Object>> doCastCodeToChineseByList(List<Map<String, Object>> dataList) {
        List<Map<String, Object>> rs = new ArrayList<>();
        for (Map<String, Object> data : dataList) {
            Map<String, Object> newMap = doCastCodeToChinese(data);
            rs.add(newMap);
        }
        return rs;
    }


    /**
     * 中文转代码
     * @param dataList
     * @return
     */
    public static List<Map<String, Object>> doCastChineseToCodeByList(List<Map<String, Object>> dataList) {
        List<Map<String, Object>> rs = new ArrayList<>();
        for (Map<String, Object> data : dataList) {
            Map<String, Object> newMap = doCastChineseToCode(data);
            rs.add(newMap);
        }
        return rs;
    }

    public static void printSelf() {
        System.out.println(dicts);
    }

    public static Map<String, List<Map<String, String>>> getAllDicts() {
        Map<String, List<Map<String, String>>> result = new HashMap<>();
        for (Map.Entry<EStandTables, Map<String, String>> dict : dicts.entrySet()) {
            List<Map<String, String>> list = new ArrayList<>();
            for (Map.Entry<String, String> entry : dict.getValue().entrySet()) {
                Map<String, String> map = new DataMap<>();
                map.put("name", entry.getValue());
                map.put("value", entry.getKey());
                list.add(map);
            }
            result.put(dict.getKey().toString(), list);
        }
        return result;
    }

    public static void loadDictVersionByDbs(List<Map<String, Object>> data) {
        for (Map<String, Object> item : data) {
            String cacheName = (String) item.get("CACHENAME");
            String version = (String) item.get("VERSION");
            if (cacheName != null && !cacheName.isEmpty()) dictVersion.put(cacheName, version);
        }
    }

    public static Map<String, String> getDictVersions() {
        return dictVersion;
    }

    /**
     * @param type 需要搜索的类型名
     * @param val  需要匹配的值
     * @return 如果匹配到CODE或CH则返回True
     */
    public static boolean isContains(String type, String val) {
        EStandTables et = EStandTables.createOf(type);
        Map<String, String> dict = dicts.get(et);
        if (dict == null) return false;
        for (Map.Entry<String, String> item : dict.entrySet()) {
            if (item.getKey().equals(val) || item.getValue().equals(val)) return true;
        }
        return false;
    }


    protected static Map<String, String> getCurrentDictByType(EStandTables type) throws DictIsNotFoundException {
        if (dicts.get(type) == null) throw new DictIsNotFoundException();
        return dicts.get(type);
    }

    /**
     * 代码转中文
     *
     * @param type    标准表
     * @param code    代码
     * @param iserror 判断是否为空，是否需要抛出异常信息
     * @return 中文信息
     * @throws DictIsNotFoundException 标准表找不到
     */
    private static String getCodeByChineseValue(EStandTables type, String code, boolean iserror) throws DictIsNotFoundException {
        if (BaseChecks.isChineseChar(code)) return code;
        String rs = null;
        try {
            rs = getCurrentDictByType(type).get(code.toUpperCase());
            if (rs == null) {
                if (iserror) throw new DictIsNotFoundException();
                rs = getCurrentDictByType(type).get(code.toLowerCase());
            }
        } catch (DictIsNotFoundException e) {
            e.printStackTrace();
        }
        return rs == null ? code : rs;
    }

    /**
     * 根据代码转换中文
     *
     * @param type 标准表
     * @param code 代码
     * @return 中文，抛异常信息，不存在就进行抛出异常信息
     * @throws DictIsNotFoundException
     */
    public static String getCodeByChineseValueForException(EStandTables type, String code) throws DictIsNotFoundException {
        return getCodeByChineseValue(type, code, true);
    }


    /**
     * @param type 字典类型(不区分大小写）无需带T_BZ_ 直接名称
     * @param code 字典对应代码
     * @return 该代码对应的中文字典值
     * @throws DictIsNotFoundException 如果找不到对应的值则抛出此异常
     */
    public static String getChineseValueByCode(EStandTables type, String code) throws DictIsNotFoundException {
        return getCodeByChineseValue(type, code, false);
    }

    /**
     * @param type  字典类型(不区分大小写）无需带T_BZ_ 直接名称
     * @param chval 中文字典值
     * @return 该中文字典值对应的字典代码
     * @throws DictIsNotFoundException 如果找不到对应的值则抛出此异常
     */
    public static String getCodeByChineseValue(EStandTables type, String chval) throws DictIsNotFoundException {
        if (chval == null) throw new DictIsNotFoundException(" 业务处理失败，失败原因：" + type + "没有找到对应的字典");
        try {
            for (Map.Entry<String, String> entry : getCurrentDictByType(type).entrySet()) {
                if (chval.equals(entry.getValue())) return entry.getKey();
            }
        } catch (DictIsNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
        throw new DictIsNotFoundException(" 业务处理失败，失败原因：" + type + "没有找到对应的字典");
    }


    /**
     * @param datas 初始化加载字典数据（只执行一次，如执行过则下次不执行）
     * @return 返回加载后当前字典的大小
     */
    public static int initLoadDataDicts(List<Map<String, Object>> datas) {
        if (!isLoaded) {
            addDictByList(datas);
            isLoaded = true;
        }
        return dicts.size();
    }

    /**
     * @param datas 重新加载字典（每次加载都会覆盖当前字典数据）
     * @return 当前字典大小
     */
    public static int reloadDataDicts(List<Map<String, Object>> datas) {
        dicts = new HashMap<>();
        isLoaded = false;
        return initLoadDataDicts(datas);
    }

    protected static void addDictByList(List<Map<String, Object>> datas) {
        if (datas != null && datas.size() > 0) {
            for (Map<String, Object> data : datas) {
                Object type = data.get("TYPE");
                if (type != null) {
                    addDictByDataResult(type.toString(), data);
                }
            }
        }
    }

    protected static void addDictByDataResult(String type, Map<String, Object> result) {
        if (type == null || type.isEmpty() || result == null) throw new IllegalArgumentException();
        EStandTables et = EStandTables.createOf(type.toUpperCase());
        if (dicts.get(et) == null) {
            dicts.put(et, new HashMap<String, String>());
        }
        Object key = result.get("CODE");
        Object val = result.get("CH");
        if (key == null || val == null) throw new IllegalArgumentException();
        dicts.get(et).put(key.toString(), val.toString());
    }
}
