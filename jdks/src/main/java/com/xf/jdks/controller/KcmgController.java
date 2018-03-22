package com.xf.jdks.controller;

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
import com.xf.jdks.dao.entity.AdminInfo;
import com.xf.jdks.dao.entity.KcInfo;
import com.xf.jdks.dao.entity.KcKsccInfo;
import com.xf.jdks.dao.entity.KcKwyInfo;
import com.xf.jdks.dao.entity.UserInfo;
import com.xf.jdks.dao.entity.VwUserKscc;
import com.xf.jdks.service.KcmgService;
import com.xf.jdks.service.KsccService;

@Controller
@RequestMapping("/kcmg")
public class KcmgController {

	@Autowired
	private KcmgService kcmgService;
	
	@ResponseBody
	@RequestMapping(value = "/pageList", method = RequestMethod.POST)
	public JSONObject pageList(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = ResponseUtils.createRequestAndSessionLoginInfo(request);
        JSONObject rs = ResponseUtils.createErrorResponseBody(null);
        try {
            rs = kcmgService.pageList(map);
        } catch (Exception e) {
            e.printStackTrace();
            rs.put("message",e.getMessage());
        }
        return rs;
	}
	
	@ResponseBody
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public JSONObject add(HttpServletRequest request) {
        JSONObject rs = ResponseUtils.createErrorResponseBody(null);
        Map<String, Object> params = ResponseUtils.createRequestAndSessionLoginInfo(request);
        try {
            rs = kcmgService.add(params);
        } catch ( Exception e) {
            e.printStackTrace();
            rs.put("message", e.getMessage());
        }
        return rs;
	}
	
	@ResponseBody
	@RequestMapping(value = "/deleted", method = RequestMethod.POST)
	public JSONObject deleted(HttpServletRequest request) {
        JSONObject rs = ResponseUtils.createErrorResponseBody(null);
        Map<String, Object> params = ResponseUtils.createRequestAndSessionLoginInfo(request);
        try {
            rs = kcmgService.deleted(params);
        } catch ( Exception e) {
            e.printStackTrace();
            rs.put("message", e.getMessage());
        }
        return rs;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/wulideleted", method = RequestMethod.POST)
	public JSONObject wulideleted(HttpServletRequest request) {
        JSONObject rs = ResponseUtils.createErrorResponseBody(null);
        Map<String, Object> params = ResponseUtils.createRequestAndSessionLoginInfo(request);
        try {
            rs = kcmgService.wulideleted(params);
        } catch ( Exception e) {
            e.printStackTrace();
            rs.put("message", e.getMessage());
        }
        return rs;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public JSONObject edit(HttpServletRequest request) {
        JSONObject rs = ResponseUtils.createErrorResponseBody(null);
        Map<String, Object> params = ResponseUtils.createRequestAndSessionLoginInfo(request);
        try {
            rs = kcmgService.edit(params);
        } catch ( Exception e) {
            e.printStackTrace();
            rs.put("message", e.getMessage());
        }
        return rs;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/searchOne", method = RequestMethod.POST)
	public JSONObject searchOne(HttpServletRequest request) {
        JSONObject rs = ResponseUtils.createErrorResponseBody(null);
        Map<String, Object> params = ResponseUtils.createRequestAndSessionLoginInfo(request);
        try {
            rs = kcmgService.searchOne(params);
        } catch ( Exception e) {
            e.printStackTrace();
            rs.put("message", e.getMessage());
        }
        return rs;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/listKwy", method = RequestMethod.POST)
	public JSONObject listKwy(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = ResponseUtils.createRequestAndSessionLoginInfo(request);
        JSONObject rs = ResponseUtils.createSuccessResponseBody(null);
        try {
            rs = kcmgService.listKwy(map);
        } catch (Exception e) {
            e.printStackTrace();
            rs.put("message",e.getMessage());
        }
        return rs;
	}

	@ResponseBody
	@RequestMapping(value = "/addKwy", method = RequestMethod.POST)
	public JSONObject addKwy(HttpServletRequest request) {
        JSONObject rs = ResponseUtils.createErrorResponseBody(null);
        Map<String, Object> params = ResponseUtils.createRequestAndSessionLoginInfo(request);
        try {
            rs = kcmgService.addKwy(params);
        } catch ( Exception e) {
            e.printStackTrace();
            rs.put("message", e.getMessage());
        }
        return rs;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/yxpageList", method = RequestMethod.POST)
	public JSONObject yxpageList(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = ResponseUtils.createRequestAndSessionLoginInfo(request);
        JSONObject rs = ResponseUtils.createErrorResponseBody(null);
        try {
            rs = kcmgService.yxpageList(map);
        } catch (Exception e) {
            e.printStackTrace();
            rs.put("message",e.getMessage());
        }
        return rs;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/pageListKcKwy", method = RequestMethod.POST)
	public JSONObject pageListKcKwy(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = ResponseUtils.createRequestAndSessionLoginInfo(request);
        JSONObject rs = ResponseUtils.createErrorResponseBody(null);
        try {
            rs = kcmgService.pageListKcKwy(map);
        } catch (Exception e) {
            e.printStackTrace();
            rs.put("message",e.getMessage());
        }
        return rs;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/deletedKwy", method = RequestMethod.POST)
	public JSONObject deletedKwy(HttpServletRequest request) {
        JSONObject rs = ResponseUtils.createErrorResponseBody(null);
        Map<String, Object> params = ResponseUtils.createRequestAndSessionLoginInfo(request);
        try {
            rs = kcmgService.deletedKwy(params);
        } catch ( Exception e) {
            e.printStackTrace();
            rs.put("message", e.getMessage());
        }
        return rs;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/kskcfp", method = RequestMethod.POST)
	public JSONObject kskcfp(HttpServletRequest request) {
        JSONObject rs = ResponseUtils.createErrorResponseBody(null);
        Map<String, Object> params = ResponseUtils.createRequestAndSessionLoginInfo(request);
        try {
            rs = kcmgService.kskcfp(params);
        } catch ( Exception e) {
            e.printStackTrace();
            rs.put("message", e.getMessage());
        }
        return rs;
	}	
	
	@ResponseBody
	@RequestMapping(value = "/createKSTZD", method = RequestMethod.POST)
	public JSONObject createKSTZD(HttpServletRequest request) {
        JSONObject rs = ResponseUtils.createErrorResponseBody(null);
        Map<String, Object> params = ResponseUtils.createRequestAndSessionLoginInfo(request);
        try {
            rs = kcmgService.createKSTZD(params);
        } catch ( Exception e) {
            e.printStackTrace();
            rs.put("message", e.getMessage());
        }
        return rs;
	}	

}
