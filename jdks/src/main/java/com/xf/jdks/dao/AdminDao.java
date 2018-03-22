package com.xf.jdks.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xf.jdks.dao.entity.AdminInfo;

public interface AdminDao {
	
    List<AdminInfo> list(AdminInfo adminInfo);
	
	List<AdminInfo> pageList(AdminInfo adminInfo);
	
	AdminInfo show(@Param("id")String id);
	
	List<AdminInfo> realtionList(AdminInfo adminInfo);

	AdminInfo adminLogin(@Param("ename")String ename,@Param("pwd")String pwd);
	
	AdminInfo adminLoginbyename(@Param("ename")String ename);
	
}


