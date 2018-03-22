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
import com.xf.jdks.service.KscjService;
import com.xf.jdks.service.UserService;
import com.xf.jdks.service.XyService;


@Controller
@RequestMapping("/kscj")
public class KscjController {
	String status="";
	String message="";
	
	@Autowired
	private KscjService kscjService;
	
    @Resource
    private FileIOComponent fileIOComponent;
	
	
	@ResponseBody
	@RequestMapping(value = "/pageList", method = RequestMethod.POST)
	public JSONObject pageList(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = ResponseUtils.createRequestAndSessionLoginInfo(request);
        JSONObject rs = ResponseUtils.createErrorResponseBody(null);
        try {
            rs = kscjService.pageList(map);
        } catch (Exception e) {
            e.printStackTrace();
            rs.put("message",e.getMessage());
        }
        return rs;
	}
	
	
	
	@ResponseBody
	@RequestMapping(value = "/cjhc", method = RequestMethod.POST)
	public JSONObject wulideleted(HttpServletRequest request) {
        JSONObject rs = ResponseUtils.createSuccessResponseBody(null);
        Map<String, Object> params = ResponseUtils.createRequestAndSessionLoginInfo(request);
        try {
            rs = kscjService.cjhc(params);
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
            rs = kscjService.edit(params);
        } catch ( Exception e) {
            e.printStackTrace();
            rs.put("message", e.getMessage());
        }
        return rs;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/searchOne", method = RequestMethod.POST)
	public JSONObject searchOne(HttpServletRequest request) {
        JSONObject rs = ResponseUtils.createSuccessResponseBody(null);
        Map<String, Object> params = ResponseUtils.createRequestAndSessionLoginInfo(request);
        try {
            rs = kscjService.searchOne(params);
        } catch ( Exception e) {
            e.printStackTrace();
            rs.put("message", e.getMessage());
        }
        return rs;
	}
	
	
    @RequestMapping(value = "/impKSCJ", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject impKSCJ(@RequestParam(value = "upfile_attachment", required = false) MultipartFile file,
                                        HttpServletRequest request) {
        Map<String,Object> map = new DataMap<>();
        JSONObject rs = ResponseUtils.createErrorResponseBody(null);
        try {
            FileInfoPo fileInfoPo = FileIOComponent.createTempFilePathByType(FileIOComponent.IMPORT_TYPE,"xlsx");
            file.transferTo(fileInfoPo.getFileObject());
            map.put("url", fileInfoPo.getFileAbsPath());//地址
            AdminInfo adminInfo = (AdminInfo) request.getSession().getAttribute("adminInfo");
            map.put("adminInfo",adminInfo);
            rs = kscjService.impKSCJ(map);
        } catch (Exception e) {
            e.printStackTrace();
            rs.put("message",e.getMessage());
        }
        return rs;
    }
	
    //导出成绩
    @RequestMapping(value = "/expKSCJ", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject expKSCJ(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = ResponseUtils.createRequestAndSessionLoginInfo(request);
        JSONObject rs = ResponseUtils.createErrorResponseBody(null);
        try {
            rs = kscjService.expKSCJ(map);
        } catch (Exception e) {
            e.printStackTrace();
            rs.put("message", e.getMessage());
        }
        return rs;
    }
    
    //导出通知单
    @RequestMapping(value = "/expKSTZD", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject expKSTZD(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = ResponseUtils.createRequestAndSessionLoginInfo(request);
        JSONObject rs = ResponseUtils.createErrorResponseBody(null);
        try {
            rs = kscjService.expKSTZD(map);
        } catch (Exception e) {
            e.printStackTrace();
            rs.put("message", e.getMessage());
        }
        return rs;
    }
    
}
