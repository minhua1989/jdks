package com.xf.jdks.commons.ext;

import com.alibaba.fastjson.JSONObject;
import com.xf.jdks.dao.bean.*;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by root on 16-10-21.
 */
public class DBMap extends HashMap {

    private String dbTable;

    public String getDbTable() {
        return dbTable;
    }

    public void setDbTable(String dbTable) {
        this.dbTable = dbTable;
    }

    public Object get(String key) {
        for (Object mapKey : super.keySet()) {
            if (mapKey.toString().equalsIgnoreCase(key)) return super.get(mapKey);
        }
        return null;
    }
    /*   Data Base Method Start  */
    public DeleteParams getDeleteParams(String tableName){
        String update_key_column = (String) this.get("update_key_column");
        String update_key_value = (String) this.get("update_key_value");
        if (update_key_column==null||update_key_value==null)throw new NullPointerException();
        return DeleteParams.createDeleteParams(dbTable==null?tableName:dbTable,Parameter.createParameter(update_key_column,update_key_value));
    }

    public InsertParams getInsertParams(String tableName) {
        InsertParams ins = InsertParams.createInsertParams(dbTable==null?tableName:dbTable);
        for (Object mapKey : super.keySet()) {
            Object value = super.get(mapKey);
            if (value != null) {
                ins.addColumn(mapKey.toString());
                ins.addValue(DataValue.createByObject(value));
            }
        }
        return ins;
    }

    public UpdateParams getUpdateParams(String tableName)throws NullPointerException{
        UpdateParams upd = UpdateParams.createUpdateParams(dbTable==null?tableName:dbTable);
        String update_key_column = (String) this.get("update_key_column");
        String update_key_value = (String) this.get("update_key_value");
        for (Object mapKey : super.keySet()) {
            if ("update_key_column".equalsIgnoreCase(mapKey.toString())||"update_key_column".equalsIgnoreCase(mapKey.toString()))continue;
            Object value = super.get(mapKey);
            upd.addParams(Parameter.createParameter(mapKey.toString(), value));
        }
        if (update_key_column==null||update_key_value==null)throw new NullPointerException();
        upd.addWhereParameter(Parameter.createParameter(update_key_column,update_key_value));
        return upd;
    }
    /*   Data Base Method End  */

    /* Json Method Start */
    public JSONObject getJsonObject(){
        JSONObject json = new JSONObject();
        for(Object mapKey:this.keySet()){
            json.put(mapKey.toString(),this.get(mapKey));
        }
        return json;
    }
    public void readForJsonObject(JSONObject object){
        for(Map.Entry<String,Object> entry:object.entrySet()){
            this.put(entry.getKey(),entry.getValue());
        }
    }
    /* Json Method End */

    /* File Method Start */
    public void readForFile(String path) throws IOException {
        File file = new File(path);
        if(file.exists()){
            String temp = "";
            StringBuffer data = new StringBuffer();
            FileReader in = new FileReader(file);
            BufferedReader reader = new BufferedReader(in);
            while ((temp=reader.readLine())!=null){
                data.append(temp);
            }
            reader.close();in.close();
            JSONObject jsonObject = JSONObject.parseObject(data.toString());
            readForJsonObject(jsonObject);
        }
    }
    public void writeToFile(String path) throws IOException {
        File file = new File(path);
        File parent = file.getParentFile();
        if(!parent.exists())parent.mkdirs();
        if(!file.exists())file.createNewFile();
        FileWriter out = new FileWriter(file);
        JSONObject object = getJsonObject();
        out.write(object.toJSONString());
        out.close();
    }
    /* File Method End */

    /* Static Method Start */
    public static DBMap createByJsonText(String jsonText){
        JSONObject object = JSONObject.parseObject(jsonText);
        DBMap db = new DBMap();
        for(Map.Entry<String,Object> entry:object.entrySet()){
            db.put(entry.getKey(),entry.getValue());
        }
        return db;
    }

    public static DBMap createByJavaBean(Object javaBean) throws InvocationTargetException, IllegalAccessException {
        DBMap db = new DBMap();
        Method[] methods = javaBean.getClass().getDeclaredMethods();
        for(int i=0,len=methods.length;i<len;i++){
            Method method = methods[i];
            int paramLength = method.getParameterTypes().length;
            if(method.getName().startsWith("get")&&paramLength==0){
                String key = method.getName().substring(3).toLowerCase();
                Object value = method.invoke(javaBean);
                db.put(key,value);
            }
        }
        return db;
    }

    public static DBMap createByMap(Map map){
        DBMap db = new DBMap();
        for(Object key:map.keySet()){
            db.put(key,map.get(key));
        }
        return db;
    }
    /* Static Method End */
}
