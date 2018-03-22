package com.xf.jdks.dao;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xf.jdks.dao.entity.KwymgInfo;

public interface KwymgDao {
	
    List<KwymgInfo> list(KwymgInfo kwymgInfo);
	
	List<KwymgInfo> pageList(KwymgInfo kwymgInfo);
	
	KwymgInfo show(@Param("id")String id);
	
	List<KwymgInfo> realtionList(KwymgInfo kwymgInfo);

	
}


