package com.xf.jdks.service.impl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSONObject;
import com.xf.jdks.commons.componet.BaseDaoComponent;
import com.xf.jdks.commons.componet.FileIOComponent;
import com.xf.jdks.commons.enums.EOperators;
import com.xf.jdks.commons.enums.EStandTables;
import com.xf.jdks.commons.global.DataDict;
import com.xf.jdks.commons.global.DataMap;
import com.xf.jdks.commons.global.FileInfoPo;
import com.xf.jdks.commons.util.BaseChecks;
import com.xf.jdks.commons.util.CollectionUtils;
import com.xf.jdks.commons.util.ExportExcel;
import com.xf.jdks.commons.util.Format;
import com.xf.jdks.commons.util.ResponseUtils;
import com.xf.jdks.dao.bean.DeleteParams;
import com.xf.jdks.dao.bean.InsertParams;
import com.xf.jdks.dao.bean.Parameter;
import com.xf.jdks.dao.bean.QueryParams;
import com.xf.jdks.dao.bean.UpdateParams;
import com.xf.jdks.dao.entity.AdminInfo;
import com.xf.jdks.exceptions.FileTypeNotFoundException;
import com.xf.jdks.service.KscjService;

@Service
public class KscjServiceImpl implements KscjService {

	@Autowired
	private BaseDaoComponent baseDaoComponent;

	@Override
	public JSONObject pageList(Map<String, Object> params) throws SQLException {
		List<Map<String, Object>> result = queryUsers(params);

		return ResponseUtils.createSuccessResponseBodyForJiem("查询成功", result);
	}

	// 查询学生信息
	private List<Map<String, Object>> queryUsers(Map<String, Object> params) throws SQLException {
		params.remove("admininfo");
		params.remove("userinfo");
		Map<String, Object> map = new DataMap<>();
		for (Map.Entry<String, Object> m : params.entrySet()) {
			if (m.getValue() != null && !"".equals(m.getValue()))
				map.put(m.getKey(), m.getValue());
		}
		QueryParams queryParams = QueryParams.createQueryParams("v_ksfsinfo");
		if (map.get("ksxtxm") != null && !"".equals(map.get("ksxtxm"))) {// 模糊查询
			String ksxtxm = (String) params.get("ksxtxm");
			// 判断是否为中文
			if (BaseChecks.isChineseChar(ksxtxm)) {
				// 是，空格全部替换
				ksxtxm = ksxtxm.replaceAll(" ", "");
			} else {
				// 否，替换两端空格
				ksxtxm = BaseChecks.bothEndsStr(ksxtxm);
			}
			queryParams.addQueryParams(Parameter.createParameter("ksxtxm", EOperators.类似, ksxtxm));
			map.remove("ksxtxm");
		}
		if (map.get("kscc") != null && !"".equals(map.get("kscc"))) {// 模糊查询
			String ksxtxm = (String) params.get("kscc");
			// 判断是否为中文
			if (BaseChecks.isChineseChar(ksxtxm)) {
				// 是，空格全部替换
				ksxtxm = ksxtxm.replaceAll(" ", "");
			} else {
				// 否，替换两端空格
				ksxtxm = BaseChecks.bothEndsStr(ksxtxm);
			}
			queryParams.addQueryParams(Parameter.createParameter("kscc", EOperators.类似, ksxtxm));
			map.remove("kscc");
		}
		map.put("deleted", "0");
		queryParams.addOrderColumns("ksccid asc,zwh asc");
		queryParams.addQueryParamsByMap(map);
		queryParams.printSelf();
		List<Map<String, Object>> data = baseDaoComponent.selectDataByParams(queryParams);
		return data;
	}

	@Override
	public JSONObject cjhc(Map<String, Object> params) throws SQLException {
		String ksccid = (String) params.get("ksccid");
		DeleteParams deleteParams = DeleteParams.createDeleteParams("T_ksfsinfo",
				Parameter.createParameter("KSCCID", ksccid));

		baseDaoComponent.deleteDataByParams(deleteParams);

		QueryParams queryParams1 = QueryParams.createQueryParams("v_kszqts4");
		queryParams1.addQueryParams(Parameter.createParameter("ksccid", ksccid));
		queryParams1.printSelf();
		List<Map<String, Object>> kssjxxlist = baseDaoComponent.selectDataByParams(queryParams1);
		for (int i = 0; i < kssjxxlist.size(); i++) {
			Map<String, Object> ksfsMap = kssjxxlist.get(i);
			String Id = UUID.randomUUID().toString();
			String ksid = (String) ksfsMap.get("ksid");
			String ksccid1 = (String) ksfsMap.get("ksccid");
			String userksccid = (String) ksfsMap.get("userksccid");
			String llksfs = String.valueOf(ksfsMap.get("llksfs"));
			InsertParams insertParams = InsertParams.createInsertParams("T_ksfsINFO", "id", "ksid", "ksccid",
					"userksccid", "llksfs", "scksfs");
			insertParams.setValues(Id, ksid, ksccid1, userksccid, llksfs, "0");
			baseDaoComponent.insertDataByParams(insertParams);

		}
		UpdateParams updateParams = UpdateParams.createUpdateParams("t_ksccmg");
		updateParams.addParam(Parameter.createParameter("sfcjsc", "1"));
		updateParams.addWhereParameter(Parameter.createParameter("id", ksccid));
		baseDaoComponent.updateDataByParams(updateParams);
		return ResponseUtils.createSuccessResponseBodyForJiem("考试成绩计算成功");

	}

	@Override
	public JSONObject edit(Map map) throws SQLException {
		String id = (String) map.get("id");
		String llksfs = (String) map.get("llksfs");
		String scksfs = (String) map.get("scksfs");
		UpdateParams updateParams = UpdateParams.createUpdateParams("t_ksfsinfo");
		updateParams.addParam(Parameter.createParameter("llksfs", llksfs));
		updateParams.addParam(Parameter.createParameter("scksfs", scksfs));
		updateParams.addWhereParameter(Parameter.createParameter("id", id));
		updateParams.printSelf();
		baseDaoComponent.updateDataByParams(updateParams);
		return ResponseUtils.createSuccessResponseBodyForJiem("修改成功");
	}

	@Override
	public JSONObject searchOne(Map map) throws SQLException {
		String id = (String) map.get("id");
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		QueryParams queryParams = QueryParams.createQueryParams("t_ksfsinfo");
		queryParams.addQueryParams(Parameter.createParameter("id", id));
		mapList = baseDaoComponent.selectDataByParams(queryParams);
		return ResponseUtils.createSuccessResponseBodyForJiem("查询成功", mapList.get(0));

	}
	
	
    @Override
    public JSONObject impKSCJ(Map<String, Object> params) throws Exception{
    	AdminInfo adminInfo = (AdminInfo) params.remove("admininfo");
        //成功的list 和失败的 list
        List<Map<String, Object>> successList = new ArrayList<>();
        List<Map<String, Object>> errorList = new ArrayList<>();
        //读取Excel
        ExportExcel exportExcel = ExportExcel.createExportExcel(params.get("url").toString(), "{KSXTXM:姓名,SFZJH:证件号码,KSXTZKZH:准考证号,ZWH:座位号,SCKSFS:操作考试成绩}");
        List<Map<String, Object>> mapList = exportExcel.readExcel();
        //mapList副本
        List<Map<String, Object>> newMap = CollectionUtils.cloneDataList(mapList);
        //校验班级是否存在,不存在抛出异常
        for (Map<String, Object> impDataMap : mapList) {
			String Id = UUID.randomUUID().toString();
			String ksxtxm= (String) impDataMap.get("ksxtxm");
			String sfzjh= (String) impDataMap.get("sfzjh");
			String ksxtzkzh= (String) impDataMap.get("ksxtzkzh");
			String zwh= (String) impDataMap.get("zwh");
			String scksfs= (String) impDataMap.get("scksfs");		
			QueryParams userqueryParams = QueryParams.createQueryParams("v_userinfo");
			userqueryParams.addQueryParams(Parameter.createParameter("ksxtxm", ksxtxm));
			userqueryParams.addQueryParams(Parameter.createParameter("ksxtsfzjh", sfzjh));
			userqueryParams.addQueryParams(Parameter.createParameter("ksxtzkzh", ksxtzkzh));
			userqueryParams.addQueryParams(Parameter.createParameter("zwh", zwh));
			userqueryParams.addQueryParams(Parameter.createParameter("deleted", "0"));
			List<Map<String, Object>> userresult= baseDaoComponent.selectDataByParams(userqueryParams);
			if(userresult.size()==0){
	        	impDataMap.put("remark", "姓名、证件号码、准考证号、座位号未找到符合信息的考生");
	        	errorList.add(impDataMap);
	        	continue;
			}
			String adduser=adminInfo.getId();	
			InsertParams insertParams = InsertParams.createInsertParams("t_ksfsinfo", "id", "ksid", "scksfs","userksccid","ksccid");
			insertParams.setValues(Id, userresult.get(0).get("id"),scksfs,userresult.get(0).get("userksccid"),userresult.get(0).get("ksccid"));
			baseDaoComponent.insertDataByParams(insertParams);   
            successList.add(impDataMap);
        }
        //将错误信息写入excel中
        String url1 = writeMessageToExcel(successList, "{KSXTXM:姓名,SFZJH:证件号码,KSXTZKZH:准考证号,ZWH:座位号,SCKSFS:操作考试成绩}");
        String url2 = writeMessageToExcel(errorList, "{KSXTXM:姓名,SFZJH:证件号码,KSXTZKZH:准考证号,ZWH:座位号,SCKSFS:操作考试成绩,REMARK:错误信息}");
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
    public JSONObject expKSCJ(Map<String, Object> params) throws SQLException, FileTypeNotFoundException, IOException {
        params.remove("limit");
        params.remove("offset");
        
        List<Map<String, Object>> result = queryUsers(params);
        FileInfoPo fileInfoPo = FileIOComponent.createTempFilePathByType(FileIOComponent.EXPORT_TYPE,"xlsx");
        String absPath = fileInfoPo.getFileAbsPath();
        String virPath = fileInfoPo.getFileVirPath();
        Map<String, Object> filePath = new HashMap<>();
        filePath.put("url", virPath);
        ExportExcel eh = ExportExcel.createExportExcel(absPath, "{NUMBER:序号,KSXTXM:考生姓名,KSXTSFZJH:身份证件号,KSXTZKZH:准考证号,KSCC:考试场次,DJMC:等级,ZWH:座位号,LLKSFS:理论考试,SCKSFS:实操考试}");
        for (int i = 0, len = result.size(); i < len; i++) {
            result.get(i).put("NUMBER", i + 1);
        }
        eh.setCurrentData(result);
        eh.writeCurrentData();
        result.add(filePath);
        JSONObject rs = ResponseUtils.createSuccessResponseBodyForJiem("done");
        rs.put("url", virPath);
        return rs;
    }
    
    @Override
    public JSONObject expKSTZD(Map<String, Object> params) throws SQLException, FileTypeNotFoundException, IOException {
        params.remove("limit");
        params.remove("offset");
        
        List<Map<String, Object>> result = queryKSTZD(params);
        FileInfoPo fileInfoPo = FileIOComponent.createTempFilePathByType(FileIOComponent.EXPORT_TYPE,"xlsx");
        String absPath = fileInfoPo.getFileAbsPath();
        String virPath = fileInfoPo.getFileVirPath();
        Map<String, Object> filePath = new HashMap<>();
        filePath.put("url", virPath);
        ExportExcel eh = ExportExcel.createExportExcel(absPath, "{NUMBER:序号,KSXTXM:考生姓名,KSXTSFZJH:身份证件号,KSXTZKZH:准考证号,KSCC:考试场次,ZWH:座位号,DJMC:考试科目名称,KSSJ:考试时间}");
        for (int i = 0, len = result.size(); i < len; i++) {
            result.get(i).put("NUMBER", i + 1);
        }
        eh.setCurrentData(result);
        eh.writeCurrentData();
        result.add(filePath);
        JSONObject rs = ResponseUtils.createSuccessResponseBodyForJiem("done");
        rs.put("url", virPath);
        return rs;
    }
    
	//查询学生信息
    private List<Map<String, Object>> queryKSTZD(Map<String, Object> params) throws SQLException{
    	params.remove("admininfo");
    	params.remove("userinfo");
        Map<String, Object> map = new DataMap<>();
        for (Map.Entry<String, Object> m : params.entrySet()) {
            if (m.getValue() != null && !"".equals(m.getValue())) map.put(m.getKey(), m.getValue());
        }
        QueryParams queryParams = QueryParams.createQueryParams("v_userinfo");
        if (map.get("ksxtxm") != null && !"".equals(map.get("ksxtxm"))) {//模糊查询
            String ksxtxm = (String) params.get("ksxtxm");
            //判断是否为中文
            if(BaseChecks.isChineseChar(ksxtxm)){
                //是，空格全部替换
            	ksxtxm = ksxtxm.replaceAll(" ","");
            }else{
                //否，替换两端空格
            	ksxtxm = BaseChecks.bothEndsStr(ksxtxm);
            }
            queryParams.addQueryParams(Parameter.createParameter("ksxtxm", EOperators.类似, ksxtxm));
            map.remove("ksxtxm");
        }
        map.put("deleted", "0");
        queryParams.addQueryParamsByMap(map);
        queryParams.printSelf();
        List<Map<String, Object>> data = baseDaoComponent.selectDataByParams(queryParams);
        return data;
    }
}
