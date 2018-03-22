package com.xf.jdks.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xf.jdks.dao.entity.BztableInfo;


public interface BztableDao {
	List<BztableInfo> list(BztableInfo bztableInfo);
	

	List<BztableInfo> list(@Param("type")String type);
}
