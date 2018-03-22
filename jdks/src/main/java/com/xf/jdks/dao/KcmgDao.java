package com.xf.jdks.dao;

import java.util.List;
import java.util.Map;

import com.xf.jdks.dao.entity.KcInfo;
import com.xf.jdks.dao.entity.KcKsccInfo;
import com.xf.jdks.dao.entity.KcKwyInfo;
import com.xf.jdks.dao.entity.VwUserKscc;

public interface KcmgDao {

	List<KcInfo> pageList(KcInfo kcInfo);
	
	List<KcKwyInfo> pageListKcKwy(KcKwyInfo kcKwyInfo);
	
	Integer queryUpdateKsRs(String kcid);
	
	void updateKsKcid(Map<String,Object> map);
}
