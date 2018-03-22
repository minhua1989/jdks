package com.xf.jdks.dao.entity;

import com.github.pagehelper.PageInfo;

public class Sttitle {
	private String id;
	private String title;
	private String content;
	private String fenshu;
	private String quanzhong;
	private String used;
	private String adduser;
	private String addtime;
	private String upuser;
	private String updatetime;
	private String deluser;
	private String stlx;
	private String sydj;
	private String deleted;
	private String pdda;
	
	public String getPdda() {
		return pdda;
	}
	public void setPdda(String pdda) {
		this.pdda = pdda;
	}
	public String getDeleted() {
		return deleted;
	}
	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}
	public String getSydj() {
		return sydj;
	}
	public void setSydj(String sydj) {
		this.sydj = sydj;
	}
	public String getStlx() {
		return stlx;
	}
	public void setStlx(String stlx) {
		this.stlx = stlx;
	}
	private PageInfo<Sttitle> pageInfo;

	public PageInfo<Sttitle> getPageInfo() {
		return pageInfo;
	}
	public void setPageInfo(PageInfo<Sttitle> pageInfo) {
		this.pageInfo = pageInfo;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getFenshu() {
		return fenshu;
	}
	public void setFenshu(String fenshu) {
		this.fenshu = fenshu;
	}
	public String getQuanzhong() {
		return quanzhong;
	}
	public void setQuanzhong(String quanzhong) {
		this.quanzhong = quanzhong;
	}
	public String getUsed() {
		return used;
	}
	public void setUsed(String used) {
		this.used = used;
	}
	public String getAdduser() {
		return adduser;
	}
	public void setAdduser(String adduser) {
		this.adduser = adduser;
	}
	public String getAddtime() {
		return addtime;
	}
	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}
	public String getUpuser() {
		return upuser;
	}
	public void setUpuser(String upuser) {
		this.upuser = upuser;
	}
	public String getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}
	public String getDeluser() {
		return deluser;
	}
	public void setDeluser(String deluser) {
		this.deluser = deluser;
	}
	
	
	
	

}
