package com.xf.jdks.dao.bean;


import com.xf.jdks.commons.util.BaseChecks;
import com.xf.jdks.dao.pojo.LoginInfo;
import com.xf.jdks.exceptions.CanNotBeExecuteError;
import com.xf.jdks.exceptions.ParameterNumberError;
import com.xf.jdks.exceptions.TableNameNullOrNotRightError;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Rio-Lee on 2016/6/10.
 * 更新表参数实体
 */
public class UpdateParams implements IParams{
    private String tableName;

    private List<Parameter> params;

    private List<Parameter> wheres;

    private DBLog dbLog;

    public void setDbLog(LoginInfo loginInfo) throws Exception {
        DBLog obj = new DBLog(loginInfo);
        obj.setExeSql(getExecuteSql(),2);
        this.dbLog = obj;
    }

    @Override
    public DBLog getCurrentLog(){
        return getDbLog();
    }

    public DBLog getDbLog(){
        return this.dbLog;
    }

    private UpdateParams() {}

    public void clearParams(){
        if(params!=null)params.clear();
        if(wheres!=null)wheres.clear();
    }

    public boolean hasSetParams(){
        return params!=null&&params.size()>0;
    }

    public void addParamsForMap(Map<String,Object> map){
        for(Map.Entry<String,Object> entry:map.entrySet()){
            Object value = entry.getValue();
            if(value!=null&&!"".equals(value.toString().trim())){
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
                this.addParam(Parameter.createParameter(key,entry.getValue()));
            }
        }
    }

    public void addWhereParameter(Parameter param){
        if(wheres==null)wheres = new ArrayList<>();
        if(param!=null)wheres.add(param);
    }

    public void addWhereParameters(Parameter... parameters){
        if(parameters!=null&&parameters.length>0){
            for(Parameter parameter:parameters){
                addWhereParameter(parameter);
            }
        }
    }
    public UpdateParams addParams(List<Parameter> args){
        for (int i=0,len=args.size();i<len;i++){
            addParam(args.get(i));
        }
        return this;
    }


    public UpdateParams addParam(Parameter param){
        if(params==null)params=new ArrayList<>();
        if(param!=null)params.add(param);
        return this;
    }

    public UpdateParams addParams(Parameter... params){
        if(params!=null&&params.length>0){
            for(Parameter parameter:params){
                addParam(parameter);
            }
        }
        return this;
    }

    protected void setTableName(String tableName){
        if(BaseChecks.checkTableNameIsRight(tableName)) {
            this.tableName = tableName;
        }
    }

    public static UpdateParams createUpdateParamsForMap(String tbname,Map<String,Object> params){
        UpdateParams up = new UpdateParams();
        up.setTableName(tbname);
        for(Map.Entry<String,Object>entry:params.entrySet()){
            if(entry.getValue()==null||"".equalsIgnoreCase(entry.getValue().toString().trim()))continue;
            if("id".equalsIgnoreCase(entry.getKey())){
                up.addWhereParameter(Parameter.createParameter(entry.getKey(),entry.getValue()));
                continue;
            }
            up.addParams(Parameter.createParameter(entry.getKey(),entry.getValue()));
        }
        return up;
    }

    public static UpdateParams createUpdateParams(String tableName,Parameter... parameters){
        UpdateParams up = new UpdateParams();
        up.setTableName(tableName);
        up.addParams(parameters);
        return up;
    }

    @Override
    public String getExecuteSql() throws Exception{
        if(!isCanBeExecute())throw new CanNotBeExecuteError();
        StringBuffer sb = new StringBuffer();
        if(!BaseChecks.checkTableNameIsRight(tableName))throw new TableNameNullOrNotRightError();
        sb.append(tableName+" set ");
        if(params==null||params.isEmpty())throw new ParameterNumberError();
        for(Parameter param:params){
            sb.append(param.getUpdateSql());
            sb.append(",");
        }
        sb.setLength(sb.length()-1);
        sb.append( " where ");
        for(int i=0,len=wheres.size();i<len;i++){
            Parameter curr = wheres.get(i);
            if(i>0&&i<len){
                sb.append(wheres.get(i).isAnd()?" and ":" or ");
            }
            if(curr.getSpeator()==1)sb.append("(");
            sb.append(wheres.get(i).getQuerySql());
            if(curr.getSpeator()==2)sb.append(")");
        }
        return sb.toString();
    }

    @Override
    public boolean isCanBeExecute() {
        return BaseChecks.checkTableNameIsRight(tableName)&&params!=null&&params.size()>0&&wheres!=null&&wheres.size()>0;
    }

    @Override
    public void printSelf() {
        System.out.println("当前为更新操作");
        System.out.println("当前表名： "+this.tableName);
        try {
            System.out.println("当前ＳＱＬ： "+getExecuteSql());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addParamsForMapValueCanKongString(Map<String,Object> map){
        for(Map.Entry<String,Object> entry:map.entrySet()){
            Object value = entry.getValue();
            if(value!=null){
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
                this.addParam(Parameter.createParameter(key,entry.getValue()));
            }
        }
    }
}
