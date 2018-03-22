package com.xf.jdks.dao.entity;
import com.github.pagehelper.PageInfo;

public class Kscc {


		private String id;
		private String userid;
		private String ksccid;
		private String used;
		private String addtime;
		private String adduser;
		private String updatetime;
		private String updateuser;
		private String sfdl;

		

		private PageInfo<Kscc> pageInfo;

		public PageInfo<Kscc> getPageInfo() {
			return pageInfo;
		}
		public void setPageInfo(PageInfo<Kscc> pageInfo) {
			this.pageInfo = pageInfo;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getUserid() {
			return userid;
		}
		public void setUserid(String userid) {
			this.userid = userid;
		}
		public String getKsccid() {
			return ksccid;
		}
		public void setKsccid(String ksccid) {
			this.ksccid = ksccid;
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
		public String getAdduser() {
			return adduser;
		}
		public void setAdduser(String adduser) {
			this.adduser = adduser;
		}
		public String getUpdatetime() {
			return updatetime;
		}
		public void setUpdatetime(String updatetime) {
			this.updatetime = updatetime;
		}
		public String getUpdateuser() {
			return updateuser;
		}
		public void setUpdateuser(String updateuser) {
			this.updateuser = updateuser;
		}
		public String getSfdl() {
			return sfdl;
		}
		public void setSfdl(String sfdl) {
			this.sfdl = sfdl;
		}

		
		
		

	}
