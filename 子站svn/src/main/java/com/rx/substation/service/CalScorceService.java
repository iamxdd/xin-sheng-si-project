package com.rx.substation.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rx.substation.domain.CalTotalScore;
import com.rx.substation.util.GetMonthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.rx.substation.dao.CalTotalScoreDAO;

@Service
public class CalScorceService {
	
	@Autowired
	CalTotalScoreDAO calTotalScoreDAO;

	public List<Map<String, Object>> getCalScorceByCity(String start, String end, int cityId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 根据站点时间段查询总分表
	 * @param start
	 * @param end
	 * @param stationCode
	 * @return
	 */
	public Map<String, Object> getCalScorceByStation(String start, String end, String stationCode) {
		return calTotalScoreDAO.getMonthScore(start,end,stationCode);
	}

	public List<Map<String, Object>> getCalScorceByBrand(String start, String end, int brandId) {
		return null;
	}
	
	public List<Map<String, Object>> getCalTotalScore(String start, String end) {
		return calTotalScoreDAO.getCalTotalScore(start,end);
	}

	public List<Map<String, Object>> findcalScorceByConditions(String[] runUnitIds,String[] cityIds,String start,
		String end, String[] stationCodes,String status,String param,String sortstatus) {
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
								List<Map<String, Object>> dataResult =calTotalScoreDAO.findcalScorceByConditions(runUnitId,citys,monthStart,monthEnd,stations,param,sortstatus);
								if(dataResult.size()>0){
									Map<String, Object> dataMap = new HashMap<>();
									String runName = null;
									String runCode = null;
									Double totalTScore = 0.0;
									Double totalVScore = 0.0;
									Double totalCScore = 0.0;
									Double totalQDScore = 0.0;
									Double totalAllScore = 0.0;
									Double totalAllCost = 0.0;
									for (int k = 0; k < dataResult.size(); k++) {
										Map<String, Object> map = dataResult.get(k);
										runName = map.get("runName").toString();
										runCode = map.get("runCode").toString();
										Double TansferScore = Double.valueOf(map.get("TansferScore").toString());
										Double ValidateDaysScore = Double.valueOf(map.get("ValidateDaysScore").toString());
										Double CompleteScore = Double.valueOf(map.get("CompleteScore").toString());
										Double QcDeductScore = Double.valueOf(map.get("QcDeductScore").toString());
										Double TotalScore = Double.valueOf(map.get("TotalScore").toString());
										Double TotalCost = Double.valueOf(map.get("TotalCost").toString());
										totalTScore = totalTScore + TansferScore;
										totalVScore = totalVScore + ValidateDaysScore;
										totalCScore = totalCScore + CompleteScore;
										totalQDScore = totalQDScore + QcDeductScore;
										totalAllScore = totalAllScore + TotalScore;
										totalAllCost = totalAllCost + TotalCost;
									}
									dataMap.put("runName",runName);
									dataMap.put("runCode",runCode);
									dataMap.put("monthTime",month.substring(0,7));
									dataMap.put("avgtansferScore",totalTScore/dataResult.size());
									dataMap.put("avgvalidateDaysScore",totalVScore/dataResult.size());
									dataMap.put("avgcompleteScore",totalCScore/dataResult.size());
									dataMap.put("avgqcDeductScore",totalQDScore/dataResult.size());
									dataMap.put("avgtotalScore",totalAllScore/dataResult.size());
									dataMap.put("avgtotalCost",totalAllCost/dataResult.size());
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
								List<Map<String, Object>> dataResult =calTotalScoreDAO.findcalScorceByConditions(runUnitId,cityId,monthStart,monthEnd,stations,param,sortstatus);
								if(dataResult.size()>0){
									Map<String, Object> dataMap = new HashMap<>();
									String Area = null;
									String CityId = null;
									Double totalTScore = 0.0;
									Double totalVScore = 0.0;
									Double totalCScore = 0.0;
									Double totalQDScore = 0.0;
									Double totalAllScore = 0.0;
									Double totalAllCost = 0.0;
									for (int k = 0; k < dataResult.size(); k++) {
										Map<String, Object> map = dataResult.get(k);
										Area = map.get("Area").toString();
										CityId = map.get("CityId").toString();
										Double TansferScore = Double.valueOf(map.get("TansferScore").toString());
										Double ValidateDaysScore = Double.valueOf(map.get("ValidateDaysScore").toString());
										Double CompleteScore = Double.valueOf(map.get("CompleteScore").toString());
										Double QcDeductScore = Double.valueOf(map.get("QcDeductScore").toString());
										Double TotalScore = Double.valueOf(map.get("TotalScore").toString());
										Double TotalCost = Double.valueOf(map.get("TotalCost").toString());
										totalTScore = totalTScore + TansferScore;
										totalVScore = totalVScore + ValidateDaysScore;
										totalCScore = totalCScore + CompleteScore;
										totalQDScore = totalQDScore + QcDeductScore;
										totalAllScore = totalAllScore + TotalScore;
										totalAllCost = totalAllCost + TotalCost;
									}
									dataMap.put("Area",Area);
									dataMap.put("CityId",CityId);
									dataMap.put("monthTime",month.substring(0,7));
									dataMap.put("avgtansferScore",totalTScore/dataResult.size());
									dataMap.put("avgvalidateDaysScore",totalVScore/dataResult.size());
									dataMap.put("avgcompleteScore",totalCScore/dataResult.size());
									dataMap.put("avgqcDeductScore",totalQDScore/dataResult.size());
									dataMap.put("avgtotalScore",totalAllScore/dataResult.size());
									dataMap.put("avgtotalCost",totalAllCost/dataResult.size());
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
			result = calTotalScoreDAO.findcalScorceByConditions(runUnitId,citys,start,end,stations,param,sortstatus);
		}
		return result;
	}

	public void saveCalTotalScore(CalTotalScore calTotalScore) {
		calTotalScoreDAO.saveCalTotalScore(calTotalScore);
	}


	public void updateCost(String start, String end, Double stationCost, String stationCode) {
		calTotalScoreDAO.updateCost(start,end,stationCost,stationCode);
	}
}
