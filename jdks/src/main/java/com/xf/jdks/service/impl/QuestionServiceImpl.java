package com.xf.jdks.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xf.jdks.dao.bean.DeleteParams;
import com.xf.jdks.dao.bean.InsertParams;
import com.xf.jdks.dao.bean.Parameter;
import com.xf.jdks.dao.bean.QueryParams;
import com.xf.jdks.dao.bean.UpdateParams;
import com.xf.jdks.dao.entity.AdminInfo;
import com.xf.jdks.dao.entity.Sttitle;
import com.xf.jdks.dao.pojo.LoginInfo;
import com.xf.jdks.service.QuestionService;
import com.xf.jdks.commons.componet.*;
import com.xf.jdks.commons.enums.EOperators;
import com.xf.jdks.commons.global.DataMap;
import com.xf.jdks.commons.global.FileInfoPo;
import com.xf.jdks.commons.global.FileTypePo;
import com.xf.jdks.commons.global.StaticCaches;
import com.xf.jdks.commons.util.*;
import com.xf.jdks.dao.*;
import com.xf.jdks.exceptions.FileTypeNotFoundException;
@Service
public class QuestionServiceImpl implements QuestionService {

	@Autowired
	private BaseDaoComponent baseDaoComponent;
	
	@Autowired
	private SttitleDao sttitleDao;
	
    @Resource
    private FileIOComponent fileIOComponent;
    
	@Override
	public JSONObject add(Map map) throws SQLException {		
			//保存试题
			Sttitle sttitle=new Sttitle();
			String question_id = UUID.randomUUID().toString();
			String title= (String) map.get("title");
			String content= (String) map.get("content");
			content=content.replaceAll("\\\\", "\\\\\\\\");
			String stlx= (String) map.get("stlx");
			String sydj= (String) map.get("sydj");
			String pdda= (String) map.get("pdda");
			String tkid= (String) map.get("tkid");
			String klno= (String) map.get("klno");
			InsertParams insertParams = InsertParams.createInsertParams("T_STTITLE", "id","title", "content","addtime","stlx","used","sydj","deleted","pdda","tkid","klno");
			insertParams.setValues(question_id, title, content,Format.getDateTime(),stlx,"0",sydj,"0",pdda,tkid,klno);
			baseDaoComponent.insertDataByParams(insertParams);
			//保存选择
			if(!"3".equals(stlx)&&!"4".equals(stlx)){
				List<Map<String,String>> options=(List<Map<String, String>>) map.get("options");
				for(int i=0;i<options.size();i++){
					Map<String,String> opt=options.get(i);
					String qoption=opt.get("qoption");
					qoption=qoption.replaceAll("\\\\", "\\\\\\\\");
					String used=opt.get("used");
					String qisok=opt.get("qisok");
					InsertParams insertParamsAns = InsertParams.createInsertParams("T_OPTIONS", "id","qoption", "used","addtime","qisok","question_id","xuhao","deleted");
					insertParamsAns.setValues(UUID.randomUUID().toString(), qoption, used,Format.getDateTime(),qisok,question_id,i+1,"0");
					baseDaoComponent.insertDataByParams(insertParamsAns);
				}
			}
		return ResponseUtils.createSuccessResponseBodyForJiem("添加成功");
	}

	@Override
	public JSONObject searchOne(Map map) throws SQLException {		
		String id = (String) map.get("id");
		List<Map<String, Object>> mapList =new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> mapListAns =new ArrayList<Map<String, Object>>();
			Map<String, String> queryPrama=new HashMap<String, String>();
			queryPrama.put("id", id);
			mapList=sttitleDao.querySttitle(queryPrama);
			QueryParams queryParamsAns = QueryParams.createQueryParams("t_options");
			queryParamsAns.addQueryParams(Parameter.createParameter("question_id", id));
			queryParamsAns.addQueryParams(Parameter.createParameter("deleted", "0"));
			queryParamsAns.addOrderColumns("xuhao asc");
			mapListAns= baseDaoComponent.selectDataByParams(queryParamsAns);
		JSONObject result = new JSONObject();
        result.put("title", mapList.get(0).get("title"));
        result.put("lv4", mapList.get(0).get("lv4"));
        result.put("lv3", mapList.get(0).get("lv3"));
        result.put("lv2", mapList.get(0).get("lv2"));
        result.put("lv1", mapList.get(0).get("lv1"));
        result.put("content", mapList.get(0).get("content"));
        result.put("stlx", mapList.get(0).get("stlx"));
        result.put("sydj", mapList.get(0).get("sydj"));
        result.put("pdda", mapList.get(0).get("pdda"));
        result.put("tkid", mapList.get(0).get("tkid"));
        result.put("klno", mapList.get(0).get("klno"));
        result.put("options", mapListAns);
		return result;
	}
	
	
	@Override
	public JSONObject pageList(Map<String, Object> params) throws SQLException {
    	params.remove("admininfo");
    	params.remove("userinfo");
    	List<Map<String, Object>> result = queryQuestions(params);
		return ResponseUtils.createSuccessResponseBodyForJiem("done", result);
	}
	



	@Override
	public JSONObject edit( Map map) throws SQLException {
			//保存试题
			Sttitle sttitle=new Sttitle();
			String question_id = (String) map.get("stitleid");
			String title= (String) map.get("title");
			String content= (String) map.get("content");
			content=content.replaceAll("\\\\", "\\\\\\\\");
			String stlx= (String) map.get("stlx");
			String sydj= (String) map.get("sydj");
			String pdda= (String) map.get("pdda");
			String tkid= (String) map.get("tkid");
			String klno= (String) map.get("klno");
	        UpdateParams updateParams = UpdateParams.createUpdateParams("T_STTITLE");
	        updateParams.addParam(Parameter.createParameter("title", title));
	        updateParams.addParam(Parameter.createParameter("updatetime", Format.getDateTime()));
	        updateParams.addParam(Parameter.createParameter("content", content));
	        updateParams.addParam(Parameter.createParameter("stlx", stlx));
	        updateParams.addParam(Parameter.createParameter("sydj", sydj));
	        updateParams.addParam(Parameter.createParameter("pdda", pdda));
	        updateParams.addParam(Parameter.createParameter("tkid", tkid));
	        updateParams.addParam(Parameter.createParameter("klno", klno));
	        updateParams.addWhereParameter(Parameter.createParameter("id", question_id));
	        baseDaoComponent.updateDataByParams(updateParams);
	        
			//保存选择
			if(!"3".equals(stlx)&&!"4".equals(stlx)){
				List<Map<String,String>> options=(List<Map<String, String>>) map.get("options");
		        QueryParams queryParamsHas = QueryParams.createQueryParams("T_OPTIONS");
		        queryParamsHas.addQueryParams(Parameter.createParameter("question_id", question_id));
		        queryParamsHas.addQueryParams(Parameter.createParameter("deleted", "0"));
		        List<Map<String, Object>>  optionHaslist = baseDaoComponent.selectDataByParams(queryParamsHas);
				for (int i=0;i<optionHaslist.size();i++){
					Map<String, Object> optionMap=optionHaslist.get(i);
					for(int j=0;j<options.size();j++){
						Map<String,String> opt=options.get(j);
						String optionid=opt.get("optionid");
						if(optionMap.get("id").equals(optionid)){
							break;
						}
						if(j==options.size()-1){
					        UpdateParams updateParamsAns = UpdateParams.createUpdateParams("T_OPTIONS");
					        updateParamsAns.addParam(Parameter.createParameter("deleted", "1"));
					        updateParamsAns.addParam(Parameter.createParameter("deltime", Format.getDateTime()));
					        updateParamsAns.addWhereParameter(Parameter.createParameter("id", optionMap.get("id")));
					        baseDaoComponent.updateDataByParams(updateParamsAns);
						}
					}
					
				}
				for(int i=0;i<options.size();i++){
					Map<String,String> opt=options.get(i);
					String optionid=opt.get("optionid");
					String qoption=opt.get("qoption");
					qoption=qoption.replaceAll("\\\\", "\\\\\\\\");
					String used=opt.get("used");
					String qisok=opt.get("qisok");
					
			        QueryParams queryParams = QueryParams.createQueryParams("T_OPTIONS");
			        queryParams.addQueryParams(Parameter.createParameter("id", optionid));
			        List<Map<String, Object>>  optionlist = baseDaoComponent.selectDataByParams(queryParams);
					if(optionlist.size()!=0){
				        UpdateParams updateParamsAns = UpdateParams.createUpdateParams("T_OPTIONS");
				        updateParamsAns.addParam(Parameter.createParameter("qoption", qoption));
				        updateParamsAns.addParam(Parameter.createParameter("updatetime", Format.getDateTime()));
				        updateParamsAns.addParam(Parameter.createParameter("used", used));
				        updateParamsAns.addParam(Parameter.createParameter("qisok", qisok));
				        updateParamsAns.addWhereParameter(Parameter.createParameter("id", optionid));
				        baseDaoComponent.updateDataByParams(updateParamsAns);
					}else{
						InsertParams insertParamsAns = InsertParams.createInsertParams("T_OPTIONS", "id","qoption", "used","addtime","qisok","question_id","xuhao","deleted");
						insertParamsAns.setValues(UUID.randomUUID().toString(), qoption, used,Format.getDateTime(),qisok,question_id,i+1,"0");
						baseDaoComponent.insertDataByParams(insertParamsAns);
					}
				}
			}else{
		        UpdateParams updateParamsAns = UpdateParams.createUpdateParams("T_OPTIONS");
		        updateParamsAns.addParam(Parameter.createParameter("deleted", "1"));
		        updateParamsAns.addParam(Parameter.createParameter("deltime", Format.getDateTime()));
		        updateParamsAns.addWhereParameter(Parameter.createParameter("question_id", question_id));
		        baseDaoComponent.updateDataByParams(updateParamsAns);		        
			}
		return ResponseUtils.createSuccessResponseBodyForJiem("修改成功");
	}



	//逻辑删除
	@Override
	public JSONObject deleted(Map map) throws SQLException {
			String used= (String) map.get("used");
			String id= (String) map.get("ids");
	        String[] ids = null;
	        if (id != null) {
	            if (id.contains(",")) {
	            	ids = id.split(",");
	            } else {
	            	ids = new String[]{id};
	            }
	        }
	        UpdateParams updateParams = UpdateParams.createUpdateParams("T_STTITLE");
	        updateParams.addParam(Parameter.createParameter("used", used));
	        updateParams.addParam(Parameter.createParameter("updatetime", Format.getDateTime()));
	        updateParams.addWhereParameter(Parameter.createParameter("id", EOperators.包含, ids));
	        updateParams.printSelf();
	        baseDaoComponent.updateDataByParams(updateParams);	
	        UpdateParams updateParamsAns = UpdateParams.createUpdateParams("T_OPTIONS");
	        updateParams.addParam(Parameter.createParameter("used", used));
	        updateParamsAns.addParam(Parameter.createParameter("updatetime", Format.getDateTime()));
	        updateParamsAns.addWhereParameter(Parameter.createParameter("question_id", EOperators.包含, ids));
	        baseDaoComponent.updateDataByParams(updateParamsAns);		        
		return ResponseUtils.createSuccessResponseBodyForJiem("设置成功");
	}

	//物理删除
	@Override
	public JSONObject wulideleted(Map map) throws SQLException {
			String id= (String) map.get("ids");
	        String[] ids = null;
	        if (id != null) {
	            if (id.contains(",")) {
	            	ids = id.split(",");
	            } else {
	            	ids = new String[]{id};
	            }
	        }
	        UpdateParams updateParams = UpdateParams.createUpdateParams("T_STTITLE");
	        updateParams.addParam(Parameter.createParameter("deleted", "1"));
	        updateParams.addParam(Parameter.createParameter("deltime", Format.getDateTime()));
	        updateParams.addWhereParameter(Parameter.createParameter("id", EOperators.包含, ids));
	        updateParams.printSelf();
	        baseDaoComponent.updateDataByParams(updateParams);	
	        UpdateParams updateParamsAns = UpdateParams.createUpdateParams("T_OPTIONS");
	        updateParamsAns.addParam(Parameter.createParameter("deleted", "1"));
	        updateParamsAns.addParam(Parameter.createParameter("deltime", Format.getDateTime()));
	        updateParamsAns.addWhereParameter(Parameter.createParameter("question_id", EOperators.包含, ids));
	        baseDaoComponent.updateDataByParams(updateParamsAns);		        
		return ResponseUtils.createSuccessResponseBodyForJiem("删除成功");
	}
	
	@Override
	public JSONObject uploadImag(MultipartFile file,int type) throws IOException, FileTypeNotFoundException{
		String absPath=null;
		String virPath=null;
			absPath=fileIOComponent.searchFileTypeByTypeId(type).getAbsPath();
			virPath=fileIOComponent.searchFileTypeByTypeId(type).getVirPath();
	  String filepath=absPath+File.separator+Format.getDateNumbers();
	  String filename=Format.getIndexDateTime()+file.getOriginalFilename();
		savePic(file.getInputStream(),filepath,filename);
      Map<String, Object> params = new HashMap<String, Object>();
      Map<String, Object> data = new HashMap<String, Object>();
      data.put("virsrc", virPath+File.separator+Format.getDateNumbers()+File.separator+filename);
      data.put("abssrc", absPath+File.separator+Format.getDateNumbers()+File.separator+filename);
      params.put("code",0);
      params.put("msg","test");
      params.put("data",data);
		return new JSONObject(params);
	}

	private void savePic(InputStream inputStream, String filepath,String fileName) {

        OutputStream os = null;
        try {
            // 2、保存文件filepath
            // 1K的数据缓冲
            byte[] bs = new byte[1024];
            // 读取到的数据长度
            int len;
            // 输出的文件流保存到本地文件

            File tempFile = new File(filepath+File.separator);
            if (!tempFile.exists()) {
                tempFile.mkdirs();
            }
            os = new FileOutputStream(tempFile.getPath() + File.separator + fileName);
            // 开始读取
            while ((len = inputStream.read(bs)) != -1) {
                os.write(bs, 0, len);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 完毕，关闭所有链接
            try {
                os.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
	
	@Override
	public JSONObject addOptions(Map map) throws SQLException {		
			Sttitle sttitle=new Sttitle();
			String Id = UUID.randomUUID().toString();
			String question_id= (String) map.get("question_id");
			String qisok= (String) map.get("qisok");
			String xuhao= (String) map.get("xuhao");
			InsertParams insertParams = InsertParams.createInsertParams("T_OPTIONS", "id","question_id", "qisok","xuhao","used","addtime","deleted");
			insertParams.setValues(Id, question_id, qisok,xuhao,"0",Format.getDateTime(),"0");
			baseDaoComponent.insertDataByParams(insertParams);
		return ResponseUtils.createSuccessResponseBodyForJiem("添加成功");
	}
	
    @Override
    public JSONObject importQuestions(Map<String, Object> params) throws IOException, SQLException{
    	AdminInfo adminInfo = (AdminInfo) params.remove("admininfo");
        //成功的list 和失败的 list
        List<Map<String, Object>> successList = new ArrayList<>();
        List<Map<String, Object>> errorList = new ArrayList<>();
        //读取Excel
        ExportExcel exportExcel = ExportExcel.createExportExcel(params.get("url").toString(), "{TITLE:试题标题,CONTENT:试题描述,STLX:试题类型,SYDJ:适用等级,PDDA:判断答案,QOPTION1:选择1,QISOK1:是否正确答案1,QOPTION2:选择2,QISOK2:是否正确答案2,QOPTION3:选择3,QISOK3:是否正确答案3,QOPTION4:选择4,QISOK4:是否正确答案4}");
        List<Map<String, Object>> mapList = exportExcel.readExcel();
        //mapList副本
        List<Map<String, Object>> newMap = CollectionUtils.cloneDataList(mapList);
        //校验班级是否存在,不存在抛出异常
        int j = 0;
        for (Map<String, Object> impDataMap : mapList) {
			Sttitle sttitle=new Sttitle();
			String question_id = UUID.randomUUID().toString();
			String title= (String) impDataMap.get("title");
			String content= (String) impDataMap.get("content");
			content=content.replaceAll("\\\\", "\\\\\\\\");
			String stlx= (String) impDataMap.get("stlx");
			String sydj= (String) impDataMap.get("sydj");
			String pdda= (String) impDataMap.get("pdda");
			if(null==title||"".equals(title)){
                newMap.get(j).put("remark", "试题标题不能为空");
                errorList.add(newMap.get(j));
                j++;
                continue;
			}
			if(null==stlx||"".equals(stlx)){
                newMap.get(j).put("remark", "试题类型不能为空");
                errorList.add(newMap.get(j));
                j++;
                continue;
			}
			if(null==sydj||"".equals(sydj)){
                newMap.get(j).put("remark", "适用等级不能为空");
                errorList.add(newMap.get(j));
                j++;
                continue;
			}
			if(null!=sydj&&!"".equals(sydj)){
				if("正确".equals(pdda)){
					pdda="1";
				}else if("错误".equals(pdda)){
					pdda="0";
				}
			}

			InsertParams insertParams = InsertParams.createInsertParams("T_STTITLE", "id","title", "content","addtime","stlx","used","sydj","deleted","pdda","adduser");
			insertParams.setValues(question_id, title, content,Format.getDateTime(),stlx,"0",sydj,"0",pdda,adminInfo.getId());
			baseDaoComponent.insertDataByParams(insertParams);
			//保存选择
			if(!"3".equals(stlx)&&!"4".equals(stlx)){
				if(null!=impDataMap.get("qoption1")&&!"".equals(impDataMap.get("qoption1"))){
					String qoption=(String) impDataMap.get("qoption1");
					qoption=qoption.replaceAll("\\\\", "\\\\\\\\");
					String used="0";
					String qisok=(String) impDataMap.get("qisok1");
					if("是".equals(qisok)){
						qisok="1";
					}else{
						qisok="0";
					}
					InsertParams insertParamsAns = InsertParams.createInsertParams("T_OPTIONS", "id","qoption", "used","addtime","qisok","question_id","xuhao","deleted","adduser");
					insertParamsAns.setValues(UUID.randomUUID().toString(), qoption, used,Format.getDateTime(),qisok,question_id,1,"0",adminInfo.getId());
					baseDaoComponent.insertDataByParams(insertParamsAns);    					
				}
				if(null!=impDataMap.get("qoption2")&&!"".equals(impDataMap.get("qoption2"))){
					String qoption=(String) impDataMap.get("qoption2");
					qoption=qoption.replaceAll("\\\\", "\\\\\\\\");
					String used="0";
					String qisok=(String) impDataMap.get("qisok2");
					if("是".equals(qisok)){
						qisok="1";
					}else{
						qisok="0";
					}
					InsertParams insertParamsAns = InsertParams.createInsertParams("T_OPTIONS", "id","qoption", "used","addtime","qisok","question_id","xuhao","deleted","adduser");
					insertParamsAns.setValues(UUID.randomUUID().toString(), qoption, used,Format.getDateTime(),qisok,question_id,2,"0",adminInfo.getId());
					baseDaoComponent.insertDataByParams(insertParamsAns);    					
				}
				if(null!=impDataMap.get("qoption3")&&!"".equals(impDataMap.get("qoption3"))){
					String qoption=(String) impDataMap.get("qoption3");
					qoption=qoption.replaceAll("\\\\", "\\\\\\\\");
					String used="0";
					String qisok=(String) impDataMap.get("qisok3");
					if("是".equals(qisok)){
						qisok="1";
					}else{
						qisok="0";
					}
					InsertParams insertParamsAns = InsertParams.createInsertParams("T_OPTIONS", "id","qoption", "used","addtime","qisok","question_id","xuhao","deleted","adduser");
					insertParamsAns.setValues(UUID.randomUUID().toString(), qoption, used,Format.getDateTime(),qisok,question_id,2,"0",adminInfo.getId());
					baseDaoComponent.insertDataByParams(insertParamsAns);    					
				}	
				if(null!=impDataMap.get("qoption4")&&!"".equals(impDataMap.get("qoption4"))){
					String qoption=(String) impDataMap.get("qoption4");
					qoption=qoption.replaceAll("\\\\", "\\\\\\\\");
					String used="0";
					String qisok=(String) impDataMap.get("qisok4");
					if("是".equals(qisok)){
						qisok="1";
					}else{
						qisok="0";
					}
					InsertParams insertParamsAns = InsertParams.createInsertParams("T_OPTIONS", "id","qoption", "used","addtime","qisok","question_id","xuhao","deleted","adduser");
					insertParamsAns.setValues(UUID.randomUUID().toString(), qoption, used,Format.getDateTime(),qisok,question_id,2,"0",adminInfo.getId());
					baseDaoComponent.insertDataByParams(insertParamsAns);    					
				}
            }
            successList.add(newMap.get(j));
            j++;
        }
        //将错误信息写入excel中
        String url1 = writeMessageToExcel(successList, "{TITLE:试题描述,CONTENT:试题内容,STLX:试题类型,SYDJ:适用等级,PDDA:判断答案,QOPTION1:选择1,QISOK1:是否正确答案1,QOPTION2:选择2,QISOK2:是否正确答案2,QOPTION3:选择3,QISOK3:是否正确答案3,QOPTION4:选择4,QISOK4:是否正确答案4}");
        String url2 = writeMessageToExcel(errorList, "{TITLE:试题描述,CONTENT:试题内容,STLX:试题类型,SYDJ:适用等级,PDDA:判断答案,QOPTION1:选择1,QISOK1:是否正确答案1,QOPTION2:选择2,QISOK2:是否正确答案2,QOPTION3:选择3,QISOK3:是否正确答案3,QOPTION4:选择4,QISOK4:是否正确答案4,REMARK:错误信息}");
        int sum = successList.size() + errorList.size();
        Map<String, Object> result = new DataMap<>();
        result.put("url1", url1);
        result.put("url2", url2);
        JSONObject rs = ResponseUtils.createSuccessResponseBodyForJiem("成功分入:" + successList.size() + "条,失败分入:" + errorList.size() + "条,总共处理:" + sum + "条", result);
        rs.put("url1", url1);
        rs.put("url2", url2);
        return rs;
    }
    
    /**
     * 写入错误和成功信息表格
     **/
    public String writeMessageToExcel(List<Map<String, Object>> list, String excelHead) throws IOException {
        //获取文件保存路径
//        Properties p = new Properties();
//        p.load(this.getClass().getResourceAsStream("/messages.properties"));
//        //生成本地唯一文件名
//        String newFileName = Format.getDateTimeLong();
//        String absPath = p.getProperty("abspath.upload.share") + "/" + newFileName + ".xlsx";
//        String virPath = p.getProperty("virpath.upload.share") + "/" + newFileName + ".xlsx";
        String absPath = "";
        String virPath = "";
        try {
            FileInfoPo fileInfoPo = FileIOComponent.createTempFilePathByType(FileIOComponent.EXPORT_TYPE,"xlsx");
            absPath = fileInfoPo.getFileAbsPath();
            virPath = fileInfoPo.getFileVirPath();
            ExportExcel exportExcel = ExportExcel.createExportExcel(absPath, excelHead);
            exportExcel.setCurrentData(list);
            exportExcel.writeCurrentData();
        } catch (FileTypeNotFoundException e) {
            e.printStackTrace();
        }
        return virPath;
    }
    
    @Override
    public JSONObject expQuestions(Map<String, Object> params) throws SQLException, FileTypeNotFoundException, IOException {
        //非在籍学生列表导出
        params.remove("limit");
        params.remove("offset");
        
        List<Map<String, Object>> stitles = queryQuestions(params);
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        for(int i=0;i<stitles.size();i++){
        	Map<String, Object> stitle=stitles.get(i);
        	Map<String, Object> question=new HashMap<>();
        	String titleid=(String) stitle.get("id");
        	question.put("title",stitle.get("title"));
        	question.put("content",stitle.get("content"));
        	question.put("stlx",stitle.get("stlx"));
        	question.put("sydj",stitle.get("sydj"));
        	question.put("pdda",stitle.get("pdda"));
			QueryParams queryParamsAns = QueryParams.createQueryParams("t_options");
			queryParamsAns.addQueryParams(Parameter.createParameter("question_id", titleid));
			List<Map<String, Object>> listAns= baseDaoComponent.selectDataByParams(queryParamsAns);
			for(int j=0;j<listAns.size();j++){
				String QOPTION="QOPTION"+j;
				String QISOK="QISOK"+j;
				question.put(QOPTION,listAns.get(j).get("qoption"));
				question.put(QISOK,listAns.get(j).get("qisok"));
			}
        }
        FileInfoPo fileInfoPo = FileIOComponent.createTempFilePathByType(FileIOComponent.EXPORT_TYPE,"xlsx");
        String absPath = fileInfoPo.getFileAbsPath();
        String virPath = fileInfoPo.getFileVirPath();
        Map<String, Object> filePath = new HashMap<>();
        filePath.put("url", virPath);
        ExportExcel eh = ExportExcel.createExportExcel(absPath, "{TITLE:试题描述,CONTENT:试题内容,STLX:试题类型,SYDJ:适用等级,PDDA:判断答案,QOPTION1:选择1,QISOK1:是否正确答案1,QOPTION2:选择2,QISOK2:是否正确答案2,QOPTION3:选择3,QISOK3:是否正确答案3,QOPTION4:选择4,QISOK4:是否正确答案4}");

        eh.setCurrentData(result);
        eh.writeCurrentData();
        result.add(filePath);
        JSONObject rs = ResponseUtils.createSuccessResponseBodyForJiem("done");
        rs.put("url", virPath);
        return rs;
    }
    
    private List<Map<String, Object>> queryQuestions(Map<String, Object> params) throws SQLException{
    	params.remove("admininfo");
    	params.remove("userinfo");
        Map<String, Object> map = new DataMap<>();
        for (Map.Entry<String, Object> m : params.entrySet()) {
            if (m.getValue() != null && !"".equals(m.getValue())) map.put(m.getKey(), m.getValue());
        }
        QueryParams queryParams = QueryParams.createQueryParams("v_sttitle_hm");
        if (map.get("title") != null && !"".equals(map.get("title"))) {//模糊查询
            String title = (String) params.get("title");
            //判断是否为中文
            if(BaseChecks.isChineseChar(title)){
                //是，空格全部替换
            	title = title.replaceAll(" ","");
            }else{
                //否，替换两端空格
            	title = BaseChecks.bothEndsStr(title);
            }
            queryParams.addQueryParams(Parameter.createParameter("title", EOperators.类似, title));
            map.remove("title");
        }
        if (map.get("content") != null && !"".equals(map.get("content"))) {//模糊查询
            String content = (String) params.get("content");
            //判断是否为中文
            if(BaseChecks.isChineseChar(content)){
                //是，空格全部替换
            	content = content.replaceAll(" ","");
            }else{
                //否，替换两端空格
            	content = BaseChecks.bothEndsStr(content);
            }
            queryParams.addQueryParams(Parameter.createParameter("content", EOperators.类似, content));
            map.remove("content");
        }
        queryParams.addQueryParamsByMap(map);
        queryParams.printSelf();
        List<Map<String, Object>> st = baseDaoComponent.selectDataByParams(queryParams);
		List<Map<String, Object>> stRs=new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> sydjList =new ArrayList<Map<String, Object>>();
		QueryParams queryParamSydj = QueryParams.createQueryParams("t_bz_tab");
		queryParamSydj.addQueryParams(Parameter.createParameter("type", "5"));
		sydjList= baseDaoComponent.selectDataByParams(queryParamSydj);
		for(int i=0;i<st.size();i++){
			Map<String, Object> tmpSt=st.get(i);
			//TODO 适用等级
			String tmpsydj=(String) tmpSt.get("sydj");
			for(int j=0;j<sydjList.size();j++){
				tmpsydj=tmpsydj.replace(sydjList.get(j).get("code").toString(), sydjList.get(j).get("name").toString());
			}
			tmpSt.put("sydj",tmpsydj);
			stRs.add(tmpSt);
		}
        return stRs;
    }

	@Override
	public JSONObject listzsd(Map<String, Object> map)throws SQLException {
		QueryParams queryParams = QueryParams.createQueryParams("t_klk");
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();		
			list = baseDaoComponent.selectDataByParams(queryParams);
		return ResponseUtils.createSuccessResponseBodyForJiem("done", list);

	}

	@Override
	public JSONObject listtk(Map<String, Object> params) throws SQLException {
    	params.remove("admininfo");
    	params.remove("userinfo");
        Map<String, Object> map = new DataMap<>();
        for (Map.Entry<String, Object> m : params.entrySet()) {
            if (m.getValue() != null && !"".equals(m.getValue())) map.put(m.getKey(), m.getValue());
        }
        QueryParams queryParams = QueryParams.createQueryParams("t_tk");
        queryParams.addOrderColumns("sydj asc");
        queryParams.addQueryParamsByMap(map);
        queryParams.printSelf();
        List<Map<String, Object>> data = baseDaoComponent.selectDataByParams(queryParams);
	       JSONObject result = ResponseUtils.createSuccessResponseBodyForJiem("查询成功", data);
			return result;
	}

	@Override
	public JSONObject addtk(Map map) throws SQLException {
		String id = UUID.randomUUID().toString();
		String tkname= (String) map.get("tkname");
		String kslx= (String) map.get("kslx");
		String sydj= (String) map.get("sydj");
		String stlx= (String) map.get("stlx");
		String allnum= (String) map.get("allnum");
		String fenshu= (String) map.get("fenshu");
		String outnum= (String) map.get("outnum");
		InsertParams insertParams = InsertParams.createInsertParams("t_tk", "id", "tkname", "kslx","sydj",
				"stlx","allnum","fenshu","outnum");
		insertParams.setValues(id,tkname, kslx,sydj,stlx,allnum,fenshu,outnum);
		baseDaoComponent.insertDataByParams(insertParams);
	return ResponseUtils.createSuccessResponseBodyForJiem("添加成功");
    

	}

	@Override
	public JSONObject searchOnetk(Map map) throws SQLException {
		String id = (String) map.get("id");
		List<Map<String, Object>> mapList =new ArrayList<Map<String, Object>>();
			QueryParams queryParams = QueryParams.createQueryParams("t_tk");
			queryParams.addQueryParams(Parameter.createParameter("id", id));
			mapList= baseDaoComponent.selectDataByParams(queryParams);
		return ResponseUtils.createSuccessResponseBodyForJiem("查询成功",mapList.get(0));
	}

	@Override
	public JSONObject edittk(Map map) throws SQLException {
		String id = (String) map.get("id");
		String tkname= (String) map.get("tkname");
		String kslx= (String) map.get("kslx");
		String sydj= (String) map.get("sydj");
		String stlx= (String) map.get("stlx");
		String allnum= (String) map.get("allnum");
		String fenshu= (String) map.get("fenshu");
		String outnum= (String) map.get("outnum");
		UpdateParams updateParams = UpdateParams.createUpdateParams("t_tk");
		updateParams.addParam(Parameter.createParameter("tkname", tkname));
		updateParams.addParam(Parameter.createParameter("kslx",kslx));
		updateParams.addParam(Parameter.createParameter("sydj", sydj));
		updateParams.addParam(Parameter.createParameter("stlx", stlx));
		updateParams.addParam(Parameter.createParameter("allnum", allnum));
		updateParams.addParam(Parameter.createParameter("fenshu",fenshu));
		updateParams.addParam(Parameter.createParameter("outnum", outnum));
		updateParams.addWhereParameter(Parameter.createParameter("id", id));
		baseDaoComponent.updateDataByParams(updateParams);
		return ResponseUtils.createSuccessResponseBodyForJiem("修改成功");
	}

	@Override
	public JSONObject pageListtk(Map<String, Object> params) throws SQLException {
    	params.remove("admininfo");
    	params.remove("userinfo");
        Map<String, Object> map = new DataMap<>();
        for (Map.Entry<String, Object> m : params.entrySet()) {
            if (m.getValue() != null && !"".equals(m.getValue())) map.put(m.getKey(), m.getValue());
        }
        QueryParams queryParams = QueryParams.createQueryParams("t_tk");
        queryParams.addQueryParamsByMap(map);
        queryParams.addOrderColumns("kslx asc,sydj asc,tkname asc");
        queryParams.printSelf();
        List<Map<String, Object>> data = baseDaoComponent.selectDataByParams(queryParams);
	       JSONObject result = ResponseUtils.createSuccessResponseBodyForJiem("查询成功", data);
			return result;
	}
}
