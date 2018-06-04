package com.rx.substation.service;

import com.rx.substation.dao.BurnPollutionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by dcx on 2017/6/19 0019.
 */
@Service
public class BurnPollutionService {

    @Autowired
    BurnPollutionDAO burnPollutionDAO;
    public List<Map<String,Object>> getBurnPollution(String start, String end, String citycode) {
        return burnPollutionDAO.getBurnPollution(start,end,citycode);
    }

    public void saveBurnPollutionService(String monthtime,String citycode,String cityname,String so2,String no2,
                                         String o3,String co,String pm2_5,String pm10, String status) {
        burnPollutionDAO.saveBurnPollution(monthtime,citycode,cityname,so2,no2,o3,co,pm2_5,pm10,status);
    }

    public Map<String,Object> getBurningM(String start, String end, String status) {
        return burnPollutionDAO.getBurningM(start,end,status);
    }

    public int getBurnPollutionDays(String start, String end, String citycode, String status) {
        return burnPollutionDAO.getBurningDays(start,end,citycode,status);
    }

    public Map<String,Object> getBurningMByCity(String start, String end, String citycode, String status) {
        return burnPollutionDAO.getBurningMByCity(start,end,citycode,status);
    }

    public List<Map<String,Object>> getBurningMAllCity(String start, String end, String status) {
        return burnPollutionDAO.getBurningMAllCity(start,end,status);
    }

    public void updateBurnPollutionService(String monthtime, String citycode, String cityname, String so2, String no2, String o3, String co, String pm2_5, String pm10, String status) {
        burnPollutionDAO.updateBurnPollution(monthtime,citycode,cityname,so2,no2,o3,co,pm2_5,pm10,status);
    }

    public int getDays(String start, String end,String citycode) {
        return burnPollutionDAO.getDays(start,end,citycode);
    }
}
