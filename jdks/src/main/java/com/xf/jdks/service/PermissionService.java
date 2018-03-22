package com.xf.jdks.service;

import java.sql.SQLException;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

public interface PermissionService {
	
    JSONObject pageRoleList(Map<String, Object> params) throws SQLException;
    
    JSONObject queryAllMenus() throws SQLException;
    
    JSONObject getMenuListByRoleId(Map<String, Object> params) throws SQLException;
 
    JSONObject configRole(Map<String, Object> params) throws SQLException;
    
}
