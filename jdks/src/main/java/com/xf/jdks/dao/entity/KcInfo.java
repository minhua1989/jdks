package com.xf.jdks.dao.entity;

import com.github.pagehelper.PageInfo;

public class KcInfo {
	private String id;
	private String kcname;
	private String kcaddress;
	private String deleted;
	private String kclxr;
	private String kclxdh;
	private String used;
	private String addtime;
	private String adduserid;
	private String deltime;
	private String deluserid;
	private String updatetime;
	private String updateuserid;
	private String maxrs;
	
	public String getMaxrs() {
		return maxrs;
	}
	public void setMaxrs(String maxrs) {
		this.maxrs = maxrs;
	}
	private PageInfo<KcInfo> pageInfo;

	public PageInfo<KcInfo> getPageInfo() {
		return pageInfo;
	}
	public void setPageInfo(PageInfo<KcInfo> pageInfo) {
		this.pageInfo = pageInfo;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getKcname() {
		return kcname;
	}
	public void setKcname(String kcname) {
		this.kcname = kcname;
	}
	public String getKcaddress() {
		return kcaddress;
	}
	public void setKcaddress(String kcaddress) {
		this.kcaddress = kcaddress;
	}
	public String getDeleted() {
		return deleted;
	}
	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}
	public String getKclxr() {
		return kclxr;
	}
	public void setKclxr(String kclxr) {
		this.kclxr = kclxr;
	}
	public String getKclxdh() {
		return kclxdh;
	}
	public void setKclxdh(String kclxdh) {
		this.kclxdh = kclxdh;
	}
	public String getUsed() {
		return used;
	}
	public void setUsed(String used) {
		this.used = used;
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
	public String getDeltime() {
		return deltime;
	}
	public void setDeltime(String deltime) {
		this.deltime = deltime;
	}
	public String getDeluserid() {
		return deluserid;
	}
	public void setDeluserid(String deluserid) {
		this.deluserid = deluserid;
	}
	public String getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}
	public String getUpdateuserid() {
		return updateuserid;
	}
	public void setUpdateuserid(String updateuserid) {
		this.updateuserid = updateuserid;
	}
}
