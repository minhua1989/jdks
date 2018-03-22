package com.xf.jdks.dao.entity;

import com.xf.jdks.commons.global.StaticCaches;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Rio-Lee on 2016/7/21.
 * 用户信息结构（保存于Session中）
 */
public class LoginInfo {

    private String id;

    private String roleid;

    public String getRoleid() {
        return roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid;
    }

    private String username;

    private String realname;

    private String opername;

    private String districtid;

    private String schoolid;

    private String areaid;

    private String operid;

    private String roletype;

    private String schoolcode;

    private String districtcode;

    private String areacode;

    private String deleted;

    private String areaname;

    private String currentURL;

    private String currentURLName;

    private String clientHost;

    private String uias;//统一身份认证()

    private String modifyPassWordTime;//记录修改密码时间

    private String bxdj;  //办学等级

    private String stuinfotablename;  //基础学生信息表

    private String xxbb;  //学校办别 公办民办

    private String xq;  //学期

    private String schoolname;//学校名称

    private String ssid;    //session id

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public String getXq() {
        return xq;
    }

    public void setXq(String xq) {
        this.xq = xq;
    }

    public String getSchoolname() {
        return schoolname;
    }

    public void setSchoolname(String schoolname) {
        this.schoolname = schoolname;
    }

    public String getModifyPassWordTime(){  return modifyPassWordTime;    }

    public void  setModifyPassWordTime(String modifyPassWordTime){this.modifyPassWordTime = modifyPassWordTime;}

    public String getUias() {
        return uias;
    }

    public void setUias(String uias) {
        this.uias = uias;
    }

    public String getClientHost() {
        return clientHost;
    }

    public void setClientHost(String clientHost) {
        this.clientHost = clientHost;
    }

    public String getCurrentURL() {
        return currentURL;
    }

    public void setCurrentURL(String currentURL) {
        this.currentURL = currentURL;
        this.currentURLName = StaticCaches.getUrlChName(currentURL);
    }

    public String getCurrentURLName(){
        return this.currentURLName;
    }

    public String getAreaname() {
        return areaname;
    }

    public void setAreaname(String areaname) {
        this.areaname = areaname;
    }

    public String getDeleted() {
        return deleted;
    }

    private  String  pwdstatus ;//验证密码是否弱密码，0不需要修改密码；1修改密码

    public String getPwdstatus() {
        return pwdstatus;
    }

    public void setPwdstatus(String pwdstatus) {
        this.pwdstatus = pwdstatus;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    public String getBxdj(){return bxdj;}

    public void setBxdj(String bxdj){this.bxdj = bxdj;}

    public String getXxbb(){return xxbb;}

    public void setXxbb(String xxbb){this.xxbb = xxbb;}

    public String getStuinfotablename(){return stuinfotablename;}

    public void setStuinfotablename(String stuinfotablename){this.stuinfotablename = stuinfotablename;}

    public static LoginInfo createLoginInfoByMap(Map<String, Object> map) throws InvocationTargetException, IllegalAccessException {
        LoginInfo rs = new LoginInfo();
        Field[] fields = LoginInfo.class.getDeclaredFields();
        List<Method> setMethods = getAllSetMethods(LoginInfo.class);
        for (Field field : fields) {
            Object value = map.get(field.getName());
            if (value != null) {
                Method method = searchSetMethodNameByFieldName(field.getName(), setMethods);
                if (method != null) method.invoke(rs, value);
            }
        }
        return rs;
    }

    private static Method searchSetMethodNameByFieldName(String fieldName, List<Method> methods) {
        for (Method method : methods) {
            if (method.getName().equalsIgnoreCase("set" + fieldName)) return method;
        }
        return null;
    }

    private static List<Method> getAllSetMethods(Class clz) {
        Method[] methods = clz.getDeclaredMethods();
        List<Method> setMethods = new ArrayList<>();
        for (Method method : methods) {
            if (method.getName().startsWith("set")) setMethods.add(method);
        }
        return setMethods;
    }


    public String getAreacode() {
        return areacode;
    }

    public void setAreacode(String areacode) {
        this.areacode = areacode;
    }

    public String getRoletype() {
        return roletype;
    }

    public void setRoletype(String roletype) {
        this.roletype = roletype;
    }

    public String getSchoolcode() {
        return schoolcode;
    }

    public void setSchoolcode(String schoolcode) {
        this.schoolcode = schoolcode;
    }

    public String getDistrictcode() {
        return districtcode;
    }

    public void setDistrictcode(String districtcode) {
        this.districtcode = districtcode;
    }

    private List<Map<String, Object>> rolelist;

    public String getDistrictid() {
        return districtid;
    }

    public void setDistrictid(String districtid) {
        this.districtid = districtid;
    }

    public String getSchoolid() {
        return schoolid;
    }

    public void setSchoolid(String schoolid) {
        this.schoolid = schoolid;
    }

    public String getAreaid() {
        return areaid;
    }

    public void setAreaid(String areaid) {
        this.areaid = areaid;
    }

    public String getOpername() {
        return opername;
    }

    public void setOpername(String opername) {
        this.opername = opername;
    }

    public String getOperid() {
        return operid;
    }

    public void setOperid(String operid) {
        this.operid = operid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public List<Map<String, Object>> getRolelist() {
        return rolelist;
    }

    public void setRolelist(List<Map<String, Object>> rolelist) {
        this.rolelist = rolelist;
    }
}
