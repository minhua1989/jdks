package com.xf.jdks.service;

import java.sql.SQLException;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;


public interface KscjService {

    JSONObject pageList(Map<String, Object> params) throws SQLException;
    
	JSONObject cjhc(Map<String, Object> params) throws SQLException;

	JSONObject edit(Map<String, Object> params)throws SQLException;

	JSONObject searchOne(Map<String, Object> params) throws SQLException;
	
    JSONObject impKSCJ(Map<String,Object> params) throws Exception;
   
    JSONObject expKSCJ(Map<String,Object> params) throws Exception;
    
    JSONObject expKSTZD(Map<String,Object> params) throws Exception;
    
	
}