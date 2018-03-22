package com.xf.jdks.commons.global;

import com.xf.jdks.commons.util.JOPlus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by root on 16-8-24.
 */
public class ValidationReport {

    private List<JOPlus> successList = new ArrayList<>();

    private List<JOPlus> fieldList = new ArrayList<>();

    public List<JOPlus> getSuccessList() {
        return successList;
    }

    public List<JOPlus> getFieldList() {
        return fieldList;
    }

    public void addSuccessValidation(Map<String,Object> data){
        JOPlus jo = JOPlus.newInstanceForMap(data);
        jo.put("validationMessage","操作成功");
        getSuccessList().add(jo);
    }

    public void addFieldValidation(Map<String,Object> data,String msg){
        JOPlus jo = JOPlus.newInstanceForMap(data);
        jo.put("remark",msg);
        getFieldList().add(jo);
    }
}
