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
import com.xf.jdks.dao.UserDao;
import com.xf.jdks.dao.bean.DeleteParams;
import com.xf.jdks.dao.bean.InsertParams;
import com.xf.jdks.dao.bean.Parameter;
import com.xf.jdks.dao.bean.QueryParams;
import com.xf.jdks.dao.bean.UpdateParams;
import com.xf.jdks.dao.entity.AdminInfo;
import com.xf.jdks.dao.entity.UserInfo;
import com.xf.jdks.exceptions.FileTypeNotFoundException;
import com.xf.jdks.service.UserService;


	@Service
	public class UserServiceImpl implements UserService {

		@Autowired
		private BaseDaoComponent baseDaoComponent;
		
		@Autowired
		private UserDao userDao;

		@Override
		public UserInfo userLogin(String zwh, String ksxtsfzjh,String ksxtzkzh) {
			return userDao.userLogin(zwh,ksxtsfzjh,ksxtzkzh);
		}

		

		@Override
		public JSONObject add(Map map) throws SQLException {	
				AdminInfo adminInfo = (AdminInfo) map.get("adminInfo");
				String Id = UUID.randomUUID().toString();
				String ksxtxm= (String) map.get("ksxtxm");
				String ksxtsfzjh= (String) map.get("ksxtsfzjh");
				String ksxtzkzh= (String) map.get("ksxtzkzh");		
				String ksccid= (String) map.get("ksccid");	
				String zwh= (String) map.get("zwh");	
				String adduser=adminInfo.getId();
				InsertParams insertParams = InsertParams.createInsertParams("T_UserINFO", "id", "ksxtxm", "ksxtsfzjh","adduser","addtime");
				insertParams.setValues(Id, ksxtxm, ksxtsfzjh,adduser,Format.getDateTime());
				baseDaoComponent.insertDataByParams(insertParams);
    			String Id2 = UUID.randomUUID().toString();
    			InsertParams insertParams2 = InsertParams.createInsertParams("t_user_kscc", "id", "userid", "ksccid","addtime","adduser","sfdl","ksxtzkzh","zwh");
    			insertParams2.setValues(Id2, Id,ksccid,Format.getDateTime(),adduser,"0",ksxtzkzh,zwh);
				baseDaoComponent.insertDataByParams(insertParams2);
			return ResponseUtils.createSuccessResponseBodyForJiem("添加成功");
		}

		@Override
		public JSONObject searchOne(Map map) throws SQLException {		
			String id = (String) map.get("id");
			UserInfo userInfo;
			List<Map<String, Object>> mapList =new ArrayList<Map<String, Object>>();
				QueryParams queryParams = QueryParams.createQueryParams("v_userinfo");
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
	        QueryParams queryParams = QueryParams.createQueryParams("v_userinfo");
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
	        queryParams.addOrderColumns("sfdl desc");
	        queryParams.printSelf();
	        List<Map<String, Object>> data = baseDaoComponent.selectDataByParams(queryParams);
	        return data;
	    }
	    



		@Override
		public JSONObject edit( Map map) throws SQLException {
				AdminInfo adminInfo = (AdminInfo) map.get("adminInfo");
				String userid = (String) map.get("id");
				String ksxtxm= (String) map.get("ksxtxm");
				String zwh= (String) map.get("zwh");	
				String ksxtsfzjh= (String) map.get("ksxtsfzjh");
				String ksxtzkzh= (String) map.get("ksxtzkzh");	
				String ksccid= (String) map.get("ksccid");
				String oldksccid=(String)map.get("oldksccid");
				String updatetime=Format.getDateTime();
				String addtime=Format.getDateTime();
				String updateuser=adminInfo.getId();
				String sfdl=(String) map.get("sfdl");
				if(null==sfdl||sfdl.equals("")){
					sfdl="0";
				}
		        UpdateParams updateParams = UpdateParams.createUpdateParams("T_USERINFO");
		        updateParams.addParam(Parameter.createParameter("ksxtxm", ksxtxm));		  
		        updateParams.addParam(Parameter.createParameter("ksxtsfzjh", ksxtsfzjh));		       	
		        updateParams.addParam(Parameter.createParameter("updatetime", updatetime));
		        updateParams.addParam(Parameter.createParameter("updateuser", updateuser));
		        updateParams.addWhereParameter(Parameter.createParameter("id", userid));		        
		        baseDaoComponent.updateDataByParams(updateParams);
		        
		         QueryParams queryParam = QueryParams.createQueryParams("T_user_kscc");
		         queryParam.addQueryParams(Parameter.createParameter("ksccid", oldksccid));
		         queryParam.addQueryParams(Parameter.createParameter("userid", userid));
		         queryParam.addQueryParams(Parameter.createParameter("deleted", "0"));
		         queryParam.addQueryParams(Parameter.createParameter("used", "0"));		
                 List<Map<String, Object>>  userkscc =null; 
                 userkscc = baseDaoComponent.selectDataByParams(queryParam);
                if(null!=userkscc&&userkscc.size()>0){
                      String userksccid= (String)userkscc.get(0).get("id");
                	  UpdateParams updateParamsKsCC = UpdateParams.createUpdateParams("t_user_kscc");
      		          updateParamsKsCC.addParam(Parameter.createParameter("sfdl", sfdl));
      		          updateParamsKsCC.addParam(Parameter.createParameter("ksccid", ksccid));
      		          updateParamsKsCC.addParam(Parameter.createParameter("updatetime", updatetime));
      		          updateParamsKsCC.addParam(Parameter.createParameter("updateuser", updateuser));
      		          updateParamsKsCC.addParam(Parameter.createParameter("zwh", zwh));
      		          updateParamsKsCC.addParam(Parameter.createParameter("ksxtzkzh", ksxtzkzh));
                		 updateParamsKsCC.addWhereParameter(Parameter.createParameter("id", userksccid));
          		    	  baseDaoComponent.updateDataByParams(updateParamsKsCC);
          		     	 //执行修改
                	   }else{//执行新增
                			String Id = UUID.randomUUID().toString();
                			InsertParams insertParams = InsertParams.createInsertParams("t_user_kscc", "id", "userid", "ksccid","addtime","adduser","sfdl","ksxtzkzh","zwh");
            				insertParams.setValues(Id, userid,ksccid,addtime,updateuser,sfdl,ksxtzkzh,zwh);
            				baseDaoComponent.insertDataByParams(insertParams);
                	   }
                			       
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
		        UpdateParams updateParams = UpdateParams.createUpdateParams("T_userinfo");
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
		        UpdateParams updateParams = UpdateParams.createUpdateParams("T_userinfo");
		        updateParams.addParam(Parameter.createParameter("deleted", "1"));
		        updateParams.addParam(Parameter.createParameter("deltime", Format.getDateTime()));
		        updateParams.addWhereParameter(Parameter.createParameter("id", EOperators.包含, ids));
		        updateParams.printSelf();
		        baseDaoComponent.updateDataByParams(updateParams);	
			return ResponseUtils.createSuccessResponseBodyForJiem("删除成功");
		}


	    @Override
	    public JSONObject importUsers(Map<String, Object> params) throws IOException, SQLException{
	    	AdminInfo adminInfo = (AdminInfo) params.remove("admininfo");
	        //成功的list 和失败的 list
	        List<Map<String, Object>> successList = new ArrayList<>();
	        List<Map<String, Object>> errorList = new ArrayList<>();
	        //读取Excel
	        ExportExcel exportExcel = ExportExcel.createExportExcel(params.get("url").toString(), "{KSXTSFZJH:身份证号,KSXTZKZH:准考证号,KSXTXM:姓名,SEX:性别}");
	        List<Map<String, Object>> mapList = exportExcel.readExcel();
	        //mapList副本
	        List<Map<String, Object>> newMap = CollectionUtils.cloneDataList(mapList);
	        //校验班级是否存在,不存在抛出异常
	        int j = 0;
	        for (Map<String, Object> impDataMap : mapList) {
				String Id = UUID.randomUUID().toString();
				String ksxtxm= (String) impDataMap.get("ksxtxm");
				String ksxtsfzjh= (String) impDataMap.get("ksxtsfzjh");
				String ksxtzkzh= (String) impDataMap.get("ksxtzkzh");		
				String sex= (String) impDataMap.get("sex");		
				if(null==ksxtxm||"".equals(ksxtxm)){
	                newMap.get(j).put("remark", "姓名不能为空");
	                errorList.add(newMap.get(j));
	                j++;
	                continue;
				}
				if(null==ksxtsfzjh||"".equals(ksxtsfzjh)){
	                newMap.get(j).put("remark", "身份证号不能为空");
	                errorList.add(newMap.get(j));
	                j++;
	                continue;
				}
				if(null==ksxtzkzh||"".equals(ksxtzkzh)){
	                newMap.get(j).put("remark", "准考证号不能为空");
	                errorList.add(newMap.get(j));
	                j++;
	                continue;
				}
				if(null==sex||"".equals(sex)){
	                newMap.get(j).put("remark", "性别不能为空");
	                errorList.add(newMap.get(j));
	                j++;
	                continue;
				}
				if("男".equals(sex)){
					sex="1";
				}else if("女".equals(sex)){
					sex="2";
				}else{
					sex="";
				}
				String adduser=adminInfo.getId();	
				InsertParams insertParams = InsertParams.createInsertParams("T_UserINFO", "id", "ksxtxm", "ksxtsfzjh","sex","adduser","addtime","deleted","used");
				insertParams.setValues(Id, ksxtxm, ksxtsfzjh,ksxtzkzh,sex,adduser,Format.getDateTime(),"0","0");
				baseDaoComponent.insertDataByParams(insertParams);   
	            successList.add(newMap.get(j));
	            j++;
	        }
	        //将错误信息写入excel中
	        String url1 = writeMessageToExcel(successList, "{KSXTSFZJH:身份证号,KSXTZKZH:准考证号,KSXTXM:姓名,SEX:性别}");
	        String url2 = writeMessageToExcel(errorList, "{KSXTSFZJH:身份证号,KSXTZKZH:准考证号,KSXTXM:姓名,SEX:性别,REMARK:错误信息}");
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
	    
	    @Override
	    public JSONObject expUsers(Map<String, Object> params) throws SQLException, FileTypeNotFoundException, IOException {
	        params.remove("limit");
	        params.remove("offset");
	        
	        List<Map<String, Object>> result = queryUsers(params);
	        FileInfoPo fileInfoPo = FileIOComponent.createTempFilePathByType(FileIOComponent.EXPORT_TYPE,"xlsx");
	        String absPath = fileInfoPo.getFileAbsPath();
	        String virPath = fileInfoPo.getFileVirPath();
	        Map<String, Object> filePath = new HashMap<>();
	        filePath.put("url", virPath);
	        ExportExcel eh = ExportExcel.createExportExcel(absPath, "{NUMBER:序号,KSXTSFZJH:身份证号,KSXTZKZH:准考证号,KSXTXM:姓名,SEX:性别}");
	        for (int i = 0, len = result.size(); i < len; i++) {
	            result.get(i).put("NUMBER", i + 1);
	        }
	        eh.setCurrentData(result);
	        eh.writeCurrentData();
	        result.add(filePath);
	        JSONObject rs = ResponseUtils.createSuccessResponseBodyForJiem("done");
	        rs.put("url", virPath);
	        return rs;
	    }

		@Override
		public JSONObject userLogin(Map map,HttpServletRequest request) {
			String zwh= (String) map.get("zwh");
			String ksxtzkzh= (String) map.get("ksxtzkzh");
			String ksxtsfzjh=(String) map.get("ksxtsfzjh");
			String vcode=(String)map.get("vcode");
			HttpSession session = request.getSession();	
			UserInfo userInfo_init =null;
			Map<String, String> entity = new HashMap<String, String>();
			String validateNum = (String) session.getAttribute("code");
				if (null != vcode && vcode.equalsIgnoreCase(validateNum)) {// 验证码正确				
					//pwd = Encrypt.jiam(pwd);
					if((""!=zwh||null!=zwh)&&(""!=ksxtzkzh||null!=ksxtzkzh)&&(""!=ksxtsfzjh||null!=ksxtsfzjh)){
						
					userInfo_init = userLogin(zwh,ksxtsfzjh,ksxtzkzh);
					if (!RuleValidator.hasEmpty(userInfo_init)) {

						if (null != ksxtzkzh && null != userInfo_init// 密码正确
								) {
							//	json.put("entity", entity);
								session.setAttribute("userInfo", userInfo_init);
								session.setAttribute("adminInfo",null);
						} else {// 加密密码错误
							return ResponseUtils.createErrorResponseBodyJiem("用户名或密码错误");
						}

					} else {
						return ResponseUtils.createErrorResponseBodyJiem("用户不存在");
					}
				}else {
		            return ResponseUtils.createErrorResponseBodyJiem("身份证件号与准考证号必须要输入");
				}
				} else {
					return ResponseUtils.createErrorResponseBodyJiem("验证码错误");
					
				}
			return ResponseUtils.createSuccessResponseBodyForJiem("登陆成功", userInfo_init);
		}

		
		
		
		
		
		
		
		@Override
		public JSONObject importxy(Map<String, Object> params,HttpServletRequest request) throws SQLException {
		  
			
			String ksccid= (String) params.get("ksccid");
			String pxpcid= (String) params.get("pxpcid");
			String dj= (String) params.get("dj"); 

		    QueryParams queryParams1 = QueryParams.createQueryParams("v_xyinfo");
		     queryParams1.addQueryParams(Parameter.createParameter("pxpcid", pxpcid));
		     queryParams1.addQueryParams(Parameter.createParameter("djdm", dj));
		     queryParams1.printSelf();	     		     
		     List<Map<String, Object>> xylist;
			 xylist = baseDaoComponent.selectDataByParams(queryParams1);
		     for(int i=0;i<xylist.size();i++)
		       {
		    		Map<String, Object> ksfsMap=xylist.get(i);
		    		String Id = (String) ksfsMap.get("id");
					
			     DeleteParams deleteParams = DeleteParams.createDeleteParams("T_userINFO", Parameter.createParameter("id", Id));           
			     baseDaoComponent.deleteDataByParams(deleteParams);
			     DeleteParams deleteParams1 = DeleteParams.createDeleteParams("T_user_kscc", Parameter.createParameter("id", Id));           
			     baseDaoComponent.deleteDataByParams(deleteParams1);

					String zjlx= (String) ksfsMap.get("zjlx");
					String sfzjh= (String) ksfsMap.get("sfzjh");
					String ksxtxm= (String) ksfsMap.get("ksxtxm");		
					String sex= (String) ksfsMap.get("sex");	
					String lxdh= (String) ksfsMap.get("lxdh");	
					String email= (String) ksfsMap.get("email");	
					InsertParams insertParams = InsertParams.createInsertParams("T_userINFO", "id", "ksxtsfzjh", "ksxtxm","sex","kszjlx","used","deleted","addtime");
					insertParams.setValues(Id, sfzjh,ksxtxm,sex,zjlx,"0","0",Format.getDateTime());
					baseDaoComponent.insertDataByParams(insertParams);
					InsertParams insertParams1 = InsertParams.createInsertParams("T_user_kscc", "id", "userid", "ksccid","sfdl","used","deleted","addtime");
					insertParams1.setValues(Id,Id,ksccid,"0","0","0",Format.getDateTime());
					baseDaoComponent.insertDataByParams(insertParams1);
		       }
		 	return ResponseUtils.createSuccessResponseBodyForJiem("考生导入成功");
			

		
		}

		
		
		
		
		@Override
		public JSONObject ksmdList(Map<String, Object> params)
				throws SQLException {
			List<Map<String, Object>> result = queryksmd(params);
			return ResponseUtils.createSuccessResponseBodyForJiem("查询成功",result);
		}

		//查询学生信息
	    private List<Map<String, Object>> queryksmd(Map<String, Object> params) throws SQLException{
	    	params.remove("admininfo");
	    	params.remove("userinfo");
	        Map<String, Object> map = new DataMap<>();
	        for (Map.Entry<String, Object> m : params.entrySet()) {
	            if (m.getValue() != null && !"".equals(m.getValue())) map.put(m.getKey(), m.getValue());
	        }
	        QueryParams queryParams = QueryParams.createQueryParams("v_userinfo");
	        map.put("used", "0");
	        map.put("deleted", "0");
	        queryParams.addQueryParamsByMap(map);
	        queryParams.printSelf();
	        List<Map<String, Object>> data = baseDaoComponent.selectDataByParams(queryParams);
	        return data;
	    }
	    

		@Override
		public JSONObject reset(Map map) throws Exception {	
			AdminInfo adminInfo = (AdminInfo) map.get("adminInfo");
			String id = (String) map.get("id");
	        QueryParams queryParams = QueryParams.createQueryParams("t_userinfo");
	        queryParams.addQueryParams(Parameter.createParameter("id", id));
	        Map<String, Object> data = baseDaoComponent.selectOneDataByParams(queryParams);
			String newpwd=(String) data.get("ksxtsfzjh");
			if(newpwd==null||"".equals(newpwd)){
				throw new Exception("身份证为空");
			}
			newpwd=newpwd.substring(newpwd.length()-6);
	        UpdateParams updateParams = UpdateParams.createUpdateParams("t_userinfo");
	        updateParams.addParam(Parameter.createParameter("pwdmd", newpwd));
	        updateParams.addParam(Parameter.createParameter("updatetime", Format.getDateTime()));
	        updateParams.addParam(Parameter.createParameter("updateuserid", adminInfo.getId()));
	        updateParams.addWhereParameter(Parameter.createParameter("id", id));
	        baseDaoComponent.updateDataByParams(updateParams);
		return ResponseUtils.createSuccessResponseBodyForJiem("重置密码成功");
		}

}
