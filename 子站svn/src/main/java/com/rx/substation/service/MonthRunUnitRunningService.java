package com.rx.substation.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rx.substation.util.GetMonthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rx.substation.dao.CalTotalScoreDAO;
import com.rx.substation.dao.MonthRunUnitRunningDAO;
import com.rx.substation.dao.MonthStationRunningDAO;

@Service
public class MonthRunUnitRunningService {
	
	@Autowired
	MonthRunUnitRunningDAO monthRunUnitRunningDAO;
	
	@Autowired
	MonthStationRunningDAO monthStationRunningDAO;


	/**
	 * 条件查询运行单位运行质量
	 * @param runUnitIds
	 * @param cityIds
	 * @param start
	 * @param end
	 * @param stationCodes
	 * @return
	 */
	public List<Map<String, Object>> findRunUnitQcByConditions(String[] runUnitIds,String[] cityIds,String start,
		String end, String[] stationCodes) {

		String runUnitId = null;
		String cityId = null;
		String citys = null;
		String stations = null;
		List<Map<String, Object>> result = new ArrayList<>();
		if (cityIds != null) {
			for (int i = 0; i < cityIds.length; i++) {
				if (i > 0) {
					citys = citys + "," + cityIds[i];
				} else {
					citys = cityIds[0];
				}
			}
		}
		if (stationCodes != null) {
			for (int i = 0; i < stationCodes.length; i++) {
				if (i > 0) {
					stations = stations + ",'" + stationCodes[i] + "'";
				} else {
					stations = "'" + stationCodes[0] + "'";
				}
			}
		}

		//以运行单位来进行展示
		if (runUnitIds != null) {
			for (int i = 0; i < runUnitIds.length; i++) {
				runUnitId = runUnitIds[i];
				try {
					List<String> months = GetMonthUtil.getMonthBetween(start, end);
					for (int j = 0; j < months.size(); j++) {
						String month = months.get(j);
						try {
							String monthStart = GetMonthUtil.getFirstDay(month);
							String monthEnd = GetMonthUtil.getLastDay(month);

							Map<String, Object> dataMap = new HashMap<>();
							Map<String, Object> findRunitQuality = monthRunUnitRunningDAO.findRunUnitQualityByConditions(monthStart, monthEnd,runUnitId);
							List<Map<String, Object>> dataResult = monthStationRunningDAO.findStationRunningByConditions(runUnitId, citys, monthStart, monthEnd, stations);

							String runUnitId1 = null;

							if (findRunitQuality != null) {
								for (String key : findRunitQuality.keySet()) {
									runUnitId1 = (String) findRunitQuality.get("runUnitId");
									int invalidTime = 0;
									for (int l = 0; l < dataResult.size(); l++) {
										Map<String, Object> map2 = dataResult.get(l);
										if (map2.get("runUnitId").equals(runUnitId1)) {
											String coefficient = map2.get("coefficient").toString();
											if (coefficient.equals("0.00")) {
												invalidTime++;
												map2.put("invalidTime", invalidTime);
											} else {
												map2.put("invalidTime", 0);
											}
										}
									}
								}
							}
							dataMap.put("StationRunning", findRunitQuality);
							String runName = null;
							String runCode = null;
							String monthTime = null;
							Map<String,Object> qualityMap = new HashMap<>();
							for (int a = 0; a < dataResult.size(); a++) {
								Map<String, Object> map = dataResult.get(a);
								runName = map.get("runName").toString();
								runCode = map.get("runCode").toString();
								monthTime = map.get("checkMonth").toString();
								qualityMap.put("runQuality",findRunitQuality.get("runQuality"));
							}
							qualityMap.put("dataResult",dataResult);

							dataMap.put("runName", runName);
							dataMap.put("runCode", runCode);
							if(monthTime == null){
								dataMap.put("monthTime", "");
							}else{
								dataMap.put("monthTime", monthTime.substring(0, 7));
							}
							dataMap.put("dataResultMap", qualityMap);
							result.add(dataMap);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}


	public List<Map<String,Object>>  findStationRunningDAO(String start, String end, String stationCode) {
		return monthStationRunningDAO.findStationRunningDAO(start,end,stationCode);
	}


	public void saveMonthStation(Map<String, Object> saveMonthStationCheck) {
		monthStationRunningDAO.saveMonthStation(saveMonthStationCheck);
	}


	public String getOperationCompany(String stationCode) {
		return  monthStationRunningDAO.getOperationCompany(stationCode);
	}


	public Double getCoefficientByStationCode(String start, String end, String stationCode) {
		return  monthStationRunningDAO.getCoefficientByStationCode(start,end,stationCode);
	}


	public List<String> getOperationCompanyStations(String stationCode) {
		return monthStationRunningDAO.getOperationCompanyStations(stationCode);
	}

	public String getCityId(String stationCode) {
		return monthStationRunningDAO.getCityId(stationCode);
	}

	public void saveMonthRunUnit(Map<String, Object> saveMonthRunUnitCheck) {
		monthRunUnitRunningDAO.saveMonthRunUnit(saveMonthRunUnitCheck);
	}

	public Map<String,Object> findRunitQualityById(String start, String end, String runUnitId) {
		return monthRunUnitRunningDAO.findRunitQualityById(start,end,runUnitId);
	}
}
