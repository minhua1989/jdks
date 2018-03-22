package com.xf.jdks.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.xf.jdks.dao.entity.KcInfo;
import com.xf.jdks.dao.entity.KcKsccInfo;
import com.xf.jdks.dao.entity.KcKwyInfo;
import com.xf.jdks.dao.entity.VwUserKscc;

public interface KcmgService {

    JSONObject pageList(Map<String, Object> params) throws SQLException;
	
	JSONObject add(Map map) throws SQLException;

	JSONObject deleted(Map map) throws SQLException;
	
	JSONObject wulideleted(Map map) throws SQLException;
	
	JSONObject searchOne(Map map) throws SQLException;

	JSONObject edit( Map map) throws SQLException;
	
	JSONObject addKwy(Map map) throws SQLException;

    JSONObject listKwy(Map<String, Object> params) throws SQLException;
	
    JSONObject pageListKcKwy(Map<String, Object> params) throws SQLException;
	
	JSONObject deletedKwy(Map map) throws SQLException;

	JSONObject kskcfp(Map map) throws SQLException;
	
	JSONObject createKSTZD(Map map) throws Exception;

	JSONObject yxpageList(Map<String, Object> params) throws SQLException;
	
}
