package com.xf.jdks.commons.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xf.jdks.commons.global.DataMap;
import com.xf.jdks.commons.global.StaticCaches;
import com.xf.jdks.dao.entity.AdminInfo;
import com.xf.jdks.dao.entity.UserInfo;
import com.xf.jdks.dao.pojo.LoginInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Created by Rio-Lee on 2016/6/14.
 * 返回报文构建工具
 */
public class ResponseUtils {

    public static List<Map<String, Object>> getPostParamsByJsonStringForArray(HttpServletRequest request) throws IOException {
        int contentLength = request.getContentLength();
        if (contentLength == 0) return null;
        byte[] buffer = new byte[contentLength];
        for (int i = 0; i < contentLength; ) {
            int readlen = request.getInputStream().read(buffer, i,
                    contentLength - i);
            if (readlen == -1) {
                break;
            }
            i += readlen;
        }
        String charEncoding = request.getCharacterEncoding();
        if (charEncoding == null) {
            charEncoding = "UTF-8";
        }
        String json = new String(buffer, charEncoding);
        JSONArray jsonArray = JSON.parseArray(json);
        List<Map<String, Object>> rs = new ArrayList<>();
        for (int i = 0, len = jsonArray.size(); i < len; i++) {
            JSONObject job = jsonArray.getJSONObject(i);
            Map<String, Object> data = new DataMap<>();
            for (Map.Entry<String, Object> entry : job.entrySet()) {
                data.put(entry.getKey(), entry.getValue());
            }
            rs.add(data);
        }
        return rs;
    }

    public static Map<String, Object> getPostParamsByJsonString(HttpServletRequest request) throws IOException {
        int contentLength = request.getContentLength();
        if (contentLength == 0) return null;
        byte[] buffer = new byte[contentLength];
        for (int i = 0; i < contentLength; ) {
            int readlen = request.getInputStream().read(buffer, i,contentLength - i);
            if (readlen == -1) {
                break;
            }
            i += readlen;
        }
        String charEncoding = request.getCharacterEncoding();
        if (charEncoding == null) {
            charEncoding = "UTF-8";
        }
        String json = new String(buffer, charEncoding);
        JSONObject jobj = JSONObject.parseObject(json);
        Map<String, Object> data = new DataMap<>();
        for (Map.Entry<String, Object> entry : jobj.entrySet()) {
            data.put(entry.getKey(), entry.getValue());
        }
        return data;
    }

    public static Map<String,Object> createRequestAndSessionLoginInfo(HttpServletRequest request){
        Map<String,Object> rs = createRequestParamsMap(request);
        UserInfo userInfo = (UserInfo) request.getSession().getAttribute("userInfo");
        if(userInfo!=null) {
            if(rs==null)rs = new DataMap<>();
            String uri = request.getRequestURI();
            uri = uri.replaceFirst("/jdks","");
            String ip = request.getHeader("x-forwarded-for");
            if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
                ip = request.getHeader("Proxy-Client-IP");
            }
            if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
                ip = request.getRemoteAddr();
            }
            ip= ip.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip;
            userInfo.setIp(ip);
            rs.put("userInfo",userInfo);
        }
        AdminInfo adminInfo = (AdminInfo) request.getSession().getAttribute("adminInfo");
        if(adminInfo!=null) {
            if(rs==null)rs = new DataMap<>();
            String uri = request.getRequestURI();
            uri = uri.replaceFirst("/jdks","");
            String ip = request.getHeader("x-forwarded-for");
            if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
                ip = request.getHeader("Proxy-Client-IP");
            }
            if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
                ip = request.getRemoteAddr();
            }
            ip= ip.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip;
            adminInfo.setIp(ip);
            rs.put("adminInfo",adminInfo);
        }
        return rs;
    }
    

    /**
     * @param request http请求
     * @return 将request对象中的参数返回，并且将Session中的用户所在区县，学校，校区一并封装{}
     */
    public static Map<String, Object> createRequestAndSessionParamsMap(HttpServletRequest request) {
        Map<String, Object> data = createRequestParamsMap(request);
        HttpSession session = request.getSession();
        LoginInfo userInfo = (LoginInfo) session.getAttribute("userinfo");
        if (userInfo != null) {
            assert data != null;
            data.put("s_userid", userInfo.getId());
        }
        return data;
    }

    /**
     * @param request http请求
     * @return 将request对象中的参数返回，并且将Session中的用户所在区县，学校，校区一并封装{s_districtid:区县ID,s_schoolid:学校ID,s_areaid:校区ID}
     */
    public static Map<String, Object> createRequestAndUserMap(HttpServletRequest request) {
        Map<String, Object> data = createRequestParamsMap(request);
        HttpSession session = request.getSession();
        LoginInfo userInfo = (LoginInfo) session.getAttribute("userinfo");
        if (userInfo != null) {
            assert data != null;
            data.put("s_userid", userInfo.getId());
            data.put("s_realname",userInfo.getRealname());
        }
        return data;
    }


    public static Map<String, Object> createRequestParamsMapForNoNull(HttpServletRequest request) {
        Map<String, Object> data = createRequestParamsMap(request);
        Map<String, Object> rs = new DataMap<>();
        for (Map.Entry<String,Object> entry:data.entrySet()) {
            if(entry.getValue()!=null&&!"".equals(entry.getValue().toString().trim()))rs.put(entry.getKey(),entry.getValue());
        }
        return rs;
    }

    /**
     * @param request Http请求对象
     * @return 将Request中的parameter取出来封装成Map返回
     */
    public static Map<String, Object> createRequestParamsMap(HttpServletRequest request) {
        if (!StaticCaches.POSTMAN_MODE) try {
            return getPostParamsByJsonString(request);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        Map<String, Object> rs = new DataMap<>();
        Enumeration<String> elmts = request.getParameterNames();
        while (elmts.hasMoreElements()) {
            String nextStr = elmts.nextElement();
            try {
                rs.put(nextStr.trim(), new String(request.getParameter(nextStr).getBytes("ISO-8859-1"), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return rs;
    }


    private static JSONObject createResponseBody(boolean isSuccess, String message, Object... datas) {
        JSONObject result = new JSONObject();
        result.put("status", isSuccess ? "success" : "error");
        result.put("message", message);
        if (datas != null && datas.length > 0) {
            if (datas.length == 1) {
                result.put("data", datas[0]);
            } else {
                List<Object> data = new ArrayList<>();
                Collections.addAll(data, datas);
                result.put("data", data);
            }
        }
        return result;
    }

    /**
     * @param message 需要传递的信息
     * @param datas   需要传递的数据（可以为多个）
     * @return 构建处理成功的Json对象
     */
    public static JSONObject createSuccessResponseBody(String message, Object... datas) {
        return createResponseBody(true, message, datas);
    }

    /**
     * @param datas 需要传递的数据（可以为多个）
     * @return 构建处理成功的Json对象
     */
    public static JSONObject createSuccessResponseBody(Object... datas) {
        return createSuccessResponseBody("处理成功", datas);
    }

    

    /**
     * 不加密的方法
     *
     * @param datas 需要传递的数据（可以为多个）
     * @return 构建处理成功的Json对象
     */
    public static JSONObject createSuccessResponseBodyForJiem(String message, Object... datas) {
        return createResponseBody(true, message, datas);
    }
    
    public static JSONObject createErrorResponseBody() {
        return createSuccessResponseBody("trace failed");
    }

    /**
     * @param message 需要传递的信息
     * @param datas   需要传递的数据（可以为多个）
     * @return 构建处理失败的Json对象
     */
    public static JSONObject createErrorResponseBody(String message, Object... datas) {
        return createResponseBody(false, message, datas);
    }
    
    

    /**
     * @param message 需要传递的信息
     * @param datas   需要传递的数据（可以为多个）
     * @return 构建处理失败的Json对象
     */
    public static JSONObject createErrorResponseBodyJiem(String message, Object... datas) {
        return createResponseBody(false, message,false, datas);
    }
}
