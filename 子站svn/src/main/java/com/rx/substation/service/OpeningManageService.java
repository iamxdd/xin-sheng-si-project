package com.rx.substation.service;

import com.rx.substation.dao.OpeningManageDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by dcx on 2017/8/31 0031.
 */
@Service
public class OpeningManageService {

    @Autowired
    OpeningManageDao openingManageDao;

    public List<Map<String,Object>> getOpeningManageData(String start, String end,String[] cityIds,String[] stationCodes,String sortstatus) {
        String citys = null;
        String stations = null;
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


        List<Map<String,Object>> OpeningManageData = openingManageDao.getOpeningManageData(start,end,citys,stations,sortstatus);


        return OpeningManageData;


    }
}
