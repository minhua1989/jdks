package com.xf.jdks.dao;

import java.util.List;
import java.util.Map;

import com.xf.jdks.dao.entity.Sttitle;


public interface SttitleDao {

	List<Map<String, Object>> pageList(Map<String, Object> map);
	
	void insertQuestion(Sttitle sttitle);
	
	List<Map<String, Object>> querySttitle(Map<String, String> queryPrama );
}
