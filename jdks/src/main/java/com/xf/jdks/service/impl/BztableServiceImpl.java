package com.xf.jdks.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.xf.jdks.commons.componet.BaseDaoComponent;
import com.xf.jdks.commons.enums.EOperators;
import com.xf.jdks.commons.global.DataMap;
import com.xf.jdks.commons.util.BaseChecks;
import com.xf.jdks.commons.util.Format;
import com.xf.jdks.commons.util.ResponseUtils;
import com.xf.jdks.dao.bean.InsertParams;
import com.xf.jdks.dao.bean.Parameter;
import com.xf.jdks.dao.bean.QueryParams;
import com.xf.jdks.dao.bean.UpdateParams;
import com.xf.jdks.dao.entity.AdminInfo;
import com.xf.jdks.dao.entity.BztableInfo;
import com.xf.jdks.dao.entity.UserInfo;
import com.xf.jdks.service.BztableService;

	@Service
	public class BztableServiceImpl implements BztableService {

		@Autowired
		private BaseDaoComponent baseDaoComponent;

		@Override
		public JSONObject list(Map<String, Object> map) throws SQLException {
			QueryParams queryParams = QueryParams.createQueryParams("v_bztab");
			//queryParams.addQueryParams(Parameter.createParameter("type",map.get("type")));
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				list = baseDaoComponent.selectDataByParams(queryParams);
			return ResponseUtils.createSuccessResponseBodyForJiem("done", list);
		
		}
		
		//题目顺序
		@Override
		public JSONObject sttilelist() throws SQLException {
			QueryParams queryParams = QueryParams.createQueryParams("t_bz_tab");
			queryParams.addQueryParams(Parameter.createParameter("type","6"));
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				list = baseDaoComponent.selectDataByParams(queryParams);
			return ResponseUtils.createSuccessResponseBodyForJiem("done", list);
		}

		//答案顺序
		@Override
		public JSONObject stdalist() throws SQLException {
			QueryParams queryParams = QueryParams.createQueryParams("t_bz_tab");
			queryParams.addQueryParams(Parameter.createParameter("type","7"));
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				list = baseDaoComponent.selectDataByParams(queryParams);
			return ResponseUtils.createSuccessResponseBodyForJiem("done", list);
		}



		//适用等级
		@Override
		public JSONObject sydjlist() throws SQLException {
			QueryParams queryParams = QueryParams.createQueryParams("t_bz_tab");
			queryParams.addQueryParams(Parameter.createParameter("type","5"));
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				list = baseDaoComponent.selectDataByParams(queryParams);
			return ResponseUtils.createSuccessResponseBodyForJiem("done", list);
		}

		@Override
		public JSONObject pageList(Map<String, Object> params) throws SQLException {
			List<Map<String, Object>> result =queryBZ(params);
			return ResponseUtils.createSuccessResponseBodyForJiem("查询成功",result);
		
		}

	
	    private List<Map<String, Object>> queryBZ(Map<String, Object> params) throws SQLException{
	    	params.remove("admininfo");
	    	params.remove("userinfo");
	        Map<String, Object> map = new DataMap<>();
	        for (Map.Entry<String, Object> m : params.entrySet()) {
	            if (m.getValue() != null && !"".equals(m.getValue())) map.put(m.getKey(), m.getValue());
	        }
	        QueryParams queryParams = QueryParams.createQueryParams("v_bztab");
	        
	        queryParams.addQueryParamsByMap(map);
	        queryParams.printSelf();
	        List<Map<String, Object>> data = baseDaoComponent.selectDataByParams(queryParams);
	        return data;
	    }
	    
		
		
		@Override
		public JSONObject add(Map map) throws SQLException {
			AdminInfo adminInfo = (AdminInfo) map.get("adminInfo");
			String Id = UUID.randomUUID().toString();
			String type= (String) map.get("type");
			String name= (String) map.get("name");
			String code= (String) map.get("code");		
			InsertParams insertParams = InsertParams.createInsertParams("T_bz_tab", "id", "type", "name","code");
			insertParams.setValues(Id, type, name,code);
			baseDaoComponent.insertDataByParams(insertParams);
			return ResponseUtils.createSuccessResponseBodyForJiem("添加成功");

		}

		@Override
		public JSONObject searchOne(Map map) throws SQLException {
			String id = (String) map.get("id");
			List<Map<String, Object>> mapList =new ArrayList<Map<String, Object>>();
				QueryParams queryParams = QueryParams.createQueryParams("v_bztab");
				queryParams.addQueryParams(Parameter.createParameter("id", id));
				mapList= baseDaoComponent.selectDataByParams(queryParams);
			return ResponseUtils.createSuccessResponseBodyForJiem("查询成功",mapList.get(0));
		}

		@Override
		public JSONObject edit(Map map) throws SQLException {
			String id = (String) map.get("id");
			String type= (String) map.get("type");
			String code= (String) map.get("code");
			String name= (String) map.get("name");	
			 UpdateParams updateParams = UpdateParams.createUpdateParams("T_bz_tab");
		        updateParams.addParam(Parameter.createParameter("type", type));
		        updateParams.addParam(Parameter.createParameter("code", code));
		        updateParams.addParam(Parameter.createParameter("name", name));	
		        updateParams.addWhereParameter(Parameter.createParameter("id", id));		        
		        baseDaoComponent.updateDataByParams(updateParams);
				return ResponseUtils.createSuccessResponseBodyForJiem("修改成功");            
		}

		@Override
		public JSONObject deleted(Map map) throws SQLException {
			String id= (String) map.get("ids");
	        String[] ids = null;
	        if (id != null) {
	            if (id.contains(",")) {
	            	ids = id.split(",");
	            } else {
	            	ids = new String[]{id};
	            }
	        }
	        UpdateParams updateParams = UpdateParams.createUpdateParams("T_bz_tab");
	        updateParams.addWhereParameter(Parameter.createParameter("id", EOperators.包含, ids));
	        updateParams.printSelf();
	        baseDaoComponent.deleteDataByParams(updateParams);	
		return ResponseUtils.createSuccessResponseBodyForJiem("删除成功");
	
		}


		
		
		
		
		

}
