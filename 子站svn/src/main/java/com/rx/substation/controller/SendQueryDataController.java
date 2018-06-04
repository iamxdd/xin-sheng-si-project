package com.rx.substation.controller;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.rx.substation.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rx.substation.dao.StationDAO;

/**
 *	条件筛选数据源
 * @author dcx
 *
 */


@RestController
@Component
@RequestMapping(value = "/scair/sendqureydata")
public class SendQueryDataController {
	
	@Autowired
	StationDAO stationDAO;

	@Autowired
	StationService stationService;
	
	@RequestMapping(value="/getstationmsg")
	public List<Map<String,Object>> getStationMsg(HttpServletRequest httpServletRequest){
		//更新运行单位
		updateMsg();
		List<Map<String,Object>> maps = new ArrayList<>();
		String[] runUnitIds = httpServletRequest.getParameterValues("runUnitIds");
		String[] stationTypeIds = httpServletRequest.getParameterValues("stationTypeIds");

		List<Map<String,Object>> citys = stationDAO.getCityMsgs(runUnitIds,stationTypeIds);
		for (int i = 0; i < citys.size(); i++) {
			Map<String,Object> city = citys.get(i);
			String cityId = city.get("CityId").toString();
			List<Map<String,Object>> stationMsg = stationDAO.getAllStationMassages(runUnitIds,stationTypeIds,cityId);
			city.put("stationMsg",stationMsg);
			maps.add(city);
		}
		return maps;

	}

	/**
	 *  多条件查询出城市站点信息
	 * @param httpServletRequest
	 * @return
	 */
	@RequestMapping(value="/getstationmsgs")
	public List<Map<String,Object>> getStationMsgs(HttpServletRequest httpServletRequest){

		List<Map<String,Object>> maps = new ArrayList<>();
		String[] runUnitIds = httpServletRequest.getParameterValues("runUnitIds");
		String[] stationTypeIds = httpServletRequest.getParameterValues("stationTypeIds");

		List<Map<String,Object>> citys = stationDAO.getCityMsgs(runUnitIds,stationTypeIds);
		for (int i = 0; i < citys.size(); i++) {
			Map<String,Object> city = citys.get(i);
			String cityId = city.get("CityId").toString();
			List<Map<String,Object>> stationMsg = stationDAO.getAllStationMassages(runUnitIds,stationTypeIds,cityId);
			city.put("stationMsg",stationMsg);
			maps.add(city);
		}
		return maps;
	}

	/**
	 * 得到所有运行单位信息
	 * @return
	 */
	@RequestMapping(value="/getrununit")
	public List<Map<String,Object>> getRunUnitMsg(){
		return stationDAO.getRunUnitMsg();

	}

	/**
	 *得到所有站点类型信息
	 * @return
	 */
	@RequestMapping(value="/getstationtype")
	public List<Map<String,Object>> getStationTypeMsg(){
		return stationDAO.getStationTypeId();

	}

	/**
	 * 得到所有站点名称
	 * @return
	 */
	@RequestMapping(value="/getallstation")
	public List<Map<String,Object>> getallstation(){

		return stationDAO.getAllStation();
	}

	/**
	 * 扣分表检查人
	 * @return
	 */
	@RequestMapping(value="/getcheckperson")
	public List<Map<String,Object>> getCheckPerson(){
		
		List<Map<String,Object>> personlist=  new ArrayList<>();
		
		Map<String,Object> checkperson1= new HashMap<>();
		Map<String,Object> checkperson2= new HashMap<>();
		Map<String,Object> checkperson3= new HashMap<>();
		
		
		checkperson1.put("checkPerson", "张三");
		checkperson2.put("checkPerson", "李四");
		checkperson3.put("checkPerson", "王二");

		personlist.add(checkperson1);
		personlist.add(checkperson2);
		personlist.add(checkperson3);
		return personlist;
		
	}


	/**
	 * 更新运行单位归属
	 */
	@RequestMapping(value="/updatemsg")
	public void updateMsg(){
		System.out.println("更新开始！");
		stationService.updateStationMsg();
		System.out.println("更新结束！");
	}


	/**
	 * 市   区县   站点  三级级联
	 *
	 *
	 */

	@RequestMapping(value="/getAllCity")
	public List<Map<String, Object>> getAllCity(){
		return stationService.getAllCity();
	}

	@RequestMapping(value="/getCounty")
	public List<Map<String, Object>> getCounty(HttpServletRequest hsr){
		String CityCode = hsr.getParameter("CityCode");
		return stationService.getCounty(CityCode);
	}

	@RequestMapping(value="/getStationCode")
	public List<Map<String, Object>> getStationCode(HttpServletRequest hsr){
		String CountyId = hsr.getParameter("CountyId");
		return stationService.getStationCode(CountyId);
	}

	@RequestMapping(value="/getRunMsgByStationCode")
	public Map<String, Object> getRunMsgByStationCode(HttpServletRequest hsr){
		String stationCode = hsr.getParameter("StationCode");
		return stationService.getRunMsgByStationCode(stationCode);
	}




}
