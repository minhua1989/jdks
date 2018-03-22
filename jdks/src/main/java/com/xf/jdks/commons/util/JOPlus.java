package com.xf.jdks.commons.util;

import com.alibaba.fastjson.JSONObject;
import com.xf.jdks.commons.global.DataMap;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by root on 16-8-22.
 */
public class JOPlus extends JSONObject{

    public static JSONObject createForMapAsNotNull(Map<String,Object> map){
        Map<String,Object> ds = new DataMap<>();
        for(Entry<String,Object> entry:map.entrySet()){
            if(entry.getValue()!=null)ds.put(entry.getKey(),entry.getValue());
        }
        return createForMap(ds);
    }

    public static JOPlus newInstanceForMapAsNotNull(Map<String,Object> map){
        JOPlus rs = new JOPlus();
        for(Entry<String,Object> entry:map.entrySet()){
            if(entry.getValue()!=null)rs.put(entry.getKey(),entry.getValue());
        }
        return rs;
    }

    public static JOPlus newInstanceForMap(Map<String,Object> map){
        JOPlus rs = new JOPlus();
        for(Entry<String,Object> entry:map.entrySet()){
            rs.put(entry.getKey(),entry.getValue());
        }
        return rs;
    }

    public static JSONObject createForMap(Map<String,Object> map){
        JSONObject rs = new JSONObject();
        for(Entry<String,Object> entry:map.entrySet()){
            rs.put(entry.getKey(),entry.getValue());
        }
        return rs;
    }

    public Map<String,Object> toMap(){
        Map<String,Object> rs = new HashMap<>();
        for(Entry<String,Object> entry:this.entrySet()){
            rs.put(entry.getKey(),entry.getValue());
        }
        return rs;
    }

    public Map<String,Object> toDataMap(){
        Map<String,Object> rs = new DataMap<>();
        for(Entry<String,Object> entry:this.entrySet()){
            rs.put(entry.getKey(),entry.getValue());
        }
        return rs;
    }
}
