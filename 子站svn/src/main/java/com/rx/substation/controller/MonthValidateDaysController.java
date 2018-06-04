package com.rx.substation.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;

import com.rx.substation.util.GetMonthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rx.substation.service.MonthValidateService;

/**
 * 站点月有效天数
 * @author dcx
 *
 */

@RestController
@RequestMapping(value = "/scair/monthvalidate")
public class MonthValidateDaysController {

	@Autowired
	MonthValidateService monthValidateService;
	@RequestMapping(value="/findall")
	public List<Map<String, Object>> findMonthValidateDays(String start, String end){
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
		return monthValidateService.findMonthValidateDays(start,end);
	}
	
	@RequestMapping(value="/findByConditions")
	public List<Map<String, Object>> findValidateByConditions(HttpServletRequest httpServletRequest){
		String status = httpServletRequest.getParameter("status");
		String[] runUnitIds =  httpServletRequest.getParameterValues("runUnitIds");
		String[] cityIds = httpServletRequest.getParameterValues("cityIds");
		String datestart = httpServletRequest.getParameter("start");
		String dateend = httpServletRequest.getParameter("end");
		String param = httpServletRequest.getParameter("param");
		String sortstatus = httpServletRequest.getParameter("sortstatus");
		String start = null;
		String end = null;
		try {
			start = GetMonthUtil.getFirstDay(datestart);
			end = GetMonthUtil.getLastDay(dateend);
		}catch (Exception e){
			e.printStackTrace();
		}
		String[] stationCodes = httpServletRequest.getParameterValues("stationCodes");
		return monthValidateService.findValidateByConditions(runUnitIds,cityIds,start,end,stationCodes,status,param,sortstatus);
	}
	
}
