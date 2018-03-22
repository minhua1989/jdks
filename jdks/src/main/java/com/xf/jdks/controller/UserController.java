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
import com.xf.jdks.service.UserService;


@Controller
@RequestMapping("/user")
public class UserController {
	String status="";
	String message="";
	
	@Autowired
	private UserService userService;
	
    @Resource
    private FileIOComponent fileIOComponent;
	
	
	@ResponseBody
	@RequestMapping(value = "/pageList", method = RequestMethod.POST)
	public JSONObject pageList(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = ResponseUtils.createRequestAndSessionLoginInfo(request);
        JSONObject rs = ResponseUtils.createErrorResponseBody(null);
        try {
            rs = userService.pageList(map);
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
            rs = userService.searchOne(map);
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
            rs = userService.add(params);
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
            rs = userService.edit(params);
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
            rs = userService.deleted(params);
        } catch ( Exception e) {
            e.printStackTrace();
            rs.put("message", e.getMessage());
        }
        return rs;
	}
	

	@RequestMapping(value = "/userLogin", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject userLogin(HttpServletRequest request,
			HttpServletResponse resp) {
        JSONObject rs = ResponseUtils.createErrorResponseBody(null);
        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(60*60*24);//24小时//以秒为单位
        try {
    		Map<String, Object> map = ResponseUtils.createRequestParamsMap(request);
            rs = userService.userLogin(map,request);
        } catch (Exception e) {
            e.printStackTrace();
            rs.put("message", e.getMessage());
        }
			return rs;
	}
	
    @RequestMapping(value = "/importUsers", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject importUsers(@RequestParam(value = "upfile_attachment", required = false) MultipartFile file,
                                        HttpServletRequest request) {
        Map<String,Object> map = new DataMap<>();
        JSONObject rs = ResponseUtils.createErrorResponseBody(null);
        try {
            FileInfoPo fileInfoPo = FileIOComponent.createTempFilePathByType(FileIOComponent.IMPORT_TYPE,"xlsx");
            file.transferTo(fileInfoPo.getFileObject());
            map.put("url", fileInfoPo.getFileAbsPath());//地址
            AdminInfo adminInfo = (AdminInfo) request.getSession().getAttribute("adminInfo");
            map.put("adminInfo",adminInfo);
            rs = userService.importUsers(map);
        } catch (Exception e) {
            e.printStackTrace();
            rs.put("message",e.getMessage());
        }
        return rs;
    }
    
    @RequestMapping(value = "/expUsers", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject expUsers(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = ResponseUtils.createRequestAndSessionLoginInfo(request);
        JSONObject rs = ResponseUtils.createErrorResponseBody(null);
        try {
            rs = userService.expUsers(map);
        } catch (Exception e) {
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
            rs = userService.wulideleted(params);
        } catch ( Exception e) {
            e.printStackTrace();
            rs.put("message", e.getMessage());
        }
        return rs;
	}
	
	
	
	@RequestMapping(value = "/importxy", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject inportxy(HttpServletRequest request,
			HttpServletResponse resp) {
        JSONObject rs = ResponseUtils.createErrorResponseBody(null);
        try {
    		Map<String, Object> map = ResponseUtils.createRequestParamsMap(request);
            rs = userService.importxy(map,request);
        } catch (Exception e) {
            e.printStackTrace();
            rs.put("message", e.getMessage());
        }
			return rs;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/ksmdList", method = RequestMethod.POST)
	public JSONObject ksmdList(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = ResponseUtils.createRequestAndSessionLoginInfo(request);
        JSONObject rs = ResponseUtils.createErrorResponseBody(null);
        try {
            rs = userService.ksmdList(map);
        } catch (Exception e) {
            e.printStackTrace();
            rs.put("message",e.getMessage());
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
            rs = userService.reset(params);
        } catch ( Exception e) {
            e.printStackTrace();
            rs.put("message", e.getMessage());
        }
        return rs;
	 
	}
	
	
}
