package com.xf.jdks.dao.bean;

/**
 * Created by Rio-Lee on 2016/6/10.
 *  Sql参数接口
 */
public interface IParams {

    /**
     * @return      需要执行的SQL语句
     */
    String getExecuteSql()throws Exception;

    /**
     * @return         可以被执行返回True不可执行返回Fasle
     */
    boolean isCanBeExecute();

    /**
     * 在控制台打印信息
     */
    void printSelf();

    DBLog getCurrentLog();
}
