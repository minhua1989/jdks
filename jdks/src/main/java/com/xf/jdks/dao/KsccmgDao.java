package com.xf.jdks.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.xf.jdks.dao.entity.KcKsccInfo;
import com.xf.jdks.dao.entity.KsccmgInfo;
import com.xf.jdks.dao.entity.T_kckscc_rel;
import com.xf.jdks.dao.entity.UserInfo;

public interface KsccmgDao {
    List<KsccmgInfo> list(KsccmgInfo ksccmgInfo);
	
    List<Map<String, Object>> pageList(Map<String, Object> map);
	
	KsccmgInfo show(@Param("id")String id);
	
	List<KsccmgInfo> realtionList(KsccmgInfo ksccmgInfo);

	List<KcKsccInfo> pageListKcKscc(KcKsccInfo kcKsccInfo);

	List<Map<String, Object>> pageListKcKs(Map<String, Object> map);
	
	
}
