package com.xf.jdks.service.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xf.jdks.commons.componet.BaseDaoComponent;
import com.xf.jdks.commons.enums.EOperators;
import com.xf.jdks.commons.global.DataMap;
import com.xf.jdks.commons.util.BaseChecks;
import com.xf.jdks.commons.util.ResponseUtils;
import com.xf.jdks.dao.bean.DeleteParams;
import com.xf.jdks.dao.bean.InsertParams;
import com.xf.jdks.dao.bean.Parameter;
import com.xf.jdks.dao.bean.QueryParams;
import com.xf.jdks.dao.bean.UpdateParams;
import com.xf.jdks.dao.entity.AdminInfo;
import com.xf.jdks.service.AdminService;
import com.xf.jdks.service.PermissionService;

@Service
public class PermissionServiceImpl  implements PermissionService{

	@Autowired
	private BaseDaoComponent baseDaoComponent;
	
	@Override
	public JSONObject pageRoleList(Map<String, Object> params)
			throws SQLException {
		List<Map<String, Object>> result = queryRole(params);
		return ResponseUtils.createSuccessResponseBodyForJiem("查询成功",result);
	}
	
	//查询角色信息
    private List<Map<String, Object>> queryRole(Map<String, Object> params) throws SQLException{
    	params.remove("admininfo");
    	params.remove("userinfo");
        Map<String, Object> map = new DataMap<>();
        for (Map.Entry<String, Object> m : params.entrySet()) {
            if (m.getValue() != null && !"".equals(m.getValue())) map.put(m.getKey(), m.getValue());
        }
        QueryParams queryParams = QueryParams.createQueryParams("t_bz_tab");
        if (map.get("name") != null && !"".equals(map.get("name"))) {//模糊查询
            String name = (String) params.get("name");
            //判断是否为中文
            if(BaseChecks.isChineseChar(name)){
                //是，空格全部替换
            	name = name.replaceAll(" ","");
            }else{
                //否，替换两端空格
            	name = BaseChecks.bothEndsStr(name);
            }
            queryParams.addQueryParams(Parameter.createParameter("name", EOperators.类似, name));
            map.remove("name");
        }
        map.put("type", "3");
        queryParams.addQueryParamsByMap(map);
        queryParams.addOrderColumns("code asc");
        queryParams.printSelf();
        List<Map<String, Object>> data = baseDaoComponent.selectDataByParams(queryParams);
        return data;
    }
    
    @Override
    public JSONObject queryAllMenus() throws SQLException {
        QueryParams queryParams = QueryParams.createQueryParams("v_queryAllMenuinfo_hm");
        List<Map<String, Object>> mapList = baseDaoComponent.selectDataByParams(queryParams);
        return ResponseUtils.createSuccessResponseBody("查询成功", mapList);
    }
    
    @Override
    public JSONObject getMenuListByRoleId(Map<String, Object> params) throws SQLException {
    	params.remove("admininfo");
    	params.remove("userinfo");
        QueryParams menuListByRoleId = QueryParams.createQueryParams("V_ROLE_MENUS");
        menuListByRoleId.addQueryParamsByMap(params);
        List<Map<String, Object>> mapList = baseDaoComponent.selectDataByParams(menuListByRoleId);
        return ResponseUtils.createSuccessResponseBody("查询成功", mapList);
    }
    
    @Override
    public JSONObject configRole(Map<String, Object> params) throws SQLException {
		AdminInfo adminInfo = (AdminInfo) params.get("adminInfo");
        String roleId = (String) params.get("roleid");
        JSONArray menus = (JSONArray)params.get("menus");
        insertMenus(menus,roleId);//保存角色菜单
        return ResponseUtils.createSuccessResponseBody();
    }
    
    //保存角色菜单
    private void insertMenus(JSONArray menus,String roleId) throws SQLException {
        DeleteParams delMenus = DeleteParams.createDeleteParams("t_rolemenus",Parameter.createParameter("roleid",roleId));
        baseDaoComponent.deleteDataByParams(delMenus);
        for(int i=0,len=menus.size();i<len;i++){
            String menuid = menus.getString(i);
            InsertParams insertParams = InsertParams.createInsertParams("t_rolemenus","id","menuid","roleid");
            insertParams.setValues(UUID.randomUUID().toString(),menuid,roleId);
            baseDaoComponent.insertDataByParams(insertParams);
        }
    }
    
}
