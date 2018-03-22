package com.xf.jdks.dao.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Rio-Lee on 2016/6/10.
 * 数据值实体对象
 */
public class DataValue {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

    private DataValue(){}

    public static DataValue createByObject(Object val){
        return new DataValue().setValue(val);
    }

    private Object value;

    public String getExecuteSql(){
        if(this.value==null)return "null";
        if(this.value instanceof String){
            return "'"+this.value.toString().replaceAll("'","")+"'";
        }
        if(this.value instanceof Date)return "to_date('"+sdf.format(this.value)+"','yyyymmddhh24miss')";
        return this.value.toString();
    }

    public Object getValue() {
        if(this.value==null)return null;
        if(this.value instanceof String){
            return String.valueOf(this.value);
        }
        if(this.value instanceof Integer){
            return Integer.valueOf(this.value.toString());
        }
        if(this.value instanceof Long){
            return Long.valueOf(this.value.toString());
        }
        if(this.value instanceof Double){
            return Double.valueOf(this.value.toString());
        }
        if(this.value instanceof Boolean){
            return Boolean.valueOf(this.value.toString());
        }
        if(this.value instanceof Date){
            return sdf.format(this.value);
        }
        throw new ClassCastException();
    }

    public DataValue setValue(Object value) {
        this.value = value;
        return this;
    }
}
