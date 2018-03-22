package com.xf.jdks.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.xf.jdks.dao.entity.Sttitle;
import com.xf.jdks.dao.pojo.LoginInfo;
import com.xf.jdks.exceptions.FileTypeNotFoundException;

public interface QuestionService {

    JSONObject pageList(Map<String, Object> params) throws SQLException;
	
	JSONObject add(Map map) throws SQLException;

	JSONObject searchOne(Map map) throws SQLException;
	
	JSONObject edit( Map map) throws SQLException;
	
	JSONObject deleted(Map map) throws SQLException;
	
	JSONObject wulideleted(Map map) throws SQLException;
	
	JSONObject uploadImag(MultipartFile file,int type) throws IOException, FileTypeNotFoundException;
	
	JSONObject addOptions(Map map) throws SQLException;
	
    JSONObject importQuestions(Map<String,Object> params) throws IOException, SQLException;
    
    JSONObject expQuestions(Map<String, Object> params) throws SQLException, FileTypeNotFoundException, IOException;

	JSONObject listzsd(Map<String, Object> map) throws SQLException;

	JSONObject listtk(Map<String, Object> map) throws SQLException;
	
	JSONObject addtk(Map map) throws SQLException;

	JSONObject searchOnetk(Map map) throws SQLException;
	
	JSONObject edittk( Map map) throws SQLException;

	JSONObject pageListtk(Map<String, Object> map) throws SQLException;
}
