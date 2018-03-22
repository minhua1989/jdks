package com.xf.jdks.commons.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class GetRequestJsonUtils {

	private static String[] noPassUrl ={};
	/***
	 * 获取 request 中 json 字符串的内容
	 * 
	 * @param request
	 * @return : <code>byte[]</code>
	 * @throws IOException
	 */
	public static String getRequestJsonString(HttpServletRequest request) throws IOException {
		String submitMehtod = request.getMethod();
		// GET
		if (submitMehtod.equals("GET")) {
			return new String(request.getQueryString().getBytes("iso-8859-1"), "utf-8").replaceAll("%22", "\"");
			// POST
		} else {
			return getRequestPostStr(request);
		}
	}
	
	/**
     * @param request Http请求对象
     * @return 将Request中的parameter取出来封装成Map返回
     */
    public static Map<String, Object> createRequestParamsMap(HttpServletRequest request) {
        Map<String, Object> rs = new HashMap<>();
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

	/**
	 * 描述:获取 post 请求的 byte[] 数组
	 * 
	 * <pre>
	 * 举例：
	 * </pre>
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public static byte[] getRequestPostBytes(HttpServletRequest request) throws IOException {
		int contentLength = request.getContentLength();
		if (contentLength < 0) {
			return null;
		}
		byte buffer[] = new byte[contentLength];
		for (int i = 0; i < contentLength;) {

			int readlen = request.getInputStream().read(buffer, i, contentLength - i);
			if (readlen == -1) {
				break;
			}
			i += readlen;
		}
		return buffer;
	}

	/**
	 * 描述:获取 post 请求内容
	 * 
	 * <pre>
	 * 举例：
	 * </pre>
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public static String getRequestPostStr(HttpServletRequest request) throws IOException {
		byte buffer[] = getRequestPostBytes(request);
		String charEncoding = request.getCharacterEncoding();
		if (charEncoding == null) {
			charEncoding = "UTF-8";
		}
		String requestUrl = request.getRequestURI().replace(request.getContextPath(), "");
		for (String url : noPassUrl) {
			if (requestUrl.contains(url)) { // 过滤掉不需要解密的
				return new String(buffer, charEncoding);
			}
		}
		// 解密
		return Encrypt.jiem(new String(buffer, charEncoding));
	}

	public static String getRequestJsonString1(HttpServletRequest request) throws IOException {
		return getRequestPostStr1(request);
	}

	public static String getRequestPostStr1(HttpServletRequest request) throws IOException {
		byte buffer[] = getRequestPostBytes(request);
		String charEncoding = request.getCharacterEncoding();
		if (charEncoding == null) {
			charEncoding = "UTF-8";
		}
		String requestUrl = request.getRequestURI().replace(request.getContextPath(), "");
		for (String url : noPassUrl) {
			if (requestUrl.contains(url)) { // 过滤掉不需要解密的
				return new String(buffer, charEncoding);
			}
		}
		return new String(buffer, charEncoding);
	}
}
