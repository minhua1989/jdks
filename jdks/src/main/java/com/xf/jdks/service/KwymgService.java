package com.xf.jdks.service;

import java.sql.SQLException;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.xf.jdks.dao.entity.KsccmgInfo;
import com.xf.jdks.dao.entity.KwymgInfo;

public interface KwymgService {
	
    JSONObject pageList(Map<String, Object> params) throws SQLException;
	
	JSONObject add(Map map) throws SQLException;

	JSONObject searchOne(Map map) throws SQLException;

	JSONObject edit( Map map) throws SQLException;
	
	JSONObject deleted(Map map) throws SQLException;

	JSONObject wulideleted(Map map) throws SQLException;


}