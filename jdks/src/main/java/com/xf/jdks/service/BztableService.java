package com.xf.jdks.service;

import java.sql.SQLException;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.xf.jdks.dao.entity.BztableInfo;

public interface BztableService {


	JSONObject list(Map<String, Object> map) throws SQLException;

	JSONObject sttilelist() throws SQLException;

	JSONObject stdalist() throws SQLException;

	JSONObject sydjlist() throws SQLException;
	
JSONObject pageList(Map<String, Object> params) throws SQLException;
	
	JSONObject add(Map map) throws SQLException;

	JSONObject searchOne(Map map) throws SQLException;
	
	JSONObject edit( Map map) throws SQLException;
	
	JSONObject deleted(Map map) throws SQLException;

}
