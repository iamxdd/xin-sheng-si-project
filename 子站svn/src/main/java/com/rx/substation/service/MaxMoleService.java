package com.rx.substation.service;

import com.rx.substation.dao.MaxMoleDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by dcx on 2017/6/27 0027.
 */
@Service
public class MaxMoleService {
    @Autowired
    MaxMoleDAO maxMoleDAO;


    public Map<String,Object> getMaxMoleByTime(String start, String end,String param) {
        return  maxMoleDAO.getMaxMoleByTime(start,end,param);
    }

    public Map<String,Object> getMaxMoleByCity(String start, String end, String citycode,String param) {
        return  maxMoleDAO.getMaxMoleByCity(start,end,citycode,param);
    }
}
