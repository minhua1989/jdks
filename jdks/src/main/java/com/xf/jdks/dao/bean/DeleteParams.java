package com.xf.jdks.dao.bean;


import com.xf.jdks.commons.util.BaseChecks;
import com.xf.jdks.dao.pojo.LoginInfo;
import com.xf.jdks.exceptions.ParameterNumberError;
import com.xf.jdks.exceptions.TableNameNullOrNotRightError;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rio-Lee on 2016/6/10.
 * 删除表参数实体
 */
public class DeleteParams implements IParams{

    private String tableName;

    private List<Parameter> params;

    private DBLog dbLog;

    public void setDbLog(LoginInfo loginInfo) throws Exception {
        DBLog obj = new DBLog(loginInfo);
        obj.setExeSql(getExecuteSql(),1);
        this.dbLog = obj;
    }

    @Override
    public DBLog getCurrentLog() {
        return getDbLog();
    }

    public DBLog getDbLog(){
        return this.dbLog;
    }

    private DeleteParams() {
    }

    protected void addParams(Parameter... params){
        for(Parameter param:params){
            addParam(param);
        }
    }

    protected void addParam(Parameter param){
        if(params==null)params=new ArrayList<>();
        if(param!=null)params.add(param);
    }

    protected void setTableName(String tbname){
        if(BaseChecks.checkTableNameIsRight(tbname)) {
            this.tableName = tbname;
        }
    }

    public static DeleteParams createDeleteParams(String tableName,Parameter... params){
        DeleteParams dp = new DeleteParams();
        dp.setTableName(tableName);
        dp.addParams(params);
        return dp;
    }

    @Override
    public String getExecuteSql() throws Exception{
        StringBuffer sb = new StringBuffer();
        if(!BaseChecks.checkTableNameIsRight(tableName))throw new TableNameNullOrNotRightError();
        sb.append(tableName+" where ");
        if(params==null||params.isEmpty())throw new ParameterNumberError();
        for(int i=0,len=params.size();i<len;i++){
            if(i>0&&i<len){
                sb.append(params.get(i).isAnd()?" and ":" or ");
            }
            sb.append(params.get(i).getQuerySql());
        }
        return sb.toString();
    }

    @Override
    public boolean isCanBeExecute() {
        return BaseChecks.checkTableNameIsRight(tableName)&&params!=null&&params.size()>0;
    }

    @Override
    public void printSelf() {
        System.out.println("当前为删除操作");
        System.out.println("当前表名： "+this.tableName);
        try {
            System.out.println("当前ＳＱＬ： "+getExecuteSql());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
