package com.rx.substation.service;

import com.rx.substation.dao.DataVerifyLogDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by dcx on 2017/7/5 0005.
 */
@Service
public class DataVerifyLogService {
    @Autowired
    DataVerifyLogDAO dataVerifyLogDAO;


    /**
     * 查询时间内该站点的断电天数
     * @param start
     * @param end
     * @param stationCode
     * @return
     */
    public int getPowerCutDays(String start, String end, String stationCode) {
         return dataVerifyLogDAO.getPowerCutDays(start,end,stationCode);
    }

    /**
     * 查询时间内该站点的其他原因天数
     * @param start
     * @param end
     * @param stationCode
     * @return
     */
    public int getOtherDays(String start, String end, String stationCode) {
        return dataVerifyLogDAO.getOtherDays(start,end,stationCode);
    }
}
