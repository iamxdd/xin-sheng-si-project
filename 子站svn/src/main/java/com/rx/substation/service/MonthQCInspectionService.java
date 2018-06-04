package com.rx.substation.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rx.substation.util.GetMonthUtil;
import com.rx.substation.util.MathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rx.substation.dao.MonthQCInspectionDAO;
import com.rx.substation.domain.Inspection;
@Service
public class MonthQCInspectionService {

	@Autowired
	MonthQCInspectionDAO monthQCInspectionDAO;

	public List<Map<String, Object>> getMonthQCInspection(String start, String end) {
		return monthQCInspectionDAO.getMonthQCInspection(start, end);
	}

	public Double getDeductByStation(String stationCode) {

		return monthQCInspectionDAO.getDeductByStation(stationCode);
	}

	public void saveDeductScore(Map<String, Object> deductMap) {

		monthQCInspectionDAO.saveDeductScore(deductMap);
	}

	/**
	 * 通过条件得到扣分表
	 *
	 * @param runUnitIds
	 * @param cityIds
	 * @param start
	 * @param end
	 * @param stationCodes
	 * @param status
	 * @return
	 */
	public List<Map<String, Object>> findDeductByConditions(String[] runUnitIds, String[] cityIds, String start,
															String end, String[] stationCodes, String status) {
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
		if (status.equals("1")) {
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
								List<Map<String, Object>> dataResult = monthQCInspectionDAO.findDeductByConditions(runUnitId, citys, monthStart, monthEnd, stations);
								if (dataResult.size() > 0) {
									Map<String, Object> dataMap = new HashMap<>();
									String runName = null;
									String runCode = null;
									//总体扣分项
									List<Map<String, Object>> reason = new ArrayList<>();
									Double totaldeduct = 0.0;
									for (int k = 0; k < dataResult.size(); k++) {
										Map<String, Object> reasonMap = new HashMap<>();
										Map<String, Object> map = dataResult.get(k);
										runName = map.get("runName").toString();
										runCode = map.get("runCode").toString();
										String a = map.get("checkTime").toString().substring(0, 10) + "检查" + runName + "的" + map.get("stationName").toString() +
												"扣" + map.get("deduct").toString() + "分";
										reasonMap.put("reason", a);
										reason.add(reasonMap);
										Double deduct = Double.valueOf(map.get("deduct").toString());
										totaldeduct = totaldeduct + deduct;
									}
									dataMap.put("runName", runName);
									dataMap.put("runCode", runCode);
									dataMap.put("reason", reason);
									dataMap.put("checkTime", month.substring(0, 7));
									dataMap.put("totaldeduct", totaldeduct);
									dataMap.put("dataResult", dataResult);
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
		} else if (status.equals("2")) {
			if (cityIds != null) {
				for (int i = 0; i < cityIds.length; i++) {
					cityId = cityIds[i];
					try {
						List<String> months = GetMonthUtil.getMonthBetween(start, end);
						for (int j = 0; j < months.size(); j++) {
							String month = months.get(j);
							try {
								String monthStart = GetMonthUtil.getFirstDay(month);
								String monthEnd = GetMonthUtil.getLastDay(month);
								List<Map<String, Object>> dataResult = monthQCInspectionDAO.findDeductByConditions(runUnitId, cityId, monthStart, monthEnd, stations);
								if (dataResult.size() > 0) {
									Map<String, Object> dataMap = new HashMap<>();
									String Area = null;
									String CityId = null;
									//总体扣分项
									List<Map<String, Object>> reason = new ArrayList<>();
									Double totaldeduct = 0.0;
									for (int k = 0; k < dataResult.size(); k++) {
										Map<String, Object> reasonMap = new HashMap<>();
										Map<String, Object> map = dataResult.get(k);
										Area = map.get("Area").toString();
										CityId = map.get("CityId").toString();
										Double deduct = Double.valueOf(map.get("deduct").toString());
										String a = map.get("checkTime").toString().substring(0, 10) + "检查" + Area + "的" + map.get("stationName").toString() +
												"扣" + map.get("deduct").toString() + "分";
										reasonMap.put("reason", a);
										reason.add(reasonMap);
										totaldeduct = totaldeduct + deduct;
									}
									dataMap.put("Area", Area);
									dataMap.put("CityId", CityId);
									dataMap.put("reason", reason);
									dataMap.put("checkTime", month.substring(0, 7));
									dataMap.put("totaldeduct", totaldeduct);
									dataMap.put("dataResult", dataResult);
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
		} else {
			result = monthQCInspectionDAO.findDeductByConditions(runUnitId, citys, start, end, stations);
		}
		return result;
	}

	public List<Map<String, Object>> findDeductByDetailId(String detailId) {
		return monthQCInspectionDAO.findDeductByDetailId(detailId);
	}

	/**
	 * 保存扣分表
	 *
	 * @param detailMap
	 */
	public void saveDeductDetail(Map<String, Object> detailMap) {
		monthQCInspectionDAO.saveDeductDetail(detailMap);
	}

	/**
	 * 得到总扣分
	 *
	 * @param detailId
	 * @return
	 */
	public Double findTotalDeduct(String detailId) {
		return monthQCInspectionDAO.findTotalDeduct(detailId);
	}

	/**
	 * 删除扣分表
	 *
	 * @param detailId
	 */
	public void deleteDeduct(String detailId) {
		monthQCInspectionDAO.deleteDeduct(detailId);
	}

	/**
	 * 删除扣分详情表
	 *
	 * @param detailId
	 */
	public void deleteDetailId(String detailId) {
		monthQCInspectionDAO.deleteDetailId(detailId);
	}

	/**
	 * 更新扣分详情
	 *
	 * @param detailMap
	 */
	public void updateDeductDetail(Map<String, Object> detailMap) {
		monthQCInspectionDAO.updateDeductDetail(detailMap);
	}

	/**
	 * 更新扣分表
	 *
	 * @param deductMap
	 */
	public void updateDeductScore(Map<String, Object> deductMap) {
		monthQCInspectionDAO.updateDeductScore(deductMap);
	}

	/**
	 * 得到检查人
	 *
	 * @param detailId
	 * @return
	 */
	public String findCheckPerson(String detailId) {
		return monthQCInspectionDAO.findCheckPerson(detailId);
	}

	/**
	 * 得到检查时间（用作回显）
	 *
	 * @param detailId
	 * @return
	 */
	public String findCheckTime(String detailId) {
		return monthQCInspectionDAO.findCheckTime(detailId);
	}


	/***
	 * 统计飞行扣分表
	 * @param runUnitIds 运行单位
	 * @param cityIds	城市
	 * @param start    开始时间
	 * @param end      结束时间
	 * @param stationCodes   站点编号
	 * @param status   查询类型   1：运行单位  2：城市  3：站点
	 * @param type		统计类型		1：月统计  2：季度  3：半年  4：年
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> countInspection(String[] runUnitIds, String[] cityIds, String start, String end, String[] stationCodes, String status, String type) throws Exception {
		String runUnitId = null;
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
		if (stationCodes != null) {
			for (int i = 0; i < stationCodes.length; i++) {
				if (i > 0) {
					stations = stations + ",'" + stationCodes[i] + "'";
				} else {
					stations = "'" + stationCodes[0] + "'";
				}
			}
		}
		//月统计以运行单位来进行展示

		if (type .equals("1") && status.equals("1") && runUnitIds != null) {
			for (int i = 0; i < runUnitIds.length; i++) {
				runUnitId = runUnitIds[i];
				double checkRate = new Double(0.000);
				List<String> months = GetMonthUtil.getMonthBetween(start, end);
				for (int j = 0; j < months.size(); j++) {
					Map<String,Object> resultmap = new HashMap<>();
					String month = months.get(j);
					String monthStart = GetMonthUtil.getFirstDay(month);
					String monthEnd = GetMonthUtil.getLastDay(month);
					List<Map<String, Object>> dataResult =monthQCInspectionDAO.countInspection(runUnitId,citys,monthStart,monthEnd,stations);

					int total = monthQCInspectionDAO.getTotalByRunUnit(runUnitId);
					if(dataResult != null && dataResult.size()!= 0 && total!=0){
						int counts = dataResult.size();
						checkRate = Double.valueOf(counts)/Double.valueOf(total);
					}
					Boolean mark = checkRate >= Double.valueOf((1.0/3.0)) ? true : false;
					resultmap.put("dataResult",dataResult);
					resultmap.put("checkRate", MathUtil.round(checkRate,3));
					resultmap.put("mark",mark);
					result.add(resultmap);
				}
			}
		}else if(type .equals("1") && status.equals("2") && cityIds != null){
			for (int i = 0; i < cityIds.length; i++) {
				citys = cityIds[i];
				double checkRate = 0.0;
				List<String> months = GetMonthUtil.getMonthBetween(start, end);
				for (int j = 0; j < months.size(); j++) {
					Map<String,Object> resultmap = new HashMap<>();
					String month = months.get(j);
					String monthStart = GetMonthUtil.getFirstDay(month);
					String monthEnd = GetMonthUtil.getLastDay(month);
					List<Map<String, Object>> dataResult =monthQCInspectionDAO.countInspection(runUnitId,citys,monthStart,monthEnd,stations);
					int total = monthQCInspectionDAO.getTotalByCity(citys);
					if(dataResult != null && total!=0){
						int counts = dataResult.size();
						checkRate = Double.valueOf(counts)/Double.valueOf(total);
					}
					Boolean mark = checkRate >= Double.valueOf(1.0/3.0) ? true : false;
					resultmap.put("dataResult",dataResult);
					resultmap.put("checkRate",MathUtil.round(checkRate,3));
					resultmap.put("mark",mark);
					result.add(resultmap);
				}
			}
		}else{
			List<String> months = GetMonthUtil.getMonthBetween(start, end);
			for (int j = 0; j < months.size(); j++) {
				Map<String,Object> resultmap = new HashMap<>();
				String month = months.get(j);
				String monthStart = GetMonthUtil.getFirstDay(month);
				String monthEnd = GetMonthUtil.getLastDay(month);
				List<Map<String, Object>> dataResult = monthQCInspectionDAO.countInspection(runUnitId, citys, monthStart, monthEnd, stations);
				resultmap.put("dataResult",dataResult);
				result.add(resultmap);
			}
		}
		return result;
	}

	public Map<String,Object> findDetailByDetailId(String detailId) {
		return monthQCInspectionDAO.findDetailByDetailId(detailId);
	}
}
