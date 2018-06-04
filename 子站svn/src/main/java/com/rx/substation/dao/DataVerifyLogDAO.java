package com.rx.substation.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * Created by dcx on 2017/7/5 0005.
 */
public interface DataVerifyLogDAO {

    /**
     * 得到断电天数（每天4小时以上才判定为断电）
     * @param start
     * @param end
     * @param stationCode
     * @return
     */
    @Select("SELECT COUNT(*) powercutdays from (" +
            "SELECT dbo.GroupTime_day(TimePoint) timeday,SUM(CASE WHEN VerifyMark ='停电' THEN 1 ELSE 0 END) hours FROM DataVerifyLog dvl " +
            "where dvl.TimePoint >= #{start} and dvl.TimePoint <= #{end} and StationCode = #{stationCode} GROUP BY dbo.GroupTime_day(TimePoint) " +
            ") a WHERE a.hours >= 4")
    int getPowerCutDays(@Param("start") String start, @Param("end") String end, @Param("stationCode") String stationCode);

    /**
     * 得到时间段内每天断电小时数
     * @param start
     * @param end
     * @param stationCode
     * @return
     */
    @Select("SELECT dbo.GroupTime_day(TimePoint) timeday,SUM(CASE WHEN VerifyMark ='停电' THEN 1 ELSE 0 END) hours FROM DataVerifyLog dvl " +
            "where dvl.TimePoint >= #{start} and dvl.TimePoint <= #{end} and StationCode = #{stationCode} GROUP BY dbo.GroupTime_day(TimePoint) ")
    List<Map<String,Object>> getPowerCutHours(@Param("start") String start, @Param("end") String end, @Param("stationCode") String stationCode);

    /**
     * 得到其他原因天数
     * @param start
     * @param end
     * @param stationCode
     * @return
     */
    @Select("SELECT COUNT(*) otherdays from DataVerifyLog dvl WHERE dvl.TimePoint >= #{start} and dvl.TimePoint <= #{end} and StationCode = #{stationCode} and VerifyMark = '其他'")
    int getOtherDays(@Param("start") String start, @Param("end") String end, @Param("stationCode") String stationCode);
}
