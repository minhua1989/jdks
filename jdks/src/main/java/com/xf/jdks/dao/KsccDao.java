package com.xf.jdks.dao;

import java.util.List;
import java.util.Map;

import com.xf.jdks.dao.entity.Kscc;
import com.xf.jdks.dao.entity.Sttitle;
import com.xf.jdks.dao.entity.VwUserKscc;

public interface KsccDao {

	List<VwUserKscc> pageList(VwUserKscc vwUserKscc);

	List<Map<String, Object>> queryST(Map<String, Object> stMap);

	Integer querySTCNT(Map<String, Object> stMap);
	
	List<Map<String, Object>> queryKSXJXX(Map<String, Object> stMap);
	
	List<Map<String, Object>> queryklno(Map<String, Object> map);
	
	List<Map<String, Object>> queryst1(Map<String, Object> map);
	
	List<Map<String, Object>> queryst2(Map<String, Object> map);
}
