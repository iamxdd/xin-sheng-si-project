package com.rx.substation.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.rx.substation.util.GetMonthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rx.substation.domain.CalTotalScore;
import com.rx.substation.service.CalScorceService;

/**
 * 总得分
 * @author dcx
 *
 */
@RestController
@RequestMapping(value = "/scair/calscorce")
public class CalScoresController {
	
	@Autowired
	CalScorceService calScorceService;
	
	@RequestMapping(value="/calByBrand")
	public List<Map<String, Object>> calScorceByBrand(String start, String end, int brandId){
		return calScorceService.getCalScorceByBrand(start, end, brandId);
	}
	@RequestMapping(value="/calByCity")
	public List<Map<String, Object>> calScorceByCity(String start, String end, int cityId){
		return calScorceService.getCalScorceByCity(start, end, cityId);
	}
	@RequestMapping(value="/calByStation")
	public Map<String, Object> calScorceByStation(String start, String end,String stationCode){
		return calScorceService.getCalScorceByStation(start, end, stationCode);
	}
	
	
	//得到上月站点得分
	@RequestMapping(value="/findall")
	public List<Map<String, Object>> getCalTotalScore(String start,String end){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
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
		return calScorceService.getCalTotalScore(start,end);
	}
	
	
	@RequestMapping(value="/checkMonthScorce")
	public Map<String, Object> getMonthScore(String start, String end,String stationCode){
		return calScorceService.getCalScorceByStation(start, end, stationCode);
	}
	
	@RequestMapping(value="/findByConditions")
	public List<Map<String, Object>> findcalScorceByConditions(HttpServletRequest httpServletRequest){
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
		return calScorceService.findcalScorceByConditions(runUnitIds,cityIds,start,end,stationCodes,status,param,sortstatus);
	}
	
	
}
