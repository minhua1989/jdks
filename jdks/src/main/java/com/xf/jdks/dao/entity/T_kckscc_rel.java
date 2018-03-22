package com.xf.jdks.dao.entity;

import com.github.pagehelper.PageInfo;

public class T_kckscc_rel {
	private String id;
	private String kcid;
	private String ksccid;
	private String deleted;
	private String deltime;
	private String used;
	private String adduserid;
	private String addtime;
	private String updateuserid;
	private String updatetime;
	private String deluserid;
	
	private PageInfo<T_kckscc_rel> pageInfo;

	public PageInfo<T_kckscc_rel> getPageInfo() {
		return pageInfo;
	}
	public void setPageInfo(PageInfo<T_kckscc_rel> pageInfo) {
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
	public String getKsccid() {
		return ksccid;
	}
	public void setKsccid(String ksccid) {
		this.ksccid = ksccid;
	}
	public String getDeleted() {
		return deleted;
	}
	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}
	public String getDeltime() {
		return deltime;
	}
	public void setDeltime(String deltime) {
		this.deltime = deltime;
	}
	public String getUsed() {
		return used;
	}
	public void setUsed(String used) {
		this.used = used;
	}
	public String getAdduserid() {
		return adduserid;
	}
	public void setAdduserid(String adduserid) {
		this.adduserid = adduserid;
	}
	public String getAddtime() {
		return addtime;
	}
	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}
	public String getUpdateuserid() {
		return updateuserid;
	}
	public void setUpdateuserid(String updateuserid) {
		this.updateuserid = updateuserid;
	}
	public String getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}
	public String getDeluserid() {
		return deluserid;
	}
	public void setDeluserid(String deluserid) {
		this.deluserid = deluserid;
	}
	
	
}
