package com.xf.jdks.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.xf.jdks.commons.util.ResponseUtils;
import com.xf.jdks.service.AdminService;
import com.xf.jdks.service.PermissionService;

@Controller
@RequestMapping("/permission")
public class PermissionController {

	@Autowired
	private PermissionService permissionService;
	
	//角色列表
	@ResponseBody
	@RequestMapping(value = "/pageRoleList", method = RequestMethod.POST)
	public JSONObject pageRoleList(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = ResponseUtils.createRequestAndSessionLoginInfo(request);
        JSONObject rs = ResponseUtils.createErrorResponseBody(null);
        try {
            rs = permissionService.pageRoleList(map);
        } catch (Exception e) {
            e.printStackTrace();
            rs.put("message",e.getMessage());
        }
        return rs;
	}
	
	//所有菜单
    @RequestMapping(value = "/queryAllMenus", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject queryAllMenus(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = ResponseUtils.createRequestAndSessionLoginInfo(request);
        JSONObject rs = ResponseUtils.createErrorResponseBody(null);
        try {
            rs = permissionService.queryAllMenus();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtils.createErrorResponseBody(e.getMessage());
        }
        return rs;
    }
    
    //该角色的所有菜单
    @RequestMapping(value = "/getMenuListByRoleId", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject getMenuListByRoleId(HttpServletRequest request) {
        // TODO: 根据角色ID获取菜单列表
        Map<String, Object> map = ResponseUtils.createRequestAndSessionLoginInfo(request);
        JSONObject rs = ResponseUtils.createErrorResponseBody(null);
        try {
            rs = permissionService.getMenuListByRoleId(map);
        } catch (Exception e) {
            e.printStackTrace();
            rs.put("message", e.getMessage());
        }
        return rs;
    }
    
    //修改角色菜单
    @RequestMapping(value = "/configRole", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject configRole(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = ResponseUtils.createRequestAndSessionLoginInfo(request);
        JSONObject rs = ResponseUtils.createErrorResponseBody(null);
        try {
            rs = permissionService.configRole(map);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtils.createErrorResponseBody(e.getMessage());
        }
        return rs;
    }
}


