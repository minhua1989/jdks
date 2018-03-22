package com.xf.jdks.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.xf.jdks.dao.entity.XyInfo;

public interface XyDao {
	  List<XyInfo> list(XyInfo xyInfo);
		
		List<XyInfo> pageList(XyInfo xyInfo);
		
		XyInfo show(@Param("id")String id);
		
		List<XyInfo> realtionList(XyInfo xyInfo);

}
