package com.xf.jdks.commons.global;

import com.xf.jdks.dao.pojo.CurrentTermInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Rio-Lee on 2016/6/16.
 * 静态缓存数据池
 */
public class StaticCaches {

    public static final boolean POSTMAN_MODE =false;

    private static Map<String,String> urlDict = new HashMap<>();
    private static CurrentTermInfo currentTerm;
    private static String realRootPath;
    private static Map<String,List<Map<String,Object>>> allDataAreas;
    private static Map<String,List<Map<String,Object>>> allFunctions;
    private static Map<String,List<Map<String,Object>>> allMenus;
    private static List<Map<String,Object>> allSchool;
    private static List<Map<String,Object>> allSchoolArea;

    public static void putUrlDict(String url,String chName){
        urlDict.put(url,chName);
    }

    public static String getUrlChName(String url){
        return urlDict.get(url);
    }

    public static CurrentTermInfo getCurrentTerm() {
        return currentTerm;
    }

    public static void setCurrentTerm(CurrentTermInfo currentTerm) {
        StaticCaches.currentTerm = currentTerm;
    }

    public static String getRealRootPath() {
        return realRootPath;
    }

    public static void setRealRootPath(String realRootPath) {
        StaticCaches.realRootPath = realRootPath;
    }

    public static String getSchoolIdBySchoolName(String schoolName){
        for(Map<String,Object> school:allSchool){
            if(schoolName!=null&&schoolName.equals(school.get("SCHOOLNAME"))){
                return (String)school.get("ID");
            }
        }
        return null;
    }

    public static String getSchoolNameBySchoolId(String schoolId){
        for(Map<String,Object> school:allSchool){
            if(schoolId!=null&&schoolId.equals(school.get("ID"))){
                return (String)school.get("SCHOOLNAME");
            }
        }
        return null;
    }

    public static String getSchoolCodeBySchoolId(String schoolId){
        for(Map<String,Object> school:allSchool){
            if(schoolId!=null&&schoolId.equals(school.get("ID"))){
                return (String)school.get("SCHOOLCODE");
            }
        }
        return null;
    }

    public static String getSchoolAreaIdBySchoolAreaName(String schoolAreaName){
        for(Map<String,Object> school:allSchoolArea){
            if(schoolAreaName!=null&&schoolAreaName.equals(school.get("AREANAME"))){
                return (String)school.get("ID");
            }
        }
        return null;
    }

    public static String getSchoolAreaNameBySchoolAreaId(String schoolAreaId){
        for(Map<String,Object> school:allSchoolArea){
            if(schoolAreaId!=null&&schoolAreaId.equals(school.get("ID"))){
                return (String)school.get("AREANAME");
            }
        }
        return null;
    }


    public static void loadAllDataAreas(List<Map<String,Object>> list){
        if(list==null)return;
        allDataAreas = new HashMap<>();
        for(Map<String,Object> item:list){
            Object roleId = item.get("ROLEID");
            if(roleId!=null){
                List<Map<String,Object>> roleDataArea = allDataAreas.get(roleId);
                if(roleDataArea==null){
                    roleDataArea = new ArrayList<>();
                    allDataAreas.put(roleId.toString(),roleDataArea);
                }
                roleDataArea.add(item);
            }
        }
    }

    public static void loadAllFuncs(List<Map<String,Object>> list){
        if(list==null)return;
        allFunctions = new HashMap<>();
        for(Map<String,Object> item:list){
            Object roleId = item.get("ROLEID");
            if(roleId!=null){
                List<Map<String,Object>> roleFunc = allFunctions.get(roleId);
                if(roleFunc==null){
                    roleFunc = new ArrayList<>();
                    allFunctions.put(roleId.toString(),roleFunc);
                }
                roleFunc.add(item);
            }
        }

    }


    public static List<Map<String,Object>> getDataAreasByRoleId(String roleId){
        return allDataAreas.get(roleId);
    }

    public static List<Map<String,Object>> getFunctionsByRoleId(String roleId){
        return allFunctions.get(roleId);
    }


    /**
     * @param areaId 校区ID
     * @return 对应的学校ID如果没有找到则返回null
     */
    public static String getSchoolIdBySchoolAreaId(String areaId){
        for(Map<String,Object> item:allSchoolArea){
            Object area = item.get("ID");
            Object schoolid = item.get("SCHOOLID");
            if(area!=null&&area.toString().equalsIgnoreCase(areaId))return schoolid==null?null:schoolid.toString();
        }
        return null;
    }

    public static void loadAllSchoolArea(List<Map<String,Object>> data){
        if(allSchoolArea==null)allSchoolArea=new ArrayList<>();
        allSchoolArea = data;
    }

    public static void loadAllSchool(List<Map<String,Object>> data){
        if(allSchool==null)allSchool = new ArrayList<>();
        allSchool = data;
    }

    /**
     * 从数据库查询出菜单列表并加载到缓存（覆盖加载）
     * @param list 通过查询v_menu_list_map查询到的结果
     */
    public static void loadByMenuList(List<Map<String,Object>> list){
        if(list==null)return;
        allMenus = new HashMap<>();
        for(Map<String,Object> item:list){
            Object roleId = item.get("ROLEID");
            if(roleId!=null){
                List<Map<String,Object>> roleMenus = allMenus.get(roleId);
                if(roleMenus==null){
                    roleMenus = new ArrayList<>();
                    allMenus.put(roleId.toString(),roleMenus);
                }
                roleMenus.add(item);
            }
        }
    }

    /**
     * 根据角色组查询对应菜单
     * @param roleId 角色权限组
     * @return 角色对应的菜单列表
     */
    public static List<Map<String,Object>> getMenuListByRoleId(String roleId){
        return allMenus.get(roleId);
    }
}
