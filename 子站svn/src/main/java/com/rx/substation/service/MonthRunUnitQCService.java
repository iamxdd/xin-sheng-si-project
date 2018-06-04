package com.rx.substation.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rx.substation.util.GetMonthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rx.substation.dao.MonthRunUnitQCDAO;

@Service
public class MonthRunUnitQCService {

	@Autowired
	MonthRunUnitQCDAO monthRunUnitQCDAO;

	/**
	 * 条件查询运行单位月工作
	 * @param runUnitIds
	 * @param cityIds
	 * @param start
	 * @param end
	 * @param stationCodes
	 * @param status
	 * @return
	 */
	public List<Map<String,Object>> findRunUnitQcByConditions(String[] runUnitIds,String[] cityIds,String start,
		String end, String[] stationCodes,String status) {
//		String citys = null;
//		String stations = null;
//		if(cityIds!=null){
//			for (int i = 0; i < cityIds.length; i++) {
//				if(i>0){
//					citys = citys + "," + cityIds[i];
//				}else{
//					citys = cityIds[0];
//				}
//			}
//		}
//		if(stationCodes!=null){
//			for (int i = 0; i < stationCodes.length; i++) {
//				if(i>0){
//					stations = stations + ",'" + stationCodes[i]+"'";
//				}else{
//					stations = "'"+ stationCodes[0]+"'";
//				}
//			}
//		}
//		List<Map<String,Object>> unitList = monthRunUnitQCDAO.findRunUnitQcByConditions(citys,start,end,stations);
//
//		List<Map<String,Object>> stationComplateList = monthRunUnitQCDAO.findMonthRunUnitQC2(start,end);
//
//		for (int i = 0; i < unitList.size(); i++) {
//			String stationCode1 = null ;
//			Map<String, Object> map1 = unitList.get(i);
//			for(String key : map1.keySet()){
//				if("stationCode".equals(key)){
//					stationCode1 = map1.get(key).toString();
//				}
//			}
//			List<Map<String, Object>> stationList = new ArrayList<>();
//			for (int j = 0; j < stationComplateList.size(); j++) {
//				Map<String, Object> map2 = stationComplateList.get(j);
//				if( map2.get("stationCode").toString().equals(stationCode1)){
//					stationList.add(map2);
//				}
//			}
//			map1.put("stationComplateList", stationList);
//		}
//
//
//		return unitList;
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
								List<Map<String, Object>> dataResult =monthRunUnitQCDAO.findRunUnitQcByConditions(runUnitId,citys,monthStart,monthEnd,stations);
								if(dataResult.size()>0){
									Map<String, Object> dataMap = new HashMap<>();
									String runName = null;
									String runCode = null;
									String monthTime =null;
									Double totaldeduct = 0.0;
									Double totalWCycle = 0.0;
									Double totalMCycle = 0.0;
									Double totalQCycle = 0.0;
									Double totalHYCycle = 0.0;
									Double totalYCycle = 0.0;
									Double totalWdeduct = 0.0;
									Double totalMdeduct = 0.0;
									Double totalQdeduct = 0.0;
									Double totalHYdeduct = 0.0;
									Double totalYdeduct = 0.0;
									int count = 0;

									//总体扣分项
									List<Map<String, Object>> reason = new ArrayList<>();
									List<Map<String, Object>> reason2 = new ArrayList<>();
									List<Map<String, Object>> reason3 = new ArrayList<>();
									List<Map<String, Object>> reason4 = new ArrayList<>();
									List<Map<String, Object>> reason5 = new ArrayList<>();

									for (int k = 0; k < dataResult.size(); k++) {
										Map<String, Object> map = dataResult.get(k);
										runName = map.get("runName").toString();
										runCode = map.get("runCode").toString();
										monthTime = map.get("checkTime").toString();
										Double deduct = Double.valueOf(map.get("deduct").toString());
										totaldeduct = totaldeduct + deduct;

										String stationCode1 = null ;
										for(String key : map.keySet()){
											if("stationCode".equals(key)){
												stationCode1 = map.get(key).toString();
											}
										}
										List<Map<String,Object>> stationComplateList = monthRunUnitQCDAO.findMonthRunUnitQC2(stationCode1+monthTime);

										List<Map<String, Object>> stationList = new ArrayList<>();


										for (int m = 0; m < stationComplateList.size(); m++) {
											Map<String, Object> map2 = stationComplateList.get(m);
											Map<String, Object> reasonMap = new HashMap<>();
											Map<String, Object> reasonMap2 = new HashMap<>();
											Map<String, Object> reasonMap3 = new HashMap<>();
											Map<String, Object> reasonMap4 = new HashMap<>();
											Map<String, Object> reasonMap5 = new HashMap<>();
											if( map2.get("stationCode").toString().equals(stationCode1)){
												stationList.add(map2);
											}
											if(map2.get("stationCode").toString().equals(stationCode1)
													 && Integer.valueOf(map2.get("type").toString())==1){
												totalWCycle = totalWCycle + Double.valueOf(map2.get("inputCycle").toString());
												if(Double.valueOf(map2.get("performance").toString())>0){
													totalWdeduct = totalWdeduct + Double.valueOf(map2.get("performance").toString());
												}
												count++;
												if((int)map2.get("inputCycle")-(int)map2.get("theoryCycle") > 0){
													String a = map.get("runName").toString()+
															map.get("stationName").toString()+"从"+
															map2.get("lastTime").toString()+"至"+
															map2.get("thisTime").toString()+"周报填写周期超过"+
															((int)map2.get("inputCycle")-(int)map2.get("theoryCycle"))+
															"天";
													reasonMap.put("reason1",a);
													map2.put("reason",a);
													reason.add(reasonMap);
												}
											}else if(map2.get("stationCode").toString().equals(stationCode1)
													&& Integer.valueOf(map2.get("type").toString())==2){

												totalMCycle = totalMCycle + Double.valueOf(map2.get("inputCycle").toString());
												if(Double.valueOf(map2.get("performance").toString())>0){
													totalMdeduct = totalMdeduct + Double.valueOf(map2.get("performance").toString());
												}
												if((int)map2.get("inputCycle")-(int)map2.get("theoryCycle") > 0){
													String a =map.get("runName").toString()+
															map.get("stationName").toString()+"从"+
															map2.get("lastTime").toString()+"至"+
															map2.get("thisTime").toString()+"月报填写周期超过"+
															((int)map2.get("inputCycle")-(int)map2.get("theoryCycle"))+
															"天";
													reasonMap2.put("reason2",a);
													reason2.add(reasonMap2);
													map2.put("reason",a);
												}

											}else if(map2.get("stationCode").toString().equals(stationCode1)
													&& Integer.valueOf(map2.get("type").toString())==3){

												totalQCycle = totalQCycle + Double.valueOf(map2.get("inputCycle").toString());
												if(Double.valueOf(map2.get("performance").toString())>0){
													totalQdeduct = totalQdeduct + Double.valueOf(map2.get("performance").toString());
												}
												if((int)map2.get("inputCycle")-(int)map2.get("theoryCycle") > 0){
													String a =map.get("runName").toString()+
															map.get("stationName").toString()+"从"+
															map2.get("lastTime").toString()+"至"+
															map2.get("thisTime").toString()+"季报填写周期超过"+
															((int)map2.get("inputCycle")-(int)map2.get("theoryCycle"))+
															"天";
													reasonMap3.put("reason3",a);
													reason3.add(reasonMap3);
													map2.put("reason",a);
												}

											} else if(map2.get("stationCode").toString().equals(stationCode1)
													&& Integer.valueOf(map2.get("type").toString())==4){
												totalHYCycle = totalHYCycle + Double.valueOf(map2.get("inputCycle").toString());
												if(Double.valueOf(map2.get("performance").toString())>0){

													totalHYdeduct = totalHYdeduct + Double.valueOf(map2.get("performance").toString());
												}
												if((int)map2.get("inputCycle")-(int)map2.get("theoryCycle") > 0){
													String a = map.get("runName").toString()+
															map.get("stationName").toString()+"从"+
															map2.get("lastTime").toString()+"至"+
															map2.get("thisTime").toString()+"半年报填写周期超过"+
															((int)map2.get("inputCycle")-(int)map2.get("theoryCycle"))+
															"天";
													reasonMap4.put("reason4",a);
													reason4.add(reasonMap4);
													map2.put("reason",a);
												}
											}else if(map2.get("stationCode").toString().equals(stationCode1)
													&& Integer.valueOf(map2.get("type").toString())==5){
												totalYCycle = totalYCycle + Double.valueOf(map2.get("inputCycle").toString());
												if(Double.valueOf(map2.get("performance").toString())>0){
													totalYdeduct = totalYdeduct + Double.valueOf(map2.get("performance").toString());
												}
												if((int)map2.get("inputCycle")-(int)map2.get("theoryCycle") > 0){
													String a = map.get("runName").toString()+
															map.get("stationName").toString()+"从"+
															map2.get("lastTime").toString()+"至"+
															map2.get("thisTime").toString()+"年报填写周期超过"+
															((int)map2.get("inputCycle")-(int)map2.get("theoryCycle"))+
															"天";
													reasonMap5.put("reason5",a);
													reason5.add(reasonMap5);
													map2.put("reason",a);
												}
											}
										}
										map.put("stationComplateList", stationList);


									}
									dataMap.put("avgWCycle",totalWCycle/(dataResult.size()*(count == 0 ? 1:count)));
									dataMap.put("avgMCycle",totalMCycle/dataResult.size());
									dataMap.put("avgQCycle",totalQCycle/dataResult.size());
									dataMap.put("avgHYCycle",totalHYCycle/dataResult.size());
									dataMap.put("avgYCycle",totalYCycle/dataResult.size());
									dataMap.put("totalWdeduct",totalWdeduct);
									dataMap.put("totalMdeduct",totalMdeduct);
									dataMap.put("totalQdeduct",totalQdeduct);
									dataMap.put("totalHYdeduct",totalHYdeduct);
									dataMap.put("totalYdeduct",totalYdeduct);

									dataMap.put("reason1", reason);
									dataMap.put("reason2", reason2);
									dataMap.put("reason3", reason3);
									dataMap.put("reason4", reason4);
									dataMap.put("reason5", reason5);


									dataMap.put("runName",runName);
									dataMap.put("runCode",runCode);
									dataMap.put("monthTime",monthTime.substring(0,7));
									dataMap.put("avgdeduct",totaldeduct/dataResult.size());
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
								List<Map<String, Object>> dataResult =monthRunUnitQCDAO.findRunUnitQcByConditions(runUnitId,cityId,monthStart,monthEnd,stations);
								if(dataResult.size()>0){
									Map<String, Object> dataMap = new HashMap<>();
									String Area = null;
									String CityId = null;
									String monthTime =null;
									Double totaldeduct = 0.0;
									Double totalWCycle = 0.0;
									Double totalMCycle = 0.0;
									Double totalQCycle = 0.0;
									Double totalHYCycle = 0.0;
									Double totalYCycle = 0.0;
									Double totalWdeduct = 0.0;
									Double totalMdeduct = 0.0;
									Double totalQdeduct = 0.0;
									Double totalHYdeduct = 0.0;
									Double totalYdeduct = 0.0;
									int count = 0;


									//总体扣分项
									List<Map<String, Object>> reason = new ArrayList<>();
									List<Map<String, Object>> reason2 = new ArrayList<>();
									List<Map<String, Object>> reason3 = new ArrayList<>();
									List<Map<String, Object>> reason4 = new ArrayList<>();
									List<Map<String, Object>> reason5 = new ArrayList<>();

									for (int k = 0; k < dataResult.size(); k++) {
										Map<String, Object> map = dataResult.get(k);
										Map<String, Object> reasonMap = new HashMap<>();
										Map<String, Object> reasonMap2 = new HashMap<>();
										Map<String, Object> reasonMap3 = new HashMap<>();
										Map<String, Object> reasonMap4 = new HashMap<>();
										Map<String, Object> reasonMap5 = new HashMap<>();
										Area = map.get("Area").toString();
										CityId = map.get("cityId").toString();
										monthTime = map.get("checkTime").toString();
										Double deduct = Double.valueOf(map.get("deduct").toString());
										totaldeduct = totaldeduct + deduct;

										String stationCode1 = null ;
										for(String key : map.keySet()){
											if("stationCode".equals(key)){
												stationCode1 = map.get(key).toString();
											}
										}
										List<Map<String,Object>> stationComplateList = monthRunUnitQCDAO.findMonthRunUnitQC2(stationCode1+monthTime);

										List<Map<String, Object>> stationList = new ArrayList<>();

										for (int m = 0; m < stationComplateList.size(); m++) {
											Map<String, Object> map2 = stationComplateList.get(m);
											if( map2.get("stationCode").toString().equals(stationCode1)){
												stationList.add(map2);
											}
											if(map2.get("stationCode").toString().equals(stationCode1)
													&& Integer.valueOf(map2.get("type").toString())==1){
												totalWCycle = totalWCycle + Double.valueOf(map2.get("inputCycle").toString());
												if(Double.valueOf(map2.get("performance").toString())>0){
													totalWdeduct = totalWdeduct + Double.valueOf(map2.get("performance").toString());
												}
												count++;
												if((int)map2.get("inputCycle")-(int)map2.get("theoryCycle") > 0){
													String a  = map.get("Area").toString()+
															map.get("stationName").toString()+"从"+
															map2.get("lastTime").toString()+"至"+
															map2.get("thisTime").toString()+"周报填写周期超过"+
															((int)map2.get("inputCycle")-(int)map2.get("theoryCycle"))+
															"天";
													reasonMap.put("reason1",a);
													reason.add(reasonMap);
													map2.put("reason",a);

												}

											}else if(map2.get("stationCode").toString().equals(stationCode1)
													&& Integer.valueOf(map2.get("type").toString())==2){
												totalMCycle = totalMCycle + Double.valueOf(map2.get("inputCycle").toString());
												if(Double.valueOf(map2.get("performance").toString())>0){
													totalMdeduct = totalMdeduct + Double.valueOf(map2.get("performance").toString());
												}
												if((int)map2.get("inputCycle")-(int)map2.get("theoryCycle") > 0){
													String a = map.get("Area").toString()+
															map.get("stationName").toString()+"从"+
															map2.get("lastTime").toString()+"至"+
															map2.get("thisTime").toString()+"月报填写周期超过"+
															((int)map2.get("inputCycle")-(int)map2.get("theoryCycle"))+
															"天";
													reasonMap2.put("reason2",a);
													reason2.add(reasonMap2);
													map2.put("reason",a);
												}

											}else if(map2.get("stationCode").toString().equals(stationCode1)
													&& Integer.valueOf(map2.get("type").toString())==3){
												totalQCycle = totalQCycle + Double.valueOf(map2.get("inputCycle").toString());
												if(Double.valueOf(map2.get("performance").toString())>0){
													totalQdeduct = totalQdeduct + Double.valueOf(map2.get("performance").toString());
												}
												if((int)map2.get("inputCycle")-(int)map2.get("theoryCycle") > 0){
													String a = map.get("Area").toString()+
															map.get("stationName").toString()+"从"+
															map2.get("lastTime").toString()+"至"+
															map2.get("thisTime").toString()+"季报填写周期超过"+
															((int)map2.get("inputCycle")-(int)map2.get("theoryCycle"))+
															"天";
													reasonMap3.put("reason3",a);
													reason3.add(reasonMap3);
													map2.put("reason",a);
												}

											}else if(map2.get("stationCode").toString().equals(stationCode1)
													&& Integer.valueOf(map2.get("type").toString())==4){
												totalHYCycle = totalHYCycle + Double.valueOf(map2.get("inputCycle").toString());
												if(Double.valueOf(map2.get("performance").toString())>0){
													totalHYdeduct = totalHYdeduct + Double.valueOf(map2.get("performance").toString());
												}
												if((int)map2.get("inputCycle")-(int)map2.get("theoryCycle") > 0){
													String a = map.get("Area").toString()+
															map.get("stationName").toString()+"从"+
															map2.get("lastTime").toString()+"至"+
															map2.get("thisTime").toString()+"半年报填写周期超过"+
															((int)map2.get("inputCycle")-(int)map2.get("theoryCycle"))+
															"天";
													reasonMap4.put("reason4",a);
													reason4.add(reasonMap4);
													map2.put("reason",a);
												}

											}else if(map2.get("stationCode").toString().equals(stationCode1)
													&& Integer.valueOf(map2.get("type").toString())==5){
												totalYCycle = totalYCycle + Double.valueOf(map2.get("inputCycle").toString());
												if(Double.valueOf(map2.get("performance").toString())>0){
													totalYdeduct = totalYdeduct + Double.valueOf(map2.get("performance").toString());
												}
												if((int)map2.get("inputCycle")-(int)map2.get("theoryCycle") > 0){
													String a = map.get("Area").toString()+
															map.get("stationName").toString()+"从"+
															map2.get("lastTime").toString()+"至"+
															map2.get("thisTime").toString()+"年报填写周期超过"+
															((int)map2.get("inputCycle")-(int)map2.get("theoryCycle"))+
															"天";
													reasonMap5.put("reason5",a);
													reason5.add(reasonMap5);
													map2.put("reason",a);
												}
											}
										}
										map.put("stationComplateList", stationList);
									}

									dataMap.put("avgWCycle",totalWCycle/(dataResult.size()*(count == 0 ? 1:count)));
									dataMap.put("avgMCycle",totalMCycle/dataResult.size());
									dataMap.put("avgQCycle",totalQCycle/dataResult.size());
									dataMap.put("avgHYCycle",totalHYCycle/dataResult.size());
									dataMap.put("avgYCycle",totalYCycle/dataResult.size());
									dataMap.put("totalWdeduct",totalWdeduct);
									dataMap.put("totalMdeduct",totalMdeduct);
									dataMap.put("totalQdeduct",totalMdeduct);
									dataMap.put("totalHYdeduct",totalHYdeduct);
									dataMap.put("totalYdeduct",totalYdeduct);

									dataMap.put("reason1", reason);
									dataMap.put("reason2", reason2);
									dataMap.put("reason3", reason3);
									dataMap.put("reason4", reason4);
									dataMap.put("reason5", reason5);

									dataMap.put("Area",Area);
									dataMap.put("CityId",CityId);
									dataMap.put("monthTime",monthTime.substring(0,7));
									dataMap.put("avgdeduct",totaldeduct/dataResult.size());
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

			result = monthRunUnitQCDAO.findRunUnitQcByConditions(runUnitId,citys,start,end,stations);


			for (int i = 0; i < result.size(); i++) {
				String stationCode1 = null ;
				String checkTime = null;
				String monthStart = null;
				String monthEnd = null;
				Map<String, Object> map1 = result.get(i);
				stationCode1 = map1.get("stationCode").toString();
				checkTime = map1.get("checkTime").toString();
				try {
					monthStart = GetMonthUtil.getFirstDay(checkTime);
					monthEnd = GetMonthUtil.getLastDay(checkTime);
				} catch (Exception e) {
					e.printStackTrace();
				}
				List<Map<String,Object>> stationComplateList = monthRunUnitQCDAO.findMonthRunUnitQC2(stationCode1+checkTime);
				List<Map<String, Object>> stationList = new ArrayList<>();
				for (int j = 0; j < stationComplateList.size(); j++) {
					Map<String, Object> map2 = stationComplateList.get(j);
					if( map2.get("stationCode").toString().equals(stationCode1)){
						stationList.add(map2);
					}
				}
				map1.put("stationComplateList", stationList);
			}
		}
		return result;
	}

	/**
	 * 得到当月该站点的运行情况
	 * @param start
	 * @param end
	 * @return
	 */
	public List<Map<String,Object>> getMonthQC(String start, String end) {
	
		List<Map<String,Object>> unitList = monthRunUnitQCDAO.findMonthRunUnitQC1(start,end);


		for (int i = 0; i < unitList.size(); i++) {
			String stationCode = null ;
			Map<String, Object> map1 = unitList.get(i);
			for(String key : map1.keySet()){
				if("stationCode".equals(key)){
					stationCode = map1.get(key).toString();
				}
			}
			List<Map<String,Object>> stationComplateList = monthRunUnitQCDAO.findMonthRunUnitQC2(stationCode+start);
			List<Map<String, Object>> stationList = new ArrayList<>();
			for (int j = 0; j < stationComplateList.size(); j++) {
				Map<String, Object> map2 = stationComplateList.get(j);
				if( map2.get("stationCode").toString().equals(stationCode)){
					stationList.add(map2);
				}
			}
			map1.put("stationComplateList", stationList);
		}
		return unitList;
	}

	public void saveStationWork(Map<String, Object> map) {
		monthRunUnitQCDAO.saveStationWork(map);
	}


	public void saveRunUnitWork(Map<String, Object> runUnitWork) {
		monthRunUnitQCDAO.saveRunUnitWork(runUnitWork);
	}
}
