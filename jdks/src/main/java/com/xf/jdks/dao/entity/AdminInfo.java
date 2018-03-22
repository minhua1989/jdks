package com.xf.jdks.dao.entity;

import com.github.pagehelper.PageInfo;
import com.xf.jdks.commons.enums.EStatus;

public class AdminInfo {

	private String id;
	private String ename;
	private String pwd;
	private String realname;
	private String lxdh;
	private String logincnt;
	private String logintime;
	private String lastlogintime;
	private String addtime;
	private String adduserid;
	private String updatetime;
	private String used;
	private String deltime;
	private String remark;
	private String pwdmd;
	private String email;
	private String roleid;
	private String sex;
	private String sexname;
	private String rolename;
	private String usedname;
	private String deleted;
	private String zjlx;
	private String sfzjhm;
	 private String ip;
	
	
	public String getZjlx() {
		return zjlx;
	}
	public void setZjlx(String zjlx) {
		this.zjlx = zjlx;
	}
	public String getSfzjhm() {
		return sfzjhm;
	}
	public void setSfzjhm(String sfzjhm) {
		this.sfzjhm = sfzjhm;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	private PageInfo<AdminInfo> pageInfo;

	
	public String getDeleted() {
		return deleted;
	}
	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEname() {
		return ename;
	}
	public void setEname(String ename) {
		this.ename = ename;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public String getLxdh() {
		return lxdh;
	}
	public void setLxdh(String lxdh) {
		this.lxdh = lxdh;
	}
	public String getLogincnt() {
		return logincnt;
	}
	public void setLogincnt(String logincnt) {
		this.logincnt = logincnt;
	}
	public String getLogintime() {
		return logintime;
	}
	public void setLogintime(String logintime) {
		this.logintime = logintime;
	}
	public String getLastlogintime() {
		return lastlogintime;
	}
	public void setLastlogintime(String lastlogintime) {
		this.lastlogintime = lastlogintime;
	}
	public String getAddtime() {
		return addtime;
	}
	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}
	public String getAdduserid() {
		return adduserid;
	}
	public void setAdduserid(String adduserid) {
		this.adduserid = adduserid;
	}
	public String getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}
	
	
	public String getDeltime() {
		return deltime;
	}
	public void setDeltime(String deltime) {
		this.deltime = deltime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getPwdmd() {
		return pwdmd;
	}
	public void setPwdmd(String pwdmd) {
		this.pwdmd = pwdmd;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public PageInfo<AdminInfo> getPageInfo() {
		return pageInfo;
	}
	public void setPageInfo(PageInfo<AdminInfo> pageInfo) {
		this.pageInfo = pageInfo;
	}
	public String getUsed() {
		return used;
	}
	public void setUsed(String used) {
		this.used = used;
	}
	public String getRoleid() {
		return roleid;
	}
	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getSexname() {
		return sexname;
	}
	public void setSexname(String sexname) {
		this.sexname = sexname;
	}
	public String getRolename() {
		return rolename;
	}
	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
	public String getUsedname() {
		return usedname;
	}
	public void setUsedname(String usedname) {
		this.usedname = usedname;
	}
	
	
	
	

}
