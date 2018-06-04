package com.rx.substation.service;

import com.rx.substation.dao.RunCostDAO;
import com.rx.substation.util.GetMonthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dcx on 17-5-17.
 */
@Service
public class RunCostService {

    @Autowired
    RunCostDAO runCostDAO;

    /**
     * 多条件查询运行费用
     * @param runUnitIds
     * @param cityIds
     * @param start
     * @param end
     * @param stationCodes
     * @param status
     * @return
     */
    public List<Map<String, Object>> findrunCostByConditions(String[] runUnitIds, String[] cityIds, String start,
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

                                Map<String,Object> findRunitQuality = null;

                                List<Map<String,Object>> runList = runCostDAO.findRunitQualityByConditions(monthStart, monthEnd);
                                for (int k = 0; k < runList.size(); k++) {
                                    findRunitQuality = runList.get(k);

                                }
                                String runUnitId1 = null;
                                Double runUnitQuality = 0.00;
                                if(findRunitQuality!=null){
                                    for(String key : findRunitQuality.keySet()){
                                        if("runUnitId".equals(key)){
                                            runUnitId1 = (String) findRunitQuality.get(key);
                                            runUnitQuality = Double.valueOf(findRunitQuality.get("runQuality").toString());
                                        }
                                    }
                                }

                                List<Map<String, Object>> dataResult =runCostDAO.findStationRunningByConditions(runUnitId,citys,monthStart,monthEnd,stations);
                                Map<String, Object> dataMap = new HashMap<>();
                                int invalidTime = 0;
                                if(dataResult.size()>0){
                                    for (int m = 0; m < dataResult.size(); m++) {
                                        Map<String, Object> map2 = dataResult.get(m);
                                        String coefficient = map2.get("coefficient").toString();
                                        String cost = runCostDAO.findcost(map2.get("checkMonth").toString(),map2.get("stationCode").toString());
                                        if(coefficient.equals("0.00")){
                                            invalidTime++;
                                            map2.put("invalidTime",invalidTime);
                                            map2.put("runUnitQuality",runUnitQuality);
                                            map2.put("calrunUnitQuality",runUnitQuality*Double.valueOf(coefficient));
                                            map2.put("stationCost",Double.valueOf(cost));
                                        }else {
                                            map2.put("invalidTime",0);
                                            map2.put("runUnitQuality",runUnitQuality);
                                            map2.put("calrunUnitQuality",runUnitQuality*Double.valueOf(map2.get("coefficient").toString()));
                                            map2.put("stationCost",Double.valueOf(cost));
                                        }
                                    }

                                    String runName = null;
                                    String runCode = null;
                                    String monthTime =null;
                                    Double totalRunUnitQuality = 0.0;
                                    Double totalCost = 0.0;
                                    for (int k = 0; k < dataResult.size(); k++) {
                                        Map<String, Object> map = dataResult.get(k);
                                        runName = map.get("runName").toString();
                                        runCode = map.get("runCode").toString();
                                        monthTime = map.get("checkMonth").toString();
                                        totalRunUnitQuality = totalRunUnitQuality + Double.valueOf(map.get("calrunUnitQuality").toString());
                                        totalCost = totalCost + Double.valueOf(map.get("stationCost").toString());
                                    }

                                    dataMap.put("runName",runName);
                                    dataMap.put("runCode",runCode);
                                    dataMap.put("monthTime",monthTime.substring(0,7));
                                    dataMap.put("dataResult",dataResult);
                                    dataMap.put("avgRunUnitQuality",totalRunUnitQuality/dataResult.size());
                                    dataMap.put("totalCost",totalCost);
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
                                List<Map<String,Object>> runList = runCostDAO.findRunitQualityByConditions(monthStart, monthEnd);
                                Map<String,Object> findRunitQuality = null;
                                for (int k = 0; k < runList.size(); k++) {
                                    findRunitQuality = runList.get(k);

                                }
                                String runUnitId1 = null;
                                Double runUnitQuality = 0.00;
                                if(findRunitQuality!=null){
                                    for(String key : findRunitQuality.keySet()){
                                        if("runUnitId".equals(key)){
                                            runUnitId1 = (String) findRunitQuality.get(key);
                                            runUnitQuality = Double.valueOf(findRunitQuality.get("runQuality").toString());
                                        }
                                    }
                                }

                                List<Map<String, Object>> dataResult =runCostDAO.findStationRunningByConditions(runUnitId,cityId,monthStart,monthEnd,stations);
                                Map<String, Object> dataMap = new HashMap<>();
                                int invalidTime = 0;
                                if(dataResult.size()>0){
                                    for (int m = 0; m < dataResult.size(); m++) {
                                        Map<String, Object> map2 = dataResult.get(m);
                                        String coefficient = map2.get("coefficient").toString();
                                        String cost = runCostDAO.findcost(map2.get("checkMonth").toString(),map2.get("stationCode").toString());
                                        if(coefficient.equals("0.00")){
                                            invalidTime++;
                                            map2.put("runUnitQuality",runUnitQuality);
                                            map2.put("calrunUnitQuality",runUnitQuality*Double.valueOf(coefficient));
                                            map2.put("stationCost",Double.valueOf(cost));
                                        }else {
                                            map2.put("runUnitQuality",runUnitQuality);
                                            map2.put("calrunUnitQuality",runUnitQuality*Double.valueOf(coefficient));
                                            map2.put("stationCost",Double.valueOf(cost));
                                        }
                                    }

                                    String Area = null;
                                    String CityId = null;
                                    String monthTime =null;
                                    Double totalRunUnitQuality = 0.0;
                                    Double totalCost = 0.0;
                                    for (int k = 0; k < dataResult.size(); k++) {
                                        Map<String, Object> map = dataResult.get(k);
                                        Area = map.get("Area").toString();
                                        CityId = map.get("CityId").toString();
                                        monthTime = map.get("checkMonth").toString();
                                        totalRunUnitQuality = totalRunUnitQuality + Double.valueOf(map.get("calrunUnitQuality").toString());
                                        totalCost = totalCost + Double.valueOf(map.get("stationCost").toString());

                                    }
                                    dataMap.put("Area",Area);
                                    dataMap.put("CityId",CityId);
                                    dataMap.put("monthTime",monthTime.substring(0,7));
                                    dataMap.put("dataResult",dataResult);
                                    dataMap.put("avgRunUnitQuality",totalRunUnitQuality/dataResult.size());
                                    dataMap.put("totalCost",totalCost);
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
            List<Map<String,Object>> runList = runCostDAO.findRunitQualityByConditions(start, end);
            List<Map<String,Object>> mapList = runCostDAO.findStationRunningByConditions(runUnitId,citys,start,end,stations);
            Map<String,Object> findRunitQuality = null;
            for (int k = 0; k < runList.size(); k++) {
                findRunitQuality = runList.get(k);

            }
            Double runUnitQuality = 0.00;
            if(findRunitQuality!=null){
                for(String key : findRunitQuality.keySet()){
                    if("runUnitId".equals(key)){
                        runUnitQuality = Double.valueOf(findRunitQuality.get("runQuality").toString());
                    }
                }
            }

            for (int i = 0; i < mapList.size(); i++) {
                Map<String,Object> map = mapList.get(i);
                String coefficient = map.get("coefficient").toString();
                String cost = runCostDAO.findcost(map.get("checkMonth").toString(),map.get("stationCode").toString());
                map.put("runUnitQuality",runUnitQuality);
                map.put("calrunUnitQuality",runUnitQuality*Double.valueOf(map.get("coefficient").toString()));
                map.put("stationCost",Double.valueOf(cost));
                result.add(map);
            }
        }
        return result;
    }

    public List<Map<String,Object>> getAllStationMsg(String stationCode, String timePoint) {
        List<Map<String,Object>> maps = runCostDAO.getStationCostMsg(stationCode,timePoint);
        if(maps.size() > 0){
            return maps;
        }else {
            return runCostDAO.getAllStationMsg();
        }
    }

    public List<Map<String,Object>> getStationCostMsg(String timePoint, String stationCode) {
        List<Map<String,Object>> result = new ArrayList<>();
        result = runCostDAO.getStationCostMsg(stationCode,timePoint);
        if(result.size() <= 0 ){
            return null;
        }else {
            return result;
        }
    }

    public void saveRunCost(Map<String, Object> map) {
        runCostDAO.saveRunCost(map);
    }

    public void updateRunCost(Map<String, Object> map) {
        runCostDAO.updateRunCost(map);
    }
}
