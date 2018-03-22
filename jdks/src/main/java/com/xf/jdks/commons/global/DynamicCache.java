package com.xf.jdks.commons.global;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Rio-Lee on 2016/7/27.
 * 动态缓存
 */
public class DynamicCache {

    private static List<Map<String,Object>> districtSchools;

    private static List<Map<String,Object>> schoolAreas;

    private static List<Map<String,Object>> schoolClasses;

    private static List<Map<String,Object>> districtCache;

    public static void addSchoolClasses(List<Map<String,Object>> data) {
        for(Map<String,Object> entiy : data) {
            schoolClasses.add(entiy);
        }
    }
    public static void loadDistrictCache(List<Map<String,Object>> data){
        districtCache = data;
    }

    /**
     * @param code 区县CODE
     * @return 区县ID
     */
    public static String getDistrictIdByDistrictCode(String code){
        for(Map<String,Object> map:districtCache){
            if(code.equals(map.get("districtcode")))return (String)map.get("id");
        }
        return code;
    }

    /**
     * @param id 区县ID
     * @return 区县CODE
     */
    public static String getDistrictCodeByDistrictId(String id){
        for(Map<String,Object> map:districtCache){
            if(id.equals(map.get("id")))return (String)map.get("districtcode");
        }
        return id;
    }

    public static void main(String[] args){
        System.out.println( getDistrictCodeByDistrictId("1"));
    }



    /**
     * @param list 查询vd_getSchoolByDistrict视图
     */
    public static void loadDistrictSchools(List<Map<String,Object>> list){
        districtSchools = list;
    }

    /**
     * @param list 查询vd_getAreaBySchool视图
     */
    public static void loadSchoolAreas(List<Map<String,Object>> list){
        schoolAreas = list;
    }

    /**
     * @param list 查询vd_getNjBySchool视图
     */
    public static void loadSchoolClasses(List<Map<String,Object>> list){
        schoolClasses = list;
    }

    /**
     * @return 返回所有学校
     */
    public static List<Map<String,Object>> getAllSchool(){
        return districtSchools;
    }

    /**
     * @param schoolId 学校ID
     * @return [{areaid,schoolid,areaname}]
     */
    public static List<Map<String,Object>> getSchoolAreasBySchool(String schoolId){
        List<Map<String,Object>> rs = new ArrayList<>();
        for(Map<String,Object> map:schoolAreas){
            if(schoolId.equals(map.get("schoolid"))){
               rs.add(map);
            }
        }
        return rs;
    }


    public static boolean hasKeyMap(List<Map<String,Object>> list,String key,Object val){
        for(Map<String,Object> map:list){
            if(val==null)continue;
            if(map.get(key)!=null&&val.equals(map.get(key)))return true;
        }
        return false;
    }

    /**
     * @param schoolId 学校ID
     * @return [{njid,njname,schoolid}]
     */
    public static List<Map<String,Object>> getNjBySchool(String schoolId){
        List<Map<String,Object>> rs = new ArrayList<>();
        for(Map<String,Object> map:schoolClasses){
            if(schoolId.equals(map.get("schoolid"))){
                String njid = (String) map.get("njid");
                if(hasKeyMap(rs,"njid",njid))continue;
                rs.add(map);
            }
        }
        return rs;
    }

    /**
     * @param areaId 校区ID
     * @return [{njid,njname,areaid}]
     */
    public static List<Map<String,Object>> getNjBySchoolArea(String areaId){
        List<Map<String,Object>> rs = new ArrayList<>();
        for(Map<String,Object> map:schoolClasses){
            if(areaId.equals(map.get("areaid"))){
                String njid = (String) map.get("njid");
                if(hasKeyMap(rs,"njid",njid))continue;
                rs.add(map);
            }
        }
        return rs;
    }

    /**
     * @param njId 年级ID
     * @return [{njid,classid,classname}]
     */
    public static List<Map<String,Object>> getClassesByNj(String njId,String schoolId){
        List<Map<String,Object>> rs = new ArrayList<>();
        for(Map<String,Object> map:schoolClasses){
            if(njId.equals(map.get("njid"))&&schoolId.equals(map.get("schoolid"))){
                rs.add(map);
            }
        }
        return rs;
    }
}
