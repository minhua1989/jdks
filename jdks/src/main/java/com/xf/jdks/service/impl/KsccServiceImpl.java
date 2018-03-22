package com.xf.jdks.service.impl;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.transaction.Transactional;

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
import com.xf.jdks.dao.KsccDao;
import com.xf.jdks.dao.SttitleDao;
import com.xf.jdks.dao.bean.DeleteParams;
import com.xf.jdks.dao.bean.InsertParams;
import com.xf.jdks.dao.bean.Parameter;
import com.xf.jdks.dao.bean.QueryParams;
import com.xf.jdks.dao.bean.UpdateParams;
import com.xf.jdks.dao.entity.AdminInfo;
import com.xf.jdks.dao.entity.Kscc;
import com.xf.jdks.dao.entity.Sttitle;
import com.xf.jdks.dao.entity.UserInfo;
import com.xf.jdks.dao.entity.VwUserKscc;
import com.xf.jdks.service.KsccService;

@Service
public class KsccServiceImpl implements KsccService {

	@Autowired
	private BaseDaoComponent baseDaoComponent;

	@Autowired
	private KsccDao ksccDao;

	@Override
	public JSONObject pageList(Map<String, Object> params) throws SQLException {
		params.remove("admininfo");
		params.remove("userinfo");
		Map<String, Object> map = new DataMap<>();
		for (Map.Entry<String, Object> m : params.entrySet()) {
			if (m.getValue() != null && !"".equals(m.getValue()))
				map.put(m.getKey(), m.getValue());
		}
		QueryParams queryParams = QueryParams.createQueryParams("v_user_kscc_hm");
		if (map.get("kscc") != null && !"".equals(map.get("kscc"))) {// 模糊查询
			String kscc = (String) params.get("kscc");
			// 判断是否为中文
			if (BaseChecks.isChineseChar(kscc)) {
				// 是，空格全部替换
				kscc = kscc.replaceAll(" ", "");
			} else {
				// 否，替换两端空格
				kscc = BaseChecks.bothEndsStr(kscc);
			}
			queryParams.addQueryParams(Parameter.createParameter("kscc", EOperators.类似, kscc));
			map.remove("kscc");
		}
		queryParams.addQueryParamsByMap(map);
		queryParams.printSelf();
		List<Map<String, Object>> data = baseDaoComponent.selectDataByParams(queryParams);
		// if (data == null || data.size() == 0) throw new
		// DataNotFoundException();
		JSONObject result = ResponseUtils.createSuccessResponseBodyForJiem("查询成功", data);
		return result;
	}

	// 已弃用
	@Override
	public JSONObject checkKsTime(Map map) throws SQLException {
		String id = (String) map.get("id");
		QueryParams queryParams = QueryParams.createQueryParams("T_KSCCMG");
		queryParams.addQueryParams(Parameter.createParameter("id", id));
		List<Map<String, Object>> optionHaslist = null;
		try {
			optionHaslist = baseDaoComponent.selectDataByParams(queryParams);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String startTime = (String) optionHaslist.get(0).get("starttime");
		String endTime = (String) optionHaslist.get(0).get("endtime");
		String tmgz = (String) optionHaslist.get(0).get("tmgz");// 1题目相同顺序相同2题目相同顺序不同3题目不同
		String dagz = (String) optionHaslist.get(0).get("dagz");// 1答案顺序一致2答案顺序不一致
		String danxs = (String) optionHaslist.get(0).get("danxs");// 单选题数
		String duoxs = (String) optionHaslist.get(0).get("duoxs");// 多选题数
		String pdts = (String) optionHaslist.get(0).get("pdts");// 判断题数
		String sydj = (String) optionHaslist.get(0).get("dj");// 适用等级
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");// 小写的mm表示的是分钟
		java.util.Date startDate = null;
		java.util.Date endDate = null;
		java.util.Date nowDate = null;
		String nowTime = Format.getDateTime();
		try {
			startDate = sdf.parse(startTime);
			endDate = sdf.parse(endTime);
			nowDate = sdf.parse(nowTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map<String, Object> timeMap = new HashMap<>();
		timeMap.put("tmgz", tmgz);
		timeMap.put("dagz", dagz);
		timeMap.put("starttime", startTime);
		timeMap.put("endtime", endTime);
		if (nowDate.equals(startDate) || nowDate.equals(endDate)) {
			timeMap.put("time", "1");// 1.代表在范围内
		} else {
			if (nowDate.after(startDate) && nowDate.before(endDate)) {
				timeMap.put("time", "1");// 1.代表在范围内
			} else {
				timeMap.put("time", "0");// 0.代表不在范围内
			}
		}
		if ("1".equals(timeMap.get("time"))) {
			QueryParams queryParamsKs = QueryParams.createQueryParams("t_user_kscc");
			queryParamsKs.addQueryParams(Parameter.createParameter("userid", map.get("userid")));
			queryParamsKs.addQueryParams(Parameter.createParameter("ksccid", id));
			List<Map<String, Object>> ksKsccList = null;
			try {
				ksKsccList = baseDaoComponent.selectDataByParams(queryParamsKs);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if ("0".equals(ksKsccList.get(0).get("sfdl"))) {
				// 题目相同
				if ("1".equals(tmgz) || "2".equals(tmgz)) {
					QueryParams queryParamSJXX = QueryParams.createQueryParams("T_KSSJ");
					queryParamSJXX.addQueryParams(Parameter.createParameter("KSCCID", id));
					List<Map<String, Object>> sjxxList = null;
					sjxxList = baseDaoComponent.selectDataByParams(queryParamSJXX);
					if (sjxxList.size() > 0) {
						// 单选
						QueryParams queryParamDANX = QueryParams.createQueryParams("T_KSSJ");
						queryParamDANX.addQueryParams(Parameter.createParameter("KSCCID", id));
						queryParamDANX.addQueryParams(Parameter.createParameter("STLX", "1"));
						List<Map<String, Object>> danxuanti = null;
						danxuanti = baseDaoComponent.selectDataByParams(queryParamDANX);
						List<Map<String, Object>> danxuantiRs = new ArrayList<Map<String, Object>>();
						for (int i = 0; i < danxuanti.size(); i++) {
							Map<String, Object> tmpdxMap = danxuanti.get(i);
							QueryParams queryParamDA = QueryParams.createQueryParams("T_OPTIONS");
							queryParamDA.addQueryParams(
									Parameter.createParameter("QUESTION_ID", danxuanti.get(i).get("stid")));
							queryParamDA.addQueryParams(Parameter.createParameter("DELETED", "0"));
							if ("1".equals(dagz)) {
								queryParamDA.addOrderColumns("xuhao asc");
							}
							List<Map<String, Object>> danxDA = null;
							danxDA = baseDaoComponent.selectDataByParams(queryParamDA);
							tmpdxMap.put("answer", danxDA);
							danxuantiRs.add(tmpdxMap);
						}
						timeMap.put("danxuanti", danxuantiRs);
						// 多选
						QueryParams queryParamDUOX = QueryParams.createQueryParams("T_KSSJ");
						queryParamDUOX.addQueryParams(Parameter.createParameter("KSCCID", id));
						queryParamDUOX.addQueryParams(Parameter.createParameter("STLX", "2"));
						List<Map<String, Object>> duoxuanti = null;
						duoxuanti = baseDaoComponent.selectDataByParams(queryParamDUOX);
						List<Map<String, Object>> duoxuantiRs = new ArrayList<Map<String, Object>>();
						for (int i = 0; i < duoxuanti.size(); i++) {
							Map<String, Object> tmpdxMap = duoxuanti.get(i);
							QueryParams queryParamDA = QueryParams.createQueryParams("T_OPTIONS");
							queryParamDA.addQueryParams(
									Parameter.createParameter("QUESTION_ID", duoxuanti.get(i).get("stid")));
							queryParamDA.addQueryParams(Parameter.createParameter("DELETED", "0"));
							if ("1".equals(dagz)) {
								queryParamDA.addOrderColumns("xuhao asc");
							}
							List<Map<String, Object>> duoxDA = null;
							duoxDA = baseDaoComponent.selectDataByParams(queryParamDA);
							tmpdxMap.put("answer", duoxDA);
							duoxuantiRs.add(tmpdxMap);
						}
						timeMap.put("duoxuanti", duoxuantiRs);
						// 判断
						QueryParams queryParamPD = QueryParams.createQueryParams("T_KSSJ");
						queryParamPD.addQueryParams(Parameter.createParameter("KSCCID", id));
						queryParamPD.addQueryParams(Parameter.createParameter("STLX", "3"));
						List<Map<String, Object>> panduanti = null;
						panduanti = baseDaoComponent.selectDataByParams(queryParamPD);
						timeMap.put("panduanti", panduanti);
					} else {
						timeMap = createkssjxx(timeMap, danxs, duoxs, pdts, sydj, id, "1", dagz);
					}
				} else {
					timeMap = createkssjxx(timeMap, danxs, duoxs, pdts, sydj, id, "0", dagz);
				}
				if (timeMap.get("errormsg") == null) {
					UpdateParams updateParamsKsCC = UpdateParams.createUpdateParams("t_user_kscc");
					updateParamsKsCC.addParam(Parameter.createParameter("sfdl", "1"));
					updateParamsKsCC.addWhereParameter(Parameter.createParameter("id", ksKsccList.get(0).get("id")));
					baseDaoComponent.updateDataByParams(updateParamsKsCC);
				}
			} else {
				timeMap.put("sfdl", "1");
			}
		}
		return ResponseUtils.createSuccessResponseBodyForJiem("done", timeMap);
	}

	// 已弃用
	// 创建考试试卷type为1需要新增，type为0不需要新增
	private Map<String, Object> createkssjxx(Map<String, Object> timeMap, String danxs, String duoxs, String pdts,
			String sydj, String ksccid, String tmtype, String dagz) {
		Map<String, Object> stdanxMap = new HashMap<String, Object>();
		Map<String, Object> stduoxMap = new HashMap<String, Object>();
		// 单选
		stdanxMap.put("stlx", "1");
		stdanxMap.put("stno", Integer.parseInt(danxs));
		stdanxMap.put("sydj", sydj);
		List<Map<String, Object>> danxuanti = ksccDao.queryST(stdanxMap);
		if (Integer.parseInt(danxs) > danxuanti.size()) {
			timeMap.put("errormsg", "题库中单选题数目小于该考场的题目数");
			return timeMap;
		}
		// 多选
		stduoxMap.put("stlx", "2");
		stduoxMap.put("stno", Integer.parseInt(duoxs));
		stduoxMap.put("sydj", sydj);
		List<Map<String, Object>> duoxuanti = ksccDao.queryST(stduoxMap);
		if (Integer.parseInt(duoxs) > duoxuanti.size()) {
			timeMap.put("errormsg", "题库中多选题数目小于该考场的题目数");
			return timeMap;
		}
		// 判断
		stdanxMap.put("stlx", "3");
		stdanxMap.put("stno", Integer.parseInt(pdts));
		stdanxMap.put("sydj", sydj);
		List<Map<String, Object>> panduanti = ksccDao.queryST(stdanxMap);
		if (Integer.parseInt(pdts) > panduanti.size()) {
			timeMap.put("errormsg", "题库中判断题数目小于该考场的题目数");
			return timeMap;
		}
		List<Map<String, Object>> danxuantiRs = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < danxuanti.size(); i++) {
			Map<String, Object> tmpdxMap = danxuanti.get(i);
			danxuanti.get(i).put("sttitle", danxuanti.get(i).get("title"));
			danxuanti.get(i).put("stid", danxuanti.get(i).get("id"));
			danxuanti.get(i).put("stxh", i + 1);
			QueryParams queryParamDA = QueryParams.createQueryParams("T_OPTIONS");
			queryParamDA.addQueryParams(Parameter.createParameter("QUESTION_ID", danxuanti.get(i).get("id")));
			queryParamDA.addQueryParams(Parameter.createParameter("DELETED", "0"));
			if ("1".equals(dagz)) {
				queryParamDA.addOrderColumns("xuhao asc");
			}
			List<Map<String, Object>> danxDA = null;
			try {
				danxDA = baseDaoComponent.selectDataByParams(queryParamDA);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			tmpdxMap.put("answer", danxDA);
			danxuantiRs.add(tmpdxMap);
			Map<String, Object> kssjMap = new HashMap<String, Object>();
			kssjMap.put("id", UUID.randomUUID().toString());
			kssjMap.put("stid", tmpdxMap.get("id"));
			String title = (String) tmpdxMap.get("title");
			title = title.replaceAll("\\\\", "\\\\\\\\");
			kssjMap.put("sttitle", title);
			kssjMap.put("stxh", i + 1);
			kssjMap.put("stlx", "1");
			kssjMap.put("deleted", "0");
			kssjMap.put("ksccid", ksccid);
			if ("1".equals(tmtype)) {
				InsertParams insertST = InsertParams.createInsertParams("T_KSSJ");
				insertST.addParamsForMap(kssjMap);
				try {
					baseDaoComponent.insertDataByParams(insertST);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		timeMap.put("danxuanti", danxuantiRs);
		// 多选
		List<Map<String, Object>> duoxuantiRs = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < duoxuanti.size(); i++) {
			Map<String, Object> tmpdxMap = duoxuanti.get(i);
			duoxuanti.get(i).put("sttitle", duoxuanti.get(i).get("title"));
			duoxuanti.get(i).put("stid", duoxuanti.get(i).get("id"));
			duoxuanti.get(i).put("stxh", i + 1);
			QueryParams queryParamDA = QueryParams.createQueryParams("T_OPTIONS");
			queryParamDA.addQueryParams(Parameter.createParameter("QUESTION_ID", duoxuanti.get(i).get("id")));
			queryParamDA.addQueryParams(Parameter.createParameter("DELETED", "0"));
			if ("1".equals(dagz)) {
				queryParamDA.addOrderColumns("xuhao asc");
			}
			List<Map<String, Object>> duoxDA = null;
			try {
				duoxDA = baseDaoComponent.selectDataByParams(queryParamDA);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			tmpdxMap.put("answer", duoxDA);
			duoxuantiRs.add(tmpdxMap);
			Map<String, Object> kssjMap = new HashMap<String, Object>();
			kssjMap.put("id", UUID.randomUUID().toString());
			kssjMap.put("stid", tmpdxMap.get("id"));
			String title = (String) tmpdxMap.get("title");
			title = title.replaceAll("\\\\", "\\\\\\\\");
			kssjMap.put("sttitle", title);
			kssjMap.put("stxh", i + 1);
			kssjMap.put("stlx", "2");
			kssjMap.put("deleted", "0");
			kssjMap.put("ksccid", ksccid);
			if ("1".equals(tmtype)) {
				InsertParams insertST = InsertParams.createInsertParams("T_KSSJ");
				insertST.addParamsForMap(kssjMap);
				try {
					baseDaoComponent.insertDataByParams(insertST);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		timeMap.put("duoxuanti", duoxuantiRs);
		// 判断
		for (int i = 0; i < panduanti.size(); i++) {
			Map<String, Object> tmpdxMap = panduanti.get(i);
			panduanti.get(i).put("sttitle", danxuanti.get(i).get("title"));
			panduanti.get(i).put("stid", danxuanti.get(i).get("id"));
			panduanti.get(i).put("stxh", i + 1);
			Map<String, Object> kssjMap = new HashMap<String, Object>();
			kssjMap.put("id", UUID.randomUUID().toString());
			kssjMap.put("stid", tmpdxMap.get("id"));
			String title = (String) tmpdxMap.get("title");
			title = title.replaceAll("\\\\", "\\\\\\\\");
			kssjMap.put("sttitle", title);
			kssjMap.put("stxh", i + 1);
			kssjMap.put("stlx", "3");
			kssjMap.put("pdda", tmpdxMap.get("pdda"));
			kssjMap.put("deleted", "0");
			kssjMap.put("ksccid", ksccid);
			if ("1".equals(tmtype)) {
				InsertParams insertST = InsertParams.createInsertParams("T_KSSJ");
				insertST.addParamsForMap(kssjMap);
				try {
					baseDaoComponent.insertDataByParams(insertST);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		timeMap.put("panduanti", panduanti);
		return timeMap;
	}

	// 保存试卷
	@Override
	public JSONObject saveKssjda(Map map) throws SQLException {
		UserInfo userinfo = (UserInfo) map.remove("userinfo");
		// 判断
		List<Map<String, Object>> panduanti = (List<Map<String, Object>>) map.get("panduanti");
		for (int i = 0; i < panduanti.size(); i++) {
			Map<String, Object> tmpMap = panduanti.get(i);
			Map<String, Object> kssjxxMap = new HashMap<String, Object>();
			String xuanxiangmc = "";
			if ("1".equals(tmpMap.get("xsda"))) {
				xuanxiangmc = "正确";
			} else if ("0".equals(tmpMap.get("xsda"))) {
				xuanxiangmc = "错误";
			}
			kssjxxMap.put("xuanxiangmc", xuanxiangmc);
			kssjxxMap.put("xsda", tmpMap.get("xsda"));
			kssjxxMap.put("updatetime", Format.getDateTime());
			kssjxxMap.put("updateuserid", userinfo.getId());
			kssjxxMap.put("sftjsj", "1");
			UpdateParams updateParams = UpdateParams.createUpdateParams("T_KSSJXX");
			updateParams.addParamsForMap(kssjxxMap);
			updateParams.addWhereParameter(Parameter.createParameter("id", tmpMap.get("id")));
			baseDaoComponent.updateDataByParams(updateParams);
		}
		List<Map<String, Object>> danxuanti = (List<Map<String, Object>>) map.get("danxuanti");
		for (int i = 0; i < danxuanti.size(); i++) {
			Map<String, Object> tmpMap = danxuanti.get(i);
			for (int j = 0; j < ((List<Map<String, Object>>) tmpMap.get("answer")).size(); j++) {
				Map<String, Object> tmpxxMap = ((List<Map<String, Object>>) tmpMap.get("answer")).get(j);
				Map<String, Object> kssjxxMap = new HashMap<String, Object>();
				kssjxxMap.put("xsda", tmpxxMap.get("xsda"));
				kssjxxMap.put("updatetime", Format.getDateTime());
				kssjxxMap.put("updateuserid", userinfo.getId());
				kssjxxMap.put("sftjsj", "1");
				UpdateParams updateParams = UpdateParams.createUpdateParams("T_KSSJXX");
				updateParams.addParamsForMap(kssjxxMap);
				updateParams.addWhereParameter(Parameter.createParameter("id", tmpxxMap.get("id")));
				baseDaoComponent.updateDataByParams(updateParams);
			}
		}
		List<Map<String, Object>> duoxuanti = (List<Map<String, Object>>) map.get("duoxuanti");
		for (int i = 0; i < duoxuanti.size(); i++) {
			Map<String, Object> tmpMap = duoxuanti.get(i);
			for (int j = 0; j < ((List<Map<String, Object>>) tmpMap.get("answer")).size(); j++) {
				Map<String, Object> tmpxxMap = ((List<Map<String, Object>>) tmpMap.get("answer")).get(j);
				Map<String, Object> kssjxxMap = new HashMap<String, Object>();
				kssjxxMap.put("xsda", tmpMap.get("xsda"));
				kssjxxMap.put("updatetime", Format.getDateTime());
				kssjxxMap.put("updateuserid", userinfo.getId());
				kssjxxMap.put("sftjsj", "1");
				UpdateParams updateParams = UpdateParams.createUpdateParams("T_KSSJXX");
				updateParams.addParamsForMap(kssjxxMap);
				updateParams.addWhereParameter(Parameter.createParameter("id", tmpxxMap.get("id")));
				baseDaoComponent.updateDataByParams(updateParams);
			}
		}
		List<Map<String, Object>> caozuoti = (List<Map<String, Object>>) map.get("caozuoti");
		for (int i = 0; i < caozuoti.size(); i++) {
			Map<String, Object> tmpMap = caozuoti.get(i);
			Map<String, Object> kssjxxMap = new HashMap<String, Object>();
			kssjxxMap.put("updatetime", Format.getDateTime());
			kssjxxMap.put("updateuserid", userinfo.getId());
			kssjxxMap.put("sftjsj", "1");
			UpdateParams updateParams = UpdateParams.createUpdateParams("T_KSSJXX");
			updateParams.addParamsForMap(kssjxxMap);
			updateParams.addWhereParameter(Parameter.createParameter("id", tmpMap.get("id")));
			baseDaoComponent.updateDataByParams(updateParams);
		}
		JSONObject rs = ResponseUtils.createSuccessResponseBodyForJiem("提交成功");
		return rs;
	}

	@Override
	public JSONObject createKssj(Map map) throws Exception {
		AdminInfo adminInfo = (AdminInfo) map.remove("admininfo");
		String id = (String) map.get("id");
		QueryParams queryParams = QueryParams.createQueryParams("T_KSCCMG");// 获得考试场次信息
		queryParams.addQueryParams(Parameter.createParameter("id", id));
		List<Map<String, Object>> optionHaslist = null;
		optionHaslist = baseDaoComponent.selectDataByParams(queryParams);
		String startTime = (String) optionHaslist.get(0).get("starttime");
		String endTime = (String) optionHaslist.get(0).get("endtime");
		String tmgz = (String) optionHaslist.get(0).get("tmgz");// 1题目相同顺序相同2题目相同顺序不同3题目不同
		String dagz = (String) optionHaslist.get(0).get("dagz");// 1答案顺序一致2答案顺序不一致
		String sydj = (String) optionHaslist.get(0).get("dj");// 适用等级
		String kslx = (String) optionHaslist.get(0).get("kslx");// 考试类型
		String czts = (String) optionHaslist.get(0).get("czts");// 操作题数
		QueryParams queryParamsKs = QueryParams.createQueryParams("t_user_kscc");// 获得该考试场次考生list
		queryParamsKs.addQueryParams(Parameter.createParameter("ksccid", id));
		List<Map<String, Object>> ksKsccList = baseDaoComponent.selectDataByParams(queryParamsKs);
		QueryParams queryParamSJ = QueryParams.createQueryParams("T_KSSJ");// 查询T_KSSJ中是否有该考场的试题，有都删除
		queryParamSJ.addQueryParams(Parameter.createParameter("KSCCID", id));
		List<Map<String, Object>> sjList = null;
		sjList = baseDaoComponent.selectDataByParams(queryParamSJ);
		if (sjList.size() > 0) {
			DeleteParams deleteParams = DeleteParams.createDeleteParams("T_KSSJ",
					Parameter.createParameter("KSCCID", id));
			baseDaoComponent.deleteDataByParams(deleteParams);
		}
		QueryParams queryParamSJXX = QueryParams.createQueryParams("T_KSSJXX");// 查询T_KSSJXX中是否有该考场的试题，有都删除
		queryParamSJXX.addQueryParams(Parameter.createParameter("KSCCID", id));
		List<Map<String, Object>> sjxxList = null;
		sjxxList = baseDaoComponent.selectDataByParams(queryParamSJXX);
		if (sjxxList.size() > 0) {
			DeleteParams deleteParams = DeleteParams.createDeleteParams("T_KSSJXX",
					Parameter.createParameter("KSCCID", id));
			baseDaoComponent.deleteDataByParams(deleteParams);
		}
		if (ksKsccList.size() == 0) {
			throw new Exception("该考试没有试卷需要生成");
		}
		// 题目相同
		if ("1".equals(tmgz) || "2".equals(tmgz)) {
			for (int i = 0; i < ksKsccList.size(); i++) {
				String ksid = (String) ksKsccList.get(i).get("userid");
				if (i == 0) {
					createkssjxxInsertKSSJ(sydj, id, dagz, kslx, adminInfo);
					createkssjxxInsertKSSJXX(sydj, id, dagz, ksid, tmgz, adminInfo);
				} else {
					createkssjxxInsertKSSJXX(sydj, id, dagz, ksid, tmgz, adminInfo);
				}
			}
		} else {
			for (int i = 0; i < ksKsccList.size(); i++) {
				System.out.println("-----" + i);
				String ksid = (String) ksKsccList.get(i).get("userid");
				createkssjxxInsertKSSJXXNoKSSJ(sydj, id, dagz, ksid, kslx, adminInfo);
			}
		}

		return ResponseUtils.createSuccessResponseBodyForJiem("成功生成" + ksKsccList.size() + "份考生试卷");
	}

	// 生成考试试卷T_KSSJXX
	private void createkssjxxInsertKSSJXXNoKSSJ(String sydj, String ksccid, String dagz, String ksid, String kslx,
			AdminInfo adminInfo) throws Exception {

		if (kslx.equals("L")) {// 理论
			QueryParams queryParamsTK = QueryParams.createQueryParams("T_TK");// 题库
			queryParamsTK.addQueryParams(Parameter.createParameter("kslx", kslx));
			queryParamsTK.addQueryParams(Parameter.createParameter("sydj", sydj));
			List<Map<String, Object>> tkList = baseDaoComponent.selectDataByParams(queryParamsTK);
			for (int i = 0; i < tkList.size(); i++) {// 循环题库
				Map<String, Object> eachTKList = tkList.get(i);
				String outnum = (String) eachTKList.get("outnum");
				String tkid = (String) eachTKList.get("id");
				String stlx = (String) eachTKList.get("stlx");
				Map<String, Object> stMap = new HashMap<String, Object>();
				stMap.put("tkid", tkid);
				stMap.put("stlx", stlx);
				stMap.put("outnum", Integer.valueOf(outnum));
				List<Map<String, Object>> klnoList = ksccDao.queryklno(stMap);// 查询知识点
				if (klnoList.size() < Integer.valueOf(outnum)) {
					throw new Exception("理论题中知识点类别总数小于需要出题数");
				}
				for (int j = 0; j < klnoList.size(); j++) {// 每个知识点出一题
					String klno = (String) klnoList.get(j).get("klno");
					int klnolen = klno.length();
					Map<String, Object> stMap1 = new HashMap<String, Object>();
					stMap1.put("tkid", tkid);
					stMap1.put("stlx", stlx);
					stMap1.put("klno", klno + ".");
					stMap1.put("sydj", sydj);
					stMap1.put("klnolen", klnolen + 1);
					List<Map<String, Object>> stList = ksccDao.queryst1(stMap1);
					int beginno = getBeginNo(stList.size(), 0);
					int endno = beginno + 1;
					Map<String, Object> stMap2 = new HashMap<String, Object>();
					stMap2.put("beginno", beginno);
					stMap2.put("endno", endno);
					stMap2.put("tkid", tkid);
					stMap2.put("stlx", stlx);
					stMap2.put("klno", klno + ".");
					stMap2.put("sydj", sydj);
					stMap2.put("klnolen", klnolen + 1);
					List<Map<String, Object>> stList2 = ksccDao.queryst2(stMap2);
					// INSERT T_KSSJ
					if ("1".equals(stlx)) {// 单选
						Map<String, Object> tmpdxMap = stList2.get(0);
						QueryParams queryParamDA = QueryParams.createQueryParams("T_OPTIONS");
						queryParamDA.addQueryParams(Parameter.createParameter("QUESTION_ID", stList2.get(0).get("id")));
						queryParamDA.addQueryParams(Parameter.createParameter("DELETED", "0"));
						if ("1".equals(dagz)) {
							queryParamDA.addOrderColumns("xuhao asc");
						}
						List<Map<String, Object>> danxDA = null;
						danxDA = baseDaoComponent.selectDataByParams(queryParamDA);
						if (!"1".equals(dagz)) {
							Collections.shuffle(danxDA);
						}
						for (int k = 0; k < danxDA.size(); k++) {
							Map<String, Object> danxDAMap = danxDA.get(k);
							Map<String, Object> kssjxxMap = new HashMap<String, Object>();
							kssjxxMap.put("id", UUID.randomUUID().toString());
							kssjxxMap.put("stid", tmpdxMap.get("id"));
							kssjxxMap.put("sttitle", tmpdxMap.get("title"));
							kssjxxMap.put("xuanxiangid", danxDAMap.get("id"));
							kssjxxMap.put("xuanxiangmc", danxDAMap.get("qoption"));
							kssjxxMap.put("xuanxiangxh", k + 1);
							kssjxxMap.put("bzda", danxDAMap.get("qisok"));
							kssjxxMap.put("stxh", i + 1);
							kssjxxMap.put("deleted", "0");
							kssjxxMap.put("ksid", ksid);
							kssjxxMap.put("stlx", tmpdxMap.get("stlx"));
							kssjxxMap.put("addtime", Format.getDateTime());
							kssjxxMap.put("adduserid", adminInfo.getId());
							kssjxxMap.put("ksccid", ksccid);
							kssjxxMap.put("fenshu", tmpdxMap.get("fenshu"));
							InsertParams insertST = InsertParams.createInsertParams("T_KSSJXX");
							insertST.addParamsForMap(kssjxxMap);
							baseDaoComponent.insertDataByParams(insertST);
						}
					} else if ("2".equals(stlx)) {// 多选
						Map<String, Object> tmpdxMap = stList2.get(0);
						QueryParams queryParamDA = QueryParams.createQueryParams("T_OPTIONS");
						queryParamDA.addQueryParams(Parameter.createParameter("QUESTION_ID", stList2.get(0).get("id")));
						queryParamDA.addQueryParams(Parameter.createParameter("DELETED", "0"));
						if ("1".equals(dagz)) {
							queryParamDA.addOrderColumns("xuhao asc");
						}
						List<Map<String, Object>> duoxDA = null;
						duoxDA = baseDaoComponent.selectDataByParams(queryParamDA);
						if (!"1".equals(dagz)) {
							Collections.shuffle(duoxDA);
						}
						for (int k = 0; k < duoxDA.size(); k++) {
							Map<String, Object> duoxDAMap = duoxDA.get(j);
							Map<String, Object> kssjxxMap = new HashMap<String, Object>();
							kssjxxMap.put("id", UUID.randomUUID().toString());
							kssjxxMap.put("stid", tmpdxMap.get("id"));
							kssjxxMap.put("sttitle", tmpdxMap.get("title"));
							kssjxxMap.put("xuanxiangid", duoxDAMap.get("id"));
							kssjxxMap.put("xuanxiangmc", duoxDAMap.get("qoption"));
							kssjxxMap.put("xuanxiangxh", k + 1);
							kssjxxMap.put("bzda", duoxDAMap.get("qisok"));
							kssjxxMap.put("stxh", i + 1);
							kssjxxMap.put("deleted", "0");
							kssjxxMap.put("ksid", ksid);
							kssjxxMap.put("stlx", tmpdxMap.get("stlx"));
							kssjxxMap.put("addtime", Format.getDateTime());
							kssjxxMap.put("adduserid", adminInfo.getId());
							kssjxxMap.put("ksccid", ksccid);
							kssjxxMap.put("fenshu", tmpdxMap.get("fenshu"));
							InsertParams insertST = InsertParams.createInsertParams("T_KSSJXX");
							insertST.addParamsForMap(kssjxxMap);
							baseDaoComponent.insertDataByParams(insertST);
						}
					} else if ("3".equals(stlx)) {// 判断
						Map<String, Object> tmppdMap = stList2.get(0);
						Map<String, Object> kssjxxMap = new HashMap<String, Object>();
						kssjxxMap.put("id", UUID.randomUUID().toString());
						kssjxxMap.put("stid", tmppdMap.get("id"));
						kssjxxMap.put("sttitle", tmppdMap.get("title"));
						kssjxxMap.put("bzda", tmppdMap.get("pdda"));
						kssjxxMap.put("stxh", i + 1);
						kssjxxMap.put("deleted", "0");
						kssjxxMap.put("ksid", ksid);
						kssjxxMap.put("stlx", tmppdMap.get("stlx"));
						kssjxxMap.put("addtime", Format.getDateTime());
						kssjxxMap.put("adduserid", adminInfo.getId());
						kssjxxMap.put("ksccid", ksccid);
						kssjxxMap.put("fenshu", tmppdMap.get("fenshu"));
						InsertParams insertST = InsertParams.createInsertParams("T_KSSJXX");
						insertST.addParamsForMap(kssjxxMap);
						baseDaoComponent.insertDataByParams(insertST);
					}
				}

			}

		} else {// 操作
			QueryParams queryParamsTK = QueryParams.createQueryParams("T_TK");
			queryParamsTK.addQueryParams(Parameter.createParameter("kslx", kslx));
			queryParamsTK.addQueryParams(Parameter.createParameter("sydj", sydj));
			List<Map<String, Object>> tkList = baseDaoComponent.selectDataByParams(queryParamsTK);
			for (int i = 0; i < tkList.size(); i++) {
				Map<String, Object> eachTKList = tkList.get(i);
				String outnum = (String) eachTKList.get("outnum");
				String tkid = (String) eachTKList.get("id");
				String stlx = (String) eachTKList.get("stlx");
				Map<String, Object> stMap = new HashMap<String, Object>();
				stMap.put("tkid", tkid);
				stMap.put("stlx", stlx);
				stMap.put("outnum", Integer.valueOf(outnum));
				List<Map<String, Object>> klnoList = ksccDao.queryklno(stMap);
				if (klnoList.size() < Integer.valueOf(outnum)) {
					throw new Exception("操作题中知识点类别总数小于需要出题数");
				}
				for (int j = 0; j < klnoList.size(); j++) {
					String klno = (String) klnoList.get(j).get("klno");
					int klnolen = klno.length();
					Map<String, Object> stMap1 = new HashMap<String, Object>();
					stMap1.put("tkid", tkid);
					stMap1.put("stlx", stlx);
					stMap1.put("klno", klno);
					stMap1.put("sydj", sydj);
					stMap1.put("tkid", tkid);
					stMap1.put("klnolen", klnolen);
					List<Map<String, Object>> stList = ksccDao.queryst1(stMap1);
					int beginno = getBeginNo(stList.size(), 0);
					int endno = beginno + 1;
					Map<String, Object> stMap2 = new HashMap<String, Object>();
					stMap2.put("beginno", beginno);
					stMap2.put("endno", endno);
					stMap2.put("tkid", tkid);
					stMap2.put("stlx", stlx);
					stMap2.put("klno", klno);
					stMap2.put("sydj", sydj);
					stMap2.put("klnolen", klnolen);
					List<Map<String, Object>> stList2 = ksccDao.queryst2(stMap2);
					// INSERT
					Map<String, Object> tmppdMap = stList2.get(0);
					Map<String, Object> kssjxxMap = new HashMap<String, Object>();
					kssjxxMap.put("id", UUID.randomUUID().toString());
					kssjxxMap.put("stid", tmppdMap.get("id"));
					kssjxxMap.put("sttitle", tmppdMap.get("title"));
					kssjxxMap.put("content", tmppdMap.get("content"));
					kssjxxMap.put("stxh", i + 1);
					kssjxxMap.put("deleted", "0");
					kssjxxMap.put("ksid", ksid);
					kssjxxMap.put("stlx", tmppdMap.get("stlx"));
					kssjxxMap.put("addtime", Format.getDateTime());
					kssjxxMap.put("adduserid", adminInfo.getId());
					kssjxxMap.put("ksccid", ksccid);
					kssjxxMap.put("fenshu", tmppdMap.get("fenshu"));
					InsertParams insertST = InsertParams.createInsertParams("T_KSSJXX");
					insertST.addParamsForMap(kssjxxMap);
					baseDaoComponent.insertDataByParams(insertST);
				}
			}
		}
	}

	// 生成考试试卷T_KSSJXX
	private void createkssjxxInsertKSSJXX(String sydj, String ksccid, String dagz, String ksid, String tmgz,
			AdminInfo adminInfo) throws Exception {
		QueryParams queryParamSJXX = QueryParams.createQueryParams("T_KSSJ");// 查询T_KSSJ
		queryParamSJXX.addQueryParams(Parameter.createParameter("KSCCID", ksccid));
		List<Map<String, Object>> sjxxList = null;
		sjxxList = baseDaoComponent.selectDataByParams(queryParamSJXX);
		if (sjxxList.size() > 0) {
			// 单选
			QueryParams queryParamDANX = QueryParams.createQueryParams("T_KSSJ");
			queryParamDANX.addQueryParams(Parameter.createParameter("KSCCID", ksccid));
			queryParamDANX.addQueryParams(Parameter.createParameter("STLX", "1"));
			if ("1".equals(tmgz)) {// 1题目相同顺序相同2题目相同顺序不同3题目不同
				queryParamDANX.addOrderColumns("stxh asc");
			}
			List<Map<String, Object>> danxuanti = null;
			danxuanti = baseDaoComponent.selectDataByParams(queryParamDANX);
			if (!"1".equals(tmgz)) {// 1题目相同顺序相同2题目相同顺序不同3题目不同
				Collections.shuffle(danxuanti);
			}

			List<Map<String, Object>> danxuantiRs = new ArrayList<Map<String, Object>>();
			for (int i = 0; i < danxuanti.size(); i++) {
				Map<String, Object> tmpdxMap = danxuanti.get(i);
				QueryParams queryParamDA = QueryParams.createQueryParams("T_OPTIONS");
				queryParamDA.addQueryParams(Parameter.createParameter("QUESTION_ID", danxuanti.get(i).get("stid")));
				queryParamDA.addQueryParams(Parameter.createParameter("DELETED", "0"));
				if ("1".equals(dagz)) {// 1答案顺序一致2答案顺序不一致
					queryParamDA.addOrderColumns("xuhao asc");
				}
				List<Map<String, Object>> danxDA = null;
				danxDA = baseDaoComponent.selectDataByParams(queryParamDA);
				if (!"1".equals(dagz)) {// 1答案顺序一致2答案顺序不一致
					Collections.shuffle(danxDA);
				}
				for (int j = 0; j < danxDA.size(); j++) {
					Map<String, Object> danxDAMap = danxDA.get(j);
					Map<String, Object> kssjxxMap = new HashMap<String, Object>();
					kssjxxMap.put("id", UUID.randomUUID().toString());
					kssjxxMap.put("stid", tmpdxMap.get("stid"));
					kssjxxMap.put("sttitle", tmpdxMap.get("sttitle"));
					kssjxxMap.put("xuanxiangid", danxDAMap.get("id"));
					kssjxxMap.put("xuanxiangmc", danxDAMap.get("qoption"));
					kssjxxMap.put("xuanxiangxh", j + 1);
					kssjxxMap.put("bzda", danxDAMap.get("qisok"));
					kssjxxMap.put("stxh", i + 1);
					kssjxxMap.put("deleted", "0");
					kssjxxMap.put("ksid", ksid);
					kssjxxMap.put("stlx", tmpdxMap.get("stlx"));
					kssjxxMap.put("addtime", Format.getDateTime());
					kssjxxMap.put("adduserid", adminInfo.getId());
					kssjxxMap.put("ksccid", ksccid);
					kssjxxMap.put("fenshu", tmpdxMap.get("fenshu"));
					InsertParams insertST = InsertParams.createInsertParams("T_KSSJXX");
					insertST.addParamsForMap(kssjxxMap);
					baseDaoComponent.insertDataByParams(insertST);
				}
			}
			// 多选
			QueryParams queryParamDUOX = QueryParams.createQueryParams("T_KSSJ");
			queryParamDUOX.addQueryParams(Parameter.createParameter("KSCCID", ksccid));
			queryParamDUOX.addQueryParams(Parameter.createParameter("STLX", "2"));
			if ("1".equals(tmgz)) {
				queryParamDUOX.addOrderColumns("stxh asc");
			}
			List<Map<String, Object>> duoxuanti = null;
			duoxuanti = baseDaoComponent.selectDataByParams(queryParamDUOX);
			if (!"1".equals(tmgz)) {
				Collections.shuffle(duoxuanti);
			}
			List<Map<String, Object>> duoxuantiRs = new ArrayList<Map<String, Object>>();
			for (int i = 0; i < duoxuanti.size(); i++) {
				Map<String, Object> tmpdxMap = danxuanti.get(i);
				QueryParams queryParamDA = QueryParams.createQueryParams("T_OPTIONS");
				queryParamDA.addQueryParams(Parameter.createParameter("QUESTION_ID", danxuanti.get(i).get("stid")));
				queryParamDA.addQueryParams(Parameter.createParameter("DELETED", "0"));
				if ("1".equals(dagz)) {
					queryParamDA.addOrderColumns("xuhao asc");
				}
				List<Map<String, Object>> duoxDA = null;
				duoxDA = baseDaoComponent.selectDataByParams(queryParamDA);
				if (!"1".equals(dagz)) {
					Collections.shuffle(duoxDA);
				}
				for (int j = 0; j < duoxDA.size(); j++) {
					Map<String, Object> duoxDAMap = duoxDA.get(j);
					Map<String, Object> kssjxxMap = new HashMap<String, Object>();
					kssjxxMap.put("id", UUID.randomUUID().toString());
					kssjxxMap.put("stid", tmpdxMap.get("stid"));
					kssjxxMap.put("sttitle", tmpdxMap.get("sttitle"));
					kssjxxMap.put("xuanxiangid", duoxDAMap.get("id"));
					kssjxxMap.put("xuanxiangmc", duoxDAMap.get("qoption"));
					kssjxxMap.put("xuanxiangxh", j + 1);
					kssjxxMap.put("bzda", duoxDAMap.get("qisok"));
					kssjxxMap.put("stxh", i + 1);
					kssjxxMap.put("deleted", "0");
					kssjxxMap.put("ksid", ksid);
					kssjxxMap.put("stlx", tmpdxMap.get("stlx"));
					kssjxxMap.put("addtime", Format.getDateTime());
					kssjxxMap.put("adduserid", adminInfo.getId());
					kssjxxMap.put("ksccid", ksccid);
					kssjxxMap.put("fenshu", tmpdxMap.get("fenshu"));
					InsertParams insertST = InsertParams.createInsertParams("T_KSSJXX");
					insertST.addParamsForMap(kssjxxMap);
					baseDaoComponent.insertDataByParams(insertST);
				}
			}
			// 判断
			QueryParams queryParamPD = QueryParams.createQueryParams("T_KSSJ");
			queryParamPD.addQueryParams(Parameter.createParameter("KSCCID", ksccid));
			queryParamPD.addQueryParams(Parameter.createParameter("STLX", "3"));
			if ("1".equals(tmgz)) {
				queryParamPD.addOrderColumns("stxh asc");
			}
			List<Map<String, Object>> panduanti = null;
			panduanti = baseDaoComponent.selectDataByParams(queryParamPD);
			if (!"1".equals(tmgz)) {
				Collections.shuffle(panduanti);
			}
			for (int i = 0; i < panduanti.size(); i++) {
				Map<String, Object> tmpdxMap = panduanti.get(i);
				Map<String, Object> kssjxxMap = new HashMap<String, Object>();
				kssjxxMap.put("id", UUID.randomUUID().toString());
				kssjxxMap.put("stid", tmpdxMap.get("stid"));
				kssjxxMap.put("sttitle", tmpdxMap.get("sttitle"));
				kssjxxMap.put("bzda", tmpdxMap.get("pdda"));
				kssjxxMap.put("stxh", i + 1);
				kssjxxMap.put("deleted", "0");
				kssjxxMap.put("ksid", ksid);
				kssjxxMap.put("stlx", tmpdxMap.get("stlx"));
				kssjxxMap.put("addtime", Format.getDateTime());
				kssjxxMap.put("adduserid", adminInfo.getId());
				kssjxxMap.put("ksccid", ksccid);
				kssjxxMap.put("fenshu", tmpdxMap.get("fenshu"));
				InsertParams insertST = InsertParams.createInsertParams("T_KSSJXX");
				insertST.addParamsForMap(kssjxxMap);
				baseDaoComponent.insertDataByParams(insertST);
			}
			// 操作
			QueryParams queryParamCZ = QueryParams.createQueryParams("T_KSSJ");
			queryParamCZ.addQueryParams(Parameter.createParameter("KSCCID", ksccid));
			queryParamCZ.addQueryParams(Parameter.createParameter("STLX", "4"));
			if ("1".equals(tmgz)) {
				queryParamCZ.addOrderColumns("stxh asc");
			}
			List<Map<String, Object>> caozuoti = baseDaoComponent.selectDataByParams(queryParamCZ);
			if (!"1".equals(tmgz)) {
				Collections.shuffle(caozuoti);
			}
			for (int i = 0; i < caozuoti.size(); i++) {
				Map<String, Object> tmpdxMap = caozuoti.get(i);
				Map<String, Object> kssjxxMap = new HashMap<String, Object>();
				kssjxxMap.put("id", UUID.randomUUID().toString());
				kssjxxMap.put("stid", tmpdxMap.get("stid"));
				kssjxxMap.put("sttitle", tmpdxMap.get("sttitle"));
				kssjxxMap.put("content", tmpdxMap.get("content"));
				kssjxxMap.put("stxh", i + 1);
				kssjxxMap.put("deleted", "0");
				kssjxxMap.put("ksid", ksid);
				kssjxxMap.put("stlx", tmpdxMap.get("stlx"));
				kssjxxMap.put("addtime", Format.getDateTime());
				kssjxxMap.put("adduserid", adminInfo.getId());
				kssjxxMap.put("ksccid", ksccid);
				kssjxxMap.put("fenshu", tmpdxMap.get("fenshu"));
				InsertParams insertST = InsertParams.createInsertParams("T_KSSJXX");
				insertST.addParamsForMap(kssjxxMap);
				baseDaoComponent.insertDataByParams(insertST);
			}
		} else {
			throw new Exception("T_KSSJ中没有数据");
		}

	}

	// 插入T_KSSJ
	private void createkssjxxInsertKSSJ(String sydj, String ksccid, String dagz, String kslx, AdminInfo adminInfo)
			throws Exception {
		if (kslx.equals("L")) {// 理论
			QueryParams queryParamsTK = QueryParams.createQueryParams("T_TK");// 题库
			queryParamsTK.addQueryParams(Parameter.createParameter("kslx", kslx));
			queryParamsTK.addQueryParams(Parameter.createParameter("sydj", sydj));
			List<Map<String, Object>> tkList = baseDaoComponent.selectDataByParams(queryParamsTK);
			if (tkList.size() == 0) {
				throw new Exception("不存在与该考试场次相符的题库");
			}
			for (int i = 0; i < tkList.size(); i++) {// 循环题库
				Map<String, Object> eachTKList = tkList.get(i);
				String outnum = (String) eachTKList.get("outnum");
				String tkid = (String) eachTKList.get("id");
				String stlx = (String) eachTKList.get("stlx");
				String fenshu = (String) eachTKList.get("fenshu");
				Map<String, Object> stMap = new HashMap<String, Object>();
				stMap.put("tkid", tkid);
				stMap.put("stlx", stlx);
				stMap.put("outnum", Integer.valueOf(outnum));
				List<Map<String, Object>> klnoList = ksccDao.queryklno(stMap);// 查询知识点
				if (klnoList.size() < Integer.valueOf(outnum)) {
					throw new Exception("试题库中的操作题所包含的知识点类别总数小于需要出题数");
				}
				for (int j = 0; j < klnoList.size(); j++) {// 每个知识点出一题
					String klno = (String) klnoList.get(j).get("klno");
					int klnolen = klno.length();
					Map<String, Object> stMap1 = new HashMap<String, Object>();
					stMap1.put("tkid", tkid);
					stMap1.put("stlx", stlx);
					stMap1.put("klno", klno);
					stMap1.put("sydj", sydj);
					stMap1.put("klnolen", klnolen);
					List<Map<String, Object>> stList = ksccDao.queryst1(stMap1);
					int beginno = getBeginNo(stList.size(), 0);
					int endno = beginno + 1;
					Map<String, Object> stMap2 = new HashMap<String, Object>();
					stMap2.put("beginno", beginno);
					stMap2.put("endno", endno);
					stMap2.put("tkid", tkid);
					stMap2.put("stlx", stlx);
					stMap2.put("klno", klno);
					stMap2.put("sydj", sydj);
					stMap2.put("klnolen", klnolen);
					List<Map<String, Object>> stList2 = ksccDao.queryst2(stMap2);
					// INSERT T_KSSJ
					if ("1".equals(stlx)) {// 单选
						Map<String, Object> tmpdxMap = stList2.get(0);
						Map<String, Object> kssjMap = new HashMap<String, Object>();
						kssjMap.put("id", UUID.randomUUID().toString());
						kssjMap.put("stid", tmpdxMap.get("id"));
						String title = (String) tmpdxMap.get("title");
						title = title.replaceAll("\\\\", "\\\\\\\\");
						kssjMap.put("sttitle", title);
						kssjMap.put("stxh", j + 1);
						kssjMap.put("stlx", "1");
						kssjMap.put("deleted", "0");
						kssjMap.put("ksccid", ksccid);
						kssjMap.put("addtime", Format.getDateTime());
						kssjMap.put("adduserid", adminInfo.getId());
						kssjMap.put("fenshu", fenshu);
						InsertParams insertST = InsertParams.createInsertParams("T_KSSJ");
						insertST.addParamsForMap(kssjMap);
						baseDaoComponent.insertDataByParams(insertST);
					} else if ("2".equals(stlx)) {// 多选
						Map<String, Object> tmpdxMap = stList2.get(0);
						Map<String, Object> kssjMap = new HashMap<String, Object>();
						kssjMap.put("id", UUID.randomUUID().toString());
						kssjMap.put("stid", tmpdxMap.get("id"));
						String title = (String) tmpdxMap.get("title");
						title = title.replaceAll("\\\\", "\\\\\\\\");
						kssjMap.put("sttitle", title);
						kssjMap.put("stxh", j + 1);
						kssjMap.put("stlx", "2");
						kssjMap.put("deleted", "0");
						kssjMap.put("ksccid", ksccid);
						kssjMap.put("addtime", Format.getDateTime());
						kssjMap.put("adduserid", adminInfo.getId());
						kssjMap.put("fenshu", fenshu);
						InsertParams insertST = InsertParams.createInsertParams("T_KSSJ");
						insertST.addParamsForMap(kssjMap);
						baseDaoComponent.insertDataByParams(insertST);
					} else if ("3".equals(stlx)) {// 判断
						Map<String, Object> tmpdxMap = stList2.get(0);
						Map<String, Object> kssjMap = new HashMap<String, Object>();
						kssjMap.put("id", UUID.randomUUID().toString());
						kssjMap.put("stid", tmpdxMap.get("id"));
						String title = (String) tmpdxMap.get("title");
						title = title.replaceAll("\\\\", "\\\\\\\\");
						kssjMap.put("sttitle", title);
						kssjMap.put("stxh", j + 1);
						kssjMap.put("stlx", "3");
						kssjMap.put("pdda", tmpdxMap.get("pdda"));
						kssjMap.put("deleted", "0");
						kssjMap.put("ksccid", ksccid);
						kssjMap.put("addtime", Format.getDateTime());
						kssjMap.put("adduserid", adminInfo.getId());
						kssjMap.put("fenshu", fenshu);
						InsertParams insertST = InsertParams.createInsertParams("T_KSSJ");
						insertST.addParamsForMap(kssjMap);
						baseDaoComponent.insertDataByParams(insertST);
					}
				}

			}

		} else {// 操作
			QueryParams queryParamsTK = QueryParams.createQueryParams("T_TK");
			queryParamsTK.addQueryParams(Parameter.createParameter("kslx", kslx));
			queryParamsTK.addQueryParams(Parameter.createParameter("sydj", sydj));
			List<Map<String, Object>> tkList = baseDaoComponent.selectDataByParams(queryParamsTK);
			for (int i = 0; i < tkList.size(); i++) {
				Map<String, Object> eachTKList = tkList.get(i);
				String outnum = (String) eachTKList.get("outnum");
				String tkid = (String) eachTKList.get("id");
				String stlx = (String) eachTKList.get("stlx");
				String fenshu = (String) eachTKList.get("fenshu");
				Map<String, Object> stMap = new HashMap<String, Object>();
				stMap.put("tkid", tkid);
				stMap.put("stlx", stlx);
				stMap.put("outnum", Integer.valueOf(outnum));
				List<Map<String, Object>> klnoList = ksccDao.queryklno(stMap);
				if (klnoList.size() < Integer.valueOf(outnum)) {
					throw new Exception("操作题中知识点类别总数小于需要出题数");
				}
				for (int j = 0; j < klnoList.size(); j++) {
					String klno = (String) klnoList.get(j).get("klno");
					int klnolen = klno.length();
					Map<String, Object> stMap1 = new HashMap<String, Object>();
					stMap1.put("tkid", tkid);
					stMap1.put("stlx", stlx);
					stMap1.put("klno", klno);
					stMap1.put("sydj", sydj);
					stMap1.put("klnolen", klnolen);
					List<Map<String, Object>> stList = ksccDao.queryst1(stMap1);
					int beginno = getBeginNo(stList.size(), 0);
					int endno = beginno + 1;
					Map<String, Object> stMap2 = new HashMap<String, Object>();
					stMap2.put("beginno", beginno);
					stMap2.put("endno", endno);
					stMap2.put("tkid", tkid);
					stMap2.put("stlx", stlx);
					stMap2.put("klno", klno);
					stMap2.put("sydj", sydj);
					stMap2.put("klnolen", klnolen);
					List<Map<String, Object>> stList2 = ksccDao.queryst2(stMap2);
					// INSERT
					Map<String, Object> tmpstMap = stList2.get(0);
					Map<String, Object> kssjMap = new HashMap<String, Object>();
					kssjMap.put("id", UUID.randomUUID().toString());
					kssjMap.put("content", tmpstMap.get("content"));
					kssjMap.put("stid", tmpstMap.get("id"));
					String title = (String) tmpstMap.get("title");
					title = title.replaceAll("\\\\", "\\\\\\\\");
					kssjMap.put("sttitle", title);
					kssjMap.put("stxh", j + 1);
					kssjMap.put("stlx", "4");
					kssjMap.put("pdda", tmpstMap.get("pdda"));
					kssjMap.put("deleted", "0");
					kssjMap.put("ksccid", ksccid);
					kssjMap.put("addtime", Format.getDateTime());
					kssjMap.put("adduserid", adminInfo.getId());
					kssjMap.put("fenshu", fenshu);
					InsertParams insertST = InsertParams.createInsertParams("T_KSSJ");
					insertST.addParamsForMap(kssjMap);
					baseDaoComponent.insertDataByParams(insertST);
				}

			}
		}
	}

	@Override
	public JSONObject loadKssjxx(Map map) throws Exception {
		UserInfo userinfo = (UserInfo) map.remove("userinfo");
		String ksccid = (String) map.get("ksccid");
		QueryParams queryParams = QueryParams.createQueryParams("T_KSCCMG");// 查询考试场次信息
		queryParams.addQueryParams(Parameter.createParameter("id", ksccid));
		List<Map<String, Object>> optionHaslist = null;
		optionHaslist = baseDaoComponent.selectDataByParams(queryParams);
		String nf = (String) optionHaslist.get(0).get("nf");
		String ksrq = (String) optionHaslist.get(0).get("ksrq");
		String startTime = (String) optionHaslist.get(0).get("starttime");
		String endTime = (String) optionHaslist.get(0).get("endtime");
		String kslx = (String) optionHaslist.get(0).get("kslx");
		String sydj = (String) optionHaslist.get(0).get("sydj");// 适用等级
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");// 小写的mm表示的是分钟
		java.util.Date startDate = null;
		java.util.Date endDate = null;
		java.util.Date nowDate = null;
		String nowTime = Format.getDateTime();
		startTime = nf + "-" + ksrq + " " + startTime;
		endTime = nf + "-" + ksrq + " " + endTime;
		startDate = sdf.parse(startTime);
		endDate = sdf.parse(endTime);
		nowDate = sdf.parse(nowTime);
		Map<String, Object> timeMap = new HashMap<>();
		timeMap.put("kslx", kslx);
		timeMap.put("starttime", startTime);
		timeMap.put("endtime", endTime);
		if (nowDate.equals(startDate) || nowDate.equals(endDate)) {
			timeMap.put("time", "1");// 1.代表在范围内
		} else {
			if (nowDate.after(startDate) && nowDate.before(endDate)) {
				timeMap.put("time", "1");// 1.代表在范围内
			} else {
				throw new Exception("考试时间不在范围内");
			}
		}
		if ("1".equals(timeMap.get("time"))) {
			QueryParams queryParamsKs = QueryParams.createQueryParams("t_user_kscc");
			queryParamsKs.addQueryParams(Parameter.createParameter("userid", userinfo.getId()));
			queryParamsKs.addQueryParams(Parameter.createParameter("ksccid", ksccid));
			List<Map<String, Object>> ksKsccList = null;
			ksKsccList = baseDaoComponent.selectDataByParams(queryParamsKs);
			if ("0".equals(ksKsccList.get(0).get("sfdl"))) {
				// 题目相同
				QueryParams queryParamSJXX = QueryParams.createQueryParams("T_KSSJXX");
				queryParamSJXX.addQueryParams(Parameter.createParameter("ksccid", ksccid));
				queryParamSJXX.addQueryParams(Parameter.createParameter("ksid", userinfo.getId()));
				List<Map<String, Object>> sjxxList = null;
				sjxxList = baseDaoComponent.selectDataByParams(queryParamSJXX);
				if (sjxxList.size() > 0) {
					// 单选
					Map<String, Object> kssjxxMap = new HashMap<>();
					kssjxxMap.put("ksccid", ksccid);
					kssjxxMap.put("ksid", userinfo.getId());
					kssjxxMap.put("stlx", "1");
					List<Map<String, Object>> sjxxListDanX = null;
					sjxxListDanX = ksccDao.queryKSXJXX(kssjxxMap);
					List<Map<String, Object>> danxuantiRs = new ArrayList<Map<String, Object>>();
					for (int i = 0; i < sjxxListDanX.size(); i++) {
						Map<String, Object> stidMap = sjxxListDanX.get(i);
						QueryParams queryParamSJXXDanXuan = QueryParams.createQueryParams("T_KSSJXX");
						queryParamSJXXDanXuan.addQueryParams(Parameter.createParameter("ksccid", ksccid));
						queryParamSJXXDanXuan.addQueryParams(Parameter.createParameter("ksid", userinfo.getId()));
						queryParamSJXXDanXuan.addQueryParams(Parameter.createParameter("stid", stidMap.get("stid")));
						queryParamSJXXDanXuan.addOrderColumns("stxh asc,xuanxiangxh asc");
						Map<String, Object> tmpdxMap = new HashMap<String, Object>();
						List<Map<String, Object>> sjxxListDanXuan = null;
						sjxxListDanXuan = baseDaoComponent.selectDataByParams(queryParamSJXXDanXuan);
						tmpdxMap.put("sttitle", sjxxListDanXuan.get(0).get("sttitle"));
						tmpdxMap.put("stxh", sjxxListDanXuan.get(0).get("stxh"));
						tmpdxMap.put("sfdt", "0"); // 未答题
						List<Map<String, Object>> answerList = new ArrayList<Map<String, Object>>();
						for (int j = 0; j < sjxxListDanXuan.size(); j++) {
							Map<String, Object> danxuanDAMap = new HashMap<String, Object>();
							danxuanDAMap.put("id", sjxxListDanXuan.get(j).get("id"));
							danxuanDAMap.put("xuanxiangmc", sjxxListDanXuan.get(j).get("xuanxiangmc"));
							danxuanDAMap.put("xuanxiangxh", sjxxListDanXuan.get(j).get("xuanxiangxh"));
							danxuanDAMap.put("xsda", sjxxListDanXuan.get(j).get("xsda"));
							if (sjxxListDanXuan.get(j).get("xsda") != null) {
								tmpdxMap.put("sfdt", "1");
							}
							answerList.add(danxuanDAMap);
						}
						tmpdxMap.put("answer", answerList);
						danxuantiRs.add(tmpdxMap);
					}
					timeMap.put("danxuanti", danxuantiRs);
					// 多选
					Map<String, Object> kssjxxMapDuoX = new HashMap<>();
					kssjxxMapDuoX.put("KSCCID", ksccid);
					kssjxxMapDuoX.put("ksid", userinfo.getId());
					kssjxxMapDuoX.put("stlx", "2");
					List<Map<String, Object>> sjxxListDuoX = null;
					sjxxListDuoX = ksccDao.queryKSXJXX(kssjxxMapDuoX);
					List<Map<String, Object>> duoxuantiRs = new ArrayList<Map<String, Object>>();
					for (int i = 0; i < sjxxListDuoX.size(); i++) {
						Map<String, Object> stidMap = sjxxListDuoX.get(i);
						QueryParams queryParamSJXXDanXuan = QueryParams.createQueryParams("T_KSSJXX");
						queryParamSJXXDanXuan.addQueryParams(Parameter.createParameter("KSCCID", ksccid));
						queryParamSJXXDanXuan.addQueryParams(Parameter.createParameter("ksid", userinfo.getId()));
						queryParamSJXXDanXuan.addQueryParams(Parameter.createParameter("stid", stidMap.get("stid")));
						queryParamSJXXDanXuan.addOrderColumns("stxh asc,xuanxiangxh asc");
						Map<String, Object> tmpdxMap = new HashMap<String, Object>();
						List<Map<String, Object>> sjxxListDanXuan = null;
						sjxxListDanXuan = baseDaoComponent.selectDataByParams(queryParamSJXXDanXuan);
						tmpdxMap.put("sttitle", sjxxListDanXuan.get(0).get("sttitle"));
						tmpdxMap.put("stxh", sjxxListDanXuan.get(0).get("stxh"));
						tmpdxMap.put("sfdt", "0"); // 未答题
						List<Map<String, Object>> answerList = new ArrayList<Map<String, Object>>();
						for (int j = 0; j < sjxxListDanXuan.size(); j++) {
							Map<String, Object> duoxuanDAMap = new HashMap<String, Object>();
							duoxuanDAMap.put("id", sjxxListDanXuan.get(j).get("id"));
							duoxuanDAMap.put("xuanxiangmc", sjxxListDanXuan.get(j).get("xuanxiangmc"));
							duoxuanDAMap.put("xuanxiangxh", sjxxListDanXuan.get(j).get("xuanxiangxh"));
							duoxuanDAMap.put("xsda", sjxxListDanXuan.get(j).get("xsda"));
							if (sjxxListDanXuan.get(j).get("xsda") != null) {
								tmpdxMap.put("sfdt", "1");
							}
							answerList.add(duoxuanDAMap);
						}
						tmpdxMap.put("answer", answerList);
						duoxuantiRs.add(tmpdxMap);
					}
					timeMap.put("duoxuanti", duoxuantiRs);
					// 判断
					QueryParams queryParamPD = QueryParams.createQueryParams("T_KSSJXX");
					queryParamPD.addQueryParams(Parameter.createParameter("KSCCID", ksccid));
					queryParamPD.addQueryParams(Parameter.createParameter("ksid", userinfo.getId()));
					queryParamPD.addQueryParams(Parameter.createParameter("STLX", "3"));
					queryParamPD.addOrderColumns("stxh asc");
					List<Map<String, Object>> panduanti = null;
					panduanti = baseDaoComponent.selectDataByParams(queryParamPD);
					timeMap.put("panduanti", panduanti);
					// 操作
					QueryParams queryParamCZ = QueryParams.createQueryParams("T_KSSJXX");
					queryParamCZ.addQueryParams(Parameter.createParameter("KSCCID", ksccid));
					queryParamCZ.addQueryParams(Parameter.createParameter("ksid", userinfo.getId()));
					queryParamCZ.addQueryParams(Parameter.createParameter("STLX", "4"));
					queryParamCZ.addOrderColumns("stxh asc");
					List<Map<String, Object>> caozuoti = baseDaoComponent.selectDataByParams(queryParamCZ);
					timeMap.put("caozuoti", caozuoti);
				} else {
					throw new Exception("没有该考试的试卷");
				}
				UpdateParams updateParamsKsCC = UpdateParams.createUpdateParams("t_user_kscc");
				updateParamsKsCC.addParam(Parameter.createParameter("sfdl", "1"));
				updateParamsKsCC.addWhereParameter(Parameter.createParameter("userid", userinfo.getId()));
				updateParamsKsCC.addWhereParameter(Parameter.createParameter("ksccid", ksccid));
				baseDaoComponent.updateDataByParams(updateParamsKsCC);
				InsertParams insertParams = InsertParams.createInsertParams("t_kssjxx_log", "id", "logtype",
						"adduserid", "addtime", "ip", "deleted", "ksid", "ksccid");
				insertParams.setValues(UUID.randomUUID().toString(), "1", userinfo.getId(), Format.getDateTime(),
						userinfo.getIp(), "0", userinfo.getId(), ksccid);
				baseDaoComponent.insertDataByParams(insertParams);
			} else {
				throw new Exception("该考生已经参加过考试,请联系管理员处理");
			}
		}
		return ResponseUtils.createSuccessResponseBodyForJiem("done", timeMap);
	}

	@Override
	public JSONObject loadKssjxxAdmin(Map map) throws Exception {
		String id = (String) map.get("id");
		String ksccid = (String) map.get("ksccid");
		QueryParams queryParams = QueryParams.createQueryParams("T_KSCCMG");// 查询考试场次信息
		queryParams.addQueryParams(Parameter.createParameter("id", ksccid));
		List<Map<String, Object>> optionHaslist = null;
		optionHaslist = baseDaoComponent.selectDataByParams(queryParams);
		String nf = (String) optionHaslist.get(0).get("nf");
		String ksrq = (String) optionHaslist.get(0).get("ksrq");
		String startTime = (String) optionHaslist.get(0).get("starttime");
		String endTime = (String) optionHaslist.get(0).get("endtime");
		String kslx = (String) optionHaslist.get(0).get("kslx");
		String sydj = (String) optionHaslist.get(0).get("sydj");// 适用等级
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");// 小写的mm表示的是分钟
		java.util.Date startDate = null;
		java.util.Date endDate = null;
		java.util.Date nowDate = null;
		String nowTime = Format.getDateTime();
		startTime = nf + "-" + ksrq + " " + startTime;
		endTime = nf + "-" + ksrq + " " + endTime;
		startDate = sdf.parse(startTime);
		endDate = sdf.parse(endTime);
		nowDate = sdf.parse(nowTime);
		Map<String, Object> timeMap = new HashMap<>();
		timeMap.put("kslx", kslx);
		timeMap.put("starttime", startTime);
		timeMap.put("endtime", endTime);
		QueryParams queryParamsKs = QueryParams.createQueryParams("t_user_kscc");
		queryParamsKs.addQueryParams(Parameter.createParameter("userid", id));
		queryParamsKs.addQueryParams(Parameter.createParameter("ksccid", ksccid));
		List<Map<String, Object>> ksKsccList = null;
		ksKsccList = baseDaoComponent.selectDataByParams(queryParamsKs);
		if ("0".equals(ksKsccList.get(0).get("sfdl"))) {
			// 题目相同
			QueryParams queryParamSJXX = QueryParams.createQueryParams("T_KSSJXX");
			queryParamSJXX.addQueryParams(Parameter.createParameter("ksccid", ksccid));
			queryParamSJXX.addQueryParams(Parameter.createParameter("ksid", id));
			List<Map<String, Object>> sjxxList = null;
			sjxxList = baseDaoComponent.selectDataByParams(queryParamSJXX);
			if (sjxxList.size() > 0) {
				// 单选
				Map<String, Object> kssjxxMap = new HashMap<>();
				kssjxxMap.put("ksccid", ksccid);
				kssjxxMap.put("ksid", id);
				kssjxxMap.put("stlx", "1");
				List<Map<String, Object>> sjxxListDanX = null;
				sjxxListDanX = ksccDao.queryKSXJXX(kssjxxMap);
				List<Map<String, Object>> danxuantiRs = new ArrayList<Map<String, Object>>();
				for (int i = 0; i < sjxxListDanX.size(); i++) {
					Map<String, Object> stidMap = sjxxListDanX.get(i);
					QueryParams queryParamSJXXDanXuan = QueryParams.createQueryParams("T_KSSJXX");
					queryParamSJXXDanXuan.addQueryParams(Parameter.createParameter("ksccid", ksccid));
					queryParamSJXXDanXuan.addQueryParams(Parameter.createParameter("ksid", id));
					queryParamSJXXDanXuan.addQueryParams(Parameter.createParameter("stid", stidMap.get("stid")));
					queryParamSJXXDanXuan.addOrderColumns("stxh asc,xuanxiangxh asc");
					Map<String, Object> tmpdxMap = new HashMap<String, Object>();
					List<Map<String, Object>> sjxxListDanXuan = null;
					sjxxListDanXuan = baseDaoComponent.selectDataByParams(queryParamSJXXDanXuan);
					tmpdxMap.put("sttitle", sjxxListDanXuan.get(0).get("sttitle"));
					tmpdxMap.put("stxh", sjxxListDanXuan.get(0).get("stxh"));
					List<Map<String, Object>> answerList = new ArrayList<Map<String, Object>>();
					for (int j = 0; j < sjxxListDanXuan.size(); j++) {
						Map<String, Object> danxuanDAMap = new HashMap<String, Object>();
						danxuanDAMap.put("id", sjxxListDanXuan.get(j).get("id"));
						danxuanDAMap.put("xuanxiangmc", sjxxListDanXuan.get(j).get("xuanxiangmc"));
						danxuanDAMap.put("xuanxiangxh", sjxxListDanXuan.get(j).get("xuanxiangxh"));
						answerList.add(danxuanDAMap);
					}
					tmpdxMap.put("answer", answerList);
					danxuantiRs.add(tmpdxMap);
				}
				timeMap.put("danxuanti", danxuantiRs);
				// 多选
				Map<String, Object> kssjxxMapDuoX = new HashMap<>();
				kssjxxMapDuoX.put("KSCCID", ksccid);
				kssjxxMapDuoX.put("ksid", id);
				kssjxxMapDuoX.put("stlx", "2");
				List<Map<String, Object>> sjxxListDuoX = null;
				sjxxListDuoX = ksccDao.queryKSXJXX(kssjxxMapDuoX);
				List<Map<String, Object>> duoxuantiRs = new ArrayList<Map<String, Object>>();
				for (int i = 0; i < sjxxListDuoX.size(); i++) {
					Map<String, Object> stidMap = sjxxListDuoX.get(i);
					QueryParams queryParamSJXXDanXuan = QueryParams.createQueryParams("T_KSSJXX");
					queryParamSJXXDanXuan.addQueryParams(Parameter.createParameter("KSCCID", ksccid));
					queryParamSJXXDanXuan.addQueryParams(Parameter.createParameter("ksid", id));
					queryParamSJXXDanXuan.addQueryParams(Parameter.createParameter("stid", stidMap.get("stid")));
					queryParamSJXXDanXuan.addOrderColumns("stxh asc,xuanxiangxh asc");
					Map<String, Object> tmpdxMap = new HashMap<String, Object>();
					List<Map<String, Object>> sjxxListDanXuan = null;
					sjxxListDanXuan = baseDaoComponent.selectDataByParams(queryParamSJXXDanXuan);
					tmpdxMap.put("sttitle", sjxxListDanXuan.get(0).get("sttitle"));
					tmpdxMap.put("stxh", sjxxListDanXuan.get(0).get("stxh"));
					List<Map<String, Object>> answerList = new ArrayList<Map<String, Object>>();
					for (int j = 0; j < sjxxListDanXuan.size(); j++) {
						Map<String, Object> duoxuanDAMap = new HashMap<String, Object>();
						duoxuanDAMap.put("id", sjxxListDanXuan.get(j).get("id"));
						duoxuanDAMap.put("xuanxiangmc", sjxxListDanXuan.get(j).get("xuanxiangmc"));
						duoxuanDAMap.put("xuanxiangxh", sjxxListDanXuan.get(j).get("xuanxiangxh"));
						answerList.add(duoxuanDAMap);
					}
					tmpdxMap.put("answer", answerList);
					duoxuantiRs.add(tmpdxMap);
				}
				timeMap.put("duoxuanti", duoxuantiRs);
				// 判断
				QueryParams queryParamPD = QueryParams.createQueryParams("T_KSSJXX");
				queryParamPD.addQueryParams(Parameter.createParameter("KSCCID", ksccid));
				queryParamPD.addQueryParams(Parameter.createParameter("ksid", id));
				queryParamPD.addQueryParams(Parameter.createParameter("STLX", "3"));
				queryParamPD.addOrderColumns("stxh asc");
				List<Map<String, Object>> panduanti = null;
				panduanti = baseDaoComponent.selectDataByParams(queryParamPD);
				timeMap.put("panduanti", panduanti);
				// 操作
				QueryParams queryParamCZ = QueryParams.createQueryParams("T_KSSJXX");
				queryParamCZ.addQueryParams(Parameter.createParameter("KSCCID", ksccid));
				queryParamCZ.addQueryParams(Parameter.createParameter("ksid", id));
				queryParamCZ.addQueryParams(Parameter.createParameter("STLX", "4"));
				queryParamCZ.addOrderColumns("stxh asc");
				List<Map<String, Object>> caozuoti = baseDaoComponent.selectDataByParams(queryParamCZ);
				timeMap.put("caozuoti", caozuoti);
			} else {
				throw new Exception("没有该考试的试卷");
			}
			// UpdateParams updateParamsKsCC =
			// UpdateParams.createUpdateParams("t_user_kscc");
			// updateParamsKsCC.addParam(Parameter.createParameter("sfdl",
			// "1"));
			// updateParamsKsCC.addWhereParameter(Parameter.createParameter("userid",
			// userinfo.getId()));
			// updateParamsKsCC.addWhereParameter(Parameter.createParameter("ksccid",
			// ksccid));
			// baseDaoComponent.updateDataByParams(updateParamsKsCC);
		} else {
			throw new Exception("该考生已经参加过考试");
		}
		return ResponseUtils.createSuccessResponseBodyForJiem("done", timeMap);
	}

	private Integer getBeginNo(Integer max, Integer min) {
		Random random = new Random();
		Integer s = random.nextInt(max) % (max - min + 1) + min;
		return s;
	}

	// 上一步下一步的时候和点击试题号时保存试卷
	@Override
	public JSONObject saveKssjdaEach(Map map) throws SQLException {
		UserInfo userinfo = (UserInfo) map.remove("userinfo");
		Map<String, Object> tmpMap = new HashMap<String, Object>();
		// 判断
		if (null != map.get("panduanti")) {
			tmpMap = (Map<String, Object>) map.get("panduanti");
			QueryParams queryParamsOld = QueryParams.createQueryParams("T_KSSJXX");
			queryParamsOld.addQueryParams(Parameter.createParameter("id", tmpMap.get("id")));
			queryParamsOld.printSelf();
			List<Map<String, Object>> dataOld = baseDaoComponent.selectDataByParams(queryParamsOld);
			Map<String, Object> kssjxxMap = new HashMap<String, Object>();
			String xuanxiangmc = "";
			if ("1".equals(tmpMap.get("xsda"))) {
				xuanxiangmc = "正确";
			} else if ("0".equals(tmpMap.get("xsda"))) {
				xuanxiangmc = "错误";
			}
			kssjxxMap.put("xuanxiangmc", xuanxiangmc);
			kssjxxMap.put("xsda", tmpMap.get("xsda"));
			kssjxxMap.put("updatetime", Format.getDateTime());
			kssjxxMap.put("updateuserid", userinfo.getId());
			UpdateParams updateParams = UpdateParams.createUpdateParams("T_KSSJXX");
			updateParams.addParamsForMap(kssjxxMap);
			updateParams.addWhereParameter(Parameter.createParameter("id", tmpMap.get("id")));
			baseDaoComponent.updateDataByParams(updateParams);
			QueryParams queryParams = QueryParams.createQueryParams("T_KSSJXX");
			queryParams.addQueryParams(Parameter.createParameter("id", tmpMap.get("id")));
			queryParams.printSelf();
			List<Map<String, Object>> data = baseDaoComponent.selectDataByParams(queryParams);
			if (tmpMap.get("xsda") != null && !"".equals(tmpMap.get("xsda"))) {// 判断未答题
				if (dataOld.get(0).get("xsda") == null || !tmpMap.get("xsda").equals(dataOld.get(0).get("xsda"))) {// 判断答案是否改变
					InsertParams insertParams = InsertParams.createInsertParams("t_kssjxx_log", "id", "stid", "sttitle",
							"xuanxiangid", "xuanxiangmc", "xuanxiangxh", "xsda", "bzda", "stxh", "deleted", "ksid",
							"stlx", "addtime", "ksccid", "adduserid", "content", "fenshu", "ip", "logtype");
					insertParams.setValues(UUID.randomUUID().toString(), data.get(0).get("stid"),
							data.get(0).get("sttitle"), data.get(0).get("xuanxiangid"), data.get(0).get("xuanxiangmc"),
							data.get(0).get("xuanxiangxh"), data.get(0).get("xsda"), data.get(0).get("bzda"),
							data.get(0).get("stxh"), data.get(0).get("deleted"), data.get(0).get("ksid"),
							data.get(0).get("stlx"), Format.getDateTime(), data.get(0).get("ksccid"), userinfo.getId(),
							data.get(0).get("content"), data.get(0).get("fenshu"), userinfo.getIp(), "2");
					baseDaoComponent.insertDataByParams(insertParams);
				}
			}
		}
		if (null != map.get("danxuanti")) {
			tmpMap = (Map<String, Object>) map.get("danxuanti");
			for (int j = 0; j < ((List<Map<String, Object>>) tmpMap.get("answer")).size(); j++) {
				Map<String, Object> tmpxxMap = ((List<Map<String, Object>>) tmpMap.get("answer")).get(j);
				QueryParams queryParamsOld = QueryParams.createQueryParams("T_KSSJXX");
				List<Map<String, Object>> dataOld = null;
				if ("1".equals(tmpxxMap.get("xsda").toString())) {
					queryParamsOld.addQueryParams(Parameter.createParameter("id", tmpxxMap.get("id")));
					queryParamsOld.printSelf();
					dataOld = baseDaoComponent.selectDataByParams(queryParamsOld);
				}
				Map<String, Object> kssjxxMap = new HashMap<String, Object>();
				kssjxxMap.put("xsda", tmpxxMap.get("xsda"));
				kssjxxMap.put("updatetime", Format.getDateTime());
				kssjxxMap.put("updateuserid", userinfo.getId());
				UpdateParams updateParams = UpdateParams.createUpdateParams("T_KSSJXX");
				updateParams.addParamsForMap(kssjxxMap);
				updateParams.addWhereParameter(Parameter.createParameter("id", tmpxxMap.get("id")));
				baseDaoComponent.updateDataByParams(updateParams);
				if ("1".equals(tmpxxMap.get("xsda").toString())) {
					QueryParams queryParams = QueryParams.createQueryParams("T_KSSJXX");
					queryParams.addQueryParams(Parameter.createParameter("id", tmpxxMap.get("id")));
					queryParams.printSelf();
					List<Map<String, Object>> data = baseDaoComponent.selectDataByParams(queryParams);
					if (tmpxxMap.get("xsda") != null && !"".equals(tmpxxMap.get("xsda"))) {// 判断未答题
						if (dataOld.get(0).get("xsda") == null
								|| !tmpxxMap.get("xsda").equals(dataOld.get(0).get("xsda"))) {// 判断答案是否改变
							InsertParams insertParams = InsertParams.createInsertParams("t_kssjxx_log", "id", "stid",
									"sttitle", "xuanxiangid", "xuanxiangmc", "xuanxiangxh", "xsda", "bzda", "stxh",
									"deleted", "ksid", "stlx", "addtime", "ksccid", "adduserid", "content", "fenshu",
									"ip", "logtype");
							insertParams.setValues(UUID.randomUUID().toString(), data.get(0).get("stid"),
									data.get(0).get("sttitle"), data.get(0).get("xuanxiangid"),
									data.get(0).get("xuanxiangmc"), data.get(0).get("xuanxiangxh"),
									data.get(0).get("xsda"), data.get(0).get("bzda"), data.get(0).get("stxh"),
									data.get(0).get("deleted"), data.get(0).get("ksid"), data.get(0).get("stlx"),
									Format.getDateTime(), data.get(0).get("ksccid"), userinfo.getId(),
									data.get(0).get("content"), data.get(0).get("fenshu"), userinfo.getIp(), "2");
							baseDaoComponent.insertDataByParams(insertParams);
						}
					}
				}
			}
		}
		if (null != map.get("duoxuanti")) {
			tmpMap = (Map<String, Object>) map.get("duoxuanti");
			for (int j = 0; j < ((List<Map<String, Object>>) tmpMap.get("answer")).size(); j++) {
				Map<String, Object> tmpxxMap = ((List<Map<String, Object>>) tmpMap.get("answer")).get(j);
				QueryParams queryParamsOld = QueryParams.createQueryParams("T_KSSJXX");
				List<Map<String, Object>> dataOld = null;
				if ("1".equals(tmpxxMap.get("xsda").toString())) {
					queryParamsOld.addQueryParams(Parameter.createParameter("id", tmpxxMap.get("id")));
					queryParamsOld.printSelf();
					dataOld = baseDaoComponent.selectDataByParams(queryParamsOld);
				}
				Map<String, Object> kssjxxMap = new HashMap<String, Object>();
				kssjxxMap.put("xsda", tmpMap.get("xsda"));
				kssjxxMap.put("updatetime", Format.getDateTime());
				kssjxxMap.put("updateuserid", userinfo.getId());
				UpdateParams updateParams = UpdateParams.createUpdateParams("T_KSSJXX");
				updateParams.addParamsForMap(kssjxxMap);
				updateParams.addWhereParameter(Parameter.createParameter("id", tmpxxMap.get("id")));
				baseDaoComponent.updateDataByParams(updateParams);
				if ("1".equals(tmpxxMap.get("xsda").toString())) {
					QueryParams queryParams = QueryParams.createQueryParams("T_KSSJXX");
					queryParams.addQueryParams(Parameter.createParameter("id", tmpxxMap.get("id")));
					queryParams.printSelf();
					List<Map<String, Object>> data = baseDaoComponent.selectDataByParams(queryParams);
					if (tmpxxMap.get("xsda") != null && !"".equals(tmpxxMap.get("xsda"))) {// 判断未答题
						if (dataOld.get(0).get("xsda") == null
								|| !tmpxxMap.get("xsda").equals(dataOld.get(0).get("xsda"))) {// 判断答案是否改变
							InsertParams insertParams = InsertParams.createInsertParams("t_kssjxx_log", "id", "stid",
									"sttitle", "xuanxiangid", "xuanxiangmc", "xuanxiangxh", "xsda", "bzda", "stxh",
									"deleted", "ksid", "stlx", "addtime", "ksccid", "adduserid", "content", "fenshu",
									"ip", "logtype");
							insertParams.setValues(UUID.randomUUID().toString(), data.get(0).get("stid"),
									data.get(0).get("sttitle"), data.get(0).get("xuanxiangid"),
									data.get(0).get("xuanxiangmc"), data.get(0).get("xuanxiangxh"),
									data.get(0).get("xsda"), data.get(0).get("bzda"), data.get(0).get("stxh"),
									data.get(0).get("deleted"), data.get(0).get("ksid"), data.get(0).get("stlx"),
									Format.getDateTime(), data.get(0).get("ksccid"), userinfo.getId(),
									data.get(0).get("content"), data.get(0).get("fenshu"), userinfo.getIp(), "2");
							baseDaoComponent.insertDataByParams(insertParams);
						}
					}
				}
			}
		}
		if (null != map.get("caozuoti")) {
			tmpMap = (Map<String, Object>) map.get("caozuoti");
			Map<String, Object> kssjxxMap = new HashMap<String, Object>();
			kssjxxMap.put("updatetime", Format.getDateTime());
			kssjxxMap.put("updateuserid", userinfo.getId());
			UpdateParams updateParams = UpdateParams.createUpdateParams("T_KSSJXX");
			updateParams.addParamsForMap(kssjxxMap);
			updateParams.addWhereParameter(Parameter.createParameter("id", tmpMap.get("id")));
			baseDaoComponent.updateDataByParams(updateParams);
		}
		JSONObject rs = ResponseUtils.createSuccessResponseBodyForJiem("保存成功");
		return rs;
	}

	@Override
	public JSONObject queryKssjxxLog(Map<String, Object> params) throws SQLException {
		params.remove("admininfo");
		params.remove("userinfo");
		Map<String, Object> map = new DataMap<>();
		for (Map.Entry<String, Object> m : params.entrySet()) {
			if (m.getValue() != null && !"".equals(m.getValue()))
				map.put(m.getKey(), m.getValue());
		}
		QueryParams queryParams = QueryParams.createQueryParams("v_kssjxx_log_hm");
		if (map.get("kscc") != null && !"".equals(map.get("kscc"))) {// 模糊查询
			String kscc = (String) params.get("kscc");
			// 判断是否为中文
			if (BaseChecks.isChineseChar(kscc)) {
				// 是，空格全部替换
				kscc = kscc.replaceAll(" ", "");
			} else {
				// 否，替换两端空格
				kscc = BaseChecks.bothEndsStr(kscc);
			}
			queryParams.addQueryParams(Parameter.createParameter("kscc", EOperators.类似, kscc));
			map.remove("kscc");
		}
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
		queryParams.addQueryParamsByMap(map);
		queryParams.printSelf();
		List<Map<String, Object>> data = baseDaoComponent.selectDataByParams(queryParams);
		// if (data == null || data.size() == 0) throw new
		// DataNotFoundException();
		JSONObject result = ResponseUtils.createSuccessResponseBodyForJiem("查询成功", data);
		return result;
	}

	@Override
	public JSONObject updatesfdl(Map<String, Object> params) throws SQLException {
		AdminInfo adminInfo = (AdminInfo) params.remove("admininfo");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("updatetime", Format.getDateTime());
		map.put("updateuser", adminInfo.getId());
		map.put("sfdl", "0");
		UpdateParams updateParams = UpdateParams.createUpdateParams("t_user_kscc");
		updateParams.addParamsForMap(map);
		updateParams.addWhereParameter(Parameter.createParameter("userid", params.get("userid")));
		updateParams.addWhereParameter(Parameter.createParameter("ksccid", params.get("ksccid")));
		baseDaoComponent.updateDataByParams(updateParams);
		InsertParams insertParams = InsertParams.createInsertParams("t_kssjxx_log", "id", "logtype",
				"adduserid", "addtime", "ip", "deleted", "ksid", "ksccid");
		insertParams.setValues(UUID.randomUUID().toString(), "3", adminInfo.getId(), Format.getDateTime(),
				adminInfo.getIp(), "0", params.get("userid"), params.get("ksccid"));
		baseDaoComponent.insertDataByParams(insertParams);
		JSONObject result = ResponseUtils.createSuccessResponseBodyForJiem("查询成功");
		return result;
	}

}
