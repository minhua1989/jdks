package com.xf.jdks.commons.util;



public class Normal {
	
	public static final String getSexname(String status){
		String rtStatus="";
		if(status.equals("1")){
			rtStatus="男";
		}else if(status.equals("2")){
			rtStatus="女";
		}
		return rtStatus;
	}
	
	public static final String getRolename(String status){
		String rtStatus="";
		if(status.equals("0")){
			rtStatus="系统管理员";
		}else if(status.equals("2")){
			rtStatus="教师";
		}
		else if(status.equals("3")){
			rtStatus="出卷人员";
		}
		return rtStatus;
	}
	
	
	
	public static final String getUsed(String status){
		String rtStatus="";
		if(status.equals("0")){
			rtStatus="有效";
		}else if(status.equals("1")){
			rtStatus="注销";
		}
		return rtStatus;
	}
	
}
