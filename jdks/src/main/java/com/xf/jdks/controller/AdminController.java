package com.xf.jdks.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSONObject;
import com.xf.jdks.commons.util.ResponseUtils;
import com.xf.jdks.commons.util.RuleValidator;
import com.xf.jdks.dao.entity.AdminInfo;
import com.xf.jdks.service.AdminService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	String status="";
	String message="";

	@Autowired
	private AdminService adminService;
	
//	@ResponseBody
//	@RequestMapping(value = "/pageList", method = RequestMethod.POST)
//	public JSONObject pageList(@RequestBody AdminInfo adminInfo, HttpServletRequest request) {
//		return adminService.pageList(adminInfo);
//	}
	
	@ResponseBody
	@RequestMapping(value = "/pageList", method = RequestMethod.POST)
	public JSONObject pageList(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = ResponseUtils.createRequestAndSessionLoginInfo(request);
        JSONObject rs = ResponseUtils.createSuccessResponseBody(null);
        try {
            rs = adminService.pageList(map);
        } catch (Exception e) {
            e.printStackTrace();
            rs.put("message",e.getMessage());
        }
        return rs;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/searchOne", method = RequestMethod.POST)
	public JSONObject searchOne(HttpServletRequest request) {
        JSONObject rs = ResponseUtils.createSuccessResponseBody(null);
        Map<String, Object> params = ResponseUtils.createRequestAndSessionLoginInfo(request);
        try {
            rs = adminService.searchOne(params);
        } catch ( Exception e) {
            e.printStackTrace();
            rs.put("message", e.getMessage());
        }
        return rs;
	}
	
	@ResponseBody
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public JSONObject add(HttpServletRequest request) {
        JSONObject rs = ResponseUtils.createSuccessResponseBody(null);
        Map<String, Object> params = ResponseUtils.createRequestAndSessionLoginInfo(request);
        try {
            rs = adminService.add(params);
        } catch ( Exception e) {
            e.printStackTrace();
            rs.put("message", e.getMessage());
        }
        return rs;
	}
	
	@ResponseBody
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public JSONObject edit(HttpServletRequest request) {
        JSONObject rs = ResponseUtils.createSuccessResponseBody(null);
        Map<String, Object> params = ResponseUtils.createRequestAndSessionLoginInfo(request);
        try {
            rs = adminService.edit(params);
        } catch ( Exception e) {
            e.printStackTrace();
            rs.put("message", e.getMessage());
        }
        return rs;
	}
	

	@ResponseBody
	@RequestMapping(value = "/deleted", method = RequestMethod.POST)
	public JSONObject deleted(HttpServletRequest request) {
        JSONObject rs = ResponseUtils.createSuccessResponseBody(null);
        Map<String, Object> params = ResponseUtils.createRequestAndSessionLoginInfo(request);
        try {
            rs = adminService.deleted(params);
        } catch ( Exception e) {
            e.printStackTrace();
            rs.put("message", e.getMessage());
        }
        return rs;
	}
	
	@ResponseBody
	@RequestMapping(value = "/wulideleted", method = RequestMethod.POST)
	public JSONObject wulideleted(HttpServletRequest request) {
        JSONObject rs = ResponseUtils.createSuccessResponseBody(null);
        Map<String, Object> params = ResponseUtils.createRequestAndSessionLoginInfo(request);
        try {
            rs = adminService.wulideleted(params);
        } catch ( Exception e) {
            e.printStackTrace();
            rs.put("message", e.getMessage());
        }
        return rs;
	}
	
		

	@RequestMapping(value = "/adminLogin", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject adminLogin(HttpServletRequest request,
			HttpServletResponse resp) {
        JSONObject rs = ResponseUtils.createSuccessResponseBody(null);
        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(60*60*24);//24小时//以秒为单位
        try {
    		Map<String, Object> map = ResponseUtils.createRequestParamsMap(request);
            rs = adminService.adminLogin(map,request);
        } catch (Exception e) {
            e.printStackTrace();
            rs.put("message", e.getMessage());
        }
			return rs;
	 
	}
	
	@RequestMapping(value = "/reset", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject reset(HttpServletRequest request,
			HttpServletResponse resp) {
        JSONObject rs = ResponseUtils.createSuccessResponseBody(null);
        Map<String, Object> params = ResponseUtils.createRequestAndSessionLoginInfo(request);
        try {
            rs = adminService.reset(params);
        } catch ( Exception e) {
            e.printStackTrace();
            rs.put("message", e.getMessage());
        }
        return rs;
	 
	}

}
	
