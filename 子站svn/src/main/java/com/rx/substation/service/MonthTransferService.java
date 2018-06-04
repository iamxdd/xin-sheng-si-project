package com.rx.substation.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rx.substation.util.GetMonthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rx.substation.dao.MonthTransferDAO;
import com.rx.substation.domain.Transfer;

/**
 * @author dcx
 *
 */
@Service
public class MonthTransferService {

	@Autowired
	MonthTransferDAO monthTransferDAO;
	
	public List<Map<String, Object>> getMonthTransferByBrand(String start,String end,int BrandId){

		return monthTransferDAO.getMonthTransferByBrand(start, end, BrandId);
	}

	public List<Map<String, Object>> getMonthTransferByCity(String start, String end,String cityCode) {

		return monthTransferDAO.getMonthTransferByCity(start,end,cityCode);
	}

	public List<Map<String, Object>> getMonthTransferByStation(String start, String end, String stationCode) {

		return monthTransferDAO.getMonthTransferByStation(start, end, stationCode);
	}
	/**
	 * 得到站點上個月傳輸率
	 * @param start
	 * @param end
	 * @return
	 */
	public Map<String, Object> getMonthTransfer(String start, String end,String stationCode) {
		return monthTransferDAO.getMonthTransfer(start, end,stationCode);
	}
	
	
	public void save(Transfer saveMonthTransfer) {
		monthTransferDAO.save(saveMonthTransfer);
		
	}

	public List<Map<String, Object>> getMonthTransferScore(String start, String end) {
		return monthTransferDAO.getMonthTransferScore(start,end);
	}

	/**
	 * 多条件查询传输率
	 * @param runUnitIds
	 * @param cityIds
	 * @param start
	 * @param end
	 * @param stationCodes
	 * @param status
	 * @return
	 */
	public List<Map<String, Object>> findTansferByConditions(String[] runUnitIds,String[] cityIds,String start,
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
								List<Map<String, Object>> dataResult =monthTransferDAO.findTansferByConditions(runUnitId,citys,monthStart,monthEnd,stations,param,sortstatus);
								if(dataResult.size()>0){
									Map<String, Object> dataMap = new HashMap<>();
									String runName = null;
									String runCode = null;
									String monthTime =null;
									Double totalso2 = 0.0;
									Double totalno2 = 0.0;
									Double totalco = 0.0;
									Double totalo3 = 0.0;
									Double totalpm10 = 0.0;
									Double totalpm2_5 = 0.0;
									Double totalavgTransfer = 0.0;
									Double totalscores = 0.0;
									for (int k = 0; k < dataResult.size(); k++) {
										Map<String, Object> map = dataResult.get(k);
										runName = map.get("runName").toString();
										runCode = map.get("runCode").toString();
										monthTime = map.get("monthTime").toString();
										Double so2 = Double.valueOf(map.get("so2").toString());
										Double no2 = Double.valueOf(map.get("no2").toString());
										Double co = Double.valueOf(map.get("co").toString());
										Double o3 = Double.valueOf(map.get("o3").toString());
										Double pm10 = Double.valueOf(map.get("pm10").toString());
										Double pm2_5 = Double.valueOf(map.get("pm2_5").toString());
										Double avgTransfer = Double.valueOf(map.get("avgTransfer").toString());
										Double scores = Double.valueOf(map.get("scores").toString());
										totalso2 = totalso2 + so2;
										totalno2 = totalno2 + no2;
										totalco = totalco + co;
										totalo3 = totalo3 + o3;
										totalpm10 = totalpm10 + pm10;
										totalpm2_5 = totalpm2_5 + pm2_5;
										totalavgTransfer = totalavgTransfer + avgTransfer;
										totalscores = totalscores + scores;
									}
									dataMap.put("runName",runName);
									dataMap.put("runCode",runCode);
									dataMap.put("monthTime",monthTime.substring(0,7));
									dataMap.put("Cavgso2",totalso2/dataResult.size());
									dataMap.put("Cavgno2",totalno2/dataResult.size());
									dataMap.put("Cavgco",totalco/dataResult.size());
									dataMap.put("Cavgo3",totalo3/dataResult.size());
									dataMap.put("Cavgpm10",totalpm10/dataResult.size());
									dataMap.put("Cavgpm2_5",totalpm2_5/dataResult.size());
									dataMap.put("CavgTransfer",totalavgTransfer/dataResult.size());
									dataMap.put("Cavgscores",totalscores/dataResult.size());
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
								List<Map<String, Object>> dataResult =monthTransferDAO.findTansferByConditions(runUnitId,cityId,monthStart,monthEnd,stations,param,sortstatus);
								if(dataResult.size()>0){
									Map<String, Object> dataMap = new HashMap<>();
									String Area = null;
									String CityId = null;
									String monthTime =null;
									Double totalso2 = 0.0;
									Double totalno2 = 0.0;
									Double totalco = 0.0;
									Double totalo3 = 0.0;
									Double totalpm10 = 0.0;
									Double totalpm2_5 = 0.0;
									Double totalavgTransfer = 0.0;
									Double totalscores = 0.0;
									for (int k = 0; k < dataResult.size(); k++) {
										Map<String, Object> map = dataResult.get(k);
										Area = map.get("Area").toString();
										CityId = map.get("CityId").toString();
										monthTime = map.get("monthTime").toString();
										Double so2 = Double.valueOf(map.get("so2").toString());
										Double no2 = Double.valueOf(map.get("no2").toString());
										Double co = Double.valueOf(map.get("co").toString());
										Double o3 = Double.valueOf(map.get("o3").toString());
										Double pm10 = Double.valueOf(map.get("pm10").toString());
										Double pm2_5 = Double.valueOf(map.get("pm2_5").toString());
										Double avgTransfer = Double.valueOf(map.get("avgTransfer").toString());
										Double scores = Double.valueOf(map.get("scores").toString());
										totalso2 = totalso2 + so2;
										totalno2 = totalno2 + no2;
										totalco = totalco + co;
										totalo3 = totalo3 + o3;
										totalpm10 = totalpm10 + pm10;
										totalpm2_5 = totalpm2_5 + pm2_5;
										totalavgTransfer = totalavgTransfer + avgTransfer;
										totalscores = totalscores + scores;
									}
									dataMap.put("Area",Area);
									dataMap.put("CityId",CityId);
									dataMap.put("monthTime",monthTime.substring(0,7));
									dataMap.put("Cavgso2",totalso2/dataResult.size());
									dataMap.put("Cavgno2",totalno2/dataResult.size());
									dataMap.put("Cavgco",totalco/dataResult.size());
									dataMap.put("Cavgo3",totalo3/dataResult.size());
									dataMap.put("Cavgpm10",totalpm10/dataResult.size());
									dataMap.put("Cavgpm2_5",totalpm2_5/dataResult.size());
									dataMap.put("CavgTransfer",totalavgTransfer/dataResult.size());
									dataMap.put("Cavgscores",totalscores/dataResult.size());
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
			result = monthTransferDAO.findTansferByConditions(runUnitId,citys,start,end,stations,param,sortstatus);
		}
		return result;
	}
	
}
