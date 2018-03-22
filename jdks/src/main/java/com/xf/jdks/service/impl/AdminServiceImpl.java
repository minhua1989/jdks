package com.xf.jdks.service.impl;

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
import com.xf.jdks.dao.bean.DeleteParams;
import com.xf.jdks.dao.bean.InsertParams;
import com.xf.jdks.dao.bean.Parameter;
import com.xf.jdks.dao.bean.QueryParams;
import com.xf.jdks.dao.bean.UpdateParams;
import com.xf.jdks.dao.entity.AdminInfo;
import com.xf.jdks.service.AdminService;
import com.xf.jdks.commons.componet.*;
import com.xf.jdks.commons.enums.EOperators;
import com.xf.jdks.commons.global.DataMap;
import com.xf.jdks.commons.util.*;
import com.xf.jdks.dao.*;
@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private BaseDaoComponent baseDaoComponent;
	
	@Autowired
	private AdminDao adminDao;
	
	
	@Override
	public JSONObject add(Map map) throws SQLException {	
			AdminInfo adminInfo = (AdminInfo) map.get("adminInfo");
			String Id = UUID.randomUUID().toString();
			String ename= (String) map.get("ename");
			String pwd= (String) map.get("pwd");
			String pwdmd= (String) map.get("pwdmd");
			String realname= (String) map.get("realname");
			String lxdh= (String) map.get("lxdh");
			String email= (String) map.get("email");
			String sex= (String) map.get("sex");
			String roleid= (String) map.get("roleid");
			String zjlx= (String) map.get("zjlx");
			String sfzjhm= (String) map.get("sfzjhm");
			InsertParams insertParams = InsertParams.createInsertParams("T_ADMININFO", "id", "ename", "pwd","realname","lxdh","addtime","email","logincnt","sex","roleid","adduserid","zjlx","sfzjhm","pwdmd");
			insertParams.setValues(Id, ename, pwd,realname,lxdh,Format.getDateTime(),email,0,sex,roleid,adminInfo.getId(),zjlx,sfzjhm,pwdmd);
			baseDaoComponent.insertDataByParams(insertParams);
		return ResponseUtils.createSuccessResponseBodyForJiem("添加成功");
	}

	@Override
	public JSONObject reset(Map map) throws Exception {	
		AdminInfo adminInfo = (AdminInfo) map.get("adminInfo");
		String id = (String) map.get("id");
        QueryParams queryParams = QueryParams.createQueryParams("T_ADMININFO");
        queryParams.addQueryParams(Parameter.createParameter("id", id));
        Map<String, Object> data = baseDaoComponent.selectOneDataByParams(queryParams);
		String newpwd=(String) data.get("sfzjhm");
		if(newpwd==null||"".equals(newpwd)){
			throw new Exception("身份证为空");
		}
		newpwd=newpwd.substring(newpwd.length()-6);
        UpdateParams updateParams = UpdateParams.createUpdateParams("T_ADMININFO");
        updateParams.addParam(Parameter.createParameter("pwdmd", newpwd));
        updateParams.addParam(Parameter.createParameter("updatetime", Format.getDateTime()));
        updateParams.addParam(Parameter.createParameter("updateuserid", adminInfo.getId()));
        updateParams.addWhereParameter(Parameter.createParameter("id", id));
        baseDaoComponent.updateDataByParams(updateParams);
	return ResponseUtils.createSuccessResponseBodyForJiem("重置密码成功");
	}
	
	
	@Override
	public JSONObject searchOne(Map map) throws SQLException {		
		String id = (String) map.get("id");
		List<Map<String, Object>> mapList =new ArrayList<Map<String, Object>>();
			QueryParams queryParams = QueryParams.createQueryParams("v_admininfo");
			queryParams.addQueryParams(Parameter.createParameter("id", id));
			mapList= baseDaoComponent.selectDataByParams(queryParams);
		return ResponseUtils.createSuccessResponseBodyForJiem("查询成功",mapList.get(0));

	}
	

    @Override
    public JSONObject pageList(Map<String, Object> params) throws SQLException {
    	params.remove("admininfo");
    	params.remove("userinfo");
        Map<String, Object> map = new DataMap<>();
        for (Map.Entry<String, Object> m : params.entrySet()) {
            if (m.getValue() != null && !"".equals(m.getValue())) map.put(m.getKey(), m.getValue());
        }
        QueryParams queryParams = QueryParams.createQueryParams("t_admininfo");
        if (map.get("ename") != null && !"".equals(map.get("ename"))) {//模糊查询
            String ename = (String) params.get("ename");
            //判断是否为中文
            if(BaseChecks.isChineseChar(ename)){
                //是，空格全部替换
            	ename = ename.replaceAll(" ","");
            }else{
                //否，替换两端空格
            	ename = BaseChecks.bothEndsStr(ename);
            }
            queryParams.addQueryParams(Parameter.createParameter("ename", EOperators.类似, ename));
            map.remove("ename");
        }
        if (map.get("realname") != null && !"".equals(map.get("realname"))) {//模糊查询
            String realname = (String) params.get("realname");
            //判断是否为中文
            if(BaseChecks.isChineseChar(realname)){
                //是，空格全部替换
            	realname = realname.replaceAll(" ","");
            }else{
                //否，替换两端空格
            	realname = BaseChecks.bothEndsStr(realname);
            }
            queryParams.addQueryParams(Parameter.createParameter("realname", EOperators.类似, realname));
            map.remove("realname");
        }
        map.put("deleted", "0");
        queryParams.addOrderColumns("ename asc");
        queryParams.addQueryParamsByMap(map);
        queryParams.printSelf();
        List<Map<String, Object>> data = baseDaoComponent.selectDataByParams(queryParams);
//        if (data == null || data.size() == 0) throw new DataNotFoundException();
        JSONObject result = ResponseUtils.createSuccessResponseBodyForJiem("查询成功", data);
		return result;
    }


	@Override
	public JSONObject edit( Map map) throws SQLException {
			AdminInfo adminInfo = (AdminInfo) map.get("adminInfo");
			String id = (String) map.get("id");
			String ename= (String) map.get("ename");
			String pwd= (String) map.get("pwd");
			String pwdmd= (String) map.get("pwdmd");
			String realname= (String) map.get("realname");
			String lxdh= (String) map.get("lxdh");
			String email= (String) map.get("email");
			String sex= (String) map.get("sex");
			String roleid= (String) map.get("roleid");
			String zjlx= (String) map.get("zjlx");
			String sfzjhm= (String) map.get("sfzjhm");
	        UpdateParams updateParams = UpdateParams.createUpdateParams("T_ADMININFO");
	        if(ename!=null&&!"".equals(ename)){
	        	updateParams.addParam(Parameter.createParameter("ename", ename));
	        }
	        if(pwd!=null&&!"".equals(pwd)){
	        	updateParams.addParam(Parameter.createParameter("pwd", pwd));
	        }
	        if(realname!=null&&!"".equals(realname)){
	        	updateParams.addParam(Parameter.createParameter("realname", realname));
	        }
	        if(lxdh!=null&&!"".equals(lxdh)){
	        	updateParams.addParam(Parameter.createParameter("lxdh", lxdh));
	        }
	        if(email!=null&&!"".equals(email)){
	        	updateParams.addParam(Parameter.createParameter("email", email));
	        }
	        if(sex!=null&&!"".equals(sex)){
	        	updateParams.addParam(Parameter.createParameter("sex", sex));
	        }
	        if(roleid!=null&&!"".equals(roleid)){
	        	updateParams.addParam(Parameter.createParameter("roleid", roleid));
	        }
	        if(zjlx!=null&&!"".equals(zjlx)){
	        	updateParams.addParam(Parameter.createParameter("zjlx", zjlx));
	        }
	        if(sfzjhm!=null&&!"".equals(sfzjhm)){
	        	updateParams.addParam(Parameter.createParameter("sfzjhm", sfzjhm));
	        }
	        if(pwdmd!=null&&!"".equals(pwdmd)){
	        	updateParams.addParam(Parameter.createParameter("pwdmd", pwdmd));
	        }
	        updateParams.addParam(Parameter.createParameter("updatetime", Format.getDateTime()));
	        updateParams.addParam(Parameter.createParameter("updateuserid", adminInfo.getId()));
	        updateParams.addWhereParameter(Parameter.createParameter("id", id));
	        baseDaoComponent.updateDataByParams(updateParams);
		return ResponseUtils.createSuccessResponseBodyForJiem("修改成功");
	}


	@Override
	public AdminInfo adminLogin(String ename, String pwd) {
		return adminDao.adminLogin(ename, pwd);
	}
	
	@Override
	public AdminInfo adminLoginbyename(String ename) {
		return adminDao.adminLoginbyename(ename);
	}
	

	//逻辑删除
	@Override
	public JSONObject deleted(Map map) throws SQLException {
		AdminInfo adminInfo = (AdminInfo) map.get("adminInfo");
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
	        UpdateParams updateParams = UpdateParams.createUpdateParams("T_ADMININFO");
	        updateParams.addParam(Parameter.createParameter("used", used));
	        updateParams.addParam(Parameter.createParameter("updatetime", Format.getDateTime()));
	        updateParams.addParam(Parameter.createParameter("updateuserid", adminInfo.getId()));
	        updateParams.addWhereParameter(Parameter.createParameter("id", EOperators.包含, ids));
	        updateParams.printSelf();
	        baseDaoComponent.updateDataByParams(updateParams);	
		return ResponseUtils.createSuccessResponseBodyForJiem("逻辑删除成功");
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
	        UpdateParams updateParams = UpdateParams.createUpdateParams("T_ADMININFO");
	        updateParams.addParam(Parameter.createParameter("deleted", "1"));
	        updateParams.addParam(Parameter.createParameter("deltime", Format.getDateTime()));
	        updateParams.addWhereParameter(Parameter.createParameter("id", EOperators.包含, ids));
	        updateParams.printSelf();
	        baseDaoComponent.updateDataByParams(updateParams);	
		return ResponseUtils.createSuccessResponseBodyForJiem("物理删除成功");
	}

	@Override
	public JSONObject adminLogin(Map map,HttpServletRequest request) {
		JSONObject rs = ResponseUtils.createSuccessResponseBody(null);
		String ename= (String) map.get("ename");
		String pwd=(String) map.get("pwd");
		String vcode=(String)map.get("vcode");
		HttpSession session = request.getSession();			
		Map<String, String> entity = new HashMap<String, String>();
		AdminInfo adminInfo_init =null;
		AdminInfo adminInfo_tmp =null;
		String validateNum = (String) session.getAttribute("code");
			if (null != vcode && vcode.equalsIgnoreCase(validateNum)) {// 验证码正确				
				//pwd = Encrypt.jiam(pwd);
				adminInfo_tmp= adminLoginbyename(ename);
				if(!RuleValidator.hasEmpty(adminInfo_tmp)){
					adminInfo_init= adminLogin(ename,pwd);
					if (!RuleValidator.hasEmpty(adminInfo_init)) {
	
						if (null != pwd && null != adminInfo_init// 密码正确
								) {
							//	json.put("entity", entity);
								session.setAttribute("adminInfo", adminInfo_init);
								session.setAttribute("userInfo", null);
						} else {// 加密密码错误
							return ResponseUtils.createErrorResponseBodyJiem("用户名或密码错误");
						}
	
					} else {
						return ResponseUtils.createErrorResponseBodyJiem("密码不正确");
					}
				}else {
					return ResponseUtils.createErrorResponseBodyJiem("用户不存在");
				}
			} else {
				return ResponseUtils.createErrorResponseBodyJiem("验证码错误");
			}
		return ResponseUtils.createSuccessResponseBodyForJiem("登陆成功", adminInfo_init);
	}



	


	}
