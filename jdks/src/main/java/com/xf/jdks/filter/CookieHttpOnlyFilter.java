package com.xf.jdks.filter;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import com.xf.jdks.dao.entity.AdminInfo;
import com.xf.jdks.dao.entity.UserInfo;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by RenJun on 2017/4/26.
 * 过滤器:
 * 1、解决安全性问题：跨站点请求伪造
 * 2、解决安全性问题：会话cookie中缺少HttpOnly属性
 *
 */
public class CookieHttpOnlyFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String reqUrl = req.getRequestURI();

        //判断是否登录
//        if (reqUrl.contains(".jsp")&&!reqUrl.contains("/login.jsp")&&!reqUrl.contains("automatic.jsp") && !reqUrl.contains("check.jsp")){
//			HttpSession session =  ((HttpServletRequest) request).getSession();
//			if (null!=session) {
//				AdminInfo adminInfo = (AdminInfo) session.getAttribute("adminInfo");
//				UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
//				if (null!=adminInfo||null!=userInfo) {
//					//校验当前权限
//	
//				}else{
//					((HttpServletResponse) response).sendRedirect("/jdks/ksxt/login.jsp");
////					request.getRequestDispatcher("/ksxt/userlogin.jsp").forward(request, response);
//					
//				}
//			}
//        }
        
//        // 解决安全性问题：跨站点请求伪造
//        String referer = req.getHeader("Referer");//获取referer
//        String serverName = request.getServerName();//获取server名称
//        if (!reqUrl.contains("/manage/login") && !reqUrl.contains("check.jsp") && null != referer && referer.indexOf(serverName) < 0) {
//
//            request.setAttribute("errorMsg", "该操作试图跨站点请求伪造！");
//            request.getRequestDispatcher("/referer.jsp").forward(request,response);
//
//        }else{
//
            // 解决会话cookie中缺少HttpOnly属性
            Cookie[] cookies = req.getCookies();
            if (!reqUrl.contains("automatic.jsp") && !reqUrl.contains("/admin/adminLogin")&& !reqUrl.contains("/user/userLogin") && !reqUrl.contains("check.jsp")) {
                if (cookies != null) {
                    for (Cookie cookie : cookies) {
                        //tomcat7及以上版本支持该属性，tomcat6不支持
                        String value = cookie.getValue();
                        StringBuilder builder = new StringBuilder();
                        builder.append("JSESSIONID=" + value + "; ");
                        builder.append("Secure; ");
                        builder.append("HttpOnly; ");
                        Calendar cal = Calendar.getInstance();
                        cal.add(Calendar.HOUR, 1);
                        Date date = cal.getTime();
                        Locale locale = Locale.CHINA;
                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", locale);
                        builder.append("Expires=" + sdf.format(date));
                        resp.setHeader("Set-Cookie", builder.toString());
                    }
                }

            }
            chain.doFilter(request, response);
//        }
    }

    @Override
    public void destroy() {

    }
}
