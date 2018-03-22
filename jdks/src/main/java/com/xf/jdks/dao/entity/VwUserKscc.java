package com.xf.jdks.dao.entity;

import com.github.pagehelper.PageInfo;

public class VwUserKscc {
	private String userid;
	private String kscc;
	private String used;
	private String ksccid;
	private String starttime;
	private String endtime;
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	public String getKsccid() {
		return ksccid;
	}
	public void setKsccid(String ksccid) {
		this.ksccid = ksccid;
	}
	private PageInfo<VwUserKscc> pageInfo;
	
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getKscc() {
		return kscc;
	}
	public void setKscc(String kscc) {
		this.kscc = kscc;
	}
	public String getUsed() {
		return used;
	}
	public void setUsed(String used) {
		this.used = used;
	}

	public void setPageInfo(PageInfo<VwUserKscc> pageInfo) {
		this.pageInfo = pageInfo;
	}
	public PageInfo<VwUserKscc> getPageInfo() {
		return pageInfo;
	}
}
