package com.xf.jdks.commons.componet;

import com.xf.jdks.commons.ext.DBList;
import com.xf.jdks.commons.ext.DBMap;
import com.xf.jdks.commons.util.CollectionUtils;
import com.xf.jdks.dao.BaseDao;
import com.xf.jdks.dao.bean.DBLog;
import com.xf.jdks.dao.bean.IParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Rio-Lee on 2016/7/22.
 */
@Component
public class BaseDaoComponent implements BaseDao {
    @Autowired
    private BaseDao baseDao;

    private static String fileName = null;
    private static long num = 0;

    private static synchronized void printSqlString(String msg) throws IOException {
        if (fileName == null) {
            fileName = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date()) + ".log";
        }
        File log = new File("/storage", fileName);
        FileWriter fw = new FileWriter(log, true);
        try {
            fw.write(num + ".SQL is: \tselet " + msg + "\n");
            fw.flush();
            num++;
        } finally {
            fw.close();
        }
    }

    public Map<String, Object> selectOneDataByParamsForIndex(IParams params, int index) throws SQLException {
        Map<String, Object> rs = null;
        List<Map<String, Object>> rsList = selectDataByParams(params);
        if (rsList != null && rsList.size() >= index + 1) {
            rs = rsList.get(index);
        }
        return rs;
    }

    public Map<String, Object> selectOneDataByParams(IParams params) throws SQLException {
        return selectOneDataByParamsForIndex(params, 0);
    }

    @Override
    public List<Map<String, Object>> selectDataByParams(IParams params) throws SQLException {
        List<Map<String, Object>> data = baseDao.selectDataByParams(params);
        DBLog log = params.getCurrentLog();
        if (log != null) {
            baseDao.insertDataByParams(log.getInsertParams());
        }
        return CollectionUtils.converMapKeyToLowerCase(data);
    }

    @Override
    public int updateDataByParams(IParams params) throws SQLException {
        DBLog log = params.getCurrentLog();
        if (log != null) {
            baseDao.insertDataByParams(log.getInsertParams());
        }
        return baseDao.updateDataByParams(params);
    }

    public void batchUpateDataByParams(IParams... paramses) throws SQLException {
        for (IParams ip : paramses) {
            updateDataByParams(ip);
        }
    }

    @Override
    public void insertDataByParams(IParams params) throws SQLException {
        DBLog log = params.getCurrentLog();
        if (log != null) {
            baseDao.insertDataByParams(log.getInsertParams());
        }
        baseDao.insertDataByParams(params);
    }

    @Override
    public void deleteDataByParams(IParams params) throws SQLException {
        DBLog log = params.getCurrentLog();
        if (log != null) {
            baseDao.insertDataByParams(log.getInsertParams());
        }
        baseDao.deleteDataByParams(params);
    }

    public DBList selectDataByParamsForDBList(IParams params) throws SQLException {
        DBList rs = new DBList();
        List<Map<String, Object>> dataList = selectDataByParams(params);
        rs.reloadForBaseList(dataList);
        return rs;
    }

    public DBMap selectOneDataByParamsForDBMap(IParams params) throws SQLException {
        Map<String, Object> data = selectOneDataByParams(params);
        return data == null ? null : DBMap.createByMap(data);
    }
}
