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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xf.jdks.commons.util.Encrypt;
import com.xf.jdks.commons.util.GetRequestJsonUtils;
import com.xf.jdks.commons.util.ResponseUtils;
import com.xf.jdks.commons.util.RuleValidator;
import com.xf.jdks.dao.entity.AdminInfo;
import com.xf.jdks.dao.pojo.LoginInfo;
import com.xf.jdks.service.AdminService;
import com.xf.jdks.service.BztableService;

@Controller
@RequestMapping("/bztable")
public class BztableConroller {
	String status="";
	String message="";

	@Autowired
	private BztableService bztableService;
	
	
	@ResponseBody
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public JSONObject list(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = ResponseUtils.createRequestAndSessionLoginInfo(request);
        JSONObject rs = ResponseUtils.createErrorResponseBody(null);
        try {
            rs = bztableService.list(map);
        } catch (Exception e) {
            e.printStackTrace();
            rs.put("message",e.getMessage());
        }
        return rs;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/pageList", method = RequestMethod.POST)
	public JSONObject pageList(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = ResponseUtils.createRequestAndSessionLoginInfo(request);
        JSONObject rs = ResponseUtils.createErrorResponseBody(null);
        try {
            rs = bztableService.pageList(map);
        } catch (Exception e) {
            e.printStackTrace();
            rs.put("message",e.getMessage());
        }
        return rs;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/searchOne", method = RequestMethod.POST)
	public JSONObject searchOne(HttpServletRequest request) {
        Map<String, Object> map = ResponseUtils.createRequestAndSessionLoginInfo(request);
        JSONObject rs = ResponseUtils.createErrorResponseBody(null);
        try {
            rs = bztableService.searchOne(map);
        } catch (Exception e) {
            e.printStackTrace();
            rs.put("message",e.getMessage());
        }
        return rs;
	}
	
	@ResponseBody
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public JSONObject add(HttpServletRequest request) {
        JSONObject rs = ResponseUtils.createSuccessResponseBody(null);
        Map<String, Object> params = ResponseUtils.createRequestAndSessionLoginInfo(request);
        try {
            rs = bztableService.add(params);
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
            rs = bztableService.edit(params);
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
            rs = bztableService.deleted(params);
        } catch ( Exception e) {
            e.printStackTrace();
            rs.put("message", e.getMessage());
        }
        return rs;
	}
	
	
	
	
	@ResponseBody
	@RequestMapping(value = "/sttilelist", method = RequestMethod.POST)
	public JSONObject sttilelist(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = ResponseUtils.createRequestAndSessionLoginInfo(request);
        JSONObject rs = ResponseUtils.createErrorResponseBody(null);
        try {
            rs = bztableService.sttilelist();
        } catch (Exception e) {
            e.printStackTrace();
            rs.put("message",e.getMessage());
        }
        return rs;
	}
	
	@ResponseBody
	@RequestMapping(value = "/stdalist", method = RequestMethod.POST)
	public JSONObject stdalist(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = ResponseUtils.createRequestAndSessionLoginInfo(request);
        JSONObject rs = ResponseUtils.createErrorResponseBody(null);
        try {
            rs = bztableService.stdalist();
        } catch (Exception e) {
            e.printStackTrace();
            rs.put("message",e.getMessage());
        }
        return rs;
	}
	
	@ResponseBody
	@RequestMapping(value = "/sydjlist", method = RequestMethod.POST)
	public JSONObject sydjlist(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = ResponseUtils.createRequestAndSessionLoginInfo(request);
        JSONObject rs = ResponseUtils.createErrorResponseBody(null);
        try {
            rs = bztableService.sydjlist();
        } catch (Exception e) {
            e.printStackTrace();
            rs.put("message",e.getMessage());
        }
        return rs;
	}
	

}
	
