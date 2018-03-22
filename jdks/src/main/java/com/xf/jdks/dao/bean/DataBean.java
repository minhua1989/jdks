package com.xf.jdks.dao.bean;


import com.xf.jdks.annotation.DataBeanClass;
import com.xf.jdks.commons.util.BaseChecks;
import com.xf.jdks.exceptions.ParameterNumberError;
import com.xf.jdks.exceptions.TableNameNullOrNotRightError;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Rio-Lee on 2016/6/11.
 * 继承该类并在类上添加@DataBeanClass注解方能正常使用
 * 数据实体抽象类
 */
public abstract class DataBean {

    private Map<String, Method> getters;

    private static String tableName;

    public DataBean() throws Exception {
        super();
        init();
    }

    protected void init() throws Exception {
        Class clzz = this.getClass();
        Method[] methods = clzz.getDeclaredMethods();
        for (Method method : methods) {
            String methodName = method.getName();
            if (methodName.startsWith("get") && !methodName.equals("getSerializableID") && !methodName.equals("getClass")) {
                String fieldName = methodName.substring(3,4).toLowerCase() + methodName.substring(4);
                if (this.getters == null) this.getters = new HashMap<>();
                this.getters.put(fieldName, method);
            }
        }
        DataBeanClass dbc = (DataBeanClass) clzz.getAnnotation(DataBeanClass.class);
        if (dbc == null) throw new Exception();
        if (!BaseChecks.checkTableNameIsRight(dbc.tablename())) throw new TableNameNullOrNotRightError();
        tableName = dbc.tablename();
    }

    private Parameter[] converByMap(Map<String, Method> args) throws InvocationTargetException, IllegalAccessException {
        List<Parameter> rom = new ArrayList<>();
        for (Map.Entry<String, Method> item : args.entrySet()) {
            Object val = item.getValue().invoke(this);
            Parameter param = Parameter.createParameter(item.getKey(), val);
            rom.add(param);
        }
        Parameter[] result = new Parameter[rom.size()];
        return rom.toArray(result);
    }

    /**
     * @return    创建一个用于更新的基本参数 （不能被执行、需要构建条件）
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws TableNameNullOrNotRightError
     */
    protected UpdateParams createBaseUpateParams() throws InvocationTargetException, IllegalAccessException, TableNameNullOrNotRightError {
        return UpdateParams.createUpdateParams(tableName, converByMap(this.getters));
    }

    /**
     * @return             创建一个用于新增的基本参数（可被执行）
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    protected InsertParams createBaseInsertParams() throws InvocationTargetException, IllegalAccessException {
        InsertParams ip = InsertParams.createInsertParams(tableName);
        for (Map.Entry<String, Method> item : this.getters.entrySet()) {
            Object val = item.getValue().invoke(this);
            ip.addColumn(item.getKey());
            ip.addValue(DataValue.createByObject(val));
        }
        return ip;
    }

    /**
     * @return 创建一个全字段查询参数 （未带任何查询条件）
     */
    protected QueryParams createBaseQueryParams() {
        QueryParams qp = QueryParams.createQueryParams(tableName);
        String[] cols = new String[this.getters.size()];
        cols = this.getters.keySet().toArray(cols);
        qp.addColumns(cols);
        return qp;
    }

    /**
     * @return           创建一个删除的基本参数（可执行）
     * @throws TableNameNullOrNotRightError
     * @throws ParameterNumberError
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    protected DeleteParams createBaseDeleteParams() throws TableNameNullOrNotRightError, ParameterNumberError, InvocationTargetException, IllegalAccessException {
        return DeleteParams.createDeleteParams(tableName,converByMap(this.getters));
    }
}
