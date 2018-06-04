package com.rx.substation.service;

import com.rx.substation.dao.MonthBasicCheckDAO;
import com.rx.substation.util.GetMonthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MonthBasicCheckService {
	
	@Autowired
	MonthBasicCheckDAO monthBasicCheckDAO;
	
	public List<Map<String,Object>> getMonthBasicCheck(String start, String end) {
		return monthBasicCheckDAO.getMonthBasicCheck(start, end);
	}

	public void saveBasicCheck(Map<String, Object> deductMap) {

		monthBasicCheckDAO.saveBasicCheck(deductMap);
	}

	/**
	 * 通过条件得到基本保障表
	 * @param runUnitIds
	 * @param cityIds
	 * @param start
	 * @param end
	 * @param stationCodes
	 * @param status
	 * @return
	 */
	public List<Map<String,Object>> findBasicByConditions(String[] runUnitIds,String[] cityIds,String start,
		String end, String[] stationCodes,String status) {
		String runUnitId = null;
		String cityId = null;
		String citys = null;
		String stations = null;
		List<Map<String, Object>> result = new ArrayList<>();
		if(cityIds!=null){
			for (int i = 0; i < cityIds.length; i++) {
				if(i>0){
					citys = citys + "," + cityIds[i];
				}else{
					citys = cityIds[0];
				}
			}
		}
		if(stationCodes!=null){
			for (int i = 0; i < stationCodes.length; i++) {
				if(i>0){
					stations = stations + ",'" + stationCodes[i]+"'";
				}else{
					stations = "'"+ stationCodes[0]+"'";
				}
			}
		}

		//以运行单位来进行展示
		if(status.equals("1")){
			if(runUnitIds!=null){
				for (int i = 0; i < runUnitIds.length; i++){
					runUnitId = runUnitIds[i];
					try {
						List<String> months = GetMonthUtil.getMonthBetween(start,end);
						for (int j = 0; j < months.size(); j++) {
							String month = months.get(j);
							try {
								String monthStart = GetMonthUtil.getFirstDay(month);
								String monthEnd = GetMonthUtil.getLastDay(month);
								List<Map<String, Object>> dataResult =monthBasicCheckDAO.findBasicCheckByConditions(runUnitId,citys,monthStart,monthEnd,stations);
								if(dataResult.size()>0){
									Map<String, Object> dataMap = new HashMap<>();
									//总体扣分项
									String runName = dataResult.get(0).get("runName").toString();
									String runCode = dataResult.get(0).get("runCode").toString();
									dataMap.put("runName",runName);
									dataMap.put("runCode",runCode);
									dataMap.put("checkTime",month.substring(0,7));
									dataMap.put("dataResult",dataResult);
									result.add(dataMap);
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
			}

		//以城市分类进行展示
		}else if (status.equals("2")){
			if(cityIds!=null){
				for (int i = 0; i < cityIds.length; i++) {
					cityId = cityIds[i];
					try {
						List<String> months = GetMonthUtil.getMonthBetween(start,end);
						for (int j = 0; j < months.size(); j++) {
							String month = months.get(j);
							try {
								String monthStart = GetMonthUtil.getFirstDay(month);
								String monthEnd = GetMonthUtil.getLastDay(month);
								List<Map<String, Object>> dataResult =monthBasicCheckDAO.findBasicCheckByConditions(runUnitId,cityId,monthStart,monthEnd,stations);
								if(dataResult.size()>0){
									Map<String, Object> dataMap = new HashMap<>();
									String Area = dataResult.get(0).get("Area").toString();
									String CityId = dataResult.get(0).get("CityId").toString();

									dataMap.put("Area",Area);
									dataMap.put("CityId",CityId);
									dataMap.put("checkTime",month.substring(0,7));
									dataMap.put("dataResult",dataResult);
									result.add(dataMap);
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
			}
			//以站点进行展示
		}else{
			List<Map<String, Object>> dataList = new ArrayList<>();
			Map<String, Object> data = new HashMap<>();
			List<Map<String, Object>> dataResult = monthBasicCheckDAO.findBasicCheckByConditions(runUnitId,citys,start,end,stations);
			data.put("dataResult",dataResult);
			data.put("checkTime",start);
			dataList.add(data);
			result = dataList;
		}
		return result;
	}

	public List<Map<String,Object>> findBasicCheckByDetailId(String detailId) {
		return monthBasicCheckDAO.findBasicCheckByDetailId(detailId);
	}

	/**
	 * 保存扣分表
	 * @param detailMap
	 */
	public void saveBasicCheckDetail(Map<String, Object> detailMap) {
		monthBasicCheckDAO.saveBasicCheckDetail(detailMap);
	}


	/**
	 * 删除扣分表
	 * @param detailId
	 */
    public void deleteBasicCheck(String detailId) {
		monthBasicCheckDAO.deleteBasicCheck(detailId);
    }

	/**
	 * 删除扣分详情表
	 * @param detailId
	 */
	public void deleteBasicCheckDetail(String detailId) {
		monthBasicCheckDAO.deleteBasicCheckDetail(detailId);
	}

	/**
	 * 更新扣分详情
	 * @param detailMap
	 */
	public void updateDeductDetail(Map<String, Object> detailMap) {
		monthBasicCheckDAO.updateBasicCheckDetail(detailMap);
	}

	/**
	 * 更新扣分表
	 * @param deductMap
	 */
	public void updateDeductScore(Map<String, Object> deductMap) {
		monthBasicCheckDAO.updateBasicCheck(deductMap);
	}

	/**
	 * 得到检查人
	 * @param detailId
	 * @return
	 */
	public Map<String, Object> findStation(String detailId) {
		return monthBasicCheckDAO.findStation(detailId);
	}

}
