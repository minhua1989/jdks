package com.xf.jdks.dao.entity;

import com.github.pagehelper.PageInfo;

public class KcKsccInfo {

		private String id;
		private String kcid;
		private String ksccid;
		private String deleted;
		private String kscc;
		private String kcname;

		private PageInfo<KcKsccInfo> pageInfo;

		public PageInfo<KcKsccInfo> getPageInfo() {
			return pageInfo;
		}
		public void setPageInfo(PageInfo<KcKsccInfo> pageInfo) {
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
		public String getKscc() {
			return kscc;
		}
		public void setKscc(String kscc) {
			this.kscc = kscc;
		}
		public String getKcname() {
			return kcname;
		}
		public void setKcname(String kcname) {
			this.kcname = kcname;
		}
	
	}
