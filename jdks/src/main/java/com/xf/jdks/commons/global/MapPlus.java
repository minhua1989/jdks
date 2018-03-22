package com.xf.jdks.commons.global;

import com.alibaba.fastjson.JSONObject;
import com.xf.jdks.dao.bean.DataValue;
import com.xf.jdks.dao.bean.InsertParams;
import com.xf.jdks.dao.bean.Parameter;
import com.xf.jdks.dao.bean.UpdateParams;

import java.util.*;
import java.util.Map.Entry;

/**
 * Created by root on 16-11-9.
 */
public class MapPlus {
    public static MapPlus getInstance(Map<String, Object> map) {
        MapPlus mapPlus = new MapPlus();
        Map<String, Object> linkMap = new LinkedHashMap<>();
        linkMap.putAll(map);
        mapPlus.currentMap = linkMap;
        return mapPlus;
    }

    private Map<String, Object> currentMap;

    private MapPlus() {
    }

    public Object get(String key) {
        return currentMap.get(key);
    }

    public void put(String key, Object value) {
        currentMap.put(key, value);
    }

    public Set<Entry<String, Object>> entrySet() {
        return currentMap.entrySet();
    }

    public Set<String> keySet() {
        return currentMap.keySet();
    }

    public int size() {
        return currentMap.size();
    }

    public boolean isEmpty() {
        return currentMap.isEmpty();
    }

    public Object remove(String key) {
        return currentMap.remove(key);
    }

    public void clear() {
        currentMap.clear();
    }

    public Collection<Object> values() {
        return currentMap.values();
    }

    public void putAll(Map<String, Object> m) {
        currentMap.putAll(m);
    }

    public List<JSONObject> getKeyValueList() {
        List<JSONObject> kvList = new ArrayList<>();
        for (Entry<String, Object> entry : currentMap.entrySet()) {
            JSONObject kv = new JSONObject();
            kv.put("name", entry.getKey());
            kv.put("value", entry.getValue());
            kvList.add(kv);
        }
        return kvList;
    }

    public UpdateParams castToUpdateParams(String tab,String pkColumn){
        UpdateParams us = UpdateParams.createUpdateParams(tab);
        Object pkValue = get(pkColumn);
        if(pkValue==null)return null;
        us.addParamsForMap(currentMap);
        us.addWhereParameter(Parameter.createParameter(pkColumn,pkValue.toString()));
        return us;
    }

    public InsertParams castToInsertParams(String tab,String pkColumn,String id){
        InsertParams is = InsertParams.createInsertParams(tab);
        is.addColumn(pkColumn);
        is.addValue(DataValue.createByObject(id));
        currentMap.put(pkColumn,id);
        for (Entry<String,Object> e : currentMap.entrySet()){
            is.addColumn(e.getKey());
            is.addValue(DataValue.createByObject(e.getValue()));
        }
        return is;
    }

    public InsertParams castToInsertParams(String tab,String pkColumn){
        String id = UUID.randomUUID().toString();
        return castToInsertParams(tab,pkColumn,id);
    }

    public Map<String, Object> getValueKeyMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        for (Entry<String, Object> v : currentMap.entrySet()) {
            if (v.getValue() != null) map.put(v.getValue().toString(), v.getKey());
        }
        return map;
    }

    public Map<String,Object> removeNullOrEmptyItem(){
        Map<String, Object> map = new LinkedHashMap<>();
        for (Entry<String,Object> entry : removeNullItem().entrySet()){
            if(!"".equals(entry.getValue().toString())) map.put(entry.getKey(),entry.getValue());
        }
        return map;
    }

    public Map<String, Object> removeNullItem() {
        Map<String, Object> map = new LinkedHashMap<>();
        for (Entry<String, Object> entry : currentMap.entrySet()) {
            if (entry.getValue() != null) map.put(entry.getKey(), entry.getValue());
        }
        return map;
    }
}
