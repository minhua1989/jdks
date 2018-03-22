package com.xf.jdks.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.xf.jdks.commons.util.ResponseUtils;
import com.xf.jdks.dao.bean.Parameter;
import com.xf.jdks.dao.bean.QueryParams;
import com.xf.jdks.dao.entity.AdminInfo;
import com.xf.jdks.dao.entity.KcKsccInfo;
import com.xf.jdks.dao.entity.KsccmgInfo;
import com.xf.jdks.dao.entity.T_kckscc_rel;
import com.xf.jdks.dao.entity.UserInfo;
import com.xf.jdks.service.KsccmgService;


@Controller
@RequestMapping("/ksccmg")
public class KsccmgController {
	String status="";
	String message="";

	@Autowired
	private KsccmgService ksccmgService;
	
	@ResponseBody
	@RequestMapping(value = "/pageList", method = RequestMethod.POST)
	public JSONObject pageList(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = ResponseUtils.createRequestAndSessionLoginInfo(request);
        JSONObject rs = ResponseUtils.createErrorResponseBody(null);
        try {
            rs = ksccmgService.pageList(map);
        } catch (Exception e) {
            e.printStackTrace();
            rs.put("message",e.getMessage());
        }
        return rs;
	}
	
	@ResponseBody
	@RequestMapping(value = "/pageListKcKscc", method = RequestMethod.POST)
	public JSONObject pageListKcKscc(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = ResponseUtils.createRequestAndSessionLoginInfo(request);
        JSONObject rs = ResponseUtils.createErrorResponseBody(null);
        try {
            rs = ksccmgService.pageListKcKscc(map);
        } catch (Exception e) {
            e.printStackTrace();
            rs.put("message",e.getMessage());
        }
        return rs;
	}
	
	@ResponseBody
	@RequestMapping(value = "/pageListKcKs", method = RequestMethod.POST)
	public JSONObject pageListKcKs(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = ResponseUtils.createRequestAndSessionLoginInfo(request);
        JSONObject rs = ResponseUtils.createErrorResponseBody(null);
        try {
            rs = ksccmgService.pageListKcKs(map);
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
            rs = ksccmgService.add(params);
        } catch ( Exception e) {
            e.printStackTrace();
            rs.put("message", e.getMessage());
        }
        return rs;
	}

	
	@ResponseBody
	@RequestMapping(value = "/addKcinfo", method = RequestMethod.POST)
	public JSONObject addKcinfo(HttpServletRequest request) {
        JSONObject rs = ResponseUtils.createSuccessResponseBody(null);
        Map<String, Object> params = ResponseUtils.createRequestAndSessionLoginInfo(request);
        try {
            rs = ksccmgService.addKcinfo(params);
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
            rs = ksccmgService.edit(params);
        } catch ( Exception e) {
            e.printStackTrace();
            rs.put("message", e.getMessage());
        }
        return rs;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/searchOne", method = RequestMethod.POST)
	public JSONObject searchOne(HttpServletRequest request) {
        Map<String, Object> map = ResponseUtils.createRequestAndSessionLoginInfo(request);
        JSONObject rs = ResponseUtils.createErrorResponseBody(null);
        try {
            rs = ksccmgService.searchOne(map);
        } catch (Exception e) {
            e.printStackTrace();
            rs.put("message",e.getMessage());
        }
        return rs;
	}
	
	@ResponseBody
	@RequestMapping(value = "/deleted", method = RequestMethod.POST)
	public JSONObject deleted(HttpServletRequest request) {
        JSONObject rs = ResponseUtils.createSuccessResponseBody(null);
        Map<String, Object> params = ResponseUtils.createRequestAndSessionLoginInfo(request);
        try {
            rs = ksccmgService.deleted(params);
        } catch ( Exception e) {
            e.printStackTrace();
            rs.put("message", e.getMessage());
        }
        return rs;
	}
	
	@ResponseBody
	@RequestMapping(value = "/deletedKcinfo", method = RequestMethod.POST)
	public JSONObject deletedKcinfo(HttpServletRequest request) {
        JSONObject rs = ResponseUtils.createSuccessResponseBody(null);
        Map<String, Object> params = ResponseUtils.createRequestAndSessionLoginInfo(request);
        try {
            rs = ksccmgService.deletedKcinfo(params);
        } catch ( Exception e) {
            e.printStackTrace();
            rs.put("message", e.getMessage());
        }
        return rs;
	}
	
	
	
	@ResponseBody
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public JSONObject list(HttpServletRequest request, HttpServletResponse response) {
        JSONObject rs = ResponseUtils.createErrorResponseBody(null);
        try {
            rs = ksccmgService.list();
        } catch (Exception e) {
            e.printStackTrace();
            rs.put("message",e.getMessage());
        }
        return rs;
	}	

	@ResponseBody
	@RequestMapping(value = "/listKcinfo", method = RequestMethod.POST)
	public JSONObject listKcinfo(HttpServletRequest request) {
        JSONObject rs = ResponseUtils.createSuccessResponseBody(null);
        Map<String, Object> params = ResponseUtils.createRequestAndSessionLoginInfo(request);
        try {
            rs = ksccmgService.listKcinfo(params);
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
            rs = ksccmgService.wulideleted(params);
        } catch ( Exception e) {
            e.printStackTrace();
            rs.put("message", e.getMessage());
        }
        return rs;
	}
	
	
	
	
	@ResponseBody
	@RequestMapping(value = "/kskcpageList", method = RequestMethod.POST)
	public JSONObject kskcpageList(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = ResponseUtils.createRequestAndSessionLoginInfo(request);
        JSONObject rs = ResponseUtils.createErrorResponseBody(null);
        try {
            rs = ksccmgService.kskcpageList(map);
        } catch (Exception e) {
            e.printStackTrace();
            rs.put("message",e.getMessage());
        }
        return rs;
	}
	
	
}

