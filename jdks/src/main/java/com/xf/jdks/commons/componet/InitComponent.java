package com.xf.jdks.commons.componet;


import com.alibaba.fastjson.JSONArray;
import com.xf.jdks.annotation.AuthorityDeclaration;
import com.xf.jdks.commons.global.*;
import com.xf.jdks.commons.util.*;
import com.xf.jdks.dao.bean.*;
import com.xf.jdks.dao.pojo.CurrentTermInfo;


import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by Rio-Lee on 2016/6/16.
 * 初始化数据组件
 */
@Component
public class InitComponent implements InitializingBean {

    private static boolean isInitializing = false;

    @Resource
    private BaseDaoComponent baseDaoComponent;
//    @Resource
//    private RoleService roleService;
    @Resource
    private FileIOComponent fileIOComponent;
//
//
////    private void loadCurrentTerm() throws SQLException {
////        QueryParams queryCurrentTerm = QueryParams.createQueryParams("t_terminfo");
////        queryCurrentTerm.addQueryParams(Parameter.createParameter("currentterm", "1"));
////        List<Map<String, Object>> currTerms = baseDaoComponent.selectDataByParams(queryCurrentTerm);
////        CurrentTermInfo cti = new CurrentTermInfo(currTerms.get(0));
////        StaticCaches.setCurrentTerm(cti);
////    }
//
//    private void reloadExceptionDictCache() throws IOException {
//        ExceptionDict.loadProperties(this.getClass().getResourceAsStream("/exceptions.properties"));
//    }
//
////    private void loadListCaches() throws SQLException {
////        QueryParams allSchools = QueryParams.createQueryParams("vd_getSchoolByDistrict");
////        QueryParams allArea = QueryParams.createQueryParams("vd_getAreaBySchool");
////        QueryParams allClass = QueryParams.createQueryParams("vd_getNjBySchool");
////        DynamicCache.loadDistrictSchools(baseDaoComponent.selectDataByParams(allSchools));
////        DynamicCache.loadSchoolAreas(baseDaoComponent.selectDataByParams(allArea));
////        DynamicCache.loadSchoolClasses(baseDaoComponent.selectDataByParams(allClass));
////    }
//
    private void reloadFileTypes() throws SQLException {
        fileIOComponent.reloadFileTypes();
    }
//
////    /**
////     * 加载学校信息缓存
////     *
////     * @throws SQLException
////     */
////    public void reloadSchoolCache() throws SQLException {
////        QueryParams schoolQuery = QueryParams.createQueryParams("t_schoolinfo");
////        schoolQuery.addColumns("ID", "SCHOOLNAME", "SCHOOLCODE");
////        StaticCaches.loadAllSchool(CollectionUtils.converMapKeyToLowerCase(baseDaoComponent.selectDataByParams(schoolQuery)));
////    }
////
////    /**
////     * 加载校区信息缓存
////     *
////     * @throws SQLException
////     */
////    public void reloadSchoolAreaCache() throws SQLException {
////        QueryParams schoolAreaQuery = QueryParams.createQueryParams("t_schoolareainfo");
////        schoolAreaQuery.addColumns("ID", "AREANAME", "AREACODE", "SCHOOLID");
////        StaticCaches.loadAllSchoolArea(CollectionUtils.converMapKeyToLowerCase(baseDaoComponent.selectDataByParams(schoolAreaQuery)));
////    }
//
//    /**
//     * 加载校验规则缓存
//     */
//    public void reloadDictDriverList() throws SQLException {
//        QueryParams queryParams = QueryParams.createQueryParams("T_DICT_DRIVER");
//        DataDict.loadDictDriver(baseDaoComponent.selectDataByParams(queryParams));
//    }
//
    /**
     * 加载数据字典缓存
     *
     * @throws SQLException
     */
    public void reloadDataDictCache() throws SQLException {
        List<Map<String, Object>> dictList = baseDaoComponent.selectDataByParams(QueryParams.createQueryParams("vf_all_bz_map"));
        DataDict.initLoadDataDicts(CollectionUtils.converMapKeyToLowerCase(dictList));
    }
//
//    /**
//     * 加载菜单列表缓存
//     *
//     * @throws SQLException
//     */
//    public void reloadMenuListCache() throws SQLException {
//        List<Map<String, Object>> menuList = baseDaoComponent.selectDataByParams(QueryParams.createQueryParams("v_role_menus"));
//        StaticCaches.loadByMenuList(CollectionUtils.converMapKeyToLowerCase(menuList));
//    }
//
//
//    private static void addLists(List dest, List... args) {
//        for (List list : args) {
//            dest.addAll(list);
//        }
//    }
////
////    private void loadFunctionUrlDict() throws SQLException {
////        QueryParams queryUrlDict = QueryParams.createQueryParams("T_FUNCURLINFO");
////        List<Map<String, Object>> urlDictList = baseDaoComponent.selectDataByParams(queryUrlDict);
////        for (Map<String, Object> urlDict : urlDictList) {
////            String url = (String) urlDict.get("url");
////            String name = (String) urlDict.get("name");
////            StaticCaches.putUrlDict(url, name);
////        }
////    }
////
////    private void loadDistrictCache() throws SQLException {
////        QueryParams queryDistrictCache = QueryParams.createQueryParams("T_DISTRICTINFO");
////        List<Map<String, Object>> data = baseDaoComponent.selectDataByParams(queryDistrictCache);
////        DynamicCache.loadDistrictCache(data);
////    }
////
////
////    private void loadDictVersion() throws SQLException {
////        QueryParams queryDictVersion = QueryParams.createQueryParams("T_VERSIONS");
////        List<Map<String, Object>> result = baseDaoComponent.selectDataByParams(queryDictVersion);
////        DataDict.loadDictVersionByDbs(result);
////    }
//
//    private void doTestRedis() {
//        for (int i = 0; i < 50000; i++) {
////            UserPo up
//        }
//    }
//
////    private void loadProvCity() throws SQLException {
////        QueryParams provCity = QueryParams.createQueryParams("T_BZ_CITY");
////        DataDict.loadProvCitys(baseDaoComponent.selectDataByParams(provCity));
////    }
//
    
    /**
     * 加载初始化数据
     */
    public void loadInitData() throws Exception {
 //       loadJdbcSource();
//        loadProvCity();
//
//        loadCurrentTerm();
        // 加载文件类型
    	reloadFileTypes();
//
//        // 加载区县字典
//        loadDistrictCache();
//        // 加载菜单缓存
//        reloadMenuListCache();
//        // 加载数据字典版本
//        loadDictVersion();
        // 加载数据字典
        reloadDataDictCache();
////        加载校验规则
//        reloadSchoolCache();
////        加载异常信息字典缓存
//        reloadExceptionDictCache();
//        reloadSchoolAreaCache();
//        loadListCaches();
//        List<Map<String, Object>> roleMenus = roleService.queryAllRoleMenus();
//        List<Map<String, Object>> roleFunctions = roleService.queryAllRoleFuncs();
//        List<Map<String, Object>> roleDataAreas = roleService.queryAllRoleDataAreas();
//        StaticCaches.loadAllDataAreas(CollectionUtils.converMapKeyToLowerCase(roleDataAreas));
//        StaticCaches.loadAllFuncs(CollectionUtils.converMapKeyToLowerCase(roleFunctions));
//        StaticCaches.loadByMenuList(CollectionUtils.converMapKeyToLowerCase(roleMenus));
//        //加载URL字典
//        loadFunctionUrlDict();


        isInitializing = true;
        System.out.println("初始化数据完成");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (isInitializing) return;
        loadInitData();
    }

//    //加载数据库
//    private void loadJdbcSource() {//TODO 记得配置jdbc.properties
//        //test93--93数据库
//        JdbcProperties.jdbcSource = "test93";
//        //服务器操作平台--windows/linux
//        JdbcProperties.serverType = "windows";
////        JdbcProperties.serverType = "linux";
//
//        loadDBCPconnectionPool();//加载DBCPConnectionPool数据库配置文件
//        loadDbutilsJdbcSource();//加载Dbutils数据库配置文件
//        loadDBPoolJdbcSource();//加载DBPool数据库配置文件
//    }
//
//    //加载DBCPconnectionPool数据库
//    private void loadDBCPconnectionPool() {
//        if ("test93".equals(JdbcProperties.jdbcSource)) {
//            JdbcProperties.DBCPCONPOOL_MASTERCONFIG = "test93_master.properties";
//            JdbcProperties.DBCPCONPOOL_SBCONFIG = "test93_sb.properties";
//            JdbcProperties.DBCPCONPOOL_JSSBCONFIG = "test93_jssb.properties";
//            JdbcProperties.DBCPCONPOOL_XFCONFIG = "test93_xf.properties";
//        }
//    }
//
//    //加载Dbutils数据库
//    private void loadDbutilsJdbcSource() {
//        if ("test93".equals(JdbcProperties.jdbcSource)) {
//            JdbcProperties.DBUTILS_MASTER_URL = "jdbc:oracle:thin:@61.151.214.93:1521:orcl";
//            JdbcProperties.DBUTILS_MASTER_USR = "xjxtuser";
//            JdbcProperties.DBUTILS_MASTER_PWD = "xjxtuser";
//            JdbcProperties.DBUTILS_SB_URL = "jdbc:oracle:thin:@61.151.214.93:1521:orcl";
//            JdbcProperties.DBUTILS_SB_USR = "xjxtuser";
//            JdbcProperties.DBUTILS_SB_PWD = "xjxtuser";
//            JdbcProperties.DBUTILS_XF_URL = "jdbc:oracle:thin:@61.151.214.93:1521:orcl";
//            JdbcProperties.DBUTILS_XF_USR = "xjxtuserxf";
//            JdbcProperties.DBUTILS_XF_PWD = "xjxtuserxf";
//        }
//    }
//
//    //加载DBPool数据库
//    private static void loadDBPoolJdbcSource() {
//        if ("test93".equals(JdbcProperties.jdbcSource)) {
//            JdbcProperties.DBPool_msurl = "test93ms.url";
//            JdbcProperties.DBPool_msusr = "test93ms.usr";
//            JdbcProperties.DBPool_mspwd = "test93ms.pwd";
//            JdbcProperties.DBPool_msmin = "test93ms.min";
//            JdbcProperties.DBPool_msmax = "test93ms.max";
//            JdbcProperties.DBPool_mstimeout = "test93ms.timeout";
//            JdbcProperties.DBPool_sburl = "test93sb.url";
//            JdbcProperties.DBPool_sbusr = "test93sb.usr";
//            JdbcProperties.DBPool_sbpwd = "test93sb.pwd";
//            JdbcProperties.DBPool_sbmin = "test93sb.min";
//            JdbcProperties.DBPool_sbmax = "test93sb.max";
//            JdbcProperties.DBPool_sbtimeout = "test93sb.timeout";
//            JdbcProperties.DBPool_xfurl = "test93xf.url";
//            JdbcProperties.DBPool_xfusr = "test93xf.usr";
//            JdbcProperties.DBPool_xfpwd = "test93xf.pwd";
//            JdbcProperties.DBPool_xfmin = "test93xf.min";
//            JdbcProperties.DBPool_xfmax = "test93xf.max";
//            JdbcProperties.DBPool_xftimeout = "test93xf.timeout";
//        }
//    }
}
