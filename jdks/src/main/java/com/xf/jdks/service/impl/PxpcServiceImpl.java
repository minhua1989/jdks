	package com.xf.jdks.service.impl;

	import java.io.IOException;
	import java.sql.SQLException;
	import java.util.ArrayList;
	import java.util.HashMap;
	import java.util.List;
	import java.util.Map;
	import java.util.UUID;

	import javax.servlet.http.HttpServletRequest;
	import javax.servlet.http.HttpSession;

	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.stereotype.Service;

	import com.alibaba.fastjson.JSONObject;
	import com.github.pagehelper.PageHelper;
	import com.github.pagehelper.PageInfo;
	import com.xf.jdks.commons.componet.BaseDaoComponent;
	import com.xf.jdks.commons.componet.FileIOComponent;
	import com.xf.jdks.commons.enums.EOperators;
	import com.xf.jdks.commons.global.DataMap;
	import com.xf.jdks.commons.global.FileInfoPo;
	import com.xf.jdks.commons.util.BaseChecks;
	import com.xf.jdks.commons.util.CollectionUtils;
	import com.xf.jdks.commons.util.ExportExcel;
	import com.xf.jdks.commons.util.Format;
	import com.xf.jdks.commons.util.ResponseUtils;
	import com.xf.jdks.commons.util.RuleValidator;
import com.xf.jdks.dao.PxpcDao;
import com.xf.jdks.dao.bean.DeleteParams;
	import com.xf.jdks.dao.bean.InsertParams;
	import com.xf.jdks.dao.bean.Parameter;
	import com.xf.jdks.dao.bean.QueryParams;
	import com.xf.jdks.dao.bean.UpdateParams;
	import com.xf.jdks.dao.entity.AdminInfo;
	import com.xf.jdks.dao.entity.UserInfo;
	import com.xf.jdks.exceptions.FileTypeNotFoundException;
import com.xf.jdks.service.PxpcService;
import com.xf.jdks.service.XyService;


		@Service
		public class PxpcServiceImpl implements PxpcService {

			@Autowired
			private BaseDaoComponent baseDaoComponent;
			
			@Autowired
			private PxpcDao pxpcDao;


			@Override
			public JSONObject add(Map map) throws SQLException {	
					AdminInfo adminInfo = (AdminInfo) map.get("adminInfo");
					String Id = UUID.randomUUID().toString();
					String pxpcmc= (String) map.get("pxpcmc");
					String pxpcdm= (String) map.get("pxpcdm");
					String pxjg= (String) map.get("pxjg");		
					String nf= (String) map.get("nf");	
					String dj=(String)map.get("dj");
					String starttime= (String) map.get("starttime");	
					String endtime=(String)map.get("endtime");
					String pxrs=(String)map.get("pxrs");
					String code= (String) map.get("code");	
					String addtime= Format.getDateTime();	
					String used= "0";
					InsertParams insertParams = InsertParams.createInsertParams("T_pxpcINFO", "id", "pxpcmc", "pxpcdm","pxjg","nf","dj","code","addtime","used","starttime","endtime","pxrs");
					insertParams.setValues(Id, pxpcmc, pxpcdm,pxjg,nf,dj,code,addtime,used,starttime,endtime,pxrs);
					baseDaoComponent.insertDataByParams(insertParams);
	    			return ResponseUtils.createSuccessResponseBodyForJiem("添加成功");
			}

			@Override
			public JSONObject searchOne(Map map) throws SQLException {		
				String id = (String) map.get("id");
				
				List<Map<String, Object>> mapList =new ArrayList<Map<String, Object>>();
					QueryParams queryParams = QueryParams.createQueryParams("t_pxpcinfo");
					queryParams.addQueryParams(Parameter.createParameter("id", id));
					mapList= baseDaoComponent.selectDataByParams(queryParams);
				return ResponseUtils.createSuccessResponseBodyForJiem("查询成功",mapList.get(0));
		}
			
			
			@Override
			public JSONObject pageList(Map<String, Object> params)
					throws SQLException {
				List<Map<String, Object>> result = queryPxpc(params);
				return ResponseUtils.createSuccessResponseBodyForJiem("查询成功",result);
			}

			//查询学生信息
		    private List<Map<String, Object>> queryPxpc(Map<String, Object> params) throws SQLException{
		    	params.remove("admininfo");
		    	params.remove("userinfo");
		        Map<String, Object> map = new DataMap<>();
		        for (Map.Entry<String, Object> m : params.entrySet()) {
		            if (m.getValue() != null && !"".equals(m.getValue())) map.put(m.getKey(), m.getValue());
		        }
		        QueryParams queryParams = QueryParams.createQueryParams("v_pxpcinfo");
		        if (map.get("pxpcmc") != null && !"".equals(map.get("pxpcmc"))) {//模糊查询
		            String pxpcmc = (String) params.get("pxpcmc");
		            //判断是否为中文
		            if(BaseChecks.isChineseChar(pxpcmc)){
		                //是，空格全部替换
		            	pxpcmc = pxpcmc.replaceAll(" ","");
		            }else{
		                //否，替换两端空格
		            	pxpcmc = BaseChecks.bothEndsStr(pxpcmc);
		            }
		            queryParams.addQueryParams(Parameter.createParameter("pxpcmc", EOperators.类似, pxpcmc));
		            map.remove("pxpcmc");
		        }
		        map.put("deleted", "0");
		        queryParams.addQueryParamsByMap(map);
		        queryParams.printSelf();
		        List<Map<String, Object>> data = baseDaoComponent.selectDataByParams(queryParams);
		        return data;
		    }
		    



			@Override
			public JSONObject edit( Map map) throws SQLException {
					AdminInfo adminInfo = (AdminInfo) map.get("adminInfo");
					String pxpcmc= (String) map.get("pxpcmc");
					String pxpcdm= (String) map.get("pxpcdm");
					String pxjg= (String) map.get("pxjg");		
					String nf= (String) map.get("nf");	
					String dj=(String)map.get("dj");
					String code= (String) map.get("code");	
					String id=(String)map.get("id");
					String starttime= (String) map.get("starttime");	
					String endtime=(String)map.get("endtime");
					String pxrs=(String)map.get("pxrs");
					String updatetime= Format.getDateTime();	
					
			        UpdateParams updateParams = UpdateParams.createUpdateParams("T_pxpcINFO");
			        updateParams.addParam(Parameter.createParameter("pxpcmc", pxpcmc));
			        updateParams.addParam(Parameter.createParameter("pxpcdm", pxpcdm));
			        updateParams.addParam(Parameter.createParameter("pxjg", pxjg));	
			        updateParams.addParam(Parameter.createParameter("nf", nf));
			        updateParams.addParam(Parameter.createParameter("dj", dj));
			        updateParams.addParam(Parameter.createParameter("starttime", starttime));	
			        updateParams.addParam(Parameter.createParameter("endtime", endtime));
			        updateParams.addParam(Parameter.createParameter("pxrs", pxrs));
			        updateParams.addParam(Parameter.createParameter("pxrs", pxrs));
			        updateParams.addParam(Parameter.createParameter("updatetime", updatetime));	
			        updateParams.addWhereParameter(Parameter.createParameter("id", id));		        
			        baseDaoComponent.updateDataByParams(updateParams);
			                      			       
				return ResponseUtils.createSuccessResponseBodyForJiem("修改成功");
			}





			//逻辑删除
			@Override
			public JSONObject deleted(Map map) throws SQLException {
					String id= (String) map.get("ids");
					String used=(String) map.get("used");
			        String[] ids = null;
			        if (id != null) {
			            if (id.contains(",")) {
			            	ids = id.split(",");
			            } else {
			            	ids = new String[]{id};
			            }
			        }
			        UpdateParams updateParams = UpdateParams.createUpdateParams("T_pxpcinfo");
			        updateParams.addParam(Parameter.createParameter("used", used));
			        updateParams.addParam(Parameter.createParameter("updatetime", Format.getDateTime()));
			        updateParams.addWhereParameter(Parameter.createParameter("id", EOperators.包含, ids));
			        updateParams.printSelf();
			        baseDaoComponent.updateDataByParams(updateParams);	
				return ResponseUtils.createSuccessResponseBodyForJiem("设置成功");
			}

			//物理删除
			@Override
			public JSONObject wulideleted(Map map) throws SQLException {
					String id= (String) map.get("ids");
			        String[] ids = null;
			        if (id != null) {
			            if (id.contains(",")) {
			            	ids = id.split(",");
			            } else {
			            	ids = new String[]{id};
			            }
			        }
			        UpdateParams updateParams = UpdateParams.createUpdateParams("T_pxpcinfo");
			        updateParams.addParam(Parameter.createParameter("deleted", "1"));
			        updateParams.addParam(Parameter.createParameter("deltime", Format.getDateTime()));
			        updateParams.addWhereParameter(Parameter.createParameter("id", EOperators.包含, ids));
			        updateParams.printSelf();
			        baseDaoComponent.updateDataByParams(updateParams);	
				return ResponseUtils.createSuccessResponseBodyForJiem("删除成功");
			}

			//培训批次列表
			@Override
			public JSONObject list(Map<String, Object> map) throws SQLException {
				QueryParams queryParams = QueryParams.createQueryParams("t_pxpcinfo");
				queryParams.addQueryParams(Parameter.createParameter("nf",map.get("nf")));
//				queryParams.addQueryParams(Parameter.createParameter("dj",map.get("dj")));
				queryParams.addQueryParams(Parameter.createParameter("used","0"));
				queryParams.addQueryParams(Parameter.createParameter("deleted","0"));
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
					list = baseDaoComponent.selectDataByParams(queryParams);
				return ResponseUtils.createSuccessResponseBodyForJiem("done", list);
			
			}
			

}

