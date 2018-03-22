package com.xf.jdks.service;

import java.sql.SQLException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.xf.jdks.dao.entity.KcKsccInfo;
import com.xf.jdks.dao.entity.KsccmgInfo;
import com.xf.jdks.dao.entity.T_kckscc_rel;
import com.xf.jdks.dao.entity.UserInfo;



public interface KsccmgService {

	
    JSONObject pageList(Map<String, Object> params) throws SQLException;

    JSONObject pageListKcKscc(Map<String, Object> params) throws SQLException;
 
    JSONObject pageListKcKs(Map<String, Object> params) throws SQLException;
    
    JSONObject list() throws SQLException;
	
	JSONObject add(Map map) throws SQLException;

	JSONObject addKcinfo(Map map) throws SQLException;
	
	JSONObject searchOne(Map map) throws SQLException;

	JSONObject edit( Map map) throws SQLException;
	
	JSONObject deleted(Map map) throws SQLException;
	
	JSONObject deletedKcinfo(Map map) throws SQLException;
	
	JSONObject wulideleted(Map map) throws SQLException;
	
	JSONObject listKcinfo(Map map) throws SQLException;

	JSONObject kskcpageList(Map<String, Object> params) throws SQLException;
	
}