package com.xf.jdks.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.xf.jdks.dao.entity.UserInfo;
import com.xf.jdks.exceptions.FileTypeNotFoundException;
public interface UserService {
	
    JSONObject pageList(Map<String, Object> params) throws SQLException;
	
	JSONObject add(Map map) throws SQLException;

	JSONObject searchOne(Map map) throws SQLException;
	
	JSONObject edit( Map map) throws SQLException;
	
	JSONObject deleted(Map map) throws SQLException;
	
	JSONObject userLogin(Map map,HttpServletRequest request);
	
	UserInfo userLogin(String ksxtxm, String ksxtsfzjh,String ksxtzkzh);

	JSONObject wulideleted(Map map) throws SQLException;
	
    JSONObject importUsers(Map<String,Object> params) throws IOException, SQLException;
    
    JSONObject expUsers(Map<String, Object> params) throws SQLException, FileTypeNotFoundException, IOException;

	JSONObject importxy(Map<String, Object> params, HttpServletRequest request) throws SQLException;

	JSONObject ksmdList(Map<String, Object> params) throws SQLException;

	JSONObject reset(Map map) throws Exception;

    
}
