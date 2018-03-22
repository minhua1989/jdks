package com.xf.jdks.interceptor;


import com.alibaba.fastjson.JSONObject;
import com.xf.jdks.commons.componet.BaseDaoComponent;
import com.xf.jdks.commons.global.DataMap;
import com.xf.jdks.commons.global.StaticCaches;
import com.xf.jdks.commons.util.BaseChecks;
import com.xf.jdks.commons.util.Format;
import com.xf.jdks.dao.bean.InsertParams;
import com.xf.jdks.dao.entity.AdminInfo;
import com.xf.jdks.dao.entity.UserInfo;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Map;


@Component
public class SessionInterceptor extends HandlerInterceptorAdapter  {

	@Resource
	private BaseDaoComponent baseDaoComponent;

	private String tableName = "t_funcurlinfo_error";

	public String[] allowUrls;//还没发现可以直接配置不拦截的资源，所以在代码里面来排除

	public void setAllowUrls(String[] allowUrls) {
        this.allowUrls = allowUrls;
    }

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		super.afterCompletion(request, response, handler, ex);
	}


	@Override
	public void afterConcurrentHandlingStarted(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
	}


	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}
 

	@Override
public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String requestUrl = request.getRequestURI().replace(request.getContextPath(), "");
		if(null != allowUrls && allowUrls.length>=1)  
            for(String url : allowUrls) {    
                if(requestUrl.contains(url)) {    
                    return true;    
                }    
            }  
		HttpSession session =  request.getSession();
		AdminInfo adminInfo = (AdminInfo) session.getAttribute("adminInfo");
		UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");

		if (null!=session) {		
			if ((null!=adminInfo)||(null!=userInfo)) {
				//校验当前权限
				return true;
			}
		}
        JSONObject noSession = new JSONObject();
        noSession.put("status","nosession");
		noSession.put("message","您长时间没有操作,请重新登录");
		response.setContentType("text/html;charset=utf-8");
		response. setCharacterEncoding("UTF-8");
        PrintWriter pw = response.getWriter();
        pw.write(noSession.toJSONString());
        pw.flush();
        pw.close();
		return false;
		
	}

	
	
	
	
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
//	@Override
//	public boolean preHandle(HttpServletRequest request,
//			HttpServletResponse response, Object handler) throws Exception {
//		if(StaticCaches.getRealRootPath()==null) {
//			StaticCaches.setRealRootPath(request.getServletContext().getRealPath("/"));//设置根目录
//		}
//		String requestUrl = request.getRequestURI().replace(request.getContextPath(), "");
//
//		//获取session
//		LoginInfo loginInfo = (LoginInfo) request.getSession().getAttribute("userinfo");
//		String sRoleType = "", sUserid = "";
//		if(null != loginInfo){
//			sRoleType = loginInfo.getRoletype();
//			sUserid = loginInfo.getId();
//		}
//
//		if("/newStudentMgr/getNewStudentListTest".equals(requestUrl)){
//			return  true;
//		}
//		if("/webinfo/getWebinfo".equals(requestUrl)){
//			return  true;
//		}
//		if (null != allowUrls && allowUrls.length >= 1) {
//			for (String url : allowUrls) {
//				if (requestUrl.contains(url)) {
//					return true;
//				}
//			}
//		}
////
////		//过滤是否可以访问 向立军 2017年5月11日12:11:38
////		Map<String, Object> urlMap = StaticCaches.getAllFunctionRoleObject(requestUrl);
////		if(urlMap != null && !BaseChecks.hasNullObject(urlMap)) {
////			//接口是否禁用 0是正常 1是禁用
////			String status = (String) urlMap.get("isopen");
////			if("1".equals(status)) {
////                //插入记录
////                Map<String, Object> funcData = new HashMap<>();
////                funcData.put("id", UUID.randomUUID().toString());
////                funcData.put("url", requestUrl);
////                funcData.put("userid", sUserid);
////                funcData.put("roletype", sRoleType);
////                funcData.put("insert_time", Format.getDateTime());
////                funcData.put("errortype", "该接口已禁用");
////                insertFuncData(funcData);
////                //接口检测
////                JSONObject noSession = new JSONObject();
////				noSession.put("status","no-permission");
////				noSession.put("message","您没有当前页面的访问权限，请联系系统管理员");
////				response.setContentType("text/html;charset=utf-8");
////				response. setCharacterEncoding("UTF-8");
////				PrintWriter pw = response.getWriter();
////				pw.write(noSession.toJSONString());
////				pw.flush();
////				pw.close();
////				return false;
////			}
////			//过滤是否公开 0是公开 1是不公开 2017年5月11日12:51:02 向立军
////			String pub = (String) urlMap.get("ispublic");
////			if("0".equals(pub)) {
////				return true;
////			}
////		}else{
////			return true;
////		}
//
//		HttpSession session =  request.getSession();
//		String ip = request.getHeader("x-forwarded-for");
//		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
//			ip = request.getHeader("Proxy-Client-IP");
//		}
//		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
//			ip = request.getHeader("WL-Proxy-Client-IP");
//		}
//		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
//			ip = request.getRemoteAddr();
//		}
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
//		String time = sdf.format(new Date());
//		Map<String,Object> map = new DataMap<>();
//		map.put("loginip",ip);
//		map.put("lastlogintime",time);
//		InsertParams insertParams = InsertParams.createInsertParams("T_LOGIN_LOG");
//		insertParams.addParamsForMap(map);
//
//		if (urlMap != null && null!=session) {
//			LoginInfo adminInfo = (LoginInfo) session.getAttribute("userinfo");
//			if(adminInfo!=null) {
//				//过滤是否符合权限
//				String roleType = (String) urlMap.get("roletype");
//				if (null==roleType) {
//                    //插入记录
//                    Map<String, Object> funcData = new HashMap<>();
//                    funcData.put("id", UUID.randomUUID().toString());
//                    funcData.put("url", requestUrl);
//                    funcData.put("userid", sUserid);
//                    funcData.put("roletype", sRoleType);
//                    funcData.put("insert_time", Format.getDateTime());
//                    funcData.put("errortype", "接口权限不匹配");
//                    insertFuncData(funcData);
//                    //接口检测
//					JSONObject noSession = new JSONObject();
//					noSession.put("status","no-permission");
//					noSession.put("message","您没有当前页面的访问权限，请联系系统管理员");
////					response.setCharacterEncoding("UTF-8");
//					response.setContentType("text/html;charset=utf-8");
//					response. setCharacterEncoding("UTF-8");
//					PrintWriter pw = response.getWriter();
//					pw.write(noSession.toJSONString());
//					pw.flush();
//					pw.close();
//					return false;
//				}
//
//				if(null!=roleType) {
//					if(!roleType.contains(adminInfo.getRoletype())) {
//                        //插入记录
//                        Map<String, Object> funcData = new HashMap<>();
//                        funcData.put("id", UUID.randomUUID().toString());
//                        funcData.put("url", requestUrl);
//                        funcData.put("userid", sUserid);
//                        funcData.put("roletype", sRoleType);
//                        funcData.put("insert_time", Format.getDateTime());
//                        funcData.put("errortype", "接口权限不匹配");
//                        insertFuncData(funcData);
//                        //接口检测
//						JSONObject noSession = new JSONObject();
//						noSession.put("status","no-permission");
//						noSession.put("message","您没有当前页面的访问权限，请联系系统管理员");
////						response.setCharacterEncoding("UTF-8");
//						response.setContentType("text/html;charset=utf-8");
//						response. setCharacterEncoding("UTF-8");
//						PrintWriter pw = response.getWriter();
//						pw.write(noSession.toJSONString());
//						pw.flush();
//						pw.close();
//						return false;
//					}
//				}
//
//				Map<String,Object> map1 = new DataMap<>();
//				map1.put("LOGENAME", adminInfo.getRealname());
//				insertParams.addParamsForMap(map1);
//				//				baseDaoComponent.insertDataByParams(insertParams);
//				return true;
//			}
//		}
//
//        JSONObject noSession = new JSONObject();
//        noSession.put("status","nosession");
//		noSession.put("message","您长时间没有操作,请重新登录");
//		response.setContentType("text/html;charset=utf-8");
//		response. setCharacterEncoding("UTF-8");
//        PrintWriter pw = response.getWriter();
//        pw.write(noSession.toJSONString());
//        pw.flush();
//        pw.close();
////}
////		baseDaoComponent.insertDataByParams(insertParams);
////		request.getRequestDispatcher("/dist/p_pub/login.html").forward(request, response);
//		return false;
//	}

//    //插入数据
//    private void insertFuncData(Map<String,Object> params) throws SQLException {
//        InsertParams insertParams = InsertParams.createInsertParams(tableName);
//        insertParams.addParamsForMap(params);
//        baseDaoComponent.insertDataByParams(insertParams);
//    }

}
