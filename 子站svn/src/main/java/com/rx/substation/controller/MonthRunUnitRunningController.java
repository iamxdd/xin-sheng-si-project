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

import com.rx.substation.service.MonthRunUnitRunningService;

/**
 * 运行单位运行质量
 * @author dcx
 *
 */
@RestController
@RequestMapping(value = "/scair/monthrununitworking")
public class MonthRunUnitRunningController {

	@Autowired
	MonthRunUnitRunningService monthRunUnitRunningService;

	@RequestMapping(value="/findByConditions")
	public List<Map<String, Object>> findRunUnitQcByConditions(HttpServletRequest httpServletRequest){
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
		return monthRunUnitRunningService.findRunUnitQcByConditions(runUnitIds,cityIds,start,end,stationCodes);
	}
	
}
