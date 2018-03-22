package com.xf.jdks.dao.entity;

import com.github.pagehelper.PageInfo;

public class KcKwyInfo {

	private String id;
	private String kcid;
	private String kwyid;
	private String deleted;
	private String realname;
	private String kcname;
	
	private PageInfo<KcKwyInfo> pageInfo;

	public PageInfo<KcKwyInfo> getPageInfo() {
		return pageInfo;
	}
	public void setPageInfo(PageInfo<KcKwyInfo> pageInfo) {
		this.pageInfo = pageInfo;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getKcid() {
		return kcid;
	}
	public void setKcid(String kcid) {
		this.kcid = kcid;
	}
	public String getKwyid() {
		return kwyid;
	}
	public void setKwyid(String kwyid) {
		this.kwyid = kwyid;
	}
	public String getDeleted() {
		return deleted;
	}
	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}
	public String getrealname() {
		return realname;
	}
	public void setrealname(String realname) {
		this.realname = realname;
	}
	public String getKcname() {
		return kcname;
	}
	public void setKcname(String kcname) {
		this.kcname = kcname;
	}
}
