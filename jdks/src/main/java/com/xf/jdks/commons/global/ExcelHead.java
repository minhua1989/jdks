package com.xf.jdks.commons.global;

import com.xf.jdks.commons.util.JOPlus;

import java.util.Map;

/**
 * Created by root on 16-10-18.
 */
public class ExcelHead {

    private String headName;

    private Integer sort;

    private String mapKey;

    private boolean needConcat;

    public static ExcelHead createByExcelHeadByResultMap(Map<String,Object> result){
        ExcelHead eh = new ExcelHead();
        JOPlus jo = JOPlus.newInstanceForMap(result);
        eh.setSort(jo.getInteger("sort"));
        eh.setMapKey(jo.getString("colname"));
        eh.setHeadName(jo.getString("chname"));
        eh.setNeedConcat("1".equalsIgnoreCase(jo.getString("merage")));
        return eh;
    }

    public boolean isNeedConcat() {
        return needConcat;
    }

    public void setNeedConcat(boolean needConcat) {
        this.needConcat = needConcat;
    }

    public String getHeadName() {
        return headName;
    }

    public void setHeadName(String headName) {
        this.headName = headName;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getMapKey() {
        return mapKey;
    }

    public void setMapKey(String mapKey) {
        this.mapKey = mapKey;
    }
}
