package com.xf.jdks.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;


import com.xf.jdks.dao.entity.UserInfo;

public interface UserDao {
	  List<UserInfo> list(UserInfo userInfo);
		
		List<UserInfo> pageList(UserInfo userInfo);
		
		UserInfo show(@Param("id")String id);
		
		List<UserInfo> realtionList(UserInfo userInfo);

	UserInfo userLogin(@Param("zwh")String zwh,@Param("ksxtsfzjh")String ksxtsfzjh,@Param("ksxtzkzh")String ksxtzkzh);
}
