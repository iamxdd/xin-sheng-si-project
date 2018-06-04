package com.rx.substation.service;

import com.rx.substation.dao.SandPollutionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by dcx on 2017/6/19 0019.
 */
@Service
public class SandPollutionService {
    @Autowired
    SandPollutionDAO sandPollutionDAO;
    public List<Map<String,Object>> getSandPollution(String start, String end, String citycode) {
        return sandPollutionDAO.getSandPollution(start,end,citycode);
    }

    public void saveSandPollution(String monthtime, String citycode,String cityname,String so2,String no2,String o3,
                                  String co,String pm2_5,String pm10,String pm2_5pm10,String status) {
        sandPollutionDAO.saveSandPollution(monthtime,citycode,cityname,so2,no2,o3,co,pm2_5,pm10,pm2_5pm10,status);
    }

    public Map<String,Object> getSandMByCity(String start, String end, String citycode, String status) {
        return sandPollutionDAO.getSandMByCity(start,end,citycode,status);
    }

    public int getSandPollutionDays(String start, String end, String citycode, String status) {
        return sandPollutionDAO.getBurningMAllCity(start,end,citycode,status);
    }

    public void updateSandPollution(String monthtime, String citycode, String cityname, String so2, String no2, String o3, String co, String pm2_5, String pm10, String pm2_5pm10, String status) {
        sandPollutionDAO.updateSandPollution(monthtime,citycode,cityname,so2,no2,o3,co,pm2_5,pm10,pm2_5pm10,status);
    }

    public int getDays(String start, String end,String citycode) {
        return sandPollutionDAO.getDays(start,end,citycode);
    }
}
