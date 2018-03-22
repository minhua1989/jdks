package com.xf.jdks.dao.entity;

public class BztableInfo {
	private String id;
	private  String code;
	private String name;
	private String type;
	private String sfkxg;//是否可修改
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSfkxg() {
		return sfkxg;
	}
	public void setSfkxg(String sfkxg) {
		this.sfkxg = sfkxg;
	}
	

}
