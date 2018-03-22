package com.xf.jdks.commons.dict;

import com.xf.jdks.commons.global.DataMap;
import com.xf.jdks.commons.util.DBUtils;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by root on 16-10-19.
 */
public class DBDictUtils {
//
//    private static final String QUERYDICT = "SELECT BZTABLE,CODECOLUMN,NAMECOLUMN FROM T_DBDICTINFO";
//
//    private static final String ITEMCOUNT = "SELECT COUNT(1) NUM FROM T_DBDICTITEM";
//
//    private static final String QUERYITEM = "SELECT BZTABLE,MAPKEY FROM T_DBDICTITEM";
//
//    private static List<DictRom> dictRomList;
//
//    private static List<DictItem> dictItemList;
//
//    private static boolean isLoaded;
//
//
//    private static DictItem searchDictItemByMapKey(String mapKey) throws Exception {
//        flushItem();
//        for (DictItem item : dictItemList) {
//            if (item.getMapKey().equalsIgnoreCase(mapKey)) {
//                return item;
//            }
//        }
//        return null;
//    }
//
//    private static DictRom searchDictRomByBzTable(String bzTable) throws Exception {
//        reloadDictRom();
//        for (DictRom rom : dictRomList) {
//            if (rom.getTableName().equalsIgnoreCase(bzTable)) return rom;
//        }
//        return null;
//    }
//
//    public static List<Map<String,Object>> castDataByList(List<Map<String,Object>> dataList) throws Exception {
//        List<Map<String,Object>> rs = new ArrayList<>();
//        for(Map<String,Object> data:dataList){
//            rs.add(castDataByMap(data));
//        }
//        return rs;
//    }
//
//    public static Map<String, Object> castDataByMap(Map<String, Object> data) throws Exception {
//        Map<String, Object> rs = new DataMap<>();
//        for (Map.Entry<String, Object> entry : data.entrySet()) {
//            DictItem currItem = searchDictItemByMapKey(entry.getKey());
//            if (currItem == null) {
//                rs.put(entry.getKey(), entry.getValue());
//                continue;
//            }
//            DictRom currRom = searchDictRomByBzTable(currItem.getBzTable());
//            if (currRom == null) {
//                rs.put(entry.getKey(), entry.getValue());
//                continue;
//            }
//            Object value = currRom.castValue(entry.getValue());
//            rs.put(entry.getKey(), value);
//        }
//        return rs;
//    }
//
//    private static void reloadDictRom() throws Exception {
//        if (!isLoaded) {
//            dictRomList = new ArrayList<>();
//            Connection conn = DBUtils.GetConnectionOracleMaster();
//            PreparedStatement pre = conn.prepareStatement(QUERYDICT);
//            ResultSet rs = pre.executeQuery();
//            while (rs.next()) {
//                DictRom dictRom = new DictRom();
//                dictRom.setTableName(rs.getString("BZTABLE"));
//                dictRom.setCodeColumn(rs.getString("CODECOLUMN"));
//                dictRom.setNameColumn(rs.getString("NAMECOLUMN"));
//                dictRomList.add(dictRom);
//            }
//            isLoaded = true;
//            rs.close();
//            pre.close();
//            conn.close();
//        }
//    }
//
//    private static boolean needLoad;
//
//    private static void flushItem() throws Exception {
//        if (needLoad || dictItemList == null) {
//            Connection conn = DBUtils.GetConnectionOracleMaster();
//            dictItemList = new ArrayList<>();
//            PreparedStatement pre = conn.prepareStatement(QUERYITEM);
//            ResultSet rs = pre.executeQuery();
//            while (rs.next()) {
//                DictItem item = new DictItem();
//                item.setBzTable(rs.getString("BZTABLE"));
//                item.setMapKey(rs.getString("MAPKEY"));
//                dictItemList.add(item);
//            }
//            needLoad = false;
//            rs.close();
//            pre.close();
//            conn.close();
//        }
//    }

}
