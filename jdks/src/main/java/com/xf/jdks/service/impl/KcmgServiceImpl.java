package com.xf.jdks.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xf.jdks.commons.componet.BaseDaoComponent;
import com.xf.jdks.commons.enums.EOperators;
import com.xf.jdks.commons.global.DataMap;
import com.xf.jdks.commons.util.BaseChecks;
import com.xf.jdks.commons.util.Format;
import com.xf.jdks.commons.util.ResponseUtils;
import com.xf.jdks.dao.BaseDao;
import com.xf.jdks.dao.KcmgDao;
import com.xf.jdks.dao.KsccDao;
import com.xf.jdks.dao.bean.DeleteParams;
import com.xf.jdks.dao.bean.InsertParams;
import com.xf.jdks.dao.bean.Parameter;
import com.xf.jdks.dao.bean.QueryParams;
import com.xf.jdks.dao.bean.UpdateParams;
import com.xf.jdks.dao.entity.AdminInfo;
import com.xf.jdks.dao.entity.KcInfo;
import com.xf.jdks.dao.entity.KcKsccInfo;
import com.xf.jdks.dao.entity.KcKwyInfo;
import com.xf.jdks.dao.entity.VwUserKscc;
import com.xf.jdks.service.KcmgService;
import com.xf.jdks.service.KsccService;

@Service
public class KcmgServiceImpl implements KcmgService {

	@Autowired
	private BaseDaoComponent baseDaoComponent;

	@Autowired
	private BaseDao baseDao;

	@Autowired
	private KcmgDao kcmgDao;

	@Override
	public JSONObject pageList(Map<String, Object> params) throws SQLException {
		params.remove("admininfo");
		params.remove("userinfo");
		Map<String, Object> map = new DataMap<>();
		for (Map.Entry<String, Object> m : params.entrySet()) {
			if (m.getValue() != null && !"".equals(m.getValue()))
				map.put(m.getKey(), m.getValue());
		}
		QueryParams queryParams = QueryParams.createQueryParams("t_kcinfo");
		if (map.get("kcname") != null && !"".equals(map.get("kcname"))) {// 模糊查询
			String kcname = (String) params.get("kcname");
			// 判断是否为中文
			if (BaseChecks.isChineseChar(kcname)) {
				// 是，空格全部替换
				kcname = kcname.replaceAll(" ", "");
			} else {
				// 否，替换两端空格
				kcname = BaseChecks.bothEndsStr(kcname);
			}
			queryParams.addQueryParams(Parameter.createParameter("kcname", EOperators.类似, kcname));
			map.remove("kcname");
		}
		map.put("deleted", "0");
		map.put("used", "0");
		queryParams.addOrderColumns("kcname asc");
		queryParams.addQueryParamsByMap(map);
		queryParams.printSelf();
		List<Map<String, Object>> data = baseDaoComponent.selectDataByParams(queryParams);
		// if (data == null || data.size() == 0) throw new
		// DataNotFoundException();
		JSONObject result = ResponseUtils.createSuccessResponseBodyForJiem("查询成功", data);
		return result;
	}

	// 逻辑删除
	@Override
	public JSONObject deleted(Map map) throws SQLException {
		AdminInfo adminInfo = (AdminInfo) map.get("adminInfo");
		String id = (String) map.get("ids");
		String used = (String) map.get("used");
		String[] ids = null;
		if (id != null) {
			if (id.contains(",")) {
				ids = id.split(",");
			} else {
				ids = new String[] { id };
			}
		}
		UpdateParams updateParams = UpdateParams.createUpdateParams("t_kcinfo");
		updateParams.addParam(Parameter.createParameter("used", used));
		updateParams.addParam(Parameter.createParameter("updatetime", Format.getDateTime()));
		updateParams.addParam(Parameter.createParameter("updateuserid", adminInfo.getId()));
		updateParams.addWhereParameter(Parameter.createParameter("id", EOperators.包含, ids));
		updateParams.printSelf();
		baseDaoComponent.updateDataByParams(updateParams);
		return ResponseUtils.createSuccessResponseBodyForJiem("设置成功");
	}

	// 物理删除
	@Override
	public JSONObject wulideleted(Map map) throws SQLException {
		AdminInfo adminInfo = (AdminInfo) map.get("adminInfo");
		String id = (String) map.get("ids");
		String[] ids = null;
		if (id != null) {
			if (id.contains(",")) {
				ids = id.split(",");
			} else {
				ids = new String[] { id };
			}
		}
		UpdateParams updateParams = UpdateParams.createUpdateParams("t_kcinfo");
		updateParams.addParam(Parameter.createParameter("deleted", "1"));
		updateParams.addParam(Parameter.createParameter("deltime", Format.getDateTime()));
		updateParams.addParam(Parameter.createParameter("deluserid", adminInfo.getId()));
		updateParams.addWhereParameter(Parameter.createParameter("id", EOperators.包含, ids));
		updateParams.printSelf();
		baseDaoComponent.updateDataByParams(updateParams);
		return ResponseUtils.createSuccessResponseBodyForJiem("删除成功");
	}

	@Override
	public JSONObject add(Map map) throws SQLException {
		String Id = UUID.randomUUID().toString();
		String kcname = (String) map.get("kcname");
		String kcaddress = (String) map.get("kcaddress");
		String kclxr = (String) map.get("kclxr");
		String kclxdh = (String) map.get("kclxdh");
		AdminInfo adminInfo = (AdminInfo) map.get("adminInfo");
		Integer maxrs = Integer.valueOf((String) map.get("maxrs"));
		InsertParams insertParams = InsertParams.createInsertParams("t_kcinfo", "id", "kcname", "kcaddress", "kclxr",
				"kclxdh", "used", "deleted", "addtime", "adduserid", "maxrs");
		insertParams.setValues(Id, kcname, kcaddress, kclxr, kclxdh, "0", "0", Format.getDateTime(), adminInfo.getId(),
				maxrs);
		baseDaoComponent.insertDataByParams(insertParams);
		return ResponseUtils.createSuccessResponseBodyForJiem("添加成功");
	}

	@Override
	public JSONObject searchOne(Map map) throws SQLException {
		String id = (String) map.get("id");
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		QueryParams queryParams = QueryParams.createQueryParams("t_kcinfo");
		queryParams.addQueryParams(Parameter.createParameter("id", id));
		mapList = baseDaoComponent.selectDataByParams(queryParams);
		return ResponseUtils.createSuccessResponseBodyForJiem("查询成功", mapList.get(0));

	}

	@Override
	public JSONObject edit(Map map) throws SQLException {
		String id = (String) map.get("id");
		String kcname = (String) map.get("kcname");
		String kcaddress = (String) map.get("kcaddress");
		String kclxr = (String) map.get("kclxr");
		String kclxdh = (String) map.get("kclxdh");
		AdminInfo adminInfo = (AdminInfo) map.get("adminInfo");
		Integer maxrs = Integer.valueOf((String) map.get("maxrs"));
		UpdateParams updateParams = UpdateParams.createUpdateParams("t_kcinfo");
		updateParams.addParam(Parameter.createParameter("kcname", kcname));
		updateParams.addParam(Parameter.createParameter("updatetime", Format.getDateTime()));
		updateParams.addParam(Parameter.createParameter("kcaddress", kcaddress));
		updateParams.addParam(Parameter.createParameter("kclxr", kclxr));
		updateParams.addParam(Parameter.createParameter("kclxdh", kclxdh));
		updateParams.addParam(Parameter.createParameter("updateuserid", adminInfo.getId()));
		updateParams.addParam(Parameter.createParameter("maxrs", maxrs));
		updateParams.addWhereParameter(Parameter.createParameter("id", id));
		baseDaoComponent.updateDataByParams(updateParams);
		return ResponseUtils.createSuccessResponseBodyForJiem("修改成功");
	}

	@Override
	public JSONObject addKwy(Map map) throws SQLException {
		String[] kwyidsadd = null;
		if(null!=map.get("kwyidsadd")&&!"".equals(map.get("kwyidsadd"))){
			kwyidsadd =((String)map.get("kwyidsadd")).split(",");
		}
		String[] kwyidsrm = null;
		if(null!=map.get("kwyidsrm")&&!"".equals(map.get("kwyidsrm"))){
			kwyidsrm =((String)map.get("kwyidsrm")).split(",");
		}
		String ksccid= (String) map.get("ksccid");
    	AdminInfo adminInfo = (AdminInfo) map.remove("admininfo");
		String kcid = (String) map.get("kcid");
		String adminid = (String) map.get("adminid");
    	if(null!=kwyidsrm){
			DeleteParams deleteParams1 = DeleteParams.createDeleteParams("t_kckwy_rel",
					Parameter.createParameter("KWYID", EOperators.包含, kwyidsrm),Parameter.createParameter("kcid", kcid),Parameter.createParameter("ksccid", ksccid));
			baseDaoComponent.deleteDataByParams(deleteParams1);
    	}
    	if(null!=kwyidsadd){
			for (int i = 0; i < kwyidsadd.length; i++) {
				String Id = UUID.randomUUID().toString();
				InsertParams insertParams = InsertParams.createInsertParams("t_kckwy_rel", "id", "kcid", "kwyid", "deleted",
						"addtime", "adduserid", "ksccid");
				insertParams.setValues(Id, kcid, kwyidsadd[i], "0", Format.getDateTime(), adminInfo.getId(), ksccid);
				baseDaoComponent.insertDataByParams(insertParams);
			}
    	}
		return ResponseUtils.createSuccessResponseBodyForJiem("添加成功");
	}

	@Override
	public JSONObject listKwy(Map map) throws SQLException {
		QueryParams queryParamKcs = QueryParams.createQueryParams("t_kckwy_rel");
		queryParamKcs.addQueryParams(Parameter.createParameter("deleted", "0"));
		queryParamKcs.addQueryParams(Parameter.createParameter("kcid", map.get("kcid")));
		List<Map<String, Object>> listKcs = new ArrayList<Map<String, Object>>();
		try {
			listKcs = baseDaoComponent.selectDataByParams(queryParamKcs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String kwyids = "";
		if (listKcs.size() != 0) {
			for (int i = 0; i < listKcs.size(); i++) {
				if (i == 0) {
					kwyids = kwyids + listKcs.get(i).get("kwyid");
				} else {
					kwyids = kwyids + "," + listKcs.get(i).get("kwyid");
				}
			}
		}
		QueryParams queryParams = QueryParams.createQueryParams("t_admininfo");
		queryParams.addQueryParams(Parameter.createParameter("deleted", "0"));
		queryParams.addQueryParams(Parameter.createParameter("roleid", "3"));
		queryParams.addQueryParams(Parameter.createParameter("id", EOperators.不包含, kwyids.split(",")));
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = baseDaoComponent.selectDataByParams(queryParams);
		return ResponseUtils.createSuccessResponseBodyForJiem("done", list);

	}

	@Override
	public JSONObject pageListKcKwy(Map<String, Object> params) throws SQLException {
		params.remove("admininfo");
		params.remove("userinfo");
		Map<String, Object> map = new DataMap<>();
		for (Map.Entry<String, Object> m : params.entrySet()) {
			if (m.getValue() != null && !"".equals(m.getValue()))
				map.put(m.getKey(), m.getValue());
		}
		QueryParams queryParams = QueryParams.createQueryParams("v_kckwy_rel");
		queryParams.addQueryParamsByMap(map);
		queryParams.printSelf();
		List<Map<String, Object>> data = baseDaoComponent.selectDataByParams(queryParams);
		// if (data == null || data.size() == 0) throw new
		// DataNotFoundException();
		JSONObject result = ResponseUtils.createSuccessResponseBodyForJiem("查询成功", data);
		return result;
	}

	// 删除考务员
	@Override
	public JSONObject deletedKwy(Map map) throws SQLException {
		AdminInfo adminInfo = (AdminInfo) map.get("adminInfo");
		String id = (String) map.get("ids");
		String[] ids = null;
		if (id != null) {
			if (id.contains(",")) {
				ids = id.split(",");
			} else {
				ids = new String[] { id };
			}
		}
		UpdateParams updateParams = UpdateParams.createUpdateParams("t_kckwy_rel");
		updateParams.addParam(Parameter.createParameter("deleted", "1"));
		updateParams.addParam(Parameter.createParameter("deluserid", adminInfo.getId()));
		updateParams.addParam(Parameter.createParameter("deltime", Format.getDateTime()));
		updateParams.addWhereParameter(Parameter.createParameter("id", EOperators.包含, ids));
		updateParams.printSelf();
		baseDaoComponent.updateDataByParams(updateParams);
		return ResponseUtils.createSuccessResponseBodyForJiem("删除成功");
	}

	// 考生考场分配
	@Override
	public JSONObject kskcfp(Map map) {
		String kcid = (String) map.get("kcid");
		Integer updateksrs = kcmgDao.queryUpdateKsRs(kcid);
		Map<String, Object> updateksMap = new HashMap<String, Object>();
		updateksMap.put("updateksrs", updateksrs);
		updateksMap.put("kcid", kcid);
		if (updateksrs != 0) {
			kcmgDao.updateKsKcid(updateksMap);
		} else {
			return ResponseUtils.createErrorResponseBodyJiem("该考场的考生已分配满额");
		}
		return ResponseUtils.createSuccessResponseBodyForJiem("自动分配考生考场成功");
	}

	// 生成考试通知单
	@Override
	public JSONObject createKSTZD(Map map) throws Exception {
		AdminInfo adminInfo = (AdminInfo) map.remove("admininfo");
		String ksccid = (String) map.get("ksccid");
		QueryParams queryParams = QueryParams.createQueryParams("t_user_kscc");
		queryParams.addQueryParams(Parameter.createParameter("ksccid", ksccid));
		List<Map<String, Object>> ksList = baseDaoComponent.selectDataByParams(queryParams);
        QueryParams queryParams1 = QueryParams.createQueryParams("v_kckscc_rel_hm");
        queryParams1.addQueryParams(Parameter.createParameter("ksccid",ksccid));
        List<Map<String, Object>> kcList = baseDaoComponent.selectDataByParams(queryParams1);
        Integer maxkcrs=0;
        for(int j=0;j<kcList.size();j++){
        	Integer tmpkcrs=(Integer) kcList.get(j).get("maxrs");
        	maxkcrs+=tmpkcrs;
        }
        if(maxkcrs<ksList.size()){
        	throw new Exception("考场总人数小于需要分配的学生数");
        }
		int i=0;
		boolean flag=false;
	        for(int k=0;k<kcList.size();k++){
	        	Integer tmpmaxkcrs=(Integer) kcList.get(k).get("maxrs");
	        	for(int h=0;h<tmpmaxkcrs;h++){
	        		int tmpk=k+1;
	        		String kcno=""+tmpk;
	        		if(kcno.length()<2){
	        			kcno="0"+tmpk;
	        		}
	        		int tmph=h+1;
	        		String zwhno=""+tmph;
	        		if(zwhno.length()<2){
	        			zwhno="0"+tmph;
	        		}
	    			if(i<ksList.size()){
		    			UpdateParams updateParams = UpdateParams.createUpdateParams("t_user_kscc");
		    			updateParams.addParam(Parameter.createParameter("kcid", kcList.get(k).get("id")));
		    			updateParams.addParam(Parameter.createParameter("zwh", zwhno));
		    			String ksxtzkzh=kcList.get(k).get("nf").toString()+kcList.get(k).get("ksrq").toString()+"0000"+kcList.get(k).get("dj")+kcno+zwhno;
		    			updateParams.addParam(Parameter.createParameter("ksxtzkzh", ksxtzkzh));
		    			updateParams.addParam(Parameter.createParameter("updatetime", Format.getDateTime()));
		    			updateParams.addParam(Parameter.createParameter("updateuser", adminInfo.getId()));
		    			updateParams.addWhereParameter(Parameter.createParameter("id", ksList.get(i).get("id")));
		    			updateParams.printSelf();
		    			baseDaoComponent.updateDataByParams(updateParams);
		    			i++;
	    			}else{
	    				flag=true;
	    				break;
	    			}
	        	}
    			if(flag){
    				break;
    			}
	        }
	       //更新是否已生成通知单字段
			UpdateParams updateSfsctzdParams = UpdateParams.createUpdateParams("t_ksccmg");
			updateSfsctzdParams.addParam(Parameter.createParameter("sfsctzd", "1"));
			updateSfsctzdParams.addParam(Parameter.createParameter("updatetime", Format.getDateTime()));
			updateSfsctzdParams.addParam(Parameter.createParameter("updateuser", adminInfo.getId()));
			updateSfsctzdParams.addWhereParameter(Parameter.createParameter("id", ksccid));
			updateSfsctzdParams.printSelf();
			baseDaoComponent.updateDataByParams(updateSfsctzdParams);
	        
		return ResponseUtils.createSuccessResponseBodyForJiem("考试通知单生成成功");
	}


	@Override
	public JSONObject yxpageList(Map<String, Object> params) throws SQLException {
		params.remove("admininfo");
		params.remove("userinfo");
		Map<String, Object> map = new DataMap<>();
		for (Map.Entry<String, Object> m : params.entrySet()) {
			if (m.getValue() != null && !"".equals(m.getValue()))
				map.put(m.getKey(), m.getValue());
		}
		QueryParams queryParams = QueryParams.createQueryParams("t_kcinfo");
		if (map.get("kcname") != null && !"".equals(map.get("kcname"))) {// 模糊查询
			String kcname = (String) params.get("kcname");
			// 判断是否为中文
			if (BaseChecks.isChineseChar(kcname)) {
				// 是，空格全部替换
				kcname = kcname.replaceAll(" ", "");
			} else {
				// 否，替换两端空格
				kcname = BaseChecks.bothEndsStr(kcname);
			}
			queryParams.addQueryParams(Parameter.createParameter("kcname", EOperators.类似, kcname));
			map.remove("kcname");
		}
		map.put("deleted", "0");
		map.put("used", "0");
		queryParams.addOrderColumns("kcname asc");
		queryParams.addQueryParamsByMap(map);
		queryParams.printSelf();
		List<Map<String, Object>> data = baseDaoComponent.selectDataByParams(queryParams);
		// if (data == null || data.size() == 0) throw new
		// DataNotFoundException();
		JSONObject result = ResponseUtils.createSuccessResponseBodyForJiem("查询成功", data);
		return result;
	}
	

}
