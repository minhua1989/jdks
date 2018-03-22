package com.xf.jdks.dao.entity;

import com.github.pagehelper.PageInfo;

public class T_options {
	private String id;
	private String question_id;
	private String qisok;
	private String xuhao;
	private String used;
	private String qoption;
	private String addtime;
	private String adduser;
	private String updattime;
	private String updateuser;
	private String deltime;
	private String deluser;
	private String deleted;
	

	public String getDeleted() {
		return deleted;
	}
	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}
	public String getQoption() {
		return qoption;
	}
	public void setQoption(String qoption) {
		this.qoption = qoption;
	}
	public String getAddtime() {
		return addtime;
	}
	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}
	public String getAdduser() {
		return adduser;
	}
	public void setAdduser(String adduser) {
		this.adduser = adduser;
	}
	public String getUpdattime() {
		return updattime;
	}
	public void setUpdattime(String updattime) {
		this.updattime = updattime;
	}
	public String getUpdateuser() {
		return updateuser;
	}
	public void setUpdateuser(String updateuser) {
		this.updateuser = updateuser;
	}
	public String getDeltime() {
		return deltime;
	}
	public void setDeltime(String deltime) {
		this.deltime = deltime;
	}
	public String getDeluser() {
		return deluser;
	}
	public void setDeluser(String deluser) {
		this.deluser = deluser;
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
	public String getQuestion_id() {
		return question_id;
	}
	public void setQuestion_id(String question_id) {
		this.question_id = question_id;
	}
	public String getQisok() {
		return qisok;
	}
	public void setQisok(String qisok) {
		this.qisok = qisok;
	}
	public String getXuhao() {
		return xuhao;
	}
	public void setXuhao(String xuhao) {
		this.xuhao = xuhao;
	}
	public String getUsed() {
		return used;
	}
	public void setUsed(String used) {
		this.used = used;
	}

	
	
	

}
