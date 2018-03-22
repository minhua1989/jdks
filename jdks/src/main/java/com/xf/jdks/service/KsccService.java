package com.xf.jdks.service;

import java.sql.SQLException;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.xf.jdks.dao.entity.Kscc;
import com.xf.jdks.dao.entity.Sttitle;
import com.xf.jdks.dao.entity.VwUserKscc;

public interface KsccService {

    JSONObject pageList(Map<String, Object> params) throws SQLException;
	
	JSONObject checkKsTime(Map map) throws SQLException;

	JSONObject createKssj(Map map) throws SQLException, Exception;

	JSONObject loadKssjxx(Map map) throws SQLException, Exception;
	
	JSONObject saveKssjda(Map map) throws SQLException;

	JSONObject loadKssjxxAdmin(Map map) throws Exception;
	
	JSONObject saveKssjdaEach(Map map) throws SQLException;
	
	JSONObject queryKssjxxLog(Map<String, Object> params) throws SQLException;
	
	JSONObject updatesfdl(Map<String, Object> params) throws SQLException;
	
	
}
