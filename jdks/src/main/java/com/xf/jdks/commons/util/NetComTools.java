package com.xf.jdks.commons.util;
/**
 * 如果需要更新通讯方式，则需要修改这个方法：换掉所有的编码和解码器
 * */
public class NetComTools {

	private int defaultSafeGdLen= 40960;
	
	public  String sendMsg(String msg,int expInfoLen){
		String res="编码失败";
		res=Base64tool.iencodeB64(msg, expInfoLen);
		return res;
	}
	public  String recMsg(String msg,int expInfoLen){
		String res="解码失败";
		res=Base64tool.idecodeB64(msg, expInfoLen);
		return res;
	}
	public  String sendMsg(String msg){
		String res="编码失败";
		res=Base64tool.iencodeB64(msg, defaultSafeGdLen);
		return res;
	}
	public  String recMsg(String msg){
		String res="解码失败";
		res=Base64tool.idecodeB64(msg, defaultSafeGdLen);
		return res;
	}
	
	
	public  String sendKey(String key){//加密
		String res="编码失败";
		res=Base64tool.iencodeB64(key);
		return res;
	}
	public  String recdKey(String key){//解密
		String res="解码失败";
		res=Base64tool.idecodeB64(key);
		return res;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		NetComTools testCom=new NetComTools();
		String test="10za!@#~$%^&><?hello汉字·氹";
		System.out.println(test);
		String msg=testCom.sendMsg(test);
		System.out.println(msg);
		String msg2=testCom.recMsg(msg);
		System.out.println(msg2);
	}

}
