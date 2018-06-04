package com.rx.substation.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.rx.substation.service.MonthCompleteStatusService;
import com.rx.substation.util.DateCycleUtil;
import com.rx.substation.util.GetMonthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rx.substation.service.MonthRunUnitQCService;
/**
 * 站点周报，月报，半年报，年报，工作完成情况
 * @author dcx
 *
 */
@RestController
@RequestMapping(value = "/scair/monthRunUnitQC")
public class MonthRunUnitQCController {

	@Autowired
	MonthRunUnitQCService monthRunUnitQCService;

	@Autowired
	MonthCompleteStatusService monthCompleteStatusService;
	
//	@RequestMapping(value="/findall")
//	public List<Map<String,Object>> findMonthValidateDays(String start,String end){
//
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
//        Calendar cal = Calendar.getInstance();
//        //获得上个月月初
//    	cal.add(Calendar.MONTH, -1);
//	    cal.set(Calendar.DATE, 1);
//	    start = sdf.format(cal.getTime());
//	    //获得上个月月末
//	    Calendar cal2 = Calendar.getInstance();
//	    cal2.add(Calendar.MONTH, -1);
//	    cal2.set(Calendar.DATE,cal2.getActualMaximum(Calendar.DAY_OF_MONTH));
//	    end = sdf.format(cal2.getTime());
//
//		return monthRunUnitQCService.getAllMonthQC(start,end);
//		return monthRunUnitQCService.getMonthQC(start,end);
//	}
	
	@RequestMapping(value="/findByConditions")
	public List<Map<String,Object>> findRunUnitQcByConditions(HttpServletRequest httpServletRequest){
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
		return monthRunUnitQCService.findRunUnitQcByConditions(runUnitIds,cityIds,start,end,stationCodes,status);
	}



	/**
	 * 获取城市的完成情况及站点的完成情况（农村站、城市、直管、省控4个类型站点）
	 * 查询数据（tableAuditStatus）
	 * timepoint
	 * tableid 周：0，月：1，季度：2，半年：3，年：4
	 * @return
	 */
	@RequestMapping(value="/getStationQCCount")
	public List<Map<String,Object>> getStationQCCount(HttpServletRequest httpServletRequest){
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
		int tableid = Integer.parseInt(httpServletRequest.getParameter("tableid"));
		String[] stationCodes = httpServletRequest.getParameterValues("stationCodes");
		List<Map<String,Object>> data  = new ArrayList<>();
		try {
			data  =monthCompleteStatusService.getTableCompleteStatus(start,end,tableid,stationCodes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}


	@RequestMapping(value="/test")
	public List<Map<String,Object>> test(){
		List<String> strings = new ArrayList<>();
		strings.add("2018-02-18 11:13:56");
		strings.add("2018-02-18 12:02:47");
		strings.add("2018-02-25 16:52:13");
		strings.add("2018-03-04 13:57:35");
		strings.add("2018-03-11 14:56:00");
		strings.add("2018-03-16 11:31:32");
		strings.add("2018-03-23 11:41:35");
		List<Map<String,Object>> result = DateCycleUtil.dateValidate(strings,0);
		return result;
	}

	@RequestMapping(value="/test1")
	public int test1(){
		int result = 0;

		result =  DateCycleUtil.getDaysBetween2("2017-03-04","2018-03-11");
		return result;
	}
}
