package com.xf.jdks.dao.bean;


import com.xf.jdks.commons.util.BaseChecks;
import com.xf.jdks.dao.pojo.LoginInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Rio-Lee on 2016/6/10.
 * 新增记录参数实体
 */
public class InsertParams implements IParams {

    private String tableName;
    private List<String> columns;
    private List<DataValue> values;
    private DBLog dbLog;

    public void setDbLog(LoginInfo loginInfo) throws Exception {
        DBLog obj = new DBLog(loginInfo);
        obj.setExeSql(getExecuteSql(),0);
        this.dbLog = obj;
    }

    @Override
    public DBLog getCurrentLog()  {
        return getDbLog();
    }

    public DBLog getDbLog(){
        return this.dbLog;
    }

    private InsertParams(){}

    public void addParamsForMap(Map<String,Object> map){
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            if("desc".equalsIgnoreCase(key)){
                key = "\"DESC\"";
            }
            if("type".equalsIgnoreCase(key)){
                key = "\"TYPE\"";
            }
            if("order".equalsIgnoreCase(key)){
                key = "\"ORDER\"";
            }
            addColumn(key);
            addValue(DataValue.createByObject(entry.getValue()));
        }
    }

    public boolean checkColumnsValuesRight(){
        if(columns==null||values==null)return false;
        return columns.size()==values.size();
    }

    public void addColumn(String col){
        if(columns==null)columns=new ArrayList<>();
        if(col!=null&&!col.isEmpty()){
            columns.add(col);
        }
    }

    protected void addValues(DataValue... dvs){
        if(dvs!=null&&dvs.length>0){
            for(int i=0,len=dvs.length;i<len;i++){
                addValue(dvs[i]);
            }
        }
    }

    public void addValue(DataValue dv){
        if(this.values==null)this.values=new ArrayList<>();
        values.add(dv);
    }

    public void setValues(Object... vals){
        if(vals!=null){
            for(Object obj:vals){
                if(obj instanceof DataValue){
                    addValues((DataValue) obj);
                }else {
                    addValues(DataValue.createByObject(obj));
                }
            }
        }
    }

    public void resetValues(Object... vals){
        if(values==null)values = new ArrayList<>();
        values.clear();
        setValues(vals);
    }


    protected void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public static InsertParams createInsertParams(String tbName, Map<String,Object> params){
        InsertParams ip  = new InsertParams();
        ip.setTableName(tbName);
        for(Map.Entry<String,Object> entry:params.entrySet()){
            ip.addColumn(entry.getKey());
            ip.setValues(entry.getValue());
        }
        return ip;
    }

    public static InsertParams createInsertParams(String tableName, String... cols){
         if(tableName==null||cols==null||tableName.isEmpty()|| BaseChecks.hasEmptyStr(cols))throw new IllegalArgumentException();
        InsertParams ip  = new InsertParams();
        ip.setTableName(tableName);
        if(cols!=null&&cols.length>0){
            for(int i=0,len=cols.length;i<len;i++){
                ip.addColumn(cols[i]);
            }
        }
        return  ip;
    }

    @Override
    public String getExecuteSql() throws Exception {
        if (tableName == null || tableName.isEmpty()) throw new IllegalArgumentException();
        StringBuffer sb = new StringBuffer();
        sb.append(tableName);
        sb.append(" (");
        for (int i = 0, len = columns.size(); i < len; i++) {
            sb.append(columns.get(i));
            sb.append(",");
        }
        sb.setLength(sb.length() - 1);
        sb.append(") values (");
        for (int i = 0, len = values.size(); i < len; i++) {
            if (values.get(i) == null) {
                sb.append("null");
            } else {
                sb.append(values.get(i).getExecuteSql());
            }
            sb.append(",");
        }
        sb.setLength(sb.length() - 1);
        sb.append(")");
        return sb.toString();
    }

    @Override
    public boolean isCanBeExecute() {
        return BaseChecks.checkTableNameIsRight(tableName)&&checkColumnsValuesRight();
    }

    @Override
    public void printSelf() {
        System.out.println("当前为新增操作");
        System.out.println("当前表名： "+this.tableName);
        try {
            System.out.println("当前ＳＱＬ： "+getExecuteSql());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
