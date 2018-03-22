package com.xf.jdks.dao.pojo;

import com.xf.jdks.commons.global.StaticCaches;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Rio-Lee on 2016/7/21.
 * 用户信息结构（保存于Session中）
 */
public class LoginInfo {

    private String id;

    private String username;

    private String realname;

    private String pwd;

    private String pwdmd;
    
    private String roleid;

    private String modifyPassWordTime;//记录修改密码时间

    private String currentURL;
    
    private String clientHost;
    
    
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getPwdmd() {
		return pwdmd;
	}

	public void setPwdmd(String pwdmd) {
		this.pwdmd = pwdmd;
	}

	public String getRoleid() {
		return roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

	public String getModifyPassWordTime() {
		return modifyPassWordTime;
	}

	public void setModifyPassWordTime(String modifyPassWordTime) {
		this.modifyPassWordTime = modifyPassWordTime;
	}

	public String getCurrentURL() {
		return currentURL;
	}

	public void setCurrentURL(String currentURL) {
		this.currentURL = currentURL;
	}

	public String getClientHost() {
		return clientHost;
	}

	public void setClientHost(String clientHost) {
		this.clientHost = clientHost;
	}
    
    
    
    
    
    }
