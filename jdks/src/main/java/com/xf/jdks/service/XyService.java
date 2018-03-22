package com.xf.jdks.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.xf.jdks.dao.entity.UserInfo;
import com.xf.jdks.exceptions.FileTypeNotFoundException;

public interface XyService {
	    JSONObject pageList(Map<String, Object> params) throws SQLException;
		
		JSONObject add(Map map) throws SQLException;

		JSONObject searchOne(Map map) throws SQLException;
		
		JSONObject edit( Map map) throws SQLException;
		
		JSONObject deleted(Map map) throws SQLException;
		

		JSONObject wulideleted(Map map) throws SQLException;
		
	    JSONObject importNewXY(Map<String,Object> params) throws Exception;
	    

}
