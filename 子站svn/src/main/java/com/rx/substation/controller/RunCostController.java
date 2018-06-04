package com.rx.substation.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rx.substation.service.RunCostService;
import com.rx.substation.util.GetMonthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 运行费用
 * @author dcx
 *
 */
@RestController
@RequestMapping(value = "/scair/runcost")
public class RunCostController {

	@Autowired
	RunCostService runCostService;

	@RequestMapping(value="/findByConditions")
	public List<Map<String, Object>> findrunCostByConditions(HttpServletRequest httpServletRequest){
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
		return runCostService.findrunCostByConditions(runUnitIds,cityIds,start,end,stationCodes,status);
	}

	@RequestMapping(value="/getAllStationMsg")
	public List<Map<String, Object>> getAllStationMsg(HttpServletRequest httpServletRequest){
		String stationCode = httpServletRequest.getParameter("StationCode");
		String timePoint = httpServletRequest.getParameter("TimePoint");
		return runCostService.getAllStationMsg(stationCode,timePoint);
	}

	@RequestMapping(value="/saveRunCost")
	public Map<String,Object> saveRunCost(HttpServletRequest httpServletRequest){
		String dataList = httpServletRequest.getParameter("dataList");
		Map<String,Object> result = new HashMap<>();
		if (dataList != null){
			List<Map<String, Object>> list = (List)JSON.parseArray(dataList);
			for (int i = 0; i < list.size() ; i++) {
				String stationCode = list.get(i).get("StationCode").toString();
				String timePoint = list.get(i).get("TimePoint").toString();
				List<Map<String,Object>> runCostList = runCostService.getStationCostMsg(timePoint,stationCode);
				if(runCostList==null){
					runCostService.saveRunCost(list.get(i));
					result.put("result",1);
				}else {
					runCostService.updateRunCost(list.get(i));
					result.put("result",2);
				}
			}
		}else {
			result.put("result",0);
		}
		return  result;
	}

	
	
}
