package com.xf.jdks.commons.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Base64tool {
	/**
	 * B64解码器
	 * 使用之前必须约定解编码格式
	 * 目前是UTF-8
	 * */
	public static String idecodeB64(String _info,int expInfoLen){
		String res="B64解码失败";
		if(null==_info||_info.length()>=expInfoLen){
			return res;
		}
		BASE64Decoder _b64dec=new BASE64Decoder();
		byte[]buffer=null;
		try {
			buffer=_b64dec.decodeBuffer(_info);
			if(null!=buffer){
				res=new String(buffer, "UTF-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
	

	
	
	
	/**
	 * B64编码器
	 * 使用之前必须约定解编码格式
	 * 目前是UTF-8
	 * */
	public static String iencodeB64(String _info,int expInfoLen){
		String res="B64编码失败";
		if(null==_info||_info.length()>=expInfoLen){
			return res;
		}
		BASE64Encoder _b64enc=new BASE64Encoder();
		byte[]buffer=null;
		try {
			buffer=_info.getBytes();
			if(buffer!=null){
				res=_b64enc.encode(buffer);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
	
	
	/**    
	     * BASE64解密   
	     * @param key          
	     * @return          
	     * @throws Exception          
	    */              
	public static String idecodeB64(String key){
		String res="B64解码失败";
		if(null==key){
			return res;
		}
		BASE64Decoder b64dec=new BASE64Decoder();
		byte[]buffer=null;
		try {
			buffer=b64dec.decodeBuffer(key);
			if(null!=buffer){
				res=new String(buffer, "UTF-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}          
	    /**         
	     * BASE64加密   
	     * @param key          
	     * @return          
	     * @throws Exception          
	     */              

	/**
	 * B64编码器
	 * 使用之前必须约定解编码格式
	 * 目前是UTF-8
	 * */
	public static String iencodeB64(String key){
		String res="B64编码失败";
		if(null==key){
			return res;
		}
		BASE64Encoder b64enc=new BASE64Encoder();
		byte[]buffer=null;
		try {
			buffer=key.getBytes();
			if(buffer!=null){
				res=b64enc.encode(buffer);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
	 
  

//	/**
//	 * @param args
//	 */
//	public static void main(String[] args) {
//		BASE64Encoder benc=new BASE64Encoder();
//		String test="10za!@#~$%^&><?hello汉字·氹";
//		String msg=iencodeB64(test, 50);
//		System.out.println(msg);
//		String res=idecodeB64(msg, 50);
//		System.out.println(test.equals(res));
//
//	}

}
