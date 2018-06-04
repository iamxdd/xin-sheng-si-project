package com.rx.substation.controller;

import com.rx.substation.service.MonthBasicCheckService;
import com.rx.substation.service.StationService;
import com.rx.substation.util.GetMonthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 基本保障检查
 * @author admin
 *
 */

@RestController
@RequestMapping(value = "/scair/basiccheck")
public class MonthBasicCheckController {

	@Autowired
	MonthBasicCheckService monthBasicCheckService;
	
	@Autowired
	StationService stationService;
	/**
	 * 查看上月所有
	 * @param start
	 * @param end
	 * @return
	 */
	@RequestMapping(value="/findall")
	public List<Map<String,Object>> findBasicCheck(String start,String end){
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
		return monthBasicCheckService.getMonthBasicCheck(start, end);
	}

	/**
	 * 查看详情
	 * @return
	 */
	@RequestMapping(value="/findByDetail")
	public Map<String,Object> findDeductByDetailId(HttpServletRequest httpServletRequest){
		String detailId = httpServletRequest.getParameter("detailId");
		List<Map<String,Object>>  basicCheckList = monthBasicCheckService.findBasicCheckByDetailId(detailId);
		Map<String, Object> stationmap = monthBasicCheckService.findStation(detailId);
		Map<String,Object> basicCheckMap =  new HashMap<>();
		basicCheckMap.put("stationmap",stationmap);
		basicCheckMap.put("deductList",basicCheckList);
		return basicCheckMap;
	}

	/**
	 * 多条件查询
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
		return monthBasicCheckService.findBasicByConditions(runUnitIds,cityIds,start,end,stationCodes,status);
	}
	
	
	
	/**
	 * 保存扣分修改扣分
	 * @param httpServletRequest
	 */
	@RequestMapping(value="/savebasiccheck")
	public Map<String,Object> saveBasicCheck(HttpServletRequest httpServletRequest){
		Map<String,Object> result = new HashMap<>();
		String DetailId = httpServletRequest.getParameter("DetailId");
		if (DetailId !=null && DetailId != ""){
			result = updateBasicCheck(httpServletRequest);
		}else {
			String StationCode = httpServletRequest.getParameter("StationCode");
			String TimePoint = httpServletRequest.getParameter("TimePoint");
			String CheckTime1 = httpServletRequest.getParameter("CheckTime1");
			String CheckTime2 = httpServletRequest.getParameter("CheckTime2");
			String Person1 = httpServletRequest.getParameter("Person1");
			String Person2 = httpServletRequest.getParameter("Person2");
			String Others = httpServletRequest.getParameter("Others");

			DetailId = UUID.randomUUID().toString().replaceAll("\\-", "");

			//保存扣分详情
			for (int i = 1; i <= 18; i++) {
				Map<String,Object> detailMap = new HashMap<>();

				String Status = httpServletRequest.getParameter("Status"+i);
				String DetailMark = httpServletRequest.getParameter("DetailMark"+i);


				detailMap.put("DetailId",DetailId);
				detailMap.put("Id",i);
				detailMap.put("Status",Status);
				detailMap.put("DetailMark",DetailMark);


				monthBasicCheckService.saveBasicCheckDetail(detailMap);

			}


			//保存扣分
			Map<String,Object> deductMap = new HashMap<>();
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd hh:mm");
			try {
				deductMap.put("CheckTime1",sdf1.parse(CheckTime1+":00"));
				deductMap.put("CheckTime2",sdf1.parse(CheckTime2+":00"));
				deductMap.put("TimePoint",sdf1.parse(TimePoint+":00"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			deductMap.put("StationCode",StationCode);
			deductMap.put("StationName",stationService.getStationNameByStationCode(StationCode));
			deductMap.put("Person1",Person1);
			deductMap.put("Person2",Person2);
			deductMap.put("Others",Others);
			deductMap.put("DetailId",DetailId);


			monthBasicCheckService.saveBasicCheck(deductMap);

			Map<String,Object> resultmap = new HashMap<>();
			resultmap.put("result", "true");
			result = resultmap;
		}
		return result;
	}

	//删除扣分表和扣分详情
	@RequestMapping(value="/deletebasiccheck")
	public Map<String,Object> deleteBasicCheck(HttpServletRequest httpServletRequest){
		Map<String,Object> resultmap = new HashMap<>();
		String detailId = httpServletRequest.getParameter("detailId");
		if(monthBasicCheckService.findBasicCheckByDetailId(detailId).size() > 0){
			monthBasicCheckService.deleteBasicCheck(detailId);
			monthBasicCheckService.deleteBasicCheckDetail(detailId);
			resultmap.put("result", "true");
			return resultmap;
		}else {
			resultmap.put("result", "false");
			return resultmap;
		}

	}


	//修改扣分表和扣分详情
	public Map<String,Object> updateBasicCheck(HttpServletRequest httpServletRequest){

		String DetailId = httpServletRequest.getParameter("DetailId");

		String StationCode = httpServletRequest.getParameter("StationCode");
		String TimePoint = httpServletRequest.getParameter("TimePoint");
		String CheckTime1 = httpServletRequest.getParameter("CheckTime1");
		String CheckTime2 = httpServletRequest.getParameter("CheckTime2");
		String Person1 = httpServletRequest.getParameter("Person1");
		String Person2 = httpServletRequest.getParameter("Person2");
		String Others = httpServletRequest.getParameter("Others");

		//修改扣分详情
		for (int i = 1; i <= 18; i++) {
			Map<String,Object> detailMap = new HashMap<>();

			String Status = httpServletRequest.getParameter("Status"+i);
			String DetailMark = httpServletRequest.getParameter("DetailMark"+i);


			detailMap.put("DetailId",DetailId);
			detailMap.put("Id",i);
			detailMap.put("Status",Status);
			detailMap.put("DetailMark",DetailMark);

			monthBasicCheckService.updateDeductDetail(detailMap);

		}


		//修改
		Map<String,Object> deductMap = new HashMap<>();
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			deductMap.put("CheckTime1",sdf1.parse(CheckTime1+":00"));
			deductMap.put("CheckTime2",sdf1.parse(CheckTime2+":00"));
			deductMap.put("TimePoint",sdf1.parse(TimePoint+":00"));

		} catch (ParseException e) {
			e.printStackTrace();
		}
		deductMap.put("StationCode",StationCode);
		deductMap.put("StationName",stationService.getStationNameByStationCode(StationCode));
		deductMap.put("Person1",Person1);
		deductMap.put("Person2",Person2);
		deductMap.put("Others",Others);
		deductMap.put("DetailId",DetailId);

		monthBasicCheckService.updateDeductScore(deductMap);

		Map<String,Object> resultmap = new HashMap<>();
		resultmap.put("result", "true");
		return resultmap;

	}
	
	
}
