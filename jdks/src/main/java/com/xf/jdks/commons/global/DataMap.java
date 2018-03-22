package com.xf.jdks.commons.global;

import java.util.HashMap;

/**
 * Created by Rio-Lee on 2016/7/21.
 */
public class DataMap<K,V> extends HashMap<K,V> {
    @Override
    public V get(Object key) {
        String strKey = (String)key;
        V value = super.get(strKey);
        if(value==null&&strKey!=null)value=super.get(strKey.toLowerCase());
        if(value==null&&strKey!=null)value=super.get(strKey.toUpperCase());
        return value;
    }

    @Override
    public V put(K key, V value) {
        String strKey = (String) key;
        if(strKey!=null)strKey = strKey.toLowerCase();
        return super.put((K) strKey,value);
    }
}
