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
import com.xf.jdks.commons.enums.EStandTables;
import com.xf.jdks.commons.global.DataDict;
import com.xf.jdks.commons.global.DataMap;
import com.xf.jdks.commons.global.FileInfoPo;
import com.xf.jdks.commons.util.BaseChecks;
import com.xf.jdks.commons.util.CollectionUtils;
import com.xf.jdks.commons.util.ExportExcel;
import com.xf.jdks.commons.util.Format;
import com.xf.jdks.commons.util.ResponseUtils;
import com.xf.jdks.commons.util.RuleValidator;
import com.xf.jdks.dao.UserDao;
import com.xf.jdks.dao.XyDao;
import com.xf.jdks.dao.bean.DeleteParams;
import com.xf.jdks.dao.bean.InsertParams;
import com.xf.jdks.dao.bean.Parameter;
import com.xf.jdks.dao.bean.QueryParams;
import com.xf.jdks.dao.bean.UpdateParams;
import com.xf.jdks.dao.entity.AdminInfo;
import com.xf.jdks.dao.entity.UserInfo;
import com.xf.jdks.exceptions.DictIsNotFoundException;
import com.xf.jdks.exceptions.FileTypeNotFoundException;
import com.xf.jdks.service.UserService;
import com.xf.jdks.service.XyService;


	@Service
	public class XyServiceImpl implements XyService {

		@Autowired
		private BaseDaoComponent baseDaoComponent;
		
		@Autowired
		private XyDao xyDao;


		@Override
		public JSONObject add(Map map) throws SQLException {	
				AdminInfo adminInfo = (AdminInfo) map.get("adminInfo");
				String Id = UUID.randomUUID().toString();
				String ksxtxm= (String) map.get("ksxtxm");
				String sfzjh= (String) map.get("sfzjh");
				String zjlx= (String) map.get("zjlx");		
				String pxpcid= (String) map.get("pxpcid");	
				String pxdj=(String)map.get("pxdj");
				String lxdh= (String) map.get("lxdh");	
				String email= (String) map.get("email");	
				String sex= (String) map.get("sex");	
				String adduser=adminInfo.getId();
				InsertParams insertParams = InsertParams.createInsertParams("T_XYINFO", "id", "ksxtxm", "sfzjh","zjlx","pxpcid","lxdh","email","sex","adduser","addtime");
				insertParams.setValues(Id, ksxtxm, sfzjh,zjlx,pxpcid,lxdh,email,sex,adduser,Format.getDateTime());
				baseDaoComponent.insertDataByParams(insertParams);
    			return ResponseUtils.createSuccessResponseBodyForJiem("添加成功");
		}

		@Override
		public JSONObject searchOne(Map map) throws SQLException {		
			String id = (String) map.get("id");
			
			List<Map<String, Object>> mapList =new ArrayList<Map<String, Object>>();
				QueryParams queryParams = QueryParams.createQueryParams("v_xyinfo");
				queryParams.addQueryParams(Parameter.createParameter("id", id));
				mapList= baseDaoComponent.selectDataByParams(queryParams);
			return ResponseUtils.createSuccessResponseBodyForJiem("查询成功",mapList.get(0));
	}
		
		
		@Override
		public JSONObject pageList(Map<String, Object> params)
				throws SQLException {
			List<Map<String, Object>> result = queryUsers(params);
			return ResponseUtils.createSuccessResponseBodyForJiem("查询成功",result);
		}

		//查询学生信息
	    private List<Map<String, Object>> queryUsers(Map<String, Object> params) throws SQLException{
	    	params.remove("admininfo");
	    	params.remove("userinfo");
	        Map<String, Object> map = new DataMap<>();
	        for (Map.Entry<String, Object> m : params.entrySet()) {
	            if (m.getValue() != null && !"".equals(m.getValue())) map.put(m.getKey(), m.getValue());
	        }
	        QueryParams queryParams = QueryParams.createQueryParams("v_Xyinfo");
	        if (map.get("ksxtxm") != null && !"".equals(map.get("ksxtxm"))) {//模糊查询
	            String ksxtxm = (String) params.get("ksxtxm");
	            //判断是否为中文
	            if(BaseChecks.isChineseChar(ksxtxm)){
	                //是，空格全部替换
	            	ksxtxm = ksxtxm.replaceAll(" ","");
	            }else{
	                //否，替换两端空格
	            	ksxtxm = BaseChecks.bothEndsStr(ksxtxm);
	            }
	            queryParams.addQueryParams(Parameter.createParameter("ksxtxm", EOperators.类似, ksxtxm));
	            map.remove("ksxtxm");
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
				String userid = (String) map.get("id");
				String ksxtxm= (String) map.get("ksxtxm");
				String sfzjh= (String) map.get("sfzjh");
				String zjlx= (String) map.get("zjlx");	
				String sex= (String) map.get("sex");
				String lxdh=(String)map.get("lxdh");
				String email=(String)map.get("email");
				String updatetime=Format.getDateTime();
				String addtime=Format.getDateTime();
				String updateuser=adminInfo.getId();

		        UpdateParams updateParams = UpdateParams.createUpdateParams("T_XyINFO");
		        updateParams.addParam(Parameter.createParameter("ksxtxm", ksxtxm));
		        updateParams.addParam(Parameter.createParameter("sfzjh", sfzjh));
		        updateParams.addParam(Parameter.createParameter("zjlx", zjlx));	
		        updateParams.addParam(Parameter.createParameter("sex", sex));
		        updateParams.addParam(Parameter.createParameter("lxdh", lxdh));
		        updateParams.addParam(Parameter.createParameter("email", email));	
		        updateParams.addParam(Parameter.createParameter("updatetime", updatetime));
		        updateParams.addParam(Parameter.createParameter("updateuser", updateuser));
		        updateParams.addWhereParameter(Parameter.createParameter("id", userid));		        
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
		        UpdateParams updateParams = UpdateParams.createUpdateParams("T_Xyinfo");
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
		        UpdateParams updateParams = UpdateParams.createUpdateParams("T_Xyinfo");
		        updateParams.addParam(Parameter.createParameter("deleted", "1"));
		        updateParams.addParam(Parameter.createParameter("deltime", Format.getDateTime()));
		        updateParams.addWhereParameter(Parameter.createParameter("id", EOperators.包含, ids));
		        updateParams.printSelf();
		        baseDaoComponent.updateDataByParams(updateParams);	
			return ResponseUtils.createSuccessResponseBodyForJiem("删除成功");
		}


	    @Override
	    public JSONObject importNewXY(Map<String, Object> params) throws Exception{
	    	AdminInfo adminInfo = (AdminInfo) params.remove("admininfo");
	        //成功的list 和失败的 list
	        List<Map<String, Object>> successList = new ArrayList<>();
	        List<Map<String, Object>> errorList = new ArrayList<>();
	        //读取Excel
	        ExportExcel exportExcel = ExportExcel.createExportExcel(params.get("url").toString(), "{KSXTXM:姓名,PXPCMC:培训班次,ZJLX:证件类型,SFZJH:证件号码,SEX:性别,LXDH:手机,EMAIL:邮箱}");
	        List<Map<String, Object>> mapList = exportExcel.readExcel();
	        //mapList副本
	        List<Map<String, Object>> newMap = CollectionUtils.cloneDataList(mapList);
	        //校验班级是否存在,不存在抛出异常
	        for (Map<String, Object> impDataMap : mapList) {
				String Id = UUID.randomUUID().toString();
				String ksxtxm= (String) impDataMap.get("ksxtxm");
				String pxpcmc= (String) impDataMap.get("pxpcmc");
				String zjlx= (String) impDataMap.get("zjlx");
				String zjlxdm="";
		        try {
		        	zjlxdm=DataDict.getCodeByChineseValue(EStandTables.证件类型, zjlx);
		        } catch (Exception e) {
		        	impDataMap.put("remark", "证件类型不符合规范");
		        	errorList.add(impDataMap);
		        	continue;
		        }

				String sfzjh= (String) impDataMap.get("sfzjh");
				String sex= (String) impDataMap.get("sex");		
				String sexdm="";
		        try {
		        	sexdm=DataDict.getCodeByChineseValue(EStandTables.性别, sex);
		        } catch (Exception e) {
		        	impDataMap.put("remark", "性别不符合规范");
		        	errorList.add(impDataMap);
		        	continue;
		        }
				String email=(String)impDataMap.get("email");
				String lxdh= (String) impDataMap.get("lxdh");		
//				if("男".equals(sex)){
//					sex="1";
//				}else if("女".equals(sex)){
//					sex="2";
//				}else{
//					sex="";
//				}
				QueryParams pxpcqueryParams = QueryParams.createQueryParams("t_pxpcinfo");
				pxpcqueryParams.addQueryParams(Parameter.createParameter("pxpcmc", pxpcmc));
				pxpcqueryParams.addQueryParams(Parameter.createParameter("deleted", "0"));
				List<Map<String, Object>> pxpcresult= baseDaoComponent.selectDataByParams(pxpcqueryParams);
				if(pxpcresult.size()==0){
		        	impDataMap.put("remark", "没有此培训班次");
		        	errorList.add(impDataMap);
		        	continue;
				}
				String adduser=adminInfo.getId();	
				InsertParams insertParams = InsertParams.createInsertParams("T_XYINFO", "id", "ksxtxm", "sfzjh","zjlx","pxpcid","lxdh","email","sex","adduser","addtime");
				insertParams.setValues(Id, ksxtxm, sfzjh,zjlxdm,pxpcresult.get(0).get("id"),lxdh,email,sexdm,adduser,Format.getDateTime());
				baseDaoComponent.insertDataByParams(insertParams);   
	            successList.add(impDataMap);
	        }
	        //将错误信息写入excel中
	        String url1 = writeMessageToExcel(successList, "{KSXTXM:姓名,PXPCMC:培训班次,ZJLX:证件类型,SFZJH:证件号码,SEX:性别,LXDH:手机,EMAIL:邮箱}");
	        String url2 = writeMessageToExcel(errorList, "{KSXTXM:姓名,PXPCMC:培训班次,ZJLX:证件类型,SFZJH:证件号码,SEX:性别,LXDH:手机,EMAIL:邮箱,REMARK:错误信息}");
	        int sum = successList.size() + errorList.size();
	        Map<String, Object> result = new DataMap<>();
	        result.put("url1", url1);
	        result.put("url2", url2);
	        JSONObject rs = ResponseUtils.createSuccessResponseBodyForJiem("成功分入:" + successList.size() + "条,失败分入:" + errorList.size() + "条,总共处理:" + sum + "条", result);
	        rs.put("url1", url1);
	        rs.put("url2", url2);
	        return rs;
	    }
	    
	    /**
	     * 写入错误和成功信息表格
	     **/
	    public String writeMessageToExcel(List<Map<String, Object>> list, String excelHead) throws IOException {
	        //获取文件保存路径
//	        Properties p = new Properties();
//	        p.load(this.getClass().getResourceAsStream("/messages.properties"));
//	        //生成本地唯一文件名
//	        String newFileName = Format.getDateTimeLong();
//	        String absPath = p.getProperty("abspath.upload.share") + "/" + newFileName + ".xlsx";
//	        String virPath = p.getProperty("virpath.upload.share") + "/" + newFileName + ".xlsx";
	        String absPath = "";
	        String virPath = "";
	        try {
	            FileInfoPo fileInfoPo = FileIOComponent.createTempFilePathByType(FileIOComponent.EXPORT_TYPE,"xlsx");
	            absPath = fileInfoPo.getFileAbsPath();
	            virPath = fileInfoPo.getFileVirPath();
	            ExportExcel exportExcel = ExportExcel.createExportExcel(absPath, excelHead);
	            exportExcel.setCurrentData(list);
	            exportExcel.writeCurrentData();
	        } catch (FileTypeNotFoundException e) {
	            e.printStackTrace();
	        }
	        return virPath;
	    }
	    

}
