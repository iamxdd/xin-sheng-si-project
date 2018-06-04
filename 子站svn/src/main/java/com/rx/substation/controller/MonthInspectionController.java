package com.rx.substation.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rx.substation.util.GetMonthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rx.substation.domain.Inspection;
import com.rx.substation.service.MonthQCInspectionService;
import com.rx.substation.service.StationService;

/**
 * 巡查扣分
 * @author admin
 *
 */

@RestController
@RequestMapping(value = "/scair/monthdeduct")
public class MonthInspectionController {

	@Autowired
	MonthQCInspectionService monthQCInspectionService;
	
	@Autowired
	StationService stationService;
	/**
	 * 查看上月所有扣分
	 * @param start
	 * @param end
	 * @return
	 */
	@RequestMapping(value="/findall")
	public List<Map<String,Object>> findMonthDeductScore(String start,String end){
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		 sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
         Calendar cal = Calendar.getInstance();
        //获得上个月月初
     	cal.add(Calendar.MONTH, -1);
 	    cal.set(Calendar.DATE, 1);
 	    start = sdf.format(cal.getTime());
 	    //获得上个月月末
 	    Calendar cal2 = Calendar.getInstance();
 	    cal2.add(Calendar.MONTH, -1);
 	    cal2.set(Calendar.DATE,cal2.getActualMaximum(Calendar.DAY_OF_MONTH));
 	    end = sdf.format(cal2.getTime());
		return monthQCInspectionService.getMonthQCInspection(start, end);
	}

	/**
	 * 查看扣分详情
	 * @return
	 */
	@RequestMapping(value="/findByDetail")
	public Map<String,Object> findDeductByDetailId(HttpServletRequest httpServletRequest){
		String detailId = httpServletRequest.getParameter("detailId");
		List<Map<String,Object>>  deductList = monthQCInspectionService.findDeductByDetailId(detailId);
		String  timepoint = monthQCInspectionService.findCheckTime(detailId);
		List detaillist = new ArrayList();
		for (int i = 0; i < deductList.size(); i++) {
			Map<String,Object> deductmap = deductList.get(i);
			if (deductmap.get("detail") != null ){
				String detail = deductmap.get("detail").toString();
				Object detailList = JSON.parse(detail);
				detaillist.add(detailList);
			}
		}
		Map<String,Object> deductTotalMap =  new HashMap<>();
		Double totalDeduct = monthQCInspectionService.findTotalDeduct(detailId);
		String checkPerson = monthQCInspectionService.findCheckPerson(detailId);
		Map<String,Object> detail = monthQCInspectionService.findDetailByDetailId(detailId);
		deductTotalMap.put("checkTime",timepoint);
		deductTotalMap.put("checkPerson",checkPerson);
		deductTotalMap.put("totalDeduct",totalDeduct);
		deductTotalMap.put("detail",detail);
		deductTotalMap.put("deductList",deductList);
		deductTotalMap.put("detailMap",detaillist);
		return deductTotalMap;
	}

	/**
	 * 多条件查询扣分
	 * @param httpServletRequest
	 * @return
	 */
	@RequestMapping(value="/findByConditions")
	public List<Map<String,Object>> findDeductByConditions(HttpServletRequest httpServletRequest){
		String status = httpServletRequest.getParameter("status");
		String[] runUnitIds =  httpServletRequest.getParameterValues("runUnitIds");
		String[] cityIds = httpServletRequest.getParameterValues("cityIds");
		String datestart = httpServletRequest.getParameter("start");
		String dateend = httpServletRequest.getParameter("end");
		String start = null;
		String end = null;
		try {
			start = GetMonthUtil.getFirstDay(datestart);
			end = GetMonthUtil.getLastDay(dateend);
		}catch (Exception e){
			e.printStackTrace();
		}
		String[] stationCodes = httpServletRequest.getParameterValues("stationCodes");
		return monthQCInspectionService.findDeductByConditions(runUnitIds,cityIds,start,end,stationCodes,status);
	}
	
	
	
	/**
	 * 保存扣分修改扣分
	 * @param httpServletRequest
	 */
	@RequestMapping(value="/savedeductscore")
	public Map<String,Object> saveDeductScore(HttpServletRequest httpServletRequest){
		Map<String,Object> result = new HashMap<>();
		//根据username进行权限判断，只需要判断该用户下的站点是否包含操作站点
		String detailId = httpServletRequest.getParameter("detailId");
		if (detailId !=null && detailId != ""){
			result = updateDeductScore(httpServletRequest);
		}else {
			String stationCode = httpServletRequest.getParameter("stationCode");
			String timePoint = httpServletRequest.getParameter("timePoint");
			String time = httpServletRequest.getParameter("checkTime");
			String checkPerson = httpServletRequest.getParameter("checkPerson");
			String runPerson = httpServletRequest.getParameter("runPerson");
			String runTime = httpServletRequest.getParameter("runTime");
			String runName = httpServletRequest.getParameter("runName");
			String other = httpServletRequest.getParameter("other");

			detailId = UUID.randomUUID().toString().replaceAll("\\-", "");

			//保存扣分详情
			double countDeduct = 0.0;
			for (int i = 0; i <= 26; i++) {
				Map<String,Object> detailMap = new HashMap<>();

				String score = httpServletRequest.getParameter("score"+i);
				String status = httpServletRequest.getParameter("status"+i);
				String mark = httpServletRequest.getParameter("mark"+i);
				if(i == 13){
					Map<String,Object> jsonMap = new HashMap<>();
					for (int j = 0; j <= 10; j++) {
						String detaildata = httpServletRequest.getParameter("detail"+j);
						jsonMap.put("detail"+j,detaildata);
					}
					String detail = JSON.toJSONString(jsonMap);
					detailMap.put("detail",detail);
				}
				if(i == 14){
					Map<String,Object> jsonMap = new HashMap<>();
					for (int j = 11; j <= 26; j++) {
						String detaildata = httpServletRequest.getParameter("detail"+j);
						jsonMap.put("detail"+j,detaildata);
					}
					String detail = JSON.toJSONString(jsonMap);
					detailMap.put("detail",detail);
				}
				if(i == 15){
					Map<String,Object> jsonMap = new HashMap<>();
					for (int j = 27; j <= 43; j++) {
						String detaildata = httpServletRequest.getParameter("detail"+j);
						jsonMap.put("detail"+j,detaildata);
					}
					String detail = JSON.toJSONString(jsonMap);
					detailMap.put("detail",detail);
				}
				if(i == 16){
					Map<String,Object> jsonMap = new HashMap<>();
					for (int j = 44; j <= 59; j++) {
						String detaildata = httpServletRequest.getParameter("detail"+j);
						jsonMap.put("detail"+j,detaildata);
					}
					String detail = JSON.toJSONString(jsonMap);
					detailMap.put("detail",detail);
				}
				if(i == 17){
					Map<String,Object> jsonMap = new HashMap<>();
					for (int j = 60; j <= 78; j++) {
						String detaildata = httpServletRequest.getParameter("detail"+j);
						jsonMap.put("detail"+j,detaildata);
					}
					String detail = JSON.toJSONString(jsonMap);
					detailMap.put("detail",detail);
				}
				if(i == 18) {
					Map<String, Object> jsonMap = new HashMap<>();
					for (int j = 79; j <= 84; j++) {
						String detaildata = httpServletRequest.getParameter("detail" + j);
						jsonMap.put("detail" + j, detaildata);
					}
					String detail = JSON.toJSONString(jsonMap);
					detailMap.put("detail", detail);
				}
				if(i == 19){
					Map<String,Object> jsonMap = new HashMap<>();
					for (int j = 85; j <= 89; j++) {
						String detaildata = httpServletRequest.getParameter("detail"+j);
						jsonMap.put("detail"+j,detaildata);
					}
					String detail = JSON.toJSONString(jsonMap);
					detailMap.put("detail",detail);
				}if(i == 20){
					Map<String,Object> jsonMap = new HashMap<>();
					for (int j = 90; j <= 94; j++) {
						String detaildata = httpServletRequest.getParameter("detail"+j);
						jsonMap.put("detail"+j,detaildata);
					}
					String detail = JSON.toJSONString(jsonMap);
					detailMap.put("detail",detail);
				}

				detailMap.put("detailId",detailId);
				detailMap.put("checkWorkId",i);
				detailMap.put("score",score);
				detailMap.put("status",status);
				detailMap.put("mark",mark);

				monthQCInspectionService.saveDeductDetail(detailMap);
				if (score!=null){
					countDeduct =  countDeduct + Double.valueOf(score);
				}
			}


			//保存扣分
			Map<String,Object> deductMap = new HashMap<>();
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			try {
				deductMap.put("checkTime",sdf1.parse(time+":00"));
				deductMap.put("timePoint",sdf1.parse(timePoint+":00"));
				deductMap.put("runTime",sdf1.parse(runTime+":00"));

			} catch (ParseException e) {
				e.printStackTrace();
			}
			deductMap.put("stationCode",stationCode);
			deductMap.put("stationName",stationService.getStationNameByStationCode(stationCode));
			deductMap.put("deduct",countDeduct);
			deductMap.put("checkPerson",checkPerson);
			deductMap.put("runPerson",runPerson);
			deductMap.put("runName",runName);
			deductMap.put("mark",other);
			deductMap.put("detailId",detailId);


			monthQCInspectionService.saveDeductScore(deductMap);

			Map<String,Object> resultmap = new HashMap<>();
			resultmap.put("result", "true");
			result = resultmap;
		}
		return result;
	}

	//删除扣分表和扣分详情
	@RequestMapping(value="/deletedeductscore")
	public Map<String,Object> deleteDeductScore(HttpServletRequest httpServletRequest){
		Map<String,Object> resultmap = new HashMap<>();
		String detailId = httpServletRequest.getParameter("detailId");
		if(monthQCInspectionService.findDeductByDetailId(detailId).size() > 0){
			monthQCInspectionService.deleteDeduct(detailId);
			monthQCInspectionService.deleteDetailId(detailId);
			resultmap.put("result", "true");
			return resultmap;
		}else {
			resultmap.put("result", "false");
			return resultmap;
		}

	}


	//修改扣分表和扣分详情
	public Map<String,Object> updateDeductScore(HttpServletRequest httpServletRequest){

		String detailId = httpServletRequest.getParameter("detailId");

		String stationCode = httpServletRequest.getParameter("stationCode");
		String time = httpServletRequest.getParameter("checkTime");
		String checkPerson = httpServletRequest.getParameter("checkPerson");
		String mark = httpServletRequest.getParameter("other");
		String runPerson = httpServletRequest.getParameter("runPerson");
		String runTime = httpServletRequest.getParameter("runTime");
		String runName = httpServletRequest.getParameter("runName");
		String timePoint = httpServletRequest.getParameter("timePoint");

		//修改扣分详情
		double countDeduct = 0.0;
		for (int i = 0; i <= 26; i++) {
			Map<String,Object> detailMap = new HashMap<>();

			String score = httpServletRequest.getParameter("score"+i);
			String status = httpServletRequest.getParameter("status"+i);
			if(i == 13){
				Map<String,Object> jsonMap = new HashMap<>();
				for (int j = 0; j <= 10; j++) {
					String detaildata = httpServletRequest.getParameter("detail"+j);
					jsonMap.put("detail"+j,detaildata);
				}
				String detail = JSON.toJSONString(jsonMap);
				detailMap.put("detail",detail);
			}
			if(i == 14){
				Map<String,Object> jsonMap = new HashMap<>();
				for (int j = 11; j <= 26; j++) {
					String detaildata = httpServletRequest.getParameter("detail"+j);
					jsonMap.put("detail"+j,detaildata);
				}
				String detail = JSON.toJSONString(jsonMap);
				detailMap.put("detail",detail);
			}
			if(i == 15){
				Map<String,Object> jsonMap = new HashMap<>();
				for (int j = 27; j <= 43; j++) {
					String detaildata = httpServletRequest.getParameter("detail"+j);
					jsonMap.put("detail"+j,detaildata);
				}
				String detail = JSON.toJSONString(jsonMap);
				detailMap.put("detail",detail);
			}
			if(i == 16){
				Map<String,Object> jsonMap = new HashMap<>();
				for (int j = 44; j <= 59; j++) {
					String detaildata = httpServletRequest.getParameter("detail"+j);
					jsonMap.put("detail"+j,detaildata);
				}
				String detail = JSON.toJSONString(jsonMap);
				detailMap.put("detail",detail);
			}
			if(i == 17){
				Map<String,Object> jsonMap = new HashMap<>();
				for (int j = 60; j <= 78; j++) {
					String detaildata = httpServletRequest.getParameter("detail"+j);
					jsonMap.put("detail"+j,detaildata);
				}
				String detail = JSON.toJSONString(jsonMap);
				detailMap.put("detail",detail);
			}
			if(i == 18) {
				Map<String, Object> jsonMap = new HashMap<>();
				for (int j = 79; j <= 84; j++) {
					String detaildata = httpServletRequest.getParameter("detail" + j);
					jsonMap.put("detail" + j, detaildata);
				}
				String detail = JSON.toJSONString(jsonMap);
				detailMap.put("detail", detail);
			}
			if(i == 19){
				Map<String,Object> jsonMap = new HashMap<>();
				for (int j = 85; j <= 89; j++) {
					String detaildata = httpServletRequest.getParameter("detail"+j);
					jsonMap.put("detail"+j,detaildata);
				}
				String detail = JSON.toJSONString(jsonMap);
				detailMap.put("detail",detail);
			}if(i == 20){
				Map<String,Object> jsonMap = new HashMap<>();
				for (int j = 90; j <= 94; j++) {
					String detaildata = httpServletRequest.getParameter("detail"+j);
					jsonMap.put("detail"+j,detaildata);
				}
				String detail = JSON.toJSONString(jsonMap);
				detailMap.put("detail",detail);
			}

			detailMap.put("detailId",detailId);
			detailMap.put("checkWorkId",i+1);
			detailMap.put("score",score);
			detailMap.put("status",status);
			monthQCInspectionService.updateDeductDetail(detailMap);
			if (score!=null){
				countDeduct =  countDeduct + Double.valueOf(score);
			}
		}


		//修改
		Map<String,Object> deductMap = new HashMap<>();
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			deductMap.put("checkTime",sdf1.parse(time));
			deductMap.put("runTime",sdf1.parse(runTime));
			deductMap.put("timePoint",sdf1.parse(timePoint));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		deductMap.put("stationCode",stationCode);
		deductMap.put("stationName",stationService.getStationNameByStationCode(stationCode));
		deductMap.put("deduct",countDeduct);
		deductMap.put("checkPerson",checkPerson);
		deductMap.put("runPerson",runPerson);
		deductMap.put("runName",runName);
		deductMap.put("mark",mark);
		deductMap.put("detailId",detailId);


		monthQCInspectionService.updateDeductScore(deductMap);

		Map<String,Object> resultmap = new HashMap<>();
		resultmap.put("result", "true");
		return resultmap;

	}


	//统计飞行扣分表
	@RequestMapping(value="/countInspection")
	public List<Map<String,Object>> countInspection(HttpServletRequest hsq){
		String status = hsq.getParameter("status");
		String type = hsq.getParameter("type");
		String[] runUnitIds =  hsq.getParameterValues("runUnitIds");
		String[] cityIds = hsq.getParameterValues("cityIds");
		String datestart = hsq.getParameter("start");
		String dateend = hsq.getParameter("end");
		String start = null;
		String end = null;
		String[] stationCodes = hsq.getParameterValues("stationCodes");
		List<Map<String,Object>> result = new ArrayList<>();
		try {
			start = GetMonthUtil.getFirstDay(datestart);
			end = GetMonthUtil.getLastDay(dateend);
			result = monthQCInspectionService.countInspection(runUnitIds,cityIds,start,end,stationCodes,status,type);
		}catch (Exception e){
			e.printStackTrace();
		}
		return result;
	}

}
