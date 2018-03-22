package com.xf.jdks.dao.entity;

import com.github.pagehelper.PageInfo;
import com.xf.jdks.commons.enums.EStatus;

public class KwymgInfo {
   private String id;
   private String xm;
   private String sex;
   private String sfzjh;
   private String lxdh;
   private String used;
   private String type;
   private String csny;
   private String addtime;
   private String sexname;
   private String typename;
   private String usedname;
   private PageInfo<KwymgInfo> pageInfo;
   private String deleted;
   private String updatetime;
   private String deltime;
   
   


public String getDeltime() {
	return deltime;
}
public void setDeltime(String deltime) {
	this.deltime = deltime;
}
public String getUpdatetime() {
	return updatetime;
}
public void setUpdatetime(String updatetime) {
	this.updatetime = updatetime;
}
public String getDeleted() {
	return deleted;
}
public void setDeleted(String deleted) {
	this.deleted = deleted;
}
public PageInfo<KwymgInfo> getPageInfo() {
	return pageInfo;
}
public void setPageInfo(PageInfo<KwymgInfo> pageInfo) {
	this.pageInfo = pageInfo;
}
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public String getXm() {
	return xm;
}
public void setXm(String xm) {
	this.xm = xm;
}
public String getSex() {
	return sex;
}
public void setSex(String sex) {
	this.sex = sex;
}
public String getSfzjh() {
	return sfzjh;
}
public void setSfzjh(String sfzjh) {
	this.sfzjh = sfzjh;
}
public String getLxdh() {
	return lxdh;
}
public void setLxdh(String lxdh) {
	this.lxdh = lxdh;
}
public String getUsed() {
	return used;
}
public void setUsed(String used) {
	this.used = used;
}
public String getType() {
	return type;
}
public void setType(String type) {
	this.type = type;
}
public String getCsny() {
	return csny;
}
public void setCsny(String csny) {
	this.csny = csny;
}
public String getAddtime() {
	return addtime;
}
public void setAddtime(String addtime) {
	this.addtime = addtime;
}
public String getSexname() {
	return sexname;
}
public void setSexname(String sexname) {
	this.sexname = sexname;
}
public String getTypename() {
	return typename;
}
public void setTypename(String typename) {
	this.typename = typename;
}
public String getUsedname() {
	return usedname;
}
public void setUsedname(String usedname) {
	this.usedname = usedname;
}
   
   
   
   
   
}
