package com.xf.jdks.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.xf.jdks.dao.entity.AdminInfo;
import com.xf.jdks.dao.entity.Sttitle;
import com.xf.jdks.dao.entity.UserInfo;
import com.xf.jdks.dao.pojo.LoginInfo;
import com.xf.jdks.exceptions.FileTypeNotFoundException;
import com.xf.jdks.service.QuestionService;


@Controller
@RequestMapping("/question")
public class QuestionController {

	@Autowired
	private QuestionService questionService;
	
	@Autowired
    private FileIOComponent fileIOComponent;
	
	
	@ResponseBody
	@RequestMapping(value = "/pageList", method = RequestMethod.POST)
	public JSONObject pageList(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = ResponseUtils.createRequestAndSessionLoginInfo(request);
        JSONObject rs = ResponseUtils.createErrorResponseBody(null);
        try {
            rs = questionService.pageList(map);
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
            rs = questionService.add(params);
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
            rs = questionService.edit(params);
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
            rs = questionService.searchOne(params);
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
            rs = questionService.deleted(params);
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
            rs = questionService.wulideleted(params);
        } catch ( Exception e) {
            e.printStackTrace();
            rs.put("message", e.getMessage());
        }
        return rs;
	}
	
	@ResponseBody
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public JSONObject uploadImag(@RequestParam MultipartFile file, HttpServletRequest request){
//		String webpath="/storage";//request.getContextPath();//web路径,tomcat下配置
//		String path="D:/storage/";//request.getSession().getServletContext().getRealPath("/");//服务器路径
//		return questionService.uploadImag(file,path,webpath);
        JSONObject rs = ResponseUtils.createErrorResponseBody(null);
        try {
            rs = questionService.uploadImag(file,1);
        } catch (Exception e) {
            e.printStackTrace();
            rs.put("message", e.getMessage());
        }
			return rs;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/addOptions", method = RequestMethod.POST)
	public JSONObject addOptions(HttpServletRequest request) {
        JSONObject rs = ResponseUtils.createSuccessResponseBody(null);
        Map<String, Object> params = ResponseUtils.createRequestAndSessionLoginInfo(request);
        try {
            rs = questionService.addOptions(params);
        } catch ( Exception e) {
            e.printStackTrace();
            rs.put("message", e.getMessage());
        }
        return rs;
	}
	
    @RequestMapping(value = "/importQuestions", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject importQuestions(@RequestParam(value = "upfile_attachment1", required = false) MultipartFile file,
                                        HttpServletRequest request) {
        Map<String,Object> map = new DataMap<>();
        JSONObject rs = ResponseUtils.createErrorResponseBody(null);
        try {
            FileInfoPo fileInfoPo = FileIOComponent.createTempFilePathByType(FileIOComponent.IMPORT_TYPE,"xlsx");
            file.transferTo(fileInfoPo.getFileObject());
            map.put("url", fileInfoPo.getFileAbsPath());//地址
            AdminInfo adminInfo = (AdminInfo) request.getSession().getAttribute("adminInfo");
            map.put("adminInfo",adminInfo);
            rs = questionService.importQuestions(map);
        } catch (Exception e) {
            e.printStackTrace();
            rs.put("message",e.getMessage());
        }
        return rs;
    }
    
    @RequestMapping(value = "/expQuestions", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject expQuestions(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = ResponseUtils.createRequestAndSessionLoginInfo(request);
        JSONObject rs = ResponseUtils.createErrorResponseBody(null);
        try {
            rs = questionService.expQuestions(map);
        } catch (Exception e) {
            e.printStackTrace();
            rs.put("message", e.getMessage());
        }
        return rs;
    }
    
    
    //知识点
	@ResponseBody
	@RequestMapping(value = "/listzsd", method = RequestMethod.POST)
	public JSONObject listzsd(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = ResponseUtils.createRequestAndSessionLoginInfo(request);
        JSONObject rs = ResponseUtils.createSuccessResponseBody(null);
        try {
            rs = questionService.listzsd(map);
        } catch (Exception e) {
            e.printStackTrace();
            rs.put("message",e.getMessage());
        }
        return rs;
	}
    
	  //知识点
		@ResponseBody
		@RequestMapping(value = "/listtk", method = RequestMethod.POST)
		public JSONObject listtk(HttpServletRequest request, HttpServletResponse response) {
	        Map<String, Object> map = ResponseUtils.createRequestAndSessionLoginInfo(request);
	        JSONObject rs = ResponseUtils.createSuccessResponseBody(null);
	        try {
	            rs = questionService.listtk(map);
	        } catch (Exception e) {
	            e.printStackTrace();
	            rs.put("message",e.getMessage());
	        }
	        return rs;
		}
		
		
		@ResponseBody
		@RequestMapping(value = "/pageListtk", method = RequestMethod.POST)
		public JSONObject pageListtk(HttpServletRequest request, HttpServletResponse response) {
	        Map<String, Object> map = ResponseUtils.createRequestAndSessionLoginInfo(request);
	        JSONObject rs = ResponseUtils.createSuccessResponseBody(null);
	        try {
	            rs = questionService.pageListtk(map);
	        } catch (Exception e) {
	            e.printStackTrace();
	            rs.put("message",e.getMessage());
	        }
	        return rs;
		}
		
		
		@ResponseBody
		@RequestMapping(value = "/addtk", method = RequestMethod.POST)
		public JSONObject addtk(HttpServletRequest request) {
	        JSONObject rs = ResponseUtils.createSuccessResponseBody(null);
	        Map<String, Object> params = ResponseUtils.createRequestAndSessionLoginInfo(request);
	        try {
	            rs = questionService.addtk(params);
	        } catch ( Exception e) {
	            e.printStackTrace();
	            rs.put("message", e.getMessage());
	        }
	        return rs;
		}

		@ResponseBody
		@RequestMapping(value = "/edittk", method = RequestMethod.POST)
		public JSONObject edittk(HttpServletRequest request) {
	        JSONObject rs = ResponseUtils.createSuccessResponseBody(null);
	        Map<String, Object> params = ResponseUtils.createRequestAndSessionLoginInfo(request);
	        try {
	            rs = questionService.edittk(params);
	        } catch ( Exception e) {
	            e.printStackTrace();
	            rs.put("message", e.getMessage());
	        }
	        return rs;
		}
		
		@ResponseBody
		@RequestMapping(value = "/searchOnetk", method = RequestMethod.POST)
		public JSONObject searchOnetk(HttpServletRequest request) {
	        JSONObject rs = ResponseUtils.createSuccessResponseBody(null);
	        Map<String, Object> params = ResponseUtils.createRequestAndSessionLoginInfo(request);
	        try {
	            rs = questionService.searchOnetk(params);
	        } catch ( Exception e) {
	            e.printStackTrace();
	            rs.put("message", e.getMessage());
	        }
	        return rs;
		}
		
    
}
