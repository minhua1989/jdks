package com.xf.jdks.dao;


import com.xf.jdks.dao.bean.IParams;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by Rio-Lee on 2016/6/10.
 * 标准DAO（通用）
 */
public interface BaseDao {

    /**
     * @param params 根据条件查询记录
     * @return 查询到的结果集
     */
    List<Map<String,Object>> selectDataByParams(IParams params)throws SQLException;

    /**
     * @param params 更新条件参数
     * @return 受影响的记录数
     */
    int updateDataByParams(IParams params)throws SQLException;

    /**
     * @param params 新增条件参数
     */
    void insertDataByParams(IParams params)throws SQLException;

    /**
     * @param params 删除条件参数
     */
    void deleteDataByParams(IParams params)throws SQLException;
}
