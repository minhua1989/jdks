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
import com.xf.jdks.commons.componet.FileIOComponent;
import com.xf.jdks.commons.util.ResponseUtils;
import com.xf.jdks.dao.entity.AdminInfo;
import com.xf.jdks.dao.entity.Kscc;
import com.xf.jdks.dao.entity.Sttitle;
import com.xf.jdks.dao.entity.UserInfo;
import com.xf.jdks.dao.entity.VwUserKscc;
import com.xf.jdks.service.KsccService;
import com.xf.jdks.service.QuestionService;

@Controller
@RequestMapping("/kscc")
public class KsccConroller {
	@Autowired
	private KsccService ksccService;
	
	@Autowired
    private FileIOComponent fileIOComponent;
	
	
	@ResponseBody
	@RequestMapping(value = "/pageList", method = RequestMethod.POST)
	public JSONObject pageList(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = ResponseUtils.createRequestAndSessionLoginInfo(request);
        JSONObject rs = ResponseUtils.createErrorResponseBody(null);
        try {
            rs = ksccService.pageList(map);
        } catch (Exception e) {
            e.printStackTrace();
            rs.put("message",e.getMessage());
        }
        return rs;
	}
	
	@ResponseBody
	@RequestMapping(value = "/checkKsTime", method = RequestMethod.POST)
	public JSONObject checkKsTime(HttpServletRequest request) {
        JSONObject rs = ResponseUtils.createErrorResponseBody(null);
        Map<String, Object> params = ResponseUtils.createRequestAndSessionLoginInfo(request);
        try {
            rs = ksccService.checkKsTime(params);
        } catch ( Exception e) {
            e.printStackTrace();
            rs.put("message", e.getMessage());
        }
        return rs;
	}
	
	//加载考生试卷
	@ResponseBody
	@RequestMapping(value = "/loadKssjxx", method = RequestMethod.POST)
	public JSONObject loadKssjxx(HttpServletRequest request) {
        JSONObject rs = ResponseUtils.createErrorResponseBody(null);
        Map<String, Object> params = ResponseUtils.createRequestAndSessionLoginInfo(request);
        try {
            rs = ksccService.loadKssjxx(params);
        } catch ( Exception e) {
            e.printStackTrace();
            rs.put("message", e.getMessage());
        }
        return rs;
	}
	
	//查看考生试卷
	@ResponseBody
	@RequestMapping(value = "/loadKssjxxAdmin", method = RequestMethod.POST)
	public JSONObject loadKssjxxAdmin(HttpServletRequest request) {
        JSONObject rs = ResponseUtils.createErrorResponseBody(null);
        Map<String, Object> params = ResponseUtils.createRequestAndSessionLoginInfo(request);
        try {
            rs = ksccService.loadKssjxxAdmin(params);
        } catch ( Exception e) {
            e.printStackTrace();
            rs.put("message", e.getMessage());
        }
        return rs;
	}
	
	@ResponseBody
	@RequestMapping(value = "/createKssj", method = RequestMethod.POST)
	public JSONObject createKssj(HttpServletRequest request) {
        JSONObject rs = ResponseUtils.createErrorResponseBody(null);
        Map<String, Object> params = ResponseUtils.createRequestAndSessionLoginInfo(request);
        try {
            rs = ksccService.createKssj(params);
        } catch ( Exception e) {
            e.printStackTrace();
            rs.put("message", e.getMessage());
        }
        return rs;
	}
	
	@ResponseBody
	@RequestMapping(value = "/saveKssjda", method = RequestMethod.POST)
	public JSONObject saveKssjda(HttpServletRequest request) {
        JSONObject rs = ResponseUtils.createErrorResponseBody(null);
        Map<String, Object> params = ResponseUtils.createRequestAndSessionLoginInfo(request);
        try {
            rs = ksccService.saveKssjda(params);
        } catch ( Exception e) {
            e.printStackTrace();
            rs.put("message", e.getMessage());
        }
        return rs;
	}

	@ResponseBody
	@RequestMapping(value = "/saveKssjdaEach", method = RequestMethod.POST)
	public JSONObject saveKssjdaEach(HttpServletRequest request) {
        JSONObject rs = ResponseUtils.createErrorResponseBody(null);
        Map<String, Object> params = ResponseUtils.createRequestAndSessionLoginInfo(request);
        try {
            rs = ksccService.saveKssjdaEach(params);
        } catch ( Exception e) {
            e.printStackTrace();
            rs.put("message", e.getMessage());
        }
        return rs;
	}
	
	@ResponseBody
	@RequestMapping(value = "/queryKssjxxLog", method = RequestMethod.POST)
	public JSONObject queryKssjxxLog(HttpServletRequest request) {
        JSONObject rs = ResponseUtils.createErrorResponseBody(null);
        Map<String, Object> params = ResponseUtils.createRequestAndSessionLoginInfo(request);
        try {
            rs = ksccService.queryKssjxxLog(params);
        } catch ( Exception e) {
            e.printStackTrace();
            rs.put("message", e.getMessage());
        }
        return rs;
	}
	
	@ResponseBody
	@RequestMapping(value = "/updatesfdl", method = RequestMethod.POST)
	public JSONObject updatesfdl(HttpServletRequest request) {
        JSONObject rs = ResponseUtils.createErrorResponseBody(null);
        Map<String, Object> params = ResponseUtils.createRequestAndSessionLoginInfo(request);
        try {
            rs = ksccService.updatesfdl(params);
        } catch ( Exception e) {
            e.printStackTrace();
            rs.put("message", e.getMessage());
        }
        return rs;
	}
	
}
