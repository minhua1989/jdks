
package com.xf.jdks.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xf.jdks.dao.bean.DeleteParams;
import com.xf.jdks.dao.bean.InsertParams;
import com.xf.jdks.dao.bean.Parameter;
import com.xf.jdks.dao.bean.QueryParams;
import com.xf.jdks.dao.bean.UpdateParams;
import com.xf.jdks.dao.entity.AdminInfo;
import com.xf.jdks.dao.entity.KcKsccInfo;
import com.xf.jdks.dao.entity.KsccmgInfo;
import com.xf.jdks.dao.entity.KwymgInfo;
import com.xf.jdks.dao.entity.T_kckscc_rel;
import com.xf.jdks.dao.entity.UserInfo;
import com.xf.jdks.service.KsccmgService;
import com.xf.jdks.service.KwymgService;
import com.xf.jdks.commons.componet.*;
import com.xf.jdks.commons.enums.EOperators;
import com.xf.jdks.commons.global.DataMap;
import com.xf.jdks.commons.util.*;
import com.xf.jdks.dao.*;
@Service
public class KsccmgServiceImpl implements KsccmgService {

	@Autowired
	private BaseDaoComponent baseDaoComponent;
	
	@Autowired
	private KsccmgDao ksccmgDao;
	
	
	@Override
	public JSONObject add(Map map) throws SQLException {		
			String Id = UUID.randomUUID().toString();
			String kscc= (String) map.get("kscc");
			String starttime= (String) map.get("starttime");
			String endtime= (String) map.get("endtime");
			String dj= (String) map.get("dj");
			String kszcll= (String) map.get("lljk");
			String kszcsc= (String) map.get("scks");
			String danxfs= (String) map.get("danxfs");
			String danxs= (String) map.get("danxs");
			String duoxfs= (String) map.get("duoxfs");
			String duoxs= (String) map.get("duoxs");
			String pdtfs= (String) map.get("pdtfs");
			String pdts= (String) map.get("pdts");
			String tmgz= (String) map.get("tmgz");
			String dagz= (String) map.get("dagz");
			String kslx= (String) map.get("kslx");
			//String sydj= (String) map.get("sydj");
			String zfs= (String) map.get("zfs");
			String ksrq= (String) map.get("ksrq");
			String nf= (String) map.get("nf");
			InsertParams insertParams = InsertParams.createInsertParams("T_ksccmg", "id", "kscc", "starttime","endtime",
					"danxfs","danxs","duoxfs","duoxs","pdtfs","pdts","addtime","used","kszcll","kszcsc","zfs","dj","ksrq","nf","kslx");
			insertParams.setValues(Id, kscc, starttime,endtime,danxfs,danxs,duoxfs,duoxs,pdtfs,pdts,Format.getDateTime(),0,kszcll,kszcsc,zfs,dj,ksrq,nf,kslx);
			baseDaoComponent.insertDataByParams(insertParams);
		return ResponseUtils.createSuccessResponseBodyForJiem("添加成功");
	}
	
	@Override
	public JSONObject addKcinfo(Map map) throws SQLException {	
			String[] kcidsadd = null;
			if(null!=map.get("kcidsadd")&&!"".equals(map.get("kcidsadd"))){
				kcidsadd =((String)map.get("kcidsadd")).split(",");
			}
			String[] kcidsrm = null;
			if(null!=map.get("kcidsrm")&&!"".equals(map.get("kcidsrm"))){
				kcidsrm =((String)map.get("kcidsrm")).split(",");
			}
			String ksccid= (String) map.get("ksccid");
	    	AdminInfo adminInfo = (AdminInfo) map.remove("admininfo");
	    	if(null!=kcidsrm){
	            DeleteParams deleteParams1 = DeleteParams.createDeleteParams("t_kckwy_rel", Parameter.createParameter("KCID",EOperators.包含,kcidsrm));
	            baseDaoComponent.deleteDataByParams(deleteParams1);
	            DeleteParams deleteParams = DeleteParams.createDeleteParams("t_kckscc_rel", Parameter.createParameter("KCID",EOperators.包含,kcidsrm));
	            baseDaoComponent.deleteDataByParams(deleteParams);
		        UpdateParams updateParams = UpdateParams.createUpdateParams("t_user_kscc");
		        updateParams.addParam(Parameter.createParameter("KCID", ""));
		        updateParams.addParam(Parameter.createParameter("ZWH", ""));
		        updateParams.addParam(Parameter.createParameter("KSXTZKZH", ""));
		        updateParams.addWhereParameter(Parameter.createParameter("KCID",EOperators.包含,kcidsrm));
		        baseDaoComponent.updateDataByParams(updateParams);
	    	}
	    	if(null!=kcidsadd){
				for(int i=0;i<kcidsadd.length;i++){
					String Id = UUID.randomUUID().toString();
					InsertParams insertParams = InsertParams.createInsertParams("t_kckscc_rel", "id", "kcid", "ksccid","deleted",
							"addtime","adduserid");
					insertParams.setValues(Id, kcidsadd[i], ksccid,"0",Format.getDateTime(),adminInfo.getId());
					baseDaoComponent.insertDataByParams(insertParams);
				}
	    	}
		return ResponseUtils.createSuccessResponseBodyForJiem("操作成功");
	}
	
	@Override
	public JSONObject searchOne(Map map) throws SQLException {		
		String id = (String) map.get("id");
		List<Map<String, Object>> mapList =new ArrayList<Map<String, Object>>();
			QueryParams queryParams = QueryParams.createQueryParams("v_ksccmg");
			queryParams.addQueryParams(Parameter.createParameter("id", id));
			mapList= baseDaoComponent.selectDataByParams(queryParams);
		return ResponseUtils.createSuccessResponseBodyForJiem("查询成功",mapList.get(0));
	}
	
	
    @Override
    public JSONObject pageList(Map<String, Object> params) throws SQLException {
		AdminInfo adminInfo = (AdminInfo) params.remove("admininfo");
		String roleid=adminInfo.getRoleid();
    	params.remove("userinfo");
    	String dj=(String) params.get("dj");
    	String nf=(String) params.get("nf");
    	String ksrq=(String) params.get("ksrq");
        Map<String, Object> map = new DataMap<>();
        for (Map.Entry<String, Object> m : params.entrySet()) {
            if (m.getValue() != null && !"".equals(m.getValue())) map.put(m.getKey(), m.getValue());
        }
        QueryParams queryParams = QueryParams.createQueryParams("v_ksccmg");
        if (map.get("kscc") != null && !"".equals(map.get("kscc"))) {//模糊查询
            String kscc = (String) params.get("kscc");
            //判断是否为中文
            if(BaseChecks.isChineseChar(kscc)){
                //是，空格全部替换
            	kscc = kscc.replaceAll(" ","");
            }else{
                //否，替换两端空格
            	kscc = BaseChecks.bothEndsStr(kscc);
            }
            queryParams.addQueryParams(Parameter.createParameter("kscc", EOperators.类似, kscc));
            
            map.remove("kscc");
        }
        map.put("deleted", "0");
        queryParams.addQueryParamsByMap(map);
        if("3".equals(roleid)){//考务员
            QueryParams queryParamsKWY = QueryParams.createQueryParams("v_kwyname");
            queryParamsKWY.addQueryParams(Parameter.createParameter("kwyid", adminInfo.getId()));
            queryParamsKWY.printSelf();
            List<Map<String, Object>> dataKWY = baseDaoComponent.selectDataByParams(queryParamsKWY);
            String ksccids="";
            for(int j=0;j<dataKWY.size();j++){
            	if(j!=dataKWY.size()-1){
            		ksccids=ksccids+dataKWY.get(j).get("ksccid")+",";
            	}else{
            		ksccids=ksccids+dataKWY.get(j).get("ksccid");
            	}
            }
        		queryParams.addQueryParams(Parameter.createParameter("id", EOperators.包含, ksccids.split(",")));
        }
//        if(starttime!=null && !"".equals(starttime)){
//        	queryParams.addQueryParams(Parameter.createParameter("starttime", EOperators.大于等于, starttime));
//        }
//        if(starttime!=null && !"".equals(starttime)){
//        	queryParams.addQueryParams(Parameter.createParameter("endtime", EOperators.小于等于, endtime));
//        }
        queryParams.printSelf();

        queryParams.addQueryParamsByMap(map);
        List<Map<String, Object>> data = baseDaoComponent.selectDataByParams(queryParams);
//        if (data == null || data.size() == 0) throw new DataNotFoundException();
        JSONObject result = ResponseUtils.createSuccessResponseBodyForJiem("查询成功", data);
		return result;
    }

    @Override
    public JSONObject pageListKcKscc(Map<String, Object> params) throws SQLException {
    	params.remove("admininfo");
    	params.remove("userinfo");
        Map<String, Object> map = new DataMap<>();
        for (Map.Entry<String, Object> m : params.entrySet()) {
            if (m.getValue() != null && !"".equals(m.getValue())) map.put(m.getKey(), m.getValue());
        }
        QueryParams queryParams = QueryParams.createQueryParams("v_kckscc_rel_hm");
        queryParams.addQueryParamsByMap(map);
        queryParams.printSelf();
        List<Map<String, Object>> data = baseDaoComponent.selectDataByParams(queryParams);
//        if (data == null || data.size() == 0) throw new DataNotFoundException();
        JSONObject result = ResponseUtils.createSuccessResponseBodyForJiem("查询成功", data);
		return result;
    }

    @Override
    public JSONObject pageListKcKs(Map<String, Object> params) throws SQLException {
    	params.remove("admininfo");
    	params.remove("userinfo");
        Map<String, Object> map = new DataMap<>();
        for (Map.Entry<String, Object> m : params.entrySet()) {
            if (m.getValue() != null && !"".equals(m.getValue())) map.put(m.getKey(), m.getValue());
        }
        List<Map<String, Object>> data = ksccmgDao.pageListKcKs(map);
//        if (data == null || data.size() == 0) throw new DataNotFoundException();
        JSONObject result = ResponseUtils.createSuccessResponseBodyForJiem("查询成功", data);
		return result;
    }


	@Override
	public JSONObject edit( Map map) throws SQLException {
			String id = (String) map.get("id");
			String kscc= (String) map.get("kscc");
			String dj= (String) map.get("dj");
			String kszcll= (String) map.get("lljk");
			String kszcsc= (String) map.get("scks");
			String starttime= (String) map.get("starttime");
			String endtime= (String) map.get("endtime");
			String danxfs= (String) map.get("danxfs");
			String danxs= (String) map.get("danxs");
			String duoxfs= (String) map.get("duoxfs");
			String duoxs= (String) map.get("duoxs");
			String pdtfs= (String) map.get("pdtfs");
			String pdts= (String) map.get("pdts");
			String tmgz= (String) map.get("tmgz");
			String dagz= (String) map.get("dagz");
			String zfs= (String) map.get("zfs");
			String ksrq= (String) map.get("ksrq");
			String nf= (String) map.get("nf");
			String kslx= (String) map.get("kslx");
	        UpdateParams updateParams = UpdateParams.createUpdateParams("t_ksccmg");
	        updateParams.addParam(Parameter.createParameter("kscc", kscc));
	        updateParams.addParam(Parameter.createParameter("dj", dj));
	        updateParams.addParam(Parameter.createParameter("kszcll", kszcll));
	        updateParams.addParam(Parameter.createParameter("kszcsc", kszcsc));
	        updateParams.addParam(Parameter.createParameter("starttime", starttime));
	        updateParams.addParam(Parameter.createParameter("endtime", endtime));
	        updateParams.addParam(Parameter.createParameter("danxfs", danxfs));
	        updateParams.addParam(Parameter.createParameter("danxs", danxs));
	        updateParams.addParam(Parameter.createParameter("duoxfs", duoxfs));
	        updateParams.addParam(Parameter.createParameter("duoxs", duoxs));
	        updateParams.addParam(Parameter.createParameter("pdtfs", pdtfs));
	        updateParams.addParam(Parameter.createParameter("pdts", pdts));
	        updateParams.addParam(Parameter.createParameter("tmgz", tmgz));
	        updateParams.addParam(Parameter.createParameter("dagz", dagz));
	        updateParams.addParam(Parameter.createParameter("zfs", zfs));
	        updateParams.addParam(Parameter.createParameter("kslx", kslx));
	        updateParams.addParam(Parameter.createParameter("nf", nf));
	        updateParams.addParam(Parameter.createParameter("ksrq", ksrq));
	        updateParams.addWhereParameter(Parameter.createParameter("id", id));
	        baseDaoComponent.updateDataByParams(updateParams);
		return ResponseUtils.createSuccessResponseBodyForJiem("修改成功");
	}


	//逻辑删除
	@Override
	public JSONObject deleted(Map map) throws SQLException {
			String id= (String) map.get("ids");
			String used=(String) map.get("used");
	        String[] ids = null;
	        if (id != null) {
	            if (id.contains(",")) {
	            	ids = id.split(",");
	            } else {
	            	ids = new String[]{id};
	            }
	        }
	        UpdateParams updateParams = UpdateParams.createUpdateParams("T_ksccmg");
	        updateParams.addParam(Parameter.createParameter("used", used));
	        updateParams.addParam(Parameter.createParameter("updatetime", Format.getDateTime()));
	        updateParams.addWhereParameter(Parameter.createParameter("id", EOperators.包含, ids));
	        updateParams.printSelf();
	        baseDaoComponent.updateDataByParams(updateParams);	
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
	        UpdateParams updateParams = UpdateParams.createUpdateParams("T_ksccmg");
	        updateParams.addParam(Parameter.createParameter("deleted", "1"));
	        updateParams.addParam(Parameter.createParameter("deltime", Format.getDateTime()));
	        updateParams.addWhereParameter(Parameter.createParameter("id", EOperators.包含, ids));
	        updateParams.printSelf();
	        baseDaoComponent.updateDataByParams(updateParams);	
		return ResponseUtils.createSuccessResponseBodyForJiem("删除成功");
	}

	//删除考场
	@Override
	public JSONObject deletedKcinfo(Map map) throws SQLException {
			String adminid= (String) map.get("adminid");
			String id= (String) map.get("ids");
	        String[] ids = null;
	        if (id != null) {
	            if (id.contains(",")) {
	            	ids = id.split(",");
	            } else {
	            	ids = new String[]{id};
	            }
	        }
	        UpdateParams updateParams = UpdateParams.createUpdateParams("t_kckscc_rel");
	        updateParams.addParam(Parameter.createParameter("deleted", "1"));
	        updateParams.addParam(Parameter.createParameter("deluserid", adminid));
	        updateParams.addParam(Parameter.createParameter("deltime", Format.getDateTime()));
	        updateParams.addWhereParameter(Parameter.createParameter("id", EOperators.包含, ids));
	        updateParams.printSelf();
	        baseDaoComponent.updateDataByParams(updateParams);	
		return ResponseUtils.createErrorResponseBodyJiem("删除成功");
	}
	







	@Override
		public JSONObject list() throws SQLException {
			QueryParams queryParams = QueryParams.createQueryParams("T_ksccmg");
			queryParams.addQueryParams(Parameter.createParameter("used","0"));
			queryParams.addQueryParams(Parameter.createParameter("deleted","0"));
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				list = baseDaoComponent.selectDataByParams(queryParams);
			return ResponseUtils.createSuccessResponseBodyForJiem("done", list);
		
		}
		
	@Override
	public JSONObject listKcinfo(Map map) {
		QueryParams queryParamKcs = QueryParams.createQueryParams("t_kckscc_rel");
		queryParamKcs.addQueryParams(Parameter.createParameter("deleted","0"));
		queryParamKcs.addQueryParams(Parameter.createParameter("ksccid",map.get("ksccid")));
		List<Map<String, Object>> listKcs = new ArrayList<Map<String, Object>>();
		try {
			listKcs = baseDaoComponent.selectDataByParams(queryParamKcs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String kcids="";
		if(listKcs.size()!=0){
			for(int i=0;i<listKcs.size();i++){
				if(i==0){
					kcids=kcids+listKcs.get(i).get("kcid");
				}else{
					kcids=kcids+","+listKcs.get(i).get("kcid");
				}
			}
		}
		QueryParams queryParams = QueryParams.createQueryParams("t_kcinfo");
		queryParams.addQueryParams(Parameter.createParameter("deleted","0"));
		queryParams.addQueryParams(Parameter.createParameter("id",EOperators.不包含,kcids.split(",")));
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = baseDaoComponent.selectDataByParams(queryParams);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseUtils.createSuccessResponseBodyForJiem("done", list);
	
	}
	

	
	
    @Override
    public JSONObject kskcpageList(Map<String, Object> params) throws SQLException {
    	params.remove("admininfo");
    	params.remove("userinfo");
        Map<String, Object> map = new DataMap<>();
        for (Map.Entry<String, Object> m : params.entrySet()) {
            if (m.getValue() != null && !"".equals(m.getValue())) map.put(m.getKey(), m.getValue());
        }
        QueryParams queryParams = QueryParams.createQueryParams("v_kskckwy");
        if (map.get("kcname") != null && !"".equals(map.get("kcname"))) {//模糊查询
            String kcname = (String) params.get("kcname");
            //判断是否为中文
            if(BaseChecks.isChineseChar(kcname)){
                //是，空格全部替换
            	kcname = kcname.replaceAll(" ","");
            }else{
                //否，替换两端空格
            	kcname = BaseChecks.bothEndsStr(kcname);
            }
            queryParams.addQueryParams(Parameter.createParameter("kcname", EOperators.类似, kcname));
            
            map.remove("kcname");
        }
        if (map.get("kwymc") != null && !"".equals(map.get("kwymc"))) {//模糊查询
            String kwymc = (String) params.get("kwymc");
            //判断是否为中文
            if(BaseChecks.isChineseChar(kwymc)){
                //是，空格全部替换
            	kwymc = kwymc.replaceAll(" ","");
            }else{
                //否，替换两端空格
            	kwymc = BaseChecks.bothEndsStr(kwymc);
            }
            queryParams.addQueryParams(Parameter.createParameter("kwymc", EOperators.类似, kwymc));
            
            map.remove("kwymc");
        }
        queryParams.addQueryParamsByMap(map);

        queryParams.printSelf();
        queryParams.addQueryParamsByMap(map);
        List<Map<String, Object>> data = baseDaoComponent.selectDataByParams(queryParams);
        JSONObject result = ResponseUtils.createSuccessResponseBodyForJiem("查询成功", data);
		return result;
    }
	
	

}
