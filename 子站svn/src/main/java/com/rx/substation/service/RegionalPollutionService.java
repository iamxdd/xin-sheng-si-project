package com.rx.substation.service;

import com.rx.substation.dao.RegionalPollutionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by dcx on 2017/6/19 0019.
 */
@Service
public class RegionalPollutionService {
    @Autowired
    RegionalPollutionDAO regionalPollutionDAO;

    public List<Map<String,Object>> getAllPollutionAea(String start, String end) {
        return regionalPollutionDAO.getAllPollutionAea(start,end);
    }

    public List<Map<String,Object>> getPollutionAea(String start, String end) {
        return regionalPollutionDAO.getPollutionAea(start,end);
    }


    public void saveAreaPollution(String monthtime, String status) {
        regionalPollutionDAO.saveAreaPollution(monthtime,status);
    }


    public int getAreaPollutionDays(String start, String end) {
        return regionalPollutionDAO.getAreaPollutionDays(start,end);
    }

    public int getNoAreaPollutionDays(String start, String end) {
        return regionalPollutionDAO.getNoAreaPollutionDays(start,end);
    }

    public List<Map<String,Object>> getNormalPollution(String start, String end) {
        return regionalPollutionDAO.getNormalPollution(start,end);
    }

    public List<Map<String,Object>> getPollutionAeaM(String start, String end) {
        return regionalPollutionDAO.getPollutionAeaM(start,end);
    }

    public void updateAreaPollution(String monthtime, String status) {
        regionalPollutionDAO.updateAreaPollution(monthtime,status);
    }

    public int getDays(String start, String end) {
        return regionalPollutionDAO.getDays(start,end);
    }
}
