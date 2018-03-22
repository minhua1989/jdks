package com.xf.jdks.dao.bean;


import com.xf.jdks.commons.enums.EOperators;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Rio-Lee on 2016/6/10.
 * 条件实体对象
 */
public class Parameter {

    private String columnName;

    private List<DataValue> values;

    private EOperators operator;

    private boolean isAnd = true;

    private int speator;//0-no do 1-open 2-close;

    public int getSpeator() {
        return speator;
    }

    public Parameter setSpeator(int speator) {
        this.speator = speator;
        return this;
    }

    private Parameter(){}


    public Parameter setAnd(boolean and){
        this.isAnd = and;
        return this;
    }

    public boolean isAnd(){
        return this.isAnd;
    }

    /**
     * @param col 字段名称
     * @param operator 运算符：只能是 = != < <= > >= in 这几种
     * @param values      字段对应的值,当条件为in时可以传递多个值
     * @return     Parameter 实例
     */
    public static Parameter createParameter(String col,EOperators operator,Object... values){
        Parameter parameter = new Parameter();
        parameter.setParameter(col,operator,values);
        return parameter;
    }

    public static Parameter createParameter(String col,Object value){
        return createParameter(col,EOperators.等于,value);
    }

    public static List<Parameter> createParameters(Map<String,Object> params){
        if(params==null)return null;
        List<Parameter> parameters = new ArrayList<>();
        for(Map.Entry<String,Object> item:params.entrySet()){
            parameters.add(Parameter.createParameter(item.getKey(),item.getValue()));
        }
        return parameters;
    }

    public void setParameter(String col, EOperators operator, Object... values) {
        if (col == null || col.isEmpty()) throw new IllegalArgumentException();
        this.columnName = col;
        this.operator = operator;
        if (values == null) {
            this.values = null;
        } else {
            this.values = new ArrayList<>();
            for (int i = 0, len = values.length; i < len; i++) {
                DataValue item;
                if(values[i] instanceof DataValue){
                    item = (DataValue) values[i];
                }else {
                    item = DataValue.createByObject(values[i]);
                }
                this.values.add(item);
            }
        }
    }

    public String getColumnName() {
        return this.columnName;
    }

    public List<DataValue> getDataValues() {
        return this.values;
    }

    public String getOperator() {
        return this.operator.toString();
    }

    public String getUpdateSql(){
        if(getColumnName()==null||getDataValues()==null||getDataValues().isEmpty())throw new IllegalArgumentException();
        return getColumnName()+" = "+getDataValues().get(0).getExecuteSql();
    }


    public String getQuerySql() {
        if (getOperator() == null || getColumnName() == null) throw new IllegalArgumentException();
        if(this.operator == EOperators.类似){
            StringBuffer rs = new StringBuffer();
            StringBuffer sqlStr = new StringBuffer();
            String str = values.get(0).getExecuteSql();
            sqlStr.append(str.substring(1));
            sqlStr.setLength(sqlStr.length()-1);
            rs.append("'%");
            rs.append(sqlStr);
            rs.append("%'");
            return getColumnName()+" "+getOperator()+" "+rs.toString();
        }
        if(this.operator == EOperators.包含||this.operator == EOperators.不包含){
            StringBuffer sqlStr = new StringBuffer();
            for(DataValue item:getDataValues()){
                sqlStr.append(item.getExecuteSql());
                sqlStr.append(",");
            }
            sqlStr.setLength(sqlStr.length()-1);
            return getColumnName()+" "+getOperator()+" ("+ sqlStr+")";
        }
        if(this.operator == EOperators.为空||this.operator == EOperators.不为空){
            return getColumnName() + " "+getOperator();
        }
        return getColumnName()+" "+getOperator()+" "+(this.values==null?"null":this.values.get(0).getExecuteSql());
    }


}
