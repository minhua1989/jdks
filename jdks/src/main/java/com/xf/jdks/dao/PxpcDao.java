package com.xf.jdks.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xf.jdks.dao.entity.PxpcInfo;

public interface PxpcDao {

		  List<PxpcInfo> list(PxpcInfo pxpcInfo);
			
			List<PxpcInfo> pageList(PxpcInfo pxpcInfo);
			
			PxpcInfo show(@Param("id")String id);
			
			List<PxpcInfo> realtionList(PxpcInfo pxpcInfo);

}
