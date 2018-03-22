package com.xf.jdks.service;

import java.sql.SQLException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.xf.jdks.dao.entity.AdminInfo;
import com.xf.jdks.dao.pojo.LoginInfo;

public interface AdminService {

    JSONObject pageList(Map<String, Object> params) throws SQLException;
	
	JSONObject add(Map map) throws SQLException;

	JSONObject searchOne(Map map) throws SQLException;
	
	JSONObject edit( Map map) throws SQLException;
	
	JSONObject deleted(Map map) throws SQLException;
	
	JSONObject adminLogin(Map map,HttpServletRequest request);

	AdminInfo adminLogin(String ename, String pwd);

	JSONObject wulideleted(Map map) throws SQLException;

	AdminInfo adminLoginbyename(String ename);
	
	JSONObject reset(Map map) throws Exception;
	
	
}
