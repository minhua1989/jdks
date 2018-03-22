package com.xf.jdks.controller;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.alibaba.fastjson.JSONObject;
import com.xf.jdks.commons.componet.FileIOComponent;
import com.xf.jdks.commons.global.DataMap;
import com.xf.jdks.commons.global.FileInfoPo;
import com.xf.jdks.commons.util.ResponseUtils;
import com.xf.jdks.commons.util.RuleValidator;
import com.xf.jdks.dao.entity.AdminInfo;
import com.xf.jdks.dao.entity.UserInfo;
import com.xf.jdks.service.PxpcService;
import com.xf.jdks.service.UserService;
import com.xf.jdks.service.XyService;


@Controller
@RequestMapping("/pxpc")
public class PxpcController {
	String status="";
	String message="";
	
	@Autowired
	private PxpcService pxpcService;
	
    @Resource
    private FileIOComponent fileIOComponent;
	
	
	@ResponseBody
	@RequestMapping(value = "/pageList", method = RequestMethod.POST)
	public JSONObject pageList(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = ResponseUtils.createRequestAndSessionLoginInfo(request);
        JSONObject rs = ResponseUtils.createErrorResponseBody(null);
        try {
            rs = pxpcService.pageList(map);
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
            rs = pxpcService.searchOne(map);
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
            rs = pxpcService.add(params);
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
            rs = pxpcService.edit(params);
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
            rs = pxpcService.deleted(params);
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
            rs = pxpcService.wulideleted(params);
        } catch ( Exception e) {
            e.printStackTrace();
            rs.put("message", e.getMessage());
        }
        return rs;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public JSONObject list(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = ResponseUtils.createRequestAndSessionLoginInfo(request);
        JSONObject rs = ResponseUtils.createErrorResponseBody(null);
        try {
            rs = pxpcService.list(map);
        } catch (Exception e) {
            e.printStackTrace();
            rs.put("message",e.getMessage());
        }
        return rs;
	}
	
}
