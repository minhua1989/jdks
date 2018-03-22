package com.xf.jdks.service;
import java.sql.SQLException;
import java.util.Map;
import com.alibaba.fastjson.JSONObject;

public interface PxpcService {
	    JSONObject pageList(Map<String, Object> params) throws SQLException;
		
		JSONObject add(Map map) throws SQLException;

		JSONObject searchOne(Map map) throws SQLException;
		
		JSONObject edit( Map map) throws SQLException;
		
		JSONObject deleted(Map map) throws SQLException;
		

		JSONObject wulideleted(Map map) throws SQLException;
		
		JSONObject list(Map<String, Object> map) throws SQLException;
		
}
